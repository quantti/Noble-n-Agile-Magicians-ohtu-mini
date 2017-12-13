package noble.lukuvinkki.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.TynkaIO;
import org.junit.Before;
import org.junit.*;
import static org.junit.Assert.*;


/**
 *
 * @author kari
 */
public class AppTest {
    
    private App app;
    private final String tietokantaURL = "jdbc:sqlite:tietokanta/testaus.sqlite3";
    
    @Before
    public void setUp() throws SQLException {
        IO io = new TynkaIO();
        this.app = new App(io, new Tietokanta(tietokantaURL));
    }
    
    @Test
    public void testAppConstructor() {
        assertTrue(this.app != null);
    }
}
