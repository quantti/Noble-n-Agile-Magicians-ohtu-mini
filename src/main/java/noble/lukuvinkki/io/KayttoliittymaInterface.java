/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.io;

import java.util.List;
import noble.lukuvinkki.dao.Dao;
import noble.lukuvinkki.dao.KirjaVinkkiDao;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;
import java.sql.SQLException;

/**
 *
 * @author kari
 */
public class KayttoliittymaInterface {
    private Dao kirjavinkkiDao;
    
    public KayttoliittymaInterface(Tietokanta tietokanta) {
        this.kirjavinkkiDao = new KirjaVinkkiDao(tietokanta);
    }

    public List<Vinkki> haeKaikkiVinkit() throws SQLException {
        List<Vinkki> kirjavinkit = kirjavinkkiDao.haeKaikki();
        return kirjavinkit;
    }

    public boolean lisaaVinkki(KirjaVinkki kirjaVinkki) throws SQLException {
        if (kirjavinkkiDao.tallenna(kirjaVinkki) != -1) {
            return true;
        }
        return false;

    }

    public Vinkki haeYksiVinkki(String id) throws SQLException {
        Vinkki vinkki = kirjavinkkiDao.haeYksi(id);
        return vinkki;
    }

    public boolean poistaVinkki(String id) throws SQLException {
        return kirjavinkkiDao.poistaVinkki(id);
    }

    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        return kirjavinkkiDao.muokkaa(vinkki);
    }

}
