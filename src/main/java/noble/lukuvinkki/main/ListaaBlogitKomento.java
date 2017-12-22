/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;

/**
 *
 * @author vankari
 */
class ListaaBlogitKomento extends Komento {

    public ListaaBlogitKomento(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }

    @Override
    public void komento() throws Exception {
        List<Vinkki> kaikkiBlogit = kayttisIO.haeKaikkiBlokit();
        if (tarkistaOnkoListaTyhjaTaiNull(kaikkiBlogit)) {
            return;
        }
        for (Vinkki vinkki : kaikkiBlogit) {
            io.tulosta(vinkki.toString());
        }
        HashMap<String, Komento> komennot = new HashMap<>();
        komennot.put("1", new AvaaBlogi("avaablogi", "1", "Avaa blogi", io, kayttisIO));
        Valikko urlValikko = new Valikko("urlvalikko", "1", "jotain", io, kayttisIO, komennot);
        urlValikko.komento();
    }

}
