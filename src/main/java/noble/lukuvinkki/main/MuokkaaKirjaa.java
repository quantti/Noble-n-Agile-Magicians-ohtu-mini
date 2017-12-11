package noble.lukuvinkki.main;

import java.sql.SQLException;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;

/**
 *
 * @author emil
 */
public class MuokkaaKirjaa extends Komento{
    
    public MuokkaaKirjaa(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }
    
    @Override
    public void komento() {
        try {
            int id = Integer.parseInt(io.lueRivi("Syötä muokattavan vinkin id-numero:"));
            KirjaVinkki vinkki = kayttisIO.haeYksiKirja(id);
            if (vinkki == null) {
                io.tulosta("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String kirjoittaja = io.lueRivi("Vinkin kirjoittaja on " + vinkki.getTekija() + ". Syötä uusi kirjoittaja tai"
                    + " jätä tyhjäksi jos haluat säilyttää saman.");
            if (!kirjoittaja.isEmpty()) {
                vinkki.setTekija(kirjoittaja);
                io.tulosta("Vinkin kirjoittajaksi on vaihdettu " + kirjoittaja + ".");
            }

            String nimi = io.lueRivi("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan nimen.");
            if (!nimi.isEmpty()) {
                vinkki.setNimi(nimi);
                io.tulosta("Vinkin nimeksi on vaihdettu " + nimi + ".");
            }
            if (kayttisIO.muokkaaKirjaa(vinkki)) {
                io.tulosta("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.tulosta("Vinkin muokkaaminen epäonnistui");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }
    
}
