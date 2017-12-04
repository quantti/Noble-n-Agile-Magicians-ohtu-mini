package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        String sql = "INSERT INTO video_vinkki(videon_nimi, videon_url) VALUES (?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, vinkki.getNimi());
        kysely.setString(2, vinkki.getUrl());
        kysely.executeUpdate();
        ResultSet rs = yhteys.createStatement().executeQuery("SELECT last_insert_rowid() as id");
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    @Override
    public VideoVinkki haeYksi(int id) throws SQLException {
        String query = "SELECT * FROM video_vinkki WHERE id = ?";
        PreparedStatement preparedStatement = yhteys.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
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
        PreparedStatement preparedStatement = yhteys.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            VideoVinkki vinkki = new VideoVinkki();
            vinkki.setId(rs.getInt("id"));
            vinkki.setNimi(rs.getString("videon_nimi"));
            vinkki.setUrl(rs.getString("videon_url"));
            videoVinkit.add(vinkki);
        }
        return videoVinkit;
    }

    @Override
    public boolean poistaVinkki(int id) throws SQLException {
        String sql = "DELETE FROM video_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        int result = kysely.executeUpdate();
        return result > 0;
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
