/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.main;

import java.sql.SQLException;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.VideoVinkki;

/**
 *
 * @author vankari
 */
public class AvaaVideo extends Komento {

    public AvaaVideo(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }

    @Override
    public void komento() throws SQLException, Exception {
        int id = Integer.parseInt(io.lueRivi("Anna id-numero"));
        VideoVinkki vinkki = kayttisIO.haeYksiVideo(id);
        if (vinkki != null) {
            kayttisIO.avaaVideo(vinkki);
        } else {
            io.tulosta("Videota ei löytynyt, tarkista id-numero");
        }
    }
}
