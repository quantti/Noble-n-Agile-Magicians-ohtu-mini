/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.IO;

import noble.lukuvinkki.io.KayttoliittymaIO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kari
 */
public class KayttoliittymaIOTest {
    KayttoliittymaIO kayttisIO;
    
    public KayttoliittymaIOTest() {
    }
    
    @Before
    public void setUp() {
        kayttisIO = new KayttoliittymaIO();
    }
    
    @Test
    public void testKayttisIO() {
        assertTrue(kayttisIO != null);
    }
}
