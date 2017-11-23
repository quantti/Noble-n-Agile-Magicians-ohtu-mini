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
public class KayttoliittymaInterface implements IO {

    // Lisäsin Tietokanta.java:n suljeYhteys -komennon ja sen näihin metodeihin -Jaakko
    @Override
    public List<Vinkki> haeKaikkiVinkit() {
        Tietokanta kanta = new Tietokanta();
        Dao kirjavinkkiDao = new KirjaVinkkiDao(kanta);
        List<Vinkki> kirjavinkit = kirjavinkkiDao.haeKaikki();
        kanta.suljeYhteys();
        return kirjavinkit;
    }

    @Override
    public void lisaaVinkki(KirjaVinkki kirjaVinkki) {
        Tietokanta kanta = new Tietokanta();
        Dao kirjavinkkiDao = new KirjaVinkkiDao(kanta);
        kirjavinkkiDao.tallenna(kirjaVinkki);
        kanta.suljeYhteys();
        System.out.println("Lisätty!");
    }

    @Override
    public Vinkki haeYksiVinkki(String id) {
        Tietokanta kanta = new Tietokanta();
        Dao kirjavinkkiDao = new KirjaVinkkiDao(kanta);
        Vinkki vinkki = kirjavinkkiDao.haeYksi(id);
        kanta.suljeYhteys();
        return vinkki;
    }

    @Override
    public void poistaVinkki(String id) {
        Tietokanta kanta = new Tietokanta();
        Dao kirjavinkkiDao = new KirjaVinkkiDao(kanta);
        kirjavinkkiDao.poistaVinkki(id);
        kanta.suljeYhteys();
    }

    @Override
    public void muokkaa(Vinkki vinkki) {
        Tietokanta kanta = new Tietokanta();
        Dao kirjavinkkiDao = new KirjaVinkkiDao(kanta);
        kirjavinkkiDao.muokkaa(vinkki);
        kanta.suljeYhteys();
    }

}
