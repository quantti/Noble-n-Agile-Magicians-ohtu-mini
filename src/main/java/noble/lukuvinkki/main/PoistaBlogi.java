package noble.lukuvinkki.main;

import java.sql.SQLException;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;

/**
 *
 * @author emil
 */
public class PoistaBlogi extends Komento {

    public PoistaBlogi(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }

    @Override
    public void komento() throws SQLException {
        int id = Integer.parseInt(io.lueRivi("Anna poistettavan vinkin id-numero:"));
        Vinkki vinkki = kayttisIO.haeYksiBlogi(id);
        if (vinkki == null) {
            io.tulosta("Vinkkiä ei löytynyt, tarkista id-numero");
            return;
        }
        String vastaus = io.lueRivi("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
        if (vastaus.contentEquals("k") && kayttisIO.poistaBlogi(id)) {
            io.tulosta("Vinkki poistettu");
        } else {
            io.tulosta("Vinkkiä ei poistettu");
        }
    }

}
