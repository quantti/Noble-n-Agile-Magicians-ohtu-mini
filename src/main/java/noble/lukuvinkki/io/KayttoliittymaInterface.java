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
import noble.lukuvinkki.dao.PodcastVinkkiDao;
import noble.lukuvinkki.dao.VideoVinkkiDao;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.VideoVinkki;

/**
 *
 * @author kari
 */
public class KayttoliittymaInterface {

    private final KirjaVinkkiDao kirjavinkkiDao;
    private final Dao podcastvinkkiDao;
    private final Dao videovinkkiDao;

    public KayttoliittymaInterface(Tietokanta tietokanta) {
        this.kirjavinkkiDao = new KirjaVinkkiDao(tietokanta);
        this.podcastvinkkiDao = new PodcastVinkkiDao(tietokanta);
        this.videovinkkiDao = new VideoVinkkiDao(tietokanta);
    }

    public List<KirjaVinkki> haeKaikkiKirjat() throws SQLException {
        List<KirjaVinkki> kirjavinkit = kirjavinkkiDao.haeKaikki();
        return kirjavinkit;
    }

    public int lisaaKirja(KirjaVinkki kirjaVinkki) throws SQLException {
        return kirjavinkkiDao.tallenna(kirjaVinkki);
    }

    public boolean lisaaPodcast(PodcastVinkki podcastVinkki) throws SQLException {
        return podcastvinkkiDao.tallenna(podcastVinkki) != -1;
    }

    public boolean lisaaVideo(VideoVinkki videoVinkki) throws SQLException {
        return videovinkkiDao.tallenna(videoVinkki) != -1;

    }

    public KirjaVinkki haeYksiKirja(String id) throws SQLException {
        KirjaVinkki vinkki = kirjavinkkiDao.haeYksi(id);
        return vinkki;
    }

    public boolean poistaKirja(String id) throws SQLException {
        return kirjavinkkiDao.poistaVinkki(id);
    }

    public boolean muokkaaKirja(KirjaVinkki vinkki) throws SQLException {
        return kirjavinkkiDao.muokkaa(vinkki);
    }

}
