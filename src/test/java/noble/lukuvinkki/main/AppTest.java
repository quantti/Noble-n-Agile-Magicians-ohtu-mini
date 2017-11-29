package noble.lukuvinkki.main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.StubIO;
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
    public void setUp() {
        IO io = new StubIO();
        this.app = new App(io, tietokantaURL);
    }
    
    @Test
    public void testAppConstructor() {
        assertTrue(this.app != null);
    }
}
