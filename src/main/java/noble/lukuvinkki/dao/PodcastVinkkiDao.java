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
        String sql = "INSERT INTO podcast_vinkki(podcastin_nimi, podcastin_url) VALUES (?, ?)";
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
        return new PodcastVinkki(id, nimi, url);
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
        int result = kysely.executeUpdate();
        return result > 0;
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
