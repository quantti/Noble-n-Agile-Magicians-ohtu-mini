
package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;


public class PodcastVinkkiDao implements Dao<PodcastVinkki> {

    private Tietokanta tietokanta;
    private Connection yhteys;
    
    public PodcastVinkkiDao(Tietokanta tietokanta){
        this.tietokanta = tietokanta;
        this.yhteys = tietokanta.yhteys();
    }
    
    @Override
    public int tallenna(PodcastVinkki vinkki) throws SQLException {
        int id = -1;
        String sql = String.format("INSERT INTO podcast_vinkki(podcastin_nimi, podcastin_url) VALUES ('%s', '%s')", vinkki.getNimi(), vinkki.getUrl());
        Statement kysely = yhteys.createStatement();
        kysely.execute(sql);
        ResultSet rs = kysely.executeQuery("SELECT last_insert_rowid() as id");
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    @Override
    public PodcastVinkki haeYksi(String id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
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
