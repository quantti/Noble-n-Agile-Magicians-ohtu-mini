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

/**
 *
 * @author kari
 */
public class KayttoliittymaIO implements IO {

    @Override
    public List<Vinkki> haeKaikkiVinkit() {
        return null;
    }

    @Override
    public void lisaaVinkki(KirjaVinkki kirjaVinkki) {
        Tietokanta kanta = new Tietokanta();
        Dao kirjaVinkkiDao = new KirjaVinkkiDao(kanta);
        kirjaVinkkiDao.tallenna(kirjaVinkki);
        System.out.println("Lisätty! (ei oikeasti, tässä vasta testaillaan)");
    }

    @Override
    public Vinkki haeYksiVinkki(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void poistaVinkki(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void muokkaa(Vinkki vinkki) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
