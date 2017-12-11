package noble.lukuvinkki.main;

import java.sql.SQLException;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.VideoVinkki;

/**
 *
 * @author emil
 */
public class MuokkaaVideota extends Komento{
    
    public MuokkaaVideota(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }
    
    @Override
    public void komento() {
        try {
            int id = Integer.parseInt(io.readLine("Syötä muokattavan vinkin id-numero:"));
            VideoVinkki vinkki = kayttisIO.haeYksiVideo(id);
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
            if (kayttisIO.muokkaaVideota(vinkki)) {
                io.print("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.print("Vinkin muokkaaminen epäonnistui");
            }
        } catch 
                (SQLException e) {
            virhe(e);
        }
    }
    
    
}
