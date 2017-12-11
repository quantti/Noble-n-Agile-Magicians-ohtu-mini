package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.VideoVinkki;

/**
 *
 * @author emil
 */
public class LisaaVideo extends Komento{
    
    public LisaaVideo(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }
    
    @Override
    public void komento() {
        try {
            String nimi = io.readLine("Syötä videon nimi: ");
            String url = io.readLine("Syötä videon url: ");
            List<String> tagit = lisaaTagit();
            VideoVinkki videoVinkki = new VideoVinkki();
            videoVinkki.setUrl(url);
            videoVinkki.setNimi(nimi);
            //TODO: lisää tagit vinkkiin
            if (kayttisIO.lisaaVideo(videoVinkki) != -1) {
                io.print("Vinkki lisätty!");
            } else {
                io.print("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    
}
