package noble.lukuvinkki.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class KirjaVinkkiDao implements Dao<KirjaVinkki> {

    private final Connection yhteys;

    public KirjaVinkkiDao(Tietokanta tietokanta) {
        this.yhteys = tietokanta.yhteys();

    }

    private KirjaVinkki keraa(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nimi = rs.getString("kirjan_nimi");
        String kirjoittaja = rs.getString("kirjan_kirjoittaja");
        KirjaVinkki vinkki = new KirjaVinkki(id, nimi, kirjoittaja);
        vinkki.setTagit(haeTagit(vinkki));
        return vinkki;
    }

    @Override
    public int tallenna(KirjaVinkki vinkki) throws SQLException {
        int id = -1;

        if (vinkki.getTekija().isEmpty() || vinkki.getNimi().isEmpty()) {
            return -1;
        }

        String sql = "INSERT INTO kirja_vinkki(kirjan_nimi, kirjan_kirjoittaja) VALUES (?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, vinkki.getNimi());
        kysely.setString(2, vinkki.getTekija());
        kysely.executeUpdate();
        ResultSet rs = yhteys.createStatement().executeQuery("SELECT last_insert_rowid() as id");

        if (rs.next()) {
            id = rs.getInt("id");
        }
        vinkki.setId(id);
        tallennaTagit(vinkki);
        return id;
    }

    @Override
    public KirjaVinkki haeYksi(int id) throws SQLException {
        String query = "SELECT * FROM kirja_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();

        if (rs.next()) {
            KirjaVinkki vinkki = keraa(rs);
            return vinkki;
        }
        return null;
    }

    @Override
    public List<Vinkki> haeKaikki() throws SQLException {
        List<Vinkki> kirjavinkit = new ArrayList<>();
        String query = "SELECT * FROM kirja_vinkki";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            KirjaVinkki vinkki = keraa(rs);
            kirjavinkit.add(vinkki);
        }
        return kirjavinkit;
    }

    @Override
    public boolean poistaVinkki(int id) throws SQLException {
        String sql = "DELETE FROM kirja_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        int tulos = kysely.executeUpdate();
        return tulos > 0;

    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        int id = vinkki.getId();
        String nimi = vinkki.getNimi();
        String kirjoittaja = ((KirjaVinkki) vinkki).getTekija();
        String sql = "UPDATE kirja_vinkki SET kirjan_nimi = ?, kirjan_kirjoittaja = ? WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, nimi);
        kysely.setString(2, kirjoittaja);
        kysely.setInt(3, id);
        int tulos = kysely.executeUpdate();
        return tulos > 0;
    }

    @Override
    public List<Vinkki> haeOtsikolla(String hakutermi) throws SQLException {
        List<Vinkki> vinkit = new ArrayList<>();
        String query = "SELECT * FROM kirja_vinkki WHERE kirjan_nimi LIKE ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setString(1, "%" + hakutermi + "%");
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            KirjaVinkki vinkki = keraa(rs);
            vinkit.add(vinkki);
        }
        return vinkit;
    }

    public List<Vinkki> haeTageilla(List<String> tagit) throws SQLException {
        String sql = "SELECT kirja_vinkki.* FROM kirja_vinkki,tagi,kirja_tagit"
                + " WHERE kirja_vinkki.id = kirja_tagit.kirja_id"
                + " AND tagi.id = kirja_tagit.tagi_id"
                + " AND tagi.tagin_nimi IN (";
        StringBuilder build = new StringBuilder(sql);
        for (int i = 0; i < tagit.size(); i++) {
            build.append("?, ");
        }
        build.replace(build.length() - 1, build.length(), ")");
        PreparedStatement st = yhteys.prepareStatement(sql);
        for (int i = 1; i <= tagit.size(); i++) {
            st.setString(i, tagit.get(i - 1));
        }
        ResultSet rs = st.executeQuery();
        List<Vinkki> vinkit = new ArrayList<>();
        while (rs.next()) {
            KirjaVinkki v = keraa(rs);
            vinkit.add(v);
        }
        return vinkit;
    }

    private List<String> haeTagit(KirjaVinkki vinkki) throws SQLException {
        List<String> tagit = new ArrayList<>();
        String sql = "SELECT tagi.*"
                + " FROM kirja_vinkki, kirja_tagit, tagi"
                + " WHERE kirja_vinkki.id = ?"
                + " AND kirja_tagit.kirja_id = kirja_vinkki.id"
                + " AND kirja_tagit.tagi_id = tagi.id";
        PreparedStatement st = yhteys.prepareStatement(sql);
        st.setInt(1, vinkki.getId());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            tagit.add(rs.getString("tagin_nimi"));
        }
        return tagit;
    }

    private void tallennaTagit(KirjaVinkki vinkki) throws SQLException {
        for (String s : vinkki.getTagit()) {
            String sql = "INSERT INTO tagi(tagin_nimi) VALUES (?)";
            PreparedStatement st = yhteys.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
        }
        for (String s : vinkki.getTagit()) {
            String sql = "INSERT INTO kirja_tagit(kirja_id, tagi_id)"
                    + " SELECT ?, tagi.id FROM tagi WHERE tagi.tagin_nimi LIKE ?";
            PreparedStatement st = yhteys.prepareStatement(sql);
            st.setInt(1, vinkki.getId());
            st.setString(2, s);
            st.executeUpdate();
        }
    }

    private void poistaOrvotTagit() throws SQLException {
        String sql = "DELETE FROM tagi WHERE tagi.id NOT IN"
                + " (SELECT kirja_tagit.tagi_id FROM kirja_tagit"
                + " UNION SELECT podcast_tagit.tagi_id FROM podcast_tagit"
                + " UNION SELECT video_tagit.tagi_id FROM video_tagit)";
        yhteys.createStatement().execute(sql);
    }
}
