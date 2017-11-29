/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.io;

import java.sql.SQLException;
import noble.lukuvinkki.TietokantaSetup;
import noble.lukuvinkki.dao.Tietokanta;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kari
 */
public class KayttoliittymaIOTest {

    private KayttoliittymaInterface kayttisIO;
    private Tietokanta tietokanta;

    @Before
    public void setUp() {
        tietokanta = TietokantaSetup.alustaTestiTietokanta();
        kayttisIO = new KayttoliittymaInterface(tietokanta);
    }

    @Test
    public void testaaKayttisIO() {
        assertTrue(kayttisIO != null);
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
