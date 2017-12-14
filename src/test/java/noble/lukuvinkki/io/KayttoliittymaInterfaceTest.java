/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.io;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import noble.lukuvinkki.TietokantaSetup;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.tietokohteet.BlogiVinkki;
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
        boolean muokkaus = kayttisIO.muokkaaKirjaa(kirjaVinkki2);
        assertTrue(muokkaus);
        KirjaVinkki kirjavinkki3 = kayttisIO.haeYksiKirja(id);
        assertEquals("Seitsemän veljestä", kirjavinkki3.getNimi());
        assertEquals("Aleksis Kivi", kirjavinkki3.getTekija());
    }

    @Test
    public void testaaMuokkaaVideota() throws SQLException {
        VideoVinkki vinkki = new VideoVinkki(0, "joku", "amazon.fi");
        int id = kayttisIO.lisaaVideo(vinkki);
        VideoVinkki vinkki2 = new VideoVinkki(id, "jotain", "www.yahoo.com");
        boolean muokkaus = kayttisIO.muokkaaVideota(vinkki2);
        assertTrue(muokkaus);
        VideoVinkki vinkki3 = kayttisIO.haeYksiVideo(id);
        assertEquals("jotain", vinkki3.getNimi());
        assertEquals("www.yahoo.com", vinkki3.getUrl());
    }

    @Test
    public void testaaMuokkaaPodcastia() throws SQLException {
        PodcastVinkki vinkki = new PodcastVinkki(0, "podi", "www.podi.fi");
        int id = kayttisIO.lisaaPodcast(vinkki);
        PodcastVinkki vinkki2 = new PodcastVinkki(id, "uusiPodi", "www.podi2.fi");
        boolean muokkaus = kayttisIO.muokkaaPodcastia(vinkki2);
        assertTrue(muokkaus);
        PodcastVinkki vinkki3 = kayttisIO.haeYksiPodcast(id);
        assertEquals("www.podi2.fi", vinkki3.getUrl());
        assertEquals("uusiPodi", vinkki3.getNimi());
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

    @Test
    public void testaaHaeKaikkiaOtsikolla() throws SQLException {
        List<Vinkki> kaikki = kayttisIO.haeKaikkiVinkit();
        assertEquals(0, kaikki.size());
        kayttisIO.lisaaKirja(new KirjaVinkki(1, "Tuntematon sotilas", "Väinö Linna"));
        kayttisIO.lisaaPodcast(new PodcastVinkki(1, "Koodisotilas", "www.google.fi"));
        kayttisIO.lisaaVideo(new VideoVinkki(1, "Tuntematon uhka", "www.youtube.com"));
        kayttisIO.lisaaBlogi(new BlogiVinkki(1, "Tuntematon Bloki", "www.timosoini.fi"));
        List<Vinkki> vinkit = kayttisIO.haeKaikkiaOtsikolla("Tuntematon");
        assertEquals(3, vinkit.size());
        vinkit = kayttisIO.haeKaikkiaOtsikolla("sotilas");
        assertEquals(2, vinkit.size());
        vinkit = kayttisIO.haeKaikkiaOtsikolla("bloki");
        assertEquals(1, vinkit.size());
    }

    @Test
    public void testaaBlogiVinkinLisaysHakuJaPoistoTietokannasta() throws SQLException {
        BlogiVinkki vinkki = new BlogiVinkki();
        vinkki.setNimi("Ploki");
        vinkki.setUrl("www.timosoini.fi");
        int id = kayttisIO.lisaaBlogi(vinkki);
        assertTrue(id != -1);
        Vinkki vinkki2 = kayttisIO.haeYksiBlogi(id);
        assertEquals(vinkki.getNimi(), vinkki2.getNimi());
        assertEquals(id, vinkki2.getId());
        boolean poisto = kayttisIO.poistaBlogi(id);
        assertTrue(poisto);
        assertTrue(kayttisIO.haeYksiBlogi(id) == null);
    }

    @Test
    public void testaaMuokkaaBlogia() throws SQLException {
        BlogiVinkki vinkki = new BlogiVinkki();
        vinkki.setNimi("Ploki");
        vinkki.setUrl("www.timosoini.fi");
        int id = kayttisIO.lisaaBlogi(vinkki);
        BlogiVinkki vinkki2 = new BlogiVinkki(id, "punaviherkommunismi", "www.persut.fi");
        boolean muokkaus = kayttisIO.muokkaablogia(vinkki2);
        assertTrue(muokkaus);
        BlogiVinkki vinkki3 = kayttisIO.haeYksiBlogi(id);
        assertEquals("punaviherkommunismi", vinkki3.getNimi());
        assertEquals("www.persut.fi", vinkki3.getUrl());
    }

    @Test
    public void tageillaHakeminenKaikistaPalauttaaTagatutVinkit() throws SQLException {
        List<String> tagit = Arrays.asList("tagi", "igat");
        KirjaVinkki kirja = new KirjaVinkki(1, "Sananmuunnossanakirja", "Kunnanhallitus");
        kirja.setTagit(tagit);
        kayttisIO.lisaaKirja(kirja);

        VideoVinkki video = new VideoVinkki(1, "trip.swf", "http://lmgtfy.com/?q=trip.swf");
        video.setTagit(tagit);
        kayttisIO.lisaaVideo(video);

        PodcastVinkki podcast = new PodcastVinkki(1, "domain squatting", "www.thug.life");
        podcast.setTagit(tagit);
        kayttisIO.lisaaPodcast(podcast);

        BlogiVinkki blogi = new BlogiVinkki(1, "asd", "asd.com");
        blogi.setTagit(tagit);
        kayttisIO.lisaaBlogi(blogi);
                
        List<Vinkki> hakutulokset = kayttisIO.haeKaikkiaTageilla(tagit);
        assertTrue(hakutulokset.contains(kirja));
        assertTrue(hakutulokset.contains(video));
        assertTrue(hakutulokset.contains(podcast));
        assertTrue(hakutulokset.contains(blogi));
    }

    @Test
    public void tageillaHakeminenKaikistaEiPalautaMuitaKuinTagatut() throws SQLException {
        List<String> tagit = Arrays.asList("tagi", "igat");
        KirjaVinkki kirja = new KirjaVinkki(1, "Sananmuunnossanakirja", "Kunnanhallitus");
        kirja.setTagit(tagit);
        kayttisIO.lisaaKirja(kirja);
        kirja.setNimi("epäkelpo");
        kirja.setTagit(new ArrayList<>());
        kayttisIO.lisaaKirja(kirja);

        VideoVinkki video = new VideoVinkki(1, "trip.swf", "http://lmgtfy.com/?q=trip.swf");
        video.setTagit(tagit);
        kayttisIO.lisaaVideo(video);
        video.setNimi("lol");
        video.setTagit(new ArrayList<>());
        kayttisIO.lisaaVideo(video);

        PodcastVinkki podcast = new PodcastVinkki(1, "domain squatting", "www.thug.life");
        podcast.setTagit(tagit);
        kayttisIO.lisaaPodcast(podcast);
        podcast.setNimi("nope");
        podcast.setTagit(new ArrayList<>());
        kayttisIO.lisaaPodcast(podcast);

        List<Vinkki> hakutulokset = kayttisIO.haeKaikkiaTageilla(tagit);
        assertEquals(3, hakutulokset.size());
        assertFalse(hakutulokset.contains(kirja));
        assertFalse(hakutulokset.contains(video));
        assertFalse(hakutulokset.contains(podcast));
    }
}
