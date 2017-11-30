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

    public List<Vinkki> haeKaikkiKirjat() throws SQLException {
        List<Vinkki> kirjavinkit = kirjavinkkiDao.haeKaikki();
        return kirjavinkit;
    }

    public int lisaaKirja(KirjaVinkki kirjaVinkki) throws SQLException {
        return kirjavinkkiDao.tallenna(kirjaVinkki);
    }

    public int lisaaPodcast(PodcastVinkki podcastVinkki) throws SQLException {
        return podcastvinkkiDao.tallenna(podcastVinkki);
    }

    public int lisaaVideo(VideoVinkki videoVinkki) throws SQLException {
        return videovinkkiDao.tallenna(videoVinkki);

    }

    public KirjaVinkki haeYksiKirja(int id) throws SQLException {
        KirjaVinkki vinkki = kirjavinkkiDao.haeYksi(id);
        return vinkki;
    }

    public boolean poistaKirja(int id) throws SQLException {
        return kirjavinkkiDao.poistaVinkki(id);
    }

    public boolean muokkaaKirja(KirjaVinkki vinkki) throws SQLException {
        return kirjavinkkiDao.muokkaa(vinkki);
    }
    
    public VideoVinkki haeYksiVideo(int id) throws SQLException {
        VideoVinkki vinkki = (VideoVinkki) videovinkkiDao.haeYksi(id);
        return vinkki;
    }
    
    public boolean poistaVideo(int id) throws SQLException {
        return videovinkkiDao.poistaVinkki(id);
    }
    
    public PodcastVinkki haeYksiPodcast(int id) throws SQLException {
        PodcastVinkki vinkki = (PodcastVinkki) podcastvinkkiDao.haeYksi(id);
        return vinkki;
    }
    
    public boolean poistaPodcast(int id) throws SQLException {
        return podcastvinkkiDao.poistaVinkki(id);
    }
}
