package noble.lukuvinkki.main;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class ListaaVideotKomento extends Komento {

    public ListaaVideotKomento(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        super(nimi, komento, teksti, io, kayttisIO);
    }

    @Override
    public void komento() throws SQLException, Exception {
        List<Vinkki> kaikkiVideot = kayttisIO.haeKaikkiVideot();
        if (tarkistaOnkoListaTyhjaTaiNull(kaikkiVideot)) {
            return;
        }
        for (Vinkki vinkki : kaikkiVideot) {
            io.tulosta(vinkki.toString());
        }
        HashMap<String, Komento> komennot = new HashMap<>();
        komennot.put("1", new AvaaVideo("avavideo", "1", "Avaa video", io, kayttisIO));
        Valikko urlValikko = new Valikko("urlvalikko", "1", "jotain", io, kayttisIO, komennot);
        urlValikko.komento();
    }

}
