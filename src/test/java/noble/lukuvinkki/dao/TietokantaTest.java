package noble.lukuvinkki.dao;

import org.junit.*;
import java.sql.Connection;
import static org.junit.Assert.*;

public class TietokantaTest {

    private Tietokanta tietokanta;

    @Before
    public void setUp() {
        try {
            tietokanta = new Tietokanta("jdbc:sqlite:tietokanta/testaus.sqlite3");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void tietokantaYhteydenLuominenToimii() {
        Connection yhteys = tietokanta.yhteys();
        assertNotEquals(null, yhteys);
    }
}
