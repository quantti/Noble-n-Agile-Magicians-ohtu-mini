/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.io;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kari
 */
public class KayttoliittymaIOTest {

    KayttoliittymaInterface kayttisIO;

    public KayttoliittymaIOTest() {
    }

    @Before
    public void setUp() {
        kayttisIO = new KayttoliittymaInterface();
    }

    @Test
    public void testaaKayttisIO() {
        assertTrue(kayttisIO != null);
    }

    
//    @Test
//    public void testaaLisaysTietokantaan() throws SQLException {
//        String kirjoittaja = "testiKirjoittaja";
//        String nimi = "testiNimi";
//        KirjaVinkki kv = new KirjaVinkki();
//        kv.setKirjoittaja(kirjoittaja);
//        kv.setNimi(nimi);
//        kayttisIO.lisaaVinkki(kv);
//        String id = Integer.toString(haeViimeisinId());
//        assertEquals(kirjoittaja, kayttisIO.haeYksiVinkki(id));
//        assertEquals(nimi, kayttisIO.haeYksiVinkki(id));
//    }
    
//    private int haeViimeisinId() throws SQLException {
//        Tietokanta kanta = new Tietokanta();
//        Connection yhteys = kanta.yhteys();
//        String query = "SELECT last_insert_rowid()";
//        PreparedStatement ps = yhteys.prepareStatement(query);
//        ResultSet rs = ps.executeQuery();
//        int id = rs.getInt("id");
//        return id;
//    }

}
