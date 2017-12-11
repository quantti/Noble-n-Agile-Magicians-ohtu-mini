/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KonsoliIO;

/**
 *
 * @author kari
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IO io = new KonsoliIO();
        try {
            String tietokantaURL = "jdbc:sqlite::resource:vinkit.sqlite3";
            App app = new App(io, tietokantaURL);
            app.kaynnista();
        } catch (SQLException ex) {
            io.tulosta("Tapahtui virhe: " + ex);
        }
    }

}
