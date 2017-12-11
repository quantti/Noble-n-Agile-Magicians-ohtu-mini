
package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;


public class ListaaKirjatKomento extends Komento {

    public ListaaKirjatKomento(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }


    
    
    @Override
    public void komento() throws SQLException {
        
            List<Vinkki> kaikkiKirjat = kayttisIO.haeKaikkiKirjat();
            if (tarkistaOnkoListaTyhjaTaiNull(kaikkiKirjat)) {
                return;
            }

            for (Vinkki vinkki : kaikkiKirjat) {
                io.print(vinkki.toString());

            }
    }
}
