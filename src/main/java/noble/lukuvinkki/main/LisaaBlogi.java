package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.BlogiVinkki;

/**
 *
 * @author emil
 */
public class LisaaBlogi extends Komento {

    public LisaaBlogi(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }

    @Override
    public void komento() throws SQLException {

        String nimi = io.lueRivi("Syötä blogin nimi: ");
        String url = io.lueRivi("Syötä blogin url: ");
        List<String> tagit = lisaaTagit();
        BlogiVinkki blogivinkki = new BlogiVinkki();
        blogivinkki.setUrl(url);
        blogivinkki.setNimi(nimi);
        blogivinkki.setTagit(tagit);
        if (kayttisIO.lisaaBlogi(blogivinkki) != -1) {
            io.tulosta("Vinkki lisätty!");
        } else {
            io.tulosta("Vinkin lisääminen epäonnistui.");
        }
    }

}
