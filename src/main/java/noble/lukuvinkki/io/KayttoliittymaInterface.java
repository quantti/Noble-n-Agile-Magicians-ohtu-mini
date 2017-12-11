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
import java.util.ArrayList;
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

    public int lisaaKirja(KirjaVinkki kirjaVinkki) throws SQLException {
        return kirjavinkkiDao.tallenna(kirjaVinkki);
    }

    public KirjaVinkki haeYksiKirja(int id) throws SQLException {
        KirjaVinkki vinkki = kirjavinkkiDao.haeYksi(id);
        return vinkki;
    }

    public List<Vinkki> haeKaikkiKirjat() throws SQLException {
        List<Vinkki> kirjavinkit = kirjavinkkiDao.haeKaikki();
        return kirjavinkit;
    }

    public boolean poistaKirja(int id) throws SQLException {
        return kirjavinkkiDao.poistaVinkki(id);
    }

    public boolean muokkaaKirjaa(KirjaVinkki vinkki) throws SQLException {
        return kirjavinkkiDao.muokkaa(vinkki);
    }
    
    public List<Vinkki> haeKirjaaOtsikolla(String hakutermi) throws SQLException {
        return kirjavinkkiDao.haeOtsikolla(hakutermi);
    }

    public int lisaaVideo(VideoVinkki videoVinkki) throws SQLException {
        return videovinkkiDao.tallenna(videoVinkki);

    }

    public VideoVinkki haeYksiVideo(int id) throws SQLException {
        VideoVinkki vinkki = (VideoVinkki) videovinkkiDao.haeYksi(id);
        return vinkki;
    }

    public List<Vinkki> haeKaikkiVideot() throws SQLException {
        List<Vinkki> videoVinkit = videovinkkiDao.haeKaikki();
        return videoVinkit;
    }

    public boolean poistaVideo(int id) throws SQLException {
        return videovinkkiDao.poistaVinkki(id);
    }
    
    public boolean muokkaaVideota(VideoVinkki vinkki) throws SQLException {
        return videovinkkiDao.muokkaa(vinkki);
    }
     
    public List<Vinkki> haeVideotaOtsikolla(String hakutermi) throws SQLException {
        return videovinkkiDao.haeOtsikolla(hakutermi);
    }

    public int lisaaPodcast(PodcastVinkki podcastVinkki) throws SQLException {
        return podcastvinkkiDao.tallenna(podcastVinkki);
    }

    public PodcastVinkki haeYksiPodcast(int id) throws SQLException {
        PodcastVinkki vinkki = (PodcastVinkki) podcastvinkkiDao.haeYksi(id);
        return vinkki;
    }

    public List<Vinkki> haeKaikkiPodcastit() throws SQLException {
        List<Vinkki> podcastVinkit = podcastvinkkiDao.haeKaikki();
        return podcastVinkit;
    }

    public boolean poistaPodcast(int id) throws SQLException {
        return podcastvinkkiDao.poistaVinkki(id);
    }
    
    public boolean muokkaaPodcastia(PodcastVinkki vinkki) throws SQLException {
        return podcastvinkkiDao.muokkaa(vinkki);
    }
    
    public List<Vinkki> haePodcastiaOtsikolla(String hakutermi) throws SQLException {
        return podcastvinkkiDao.haeOtsikolla(hakutermi);
    }

    public List<Vinkki> haeKaikkiVinkit() throws SQLException {
        List<Vinkki> kaikkiVinkit = new ArrayList<>();
        kaikkiVinkit.addAll(this.haeKaikkiKirjat());
        kaikkiVinkit.addAll(this.haeKaikkiPodcastit());
        kaikkiVinkit.addAll(this.haeKaikkiVideot());
        return kaikkiVinkit;
    }
    
    public List<Vinkki> haeKaikkiaOtsikolla(String hakutermi) throws SQLException {
        List<Vinkki> kaikkiVinkit = new ArrayList<>();
        kaikkiVinkit.addAll(haeKirjaaOtsikolla(hakutermi));
        kaikkiVinkit.addAll(haePodcastiaOtsikolla(hakutermi));
        kaikkiVinkit.addAll(haeVideotaOtsikolla(hakutermi));
        return kaikkiVinkit;
    }
    
    public void avaaPodcast(PodcastVinkki vinkki) throws Exception {
        UrlinAvaaja avaaja = new UrlinAvaaja(vinkki.getUrl());
        avaaja.avaa();
    }
    
    public void avaaVideo(VideoVinkki vinkki) throws Exception {
        UrlinAvaaja avaaja = new UrlinAvaaja(vinkki.getUrl());
        avaaja.avaa();
    }
}
