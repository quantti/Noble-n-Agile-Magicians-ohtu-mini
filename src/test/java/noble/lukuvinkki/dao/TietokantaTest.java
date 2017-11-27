package noble.lukuvinkki.dao;

import org.junit.*;
import java.sql.Connection;
import static org.junit.Assert.fail;

public class TietokantaTest {

    Tietokanta tietokanta;

    @Before
    public void setUp() {
        try {
            tietokanta = new Tietokanta("jdbc:sqlite:tietokanta/testaus.sqlite3");
        } catch (Exception ignore) {
        }
    }

    @Test
    public void tietokantaYhteydenLuominenToimii() {
        try {
            Connection yhteys = tietokanta.yhteys();
            assertNotEquals(null, yhteys);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}
