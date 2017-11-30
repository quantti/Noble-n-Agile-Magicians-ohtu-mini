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
        kirjaVinkki.setKirjoittaja("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        assertTrue(id != -1);
        Vinkki kirjaVinkki2 = kayttisIO.haeYksiKirja(Integer.toString(id));
        assertEquals(kirjaVinkki.getNimi(), kirjaVinkki2.getNimi());
        boolean poisto = kayttisIO.poistaKirja(Integer.toString(id));
        assertTrue(poisto);
        assertTrue(kayttisIO.haeYksiKirja(Integer.toString(id)) == null);
    }
    
    @Test
    public void testaaHaeKaikkiKirjat() throws SQLException {
        List<KirjaVinkki> kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(0, kirjat.size());
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setKirjoittaja("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(1, kirjat.size());
        kayttisIO.poistaKirja(Integer.toString(id));
        kirjat = kayttisIO.haeKaikkiKirjat();
        assertEquals(0, kirjat.size());
    }
    
    @Test
    public void testaaMuokkaaKirjaa() throws SQLException {
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setKirjoittaja("Arto Paasilinna");
        kirjaVinkki.setNimi("Jäniksen vuosi");
        int id = kayttisIO.lisaaKirja(kirjaVinkki);
        KirjaVinkki kirjaVinkki2 = new KirjaVinkki(id, "Seitsemän veljestä", "Aleksis Kivi");
        boolean muokkaus = kayttisIO.muokkaaKirja(kirjaVinkki2);
        assertTrue(muokkaus);
        KirjaVinkki kirjavinkki3 = kayttisIO.haeYksiKirja(Integer.toString(id));
        assertEquals("Seitsemän veljestä", kirjavinkki3.getNimi());
        assertEquals("Aleksis Kivi", kirjavinkki3.getKirjoittaja());
    }
}
