package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;

/**
 *
 * @author emil
 */
public class LisaaKirja extends Komento {

    public LisaaKirja(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }

    @Override
    public void komento() throws SQLException {

        String kirjoittaja = io.lueRivi("Syötä kirjan kirjoittaja: ");
        String nimi = io.lueRivi("Syötä kirjan nimi: ");
        List<String> tagit = lisaaTagit();
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setTekija(kirjoittaja);
        kirjaVinkki.setNimi(nimi);
        kirjaVinkki.setTagit(tagit);
        if (kayttisIO.lisaaKirja(kirjaVinkki) != -1) {
            io.tulosta("Vinkki lisätty!");
        } else {
            io.tulosta("Vinkin lisääminen epäonnistui.");
        }

    }

}
