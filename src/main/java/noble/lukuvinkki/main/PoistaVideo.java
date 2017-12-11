package noble.lukuvinkki.main;

import java.sql.SQLException;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;

/**
 *
 * @author emil
 */
public class PoistaVideo extends Komento{
    
    public PoistaVideo(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }
    
    @Override
    public void komento() throws SQLException {
            int id = Integer.parseInt(io.readLine("Anna poistettavan vinkin id-numero:"));
            Vinkki vinkki = kayttisIO.haeYksiVideo(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String vastaus = io.readLine("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
            if (vastaus.contentEquals("k") && kayttisIO.poistaVideo(id)) {
                io.print("Vinkki poistettu");
            } else {
                io.print("Vinkkiä ei poistettu");
            }

    }
    
}
