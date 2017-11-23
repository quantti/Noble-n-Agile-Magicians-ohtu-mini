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
public class KayttoliittymaInterface {
    private Dao kirjavinkkiDao;
    
    public KayttoliittymaInterface(Tietokanta tietokanta) {
        this.kirjavinkkiDao = new KirjaVinkkiDao(tietokanta);
    }

    // Lisäsin Tietokanta.java:n suljeYhteys -komennon ja sen näihin metodeihin -Jaakko
    public List<Vinkki> haeKaikkiVinkit() {
        List<Vinkki> kirjavinkit = kirjavinkkiDao.haeKaikki();
        return kirjavinkit;
    }

    public void lisaaVinkki(KirjaVinkki kirjaVinkki) {
        kirjavinkkiDao.tallenna(kirjaVinkki);
        System.out.println("Lisätty!");
    }

    public Vinkki haeYksiVinkki(String id) {
        Vinkki vinkki = kirjavinkkiDao.haeYksi(id);
        return vinkki;
    }

    public void poistaVinkki(String id) {
        kirjavinkkiDao.poistaVinkki(id);
    }

    public void muokkaa(Vinkki vinkki) {
        kirjavinkkiDao.muokkaa(vinkki);
    }

}
