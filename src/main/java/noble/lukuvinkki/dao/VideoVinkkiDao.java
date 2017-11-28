
package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import noble.lukuvinkki.tietokohteet.VideoVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class VideoVinkkiDao implements Dao<VideoVinkki> {

    private Tietokanta tietokanta;
    private Connection yhteys;
    
    public VideoVinkkiDao(Tietokanta tietokanta){
        this.tietokanta = tietokanta;
        this.yhteys = tietokanta.yhteys();
    }
    
     @Override
    public int tallenna(VideoVinkki vinkki) throws SQLException {
        int id = -1;
        String sql = String.format("INSERT INTO video_vinkki(videon_nimi, videon_url) VALUES ('%s', '%s')", vinkki.getNimi(), vinkki.getUrl());
        Statement kysely = yhteys.createStatement();
        kysely.execute(sql);
        ResultSet rs = kysely.executeQuery("SELECT last_insert_rowid() as id");
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    @Override
    public Vinkki haeYksi(String id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Vinkki> haeKaikki() throws SQLException {
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
