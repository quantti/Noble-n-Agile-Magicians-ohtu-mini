package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import java.sql.SQLException;
import java.util.HashMap;

public class App {

    private KayttoliittymaInterface kayttisIO;
    private IO io;
    private Tietokanta tietokanta;
    private HashMap<String, Komento> komennot;

    public App(IO io, String tietokantaURL) throws SQLException {
        this(io, new Tietokanta(tietokantaURL));

    }

    public App(IO io, Tietokanta tietokanta) {
        try {
            this.tietokanta = tietokanta;
            kayttisIO = new KayttoliittymaInterface(tietokanta);
            this.io = io;
            KomentoFactory komentoFactory = new KomentoFactory(io, kayttisIO);
            komennot = komentoFactory.getPaavalikonkomennot();

        } catch (Exception e) {
            virhe(e);
        }
    }

    public void kaynnista() {
        io.tulosta("\nTervetuloa käyttämään Lukuvinkkiä!\n");
        kysy();
    }

    private void virhe(Exception e) {
        io.tulosta("Virhe: " + e.getMessage());
    }

    private void listaaValikko(HashMap<String, Komento> komennot) {
        io.tulosta("\nValitse alta haluamasi toiminto:\n");
        for (Komento komento : komennot.values()) {
            io.tulosta(komento.toString());
        }
        io.tulosta("q) lopeta ohjelma\n");
    }

    private void kysy() {

        while (true) {
            listaaValikko(komennot);
            String vastaus = io.lueRivi("Anna komento: ");
            if (vastaus.equalsIgnoreCase("q")) {
                try {
                    tietokanta.suljeYhteys();
                    io.tulosta("Heippa!");
                    break;
                } catch (SQLException ex) {
                    virhe(ex);
                }
            }
            valinnat(komennot, vastaus);

        }
    }

    private void valinnat(HashMap<String, Komento> komennot, String valinta) {
        Komento komento = komennot.get(valinta);
        if (komento == null) {
            io.tulosta("Väärä valinta");
            return;
        }
        try {
            komento.komento();
        } catch (Exception e) {
            virhe(e);
        }
    }
}
