package noble.lukuvinkki.dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;

import noble.lukuvinkki.tietokohteet.VideoVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class VideoVinkkiDao implements Dao<VideoVinkki> {

    private final Connection yhteys;

    public VideoVinkkiDao(Tietokanta tietokanta) {
        this.yhteys = tietokanta.yhteys();
    }

    @Override
    public int tallenna(VideoVinkki vinkki) throws SQLException {
        int id = -1;

        if (vinkki.getUrl().isEmpty() || vinkki.getNimi().isEmpty()) {
            return -1;
        }

        String sql = "INSERT INTO video_vinkki(videon_nimi, videon_url) VALUES (?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, vinkki.getNimi());
        kysely.setString(2, vinkki.getUrl());
        kysely.executeUpdate();
        ResultSet rs = yhteys.createStatement().executeQuery("SELECT last_insert_rowid() as id");
        if (rs.next()) {
            id = rs.getInt("id");
        }
        tallennaTagit(vinkki);
        return id;
    }

    @Override
    public VideoVinkki haeYksi(int id) throws SQLException {
        String query = "SELECT * FROM video_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        if (rs.next()) {
            VideoVinkki videoVinkki = keraa(rs);
            return videoVinkki;
        }
        return null;
    }

    private VideoVinkki keraa(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nimi = rs.getString("videon_nimi");
        String url = rs.getString("videon_url");
        return new VideoVinkki(id, nimi, url);
    }

    @Override
    public List<Vinkki> haeKaikki() throws SQLException {
        List<Vinkki> videoVinkit = new ArrayList<>();
        String query = "SELECT * FROM video_vinkki";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            VideoVinkki vinkki = keraa(rs);
            videoVinkit.add(vinkki);
        }
        return videoVinkit;
    }

    @Override
    public boolean poistaVinkki(int id) throws SQLException {
        String sql = "DELETE FROM video_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        int tulos = kysely.executeUpdate();
        return tulos > 0;
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        VideoVinkki videoVinkki = (VideoVinkki) vinkki;
        int id = videoVinkki.getId();
        String nimi = videoVinkki.getNimi();
        String url = videoVinkki.getUrl();
        String sql = "UPDATE video_vinkki SET videon_nimi = ?, videon_url = ? WHERE id = ?";
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
        String query = "SELECT * FROM video_vinkki WHERE videon_nimi LIKE ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setString(1, "%" + hakutermi + "%");
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            VideoVinkki vinkki = keraa(rs);
            vinkit.add(vinkki);
        }
        return vinkit;
    }

    public List<Vinkki> haeTageilla(List<String> tagit) throws SQLException {
        StringBuilder build = new StringBuilder("SELECT video_vinkki.* FROM video_vinkki,tagi,video_tagit"
                + " WHERE video_vinkki.id = video_tagit.video_id"
                + " AND tagi.id = video_tagit.tagi_id"
                + " AND tagi.tagin_nimi IN (");
        for (int i = 0; i < tagit.size(); i++) {
            build.append("?, ");
        }
        build.replace(build.length() - 2, build.length(), ")");
        PreparedStatement st = yhteys.prepareStatement(build.toString());
        for (int i = 1; i <= tagit.size(); i++) {
            st.setString(i, tagit.get(i - 1));
        }
        ResultSet rs = st.executeQuery();
        List<Vinkki> vinkit = new ArrayList<>();
        while (rs.next()) {
            VideoVinkki v = keraa(rs);
            vinkit.add(v);
        }
        return vinkit;
    }

    private List<String> haeTagit(VideoVinkki vinkki) throws SQLException {
        List<String> tagit = new ArrayList<>();
        String sql = "SELECT tagi.*"
                + " FROM video_vinkki, video_tagit, tagi"
                + " WHERE video_vinkki.id = ?"
                + " AND video_tagit.video_id = video_vinkki.id"
                + " AND video_tagit.tagi_id = tagi.id";
        PreparedStatement st = yhteys.prepareStatement(sql);
        st.setInt(1, vinkki.getId());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            tagit.add(rs.getString("tagin_nimi"));
        }
        return tagit;
    }

    private void tallennaTagit(VideoVinkki vinkki) throws SQLException {
        for (String s : vinkki.getTagit()) {
            String sql = "INSERT INTO tagi(tagin_nimi) VALUES (?)";
            PreparedStatement st = yhteys.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
        }
        for (String s : vinkki.getTagit()) {
            String sql = "INSERT OR IGNORE INTO video_tagit(video_id, tagi_id)"
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
