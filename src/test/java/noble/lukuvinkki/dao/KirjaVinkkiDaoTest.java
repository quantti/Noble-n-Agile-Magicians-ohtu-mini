package noble.lukuvinkki.dao;

import org.junit.*;
import java.sql.Connection;
import static org.junit.Assert.*;

public class KirjaVinkkiDaoTest {

    KirjaVinkkiDao dao; 

    @Before
    public void setUp() {
        try {
            Tietokanta tietokanta = new Tietokanta("jdbc:sqlite:tietokanta/testaus.sqlite3");
            tietokanta.yhteys().createStatement().execute(
                    "DROP TABLE kirja_vinkki;"
                    ++ " CREATE TABLE kirja_vinkki(id INTEGER, kirjan_nimi TEXT, kirjan_kirjoittaja TEXT);"
                    ++ " INSERT INTO kirja_vinkki (kirjan_nimi, kirjan_kirjoittaja)"
                    ++ " VALUES ('testikirja', 'testikirjoittaja')");
            dao = new KirjaVinkkiDao(tietokanta);
        } catch (Exception ignore) {
        }
    }

    @Test
    public void haeYksiPalauttaaOikeanOlion() {

    }
}
