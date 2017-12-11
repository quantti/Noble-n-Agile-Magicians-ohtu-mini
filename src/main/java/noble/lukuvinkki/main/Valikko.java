package noble.lukuvinkki.main;

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
    public void komento() {
        alaValikko(komennot);
        String valinta = io.lueRivi("Anna valintasi: ");
        alaValikonValinnat(komennot, valinta);
    }

}
