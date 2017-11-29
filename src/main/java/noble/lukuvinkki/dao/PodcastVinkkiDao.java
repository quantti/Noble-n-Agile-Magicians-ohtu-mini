
package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    
    public PodcastVinkki haeYksi(String id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List haeKaikki() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean poistaVinkki(String id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
