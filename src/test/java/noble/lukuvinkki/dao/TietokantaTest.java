package noble.lukuvinkki.dao;

import org.junit.*;
import java.sql.Connection;
import static org.junit.Assert.fail;

public class TietokantaTest {

    Tietokanta tietokanta;

    @Before
    public void setUp() {
        tietokanta = new Tietokanta();
    }

    @Test
    public void tietokantaYhteydenLuominenToimii() {
        try {
            Connection yhteys = tietokanta.yhteys();
        } catch (Exception e) {
            fail();
        }
    }
}
