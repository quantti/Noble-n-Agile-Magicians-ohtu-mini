package noble.lukuvinkki.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.TietokantaSetup;
import noble.lukuvinkki.tietokohteet.*;
import org.junit.*;
import static org.junit.Assert.*;

public class KirjaVinkkiDaoTest {

    private KirjaVinkkiDao dao;
    private Tietokanta tietokanta;

    @Before
    public void setUp() {
        try {
            tietokanta = TietokantaSetup.alustaTestiTietokanta("jdbc:sqlite:tietokanta/testaus.sqlite3");
            String sql = "INSERT INTO kirja_vinkki (kirjan_nimi, kirjan_kirjoittaja) VALUES ('testikirja', 'testikirjoittaja')";
            tietokanta.yhteys().createStatement().execute(sql);
            dao = new KirjaVinkkiDao(tietokanta);
        } catch (SQLException ex) {
            ex.printStackTrace();
            fail();
        }
    }

    @Test
    public void haeYksiPalauttaaOlionJosHaetaanOikeallaId() {
        Vinkki vinkki = null;
        try {
            vinkki = dao.haeYksi("1");
        } catch (SQLException ex) {
            fail("SQLException: " + ex.getMessage());
        }
        assertNotEquals(null, vinkki);
        assertEquals("testikirja", vinkki.getNimi());
        assertEquals("testikirjoittaja", ((KirjaVinkki) vinkki).getKirjoittaja());
    }

    @Test
    public void haeYksiPalauttaaNullJosHaetaanOlemattomallaId() {
        Vinkki vinkki = null;
        try {
            vinkki = dao.haeYksi("0");
            assertEquals(null, vinkki);
            vinkki = dao.haeYksi("-1");
            assertEquals(null, vinkki);
            vinkki = dao.haeYksi("9999");
            assertEquals(null, vinkki);
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @Test
    public void poistaPoistaaRivinTietokannastaJosIdOikein() {
        try {
            dao.poistaVinkki("1");
            String sql = "SELECT COUNT(*) as count FROM kirja_vinkki;";
            ResultSet rs = tietokanta.yhteys().prepareStatement(sql).executeQuery();
            assertEquals(0, rs.getInt("count"));
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @Test
    public void poistaEiPoistaRiviaJosIdVaarin() {
        try {
            dao.poistaVinkki("2");
            String sql = "SELECT COUNT(*) as count FROM kirja_vinkki;";
            ResultSet rs = tietokanta.yhteys().prepareStatement(sql).executeQuery();
            assertEquals(1, rs.getInt("count"));
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @Test
    public void tallennaLisaaRivinTietokantaan() {
        try {
            KirjaVinkki vinkki = new KirjaVinkki(2, "testi2", "T. Testaaja");
            dao.tallenna(vinkki);
            Vinkki v = dao.haeYksi("2");
            assertEquals(vinkki, v);
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @Test
    public void haeKaikkiPalauttaaTyhjanListanJosTietokannassaEiRiveja() {
        try {
            dao.poistaVinkki("1");
            List<KirjaVinkki> vinkit = dao.haeKaikki();
            assertNotEquals(null, vinkit);
            assertTrue(vinkit.isEmpty());
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @Test
    public void haeKaikkiPalauttaaListanVinkkejaJosTietokannassaRiveja1() {
        try {
            List<KirjaVinkki> vinkit = dao.haeKaikki();
            assertNotEquals(null, vinkit);
            assertFalse(vinkit.isEmpty());
            Vinkki v = new KirjaVinkki(1, "testikirja", "testikirjoittaja");
            assertEquals(v, dao.haeKaikki().get(0));
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @Test
    public void haeKaikkiPalauttaaListanVinkkejaJosTietokannassaRiveja2() {
        try {
            Vinkki v = new KirjaVinkki(2, "testi2", "kirjamestari");
            dao.tallenna((KirjaVinkki) v);
            List<KirjaVinkki> vinkit = dao.haeKaikki();
            assertNotEquals(null, vinkit);
            assertFalse(vinkit.isEmpty());
            assertEquals(v, dao.haeKaikki().get(1));
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @Test
    public void muokkaaMuuttaaYhtaRiviaTietokannassaJosIdOikein() {
        try {
            Vinkki uusi = new KirjaVinkki(2, "uusikirja", "kirjaaja");
            dao.tallenna((KirjaVinkki) uusi);
            Vinkki v = new KirjaVinkki(1, "do not steal", "made by me");
            dao.muokkaa(v);
            Vinkki muokattu = dao.haeYksi("1");
            assertEquals(v, muokattu);
            Vinkki eiMuokattu = dao.haeYksi("2");
            assertEquals(uusi, eiMuokattu);
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @Test
    public void muokkaaEiMuutaRiviaTietokannassaJosIdVaarin1() {
        try {
            Vinkki v = new KirjaVinkki(2, "do not steal", "made by me");
            dao.muokkaa(v);
            Vinkki eiMuokattu = dao.haeYksi("1");
            assertNotEquals(v, eiMuokattu);
        } catch (Exception e) {
            fail("Virhe: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        try {
            tietokanta.suljeYhteys();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
