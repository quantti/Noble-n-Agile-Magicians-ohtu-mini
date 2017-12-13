
package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;


public class HaeOtsikollaKomento extends Komento{

    public HaeOtsikollaKomento(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }


    

    
    @Override
    public void komento() {
        try {
            String hakutermi = io.lueRivi("Anna hakutermi");
            List<Vinkki> vinkit = kayttisIO.haeKaikkiaOtsikolla(hakutermi);
            if (tarkistaOnkoListaTyhjaTaiNull(vinkit)) {
                return;
            }
            for (Vinkki vinkki : vinkit) {
                System.out.println(vinkki);
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
