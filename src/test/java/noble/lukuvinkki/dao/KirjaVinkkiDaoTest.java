package noble.lukuvinkki.dao;

import org.junit.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import noble.lukuvinkki.TietokantaSetup;
import noble.lukuvinkki.tietokohteet.*;

public class KirjaVinkkiDaoTest {

    KirjaVinkkiDao dao;
    Tietokanta tietokanta;

    @Before
    public void setUp() {
        try {
            tietokanta = TietokantaSetup.alustaTestiTietokanta();
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

    @After
    public void tearDown() {
        try {
            tietokanta.suljeYhteys();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
