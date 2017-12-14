
package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;


public class ListaaKaikkiKomento extends Komento {

    public ListaaKaikkiKomento(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }




    
    @Override
    public void komento() throws SQLException {
            List<Vinkki> kaikkiVinkit = kayttisIO.haeKaikkiVinkit();
            if (tarkistaOnkoListaTyhjaTaiNull(kaikkiVinkit)) {
                return;
            }
            for (Vinkki vinkki : kaikkiVinkit) {
                io.tulosta(vinkki.toString());
            }

    }
}
