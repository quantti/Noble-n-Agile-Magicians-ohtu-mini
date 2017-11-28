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
        tietokanta = TietokantaSetup.alustaTestiTietokanta();
        dao = new KirjaVinkkiDao(tietokanta);
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
        assertEquals("testikirjoittaja", vinkki.getKirjoittaja());
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
