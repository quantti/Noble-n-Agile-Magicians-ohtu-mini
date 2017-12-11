package noble.lukuvinkki.main;

import java.sql.SQLException;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;

/**
 *
 * @author emil
 */
public class MuokkaaPodcastia extends Komento{
    
    public MuokkaaPodcastia(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }
    
    @Override
    public void komento() throws SQLException {
            int id = Integer.parseInt(io.readLine("Syötä muokattavan vinkin id-numero:"));
            PodcastVinkki vinkki = kayttisIO.haeYksiPodcast(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String nimi = io.readLine("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan nimen.");
            if (!nimi.isEmpty()) {
                vinkki.setNimi(nimi);
                io.print("Vinkin nimeksi on vaihdettu " + nimi + ".");
            }
            String url = io.readLine("Vinkin url on " + vinkki.getUrl() + ". Syötä uusi url tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan");
            if (!url.isEmpty()) {
                vinkki.setUrl(url);
                io.print("Vinkin urliksi on vaihdettu " + url + ".");
            }
            if (kayttisIO.muokkaaPodcastia(vinkki)) {
                io.print("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.print("Vinkin muokkaaminen epäonnistui");
            }

    }

    
}
