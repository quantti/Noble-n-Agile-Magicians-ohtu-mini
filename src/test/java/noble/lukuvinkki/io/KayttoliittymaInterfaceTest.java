/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.io;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.TietokantaSetup;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.VideoVinkki;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kari
 */
public class KayttoliittymaInterfaceTest {

    private KayttoliittymaInterface kayttisIO;
    private Tietokanta tietokanta;

    @Before
    public void setUp() {
        tietokanta = TietokantaSetup.alustaTestiTietokanta("jdbc:sqlite:tietokanta/testaus.sqlite3");
        kayttisIO = new KayttoliittymaInterface(tietokanta);
    }

    @After
    public void tearDown() {
        try {
            tietokanta.suljeYhteys();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void testaaKayttisIO() {
        assertTrue(kayttisIO != null);
    }

    @Test
    public void testaaKirjaVinkinLisaysHakuJaPoistoTietokannasta() throws SQLException {
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setTekija("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        assertTrue(id != -1);
        Vinkki kirjaVinkki2 = kayttisIO.haeYksiKirja(id);
        assertEquals(kirjaVinkki.getNimi(), kirjaVinkki2.getNimi());
        assertEquals(kirjaVinkki.getTekija(), kirjaVinkki2.getTekija());
        boolean poisto = kayttisIO.poistaKirja(id);
        assertTrue(poisto);
        assertTrue(kayttisIO.haeYksiKirja(id) == null);
    }

    @Test
    public void testaaHaeKaikkiKirjat() throws SQLException {
        List<Vinkki> kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(0, kirjat.size());
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setTekija("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(1, kirjat.size());
        kayttisIO.poistaKirja(id);
        kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(0, kirjat.size());
    }

    @Test
    public void testaaHaeKaikkiVideot() throws SQLException {
        List<Vinkki> videot = kayttisIO.haeKaikkiVideot();
        assertEquals(0, videot.size());
        VideoVinkki videoVinkki = new VideoVinkki();
        videoVinkki.setNimi("Video");
        videoVinkki.setUrl("www.google.com");
        int id = kayttisIO.lisaaVideo(videoVinkki);
        videot = kayttisIO.haeKaikkiVideot();
        assertEquals(1, videot.size());
        assertEquals("Video", videot.get(0).getNimi());
        kayttisIO.poistaVideo(id);
        videot = kayttisIO.haeKaikkiVideot();
        assertEquals(0, videot.size());
    }

    @Test
    public void testaaHaeKaikki() throws SQLException {
        List<Vinkki> kaikki = kayttisIO.haeKaikkiVinkit();
        assertEquals(0, kaikki.size());
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setTekija("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        kayttisIO.lisaaKirja(kirjaVinkki);
        VideoVinkki videoVinkki = new VideoVinkki();
        videoVinkki.setNimi("Video");
        videoVinkki.setUrl("www.google.com");
        kayttisIO.lisaaVideo(videoVinkki);
        PodcastVinkki podcastVinkki = new PodcastVinkki();
        podcastVinkki.setNimi("podcast");
        podcastVinkki.setUrl("www.podcast.com");
        kayttisIO.lisaaPodcast(podcastVinkki);
        kaikki = kayttisIO.haeKaikkiVinkit();
        assertEquals(3, kaikki.size());
        assertEquals("Jäniksen vuosi", kaikki.get(0).getNimi());
        assertEquals("podcast", kaikki.get(1).getNimi());
        assertEquals("Video", kaikki.get(2).getNimi());
    }

    @Test
    public void testaaHaeKaikkiPodcastit() throws SQLException {
        List<Vinkki> podcastit = kayttisIO.haeKaikkiPodcastit();
        assertEquals(0, podcastit.size());
        PodcastVinkki podcastVinkki = new PodcastVinkki();
        podcastVinkki.setNimi("podcast");
        podcastVinkki.setUrl("www.podcast.com");
        int id = kayttisIO.lisaaPodcast(podcastVinkki);
        podcastit = kayttisIO.haeKaikkiPodcastit();
        assertEquals(1, podcastit.size());
        assertEquals("podcast", podcastit.get(0).getNimi());
        kayttisIO.poistaPodcast(id);
        podcastit = kayttisIO.haeKaikkiPodcastit();
        assertEquals(0, podcastit.size());
    }

    @Test
    public void testaaMuokkaaKirjaa() throws SQLException {
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setTekija("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        KirjaVinkki kirjaVinkki2 = new KirjaVinkki(id, "Seitsemän veljestä", "Aleksis Kivi");
        boolean muokkaus = kayttisIO.muokkaaKirja(kirjaVinkki2);
        assertTrue(muokkaus);
        KirjaVinkki kirjavinkki3 = kayttisIO.haeYksiKirja(id);
        assertEquals("Seitsemän veljestä", kirjavinkki3.getNimi());
        assertEquals("Aleksis Kivi", kirjavinkki3.getTekija());
    }

    @Test
    public void testaaVideonLisaysJaPoisto() throws SQLException {
        VideoVinkki videoVinkki = new VideoVinkki();
        videoVinkki.setNimi("Video");
        videoVinkki.setUrl("www.youtube.com");
        int id = kayttisIO.lisaaVideo(videoVinkki);
        assertTrue(id != -1);
        VideoVinkki vinkki2 = kayttisIO.haeYksiVideo(id);
        assertEquals(videoVinkki.getNimi(), vinkki2.getNimi());
        assertEquals(videoVinkki.getUrl(), vinkki2.getUrl());
        boolean poisto = kayttisIO.poistaVideo(id);
        assertTrue(poisto);
        assertTrue(kayttisIO.haeYksiVideo(id) == null);
    }

    @Test
    public void testaaPodcastinLisaysJaPoisto() throws SQLException {
        PodcastVinkki podcastVinkki = new PodcastVinkki();
        podcastVinkki.setNimi("Podcast");
        podcastVinkki.setUrl("www.podcast.com");
        int id = kayttisIO.lisaaPodcast(podcastVinkki);
        assertTrue(id != -1);
        PodcastVinkki vinkki2 = kayttisIO.haeYksiPodcast(id);
        assertEquals(podcastVinkki.getNimi(), vinkki2.getNimi());
        assertEquals(podcastVinkki.getUrl(), vinkki2.getUrl());
        boolean poisto = kayttisIO.poistaPodcast(id);
        assertTrue(poisto);
        assertTrue(kayttisIO.haeYksiPodcast(id) == null);
    }

}
