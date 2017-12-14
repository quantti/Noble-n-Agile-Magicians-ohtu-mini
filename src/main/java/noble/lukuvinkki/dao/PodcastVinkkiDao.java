package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class PodcastVinkkiDao implements Dao<PodcastVinkki> {

    private final Connection yhteys;

    public PodcastVinkkiDao(Tietokanta tietokanta) {
        this.yhteys = tietokanta.yhteys();
    }

    @Override
    public int tallenna(PodcastVinkki vinkki) throws SQLException {
        int id = -1;

        if (vinkki.getUrl().isEmpty() || vinkki.getNimi().isEmpty()) {
            return -1;
        }
        String sql = "INSERT INTO podcast_vinkki(podcastin_nimi, podcastin_url) VALUES (?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, vinkki.getNimi());
        kysely.setString(2, vinkki.getUrl());
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
    public PodcastVinkki haeYksi(int id) throws SQLException {
        String query = "SELECT * FROM podcast_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        if (rs.next()) {
            PodcastVinkki podcastVinkki = keraa(rs);
            return podcastVinkki;
        }
        return null;
    }

    private PodcastVinkki keraa(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nimi = rs.getString("podcastin_nimi");
        String url = rs.getString("podcastin_url");
        PodcastVinkki v = new PodcastVinkki(id, nimi, url);
        v.setTagit(haeTagit(v));
        return v;
    }

    @Override
    public List haeKaikki() throws SQLException {
        List<Vinkki> podcastVinkit = new ArrayList<>();
        String query = "SELECT * FROM podcast_vinkki";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            PodcastVinkki vinkki = new PodcastVinkki();
            vinkki.setId(rs.getInt("id"));
            vinkki.setNimi(rs.getString("podcastin_nimi"));
            vinkki.setUrl(rs.getString("podcastin_url"));
            podcastVinkit.add(vinkki);
        }
        return podcastVinkit;
    }

    @Override
    public boolean poistaVinkki(int id) throws SQLException {
        String sql = "DELETE FROM podcast_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        int tulos = kysely.executeUpdate();
        return tulos > 0;
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        PodcastVinkki podcastVinkki = (PodcastVinkki) vinkki;
        int id = podcastVinkki.getId();
        String nimi = podcastVinkki.getNimi();
        String url = podcastVinkki.getUrl();
        String sql = "UPDATE podcast_vinkki SET podcastin_nimi = ?, podcastin_url = ? WHERE id = ?";
        PreparedStatement paivitys = yhteys.prepareStatement(sql);
        paivitys.setString(1, nimi);
        paivitys.setString(2, url);
        paivitys.setInt(3, id);
        int tulos = paivitys.executeUpdate();
        return tulos > 0;
    }

    @Override
    public List<Vinkki> haeOtsikolla(String hakutermi) throws SQLException {
        List<Vinkki> vinkit = new ArrayList<>();
        String query = "SELECT * FROM podcast_vinkki WHERE podcastin_nimi LIKE ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setString(1, "%" + hakutermi + "%");
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            PodcastVinkki podcastVinkki = keraa(rs);
            vinkit.add(podcastVinkki);
        }
        return vinkit;
    }

    public List<Vinkki> haeTageilla(List<String> tagit) throws SQLException {
        if (tagit.isEmpty()) {
            return new ArrayList<>();
        }
        StringBuilder build = new StringBuilder("SELECT DISTINCT podcast_vinkki.* FROM podcast_vinkki,tagi,podcast_tagit"
                + " WHERE podcast_vinkki.id = podcast_tagit.podcast_id"
                + " AND tagi.id = podcast_tagit.tagi_id"
                + " AND tagi.tagin_nimi LIKE ?");
        for (int i = 1; i < tagit.size(); i++) { //ensimmäinen tagi on jo käytetty
            build.append(" INTERSECT SELECT DISTINCT podcast_vinkki.* FROM podcast_vinkki,tagi,podcast_tagit"
                    + " WHERE podcast_vinkki.id = podcast_tagit.podcast_id"
                    + " AND tagi.id = podcast_tagit.tagi_id"
                    + " AND tagi.tagin_nimi LIKE ?");
        }
        PreparedStatement st = yhteys.prepareStatement(build.toString());
        for (int i = 1; i <= tagit.size(); i++) {
            st.setString(i, tagit.get(i - 1));
        }
        ResultSet rs = st.executeQuery();
        List<Vinkki> vinkit = new ArrayList<>();
        while (rs.next()) {
            PodcastVinkki v = keraa(rs);
            vinkit.add(v);
        }
        return vinkit;
    }

    private List<String> haeTagit(PodcastVinkki vinkki) throws SQLException {
        List<String> tagit = new ArrayList<>();
        String sql = "SELECT tagi.*"
                + " FROM podcast_vinkki, podcast_tagit, tagi"
                + " WHERE podcast_vinkki.id = ?"
                + " AND podcast_tagit.podcast_id = podcast_vinkki.id"
                + " AND podcast_tagit.tagi_id = tagi.id";
        PreparedStatement st = yhteys.prepareStatement(sql);
        st.setInt(1, vinkki.getId());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            tagit.add(rs.getString("tagin_nimi"));
        }
        return tagit;
    }

    private void tallennaTagit(PodcastVinkki vinkki) throws SQLException {
        for (String tagi : vinkki.getTagit()) {
            String sql = "INSERT OR IGNORE INTO tagi(tagin_nimi) VALUES (?)";
            PreparedStatement st = yhteys.prepareStatement(sql);
            st.setString(1, tagi);
            st.executeUpdate();
        }
        for (String tagi : vinkki.getTagit()) {
            String sql = "INSERT INTO podcast_tagit(podcast_id, tagi_id)"
                    + " SELECT ?, tagi.id FROM tagi WHERE tagi.tagin_nimi LIKE ?";
            PreparedStatement st = yhteys.prepareStatement(sql);
            st.setInt(1, vinkki.getId());
            st.setString(2, tagi);
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
