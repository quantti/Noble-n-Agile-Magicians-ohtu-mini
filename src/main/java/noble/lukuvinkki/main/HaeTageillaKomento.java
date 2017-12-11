/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;

/**
 *
 * @author ttiira
 */
public class HaeTageillaKomento extends Komento {

    public HaeTageillaKomento(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }

    @Override
    public void komento() throws SQLException {
        List<String> tagit = lisaaTagit();
        List<Vinkki> vinkit = kayttisIO.haeKaikkiaTageilla(tagit);
        if (tarkistaOnkoListaTyhjaTaiNull(vinkit)) {
            return;
        }
        for (Vinkki vinkki : vinkit) {
            io.print(vinkki.toString());
        }
    }

}
