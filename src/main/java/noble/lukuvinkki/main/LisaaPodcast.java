package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;

/**
 *
 * @author emil
 */
public class LisaaPodcast extends Komento {

    public LisaaPodcast(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }

    public void komento() throws SQLException {

        String nimi = io.lueRivi("Syötä podcastin nimi: ");
        String url = io.lueRivi("Syötä podcastin url: ");
        List<String> tagit = lisaaTagit();
        PodcastVinkki podcastVinkki = new PodcastVinkki();
        podcastVinkki.setUrl(url);
        podcastVinkki.setNimi(nimi);
        podcastVinkki.setTagit(tagit);
        if (kayttisIO.lisaaPodcast(podcastVinkki) != -1) {
            io.tulosta("Vinkki lisätty!");
        } else {
            io.tulosta("Vinkin lisääminen epäonnistui.");
        }
    }

}
