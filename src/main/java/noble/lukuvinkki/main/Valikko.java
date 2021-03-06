package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.HashMap;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;

/**
 *
 * @author emil
 */
public class Valikko extends Komento {

    private HashMap<String, Komento> komennot;
    public Valikko(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO, HashMap<String, Komento> komennot) {
        super(nimi, komento, teksti, io, kayttisIO);
        this.komennot = komennot;
    }

    @Override
    public void komento() throws SQLException, Exception {
        int poistu = alaValikko(komennot);
        String valinta = io.lueRivi("Anna valintasi: ");
        if (Integer.parseInt(valinta) == poistu) {
            return;
        }
        valinnat(komennot, valinta);
    }

}
