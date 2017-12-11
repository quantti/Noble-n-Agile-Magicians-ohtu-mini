package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaInterface;
import java.util.List;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;
import java.sql.SQLException;
import noble.lukuvinkki.io.UrlinAvaaja;
import java.util.ArrayList;
import java.util.HashMap;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.VideoVinkki;

public class App {

    private KayttoliittymaInterface kayttisIO;
    private IO io;
    private HashMap<String, Komento> listausKomennot;
    private Tietokanta tietokanta;
    private HashMap<String, Komento> muokkausKomennot;
    private HashMap<String, Komento> poistoKomennot;
    private HashMap<String, Komento> komennot;

    public App(IO io, String tietokantaURL) {
        try {
            this.tietokanta = new Tietokanta(tietokantaURL);
            kayttisIO = new KayttoliittymaInterface(tietokanta);
            this.io = io;
            KomentoFactory komentoFactory = new KomentoFactory(io, kayttisIO);
            listausKomennot = komentoFactory.getListauskomennot();
            muokkausKomennot = komentoFactory.getMuokkauskomennot();
            poistoKomennot = komentoFactory.getPoistokomennot();
            komennot = komentoFactory.getPaavalikonkomennot();
        } catch (Exception e) {
            virhe(e);
        }
    }

    public App(IO io, Tietokanta tietokanta) {
        try {
            this.tietokanta = tietokanta;
            kayttisIO = new KayttoliittymaInterface(tietokanta);
            this.io = io;
            KomentoFactory komentoFactory = new KomentoFactory(io, kayttisIO);
            listausKomennot = komentoFactory.getListauskomennot();
            muokkausKomennot = komentoFactory.getMuokkauskomennot();
            poistoKomennot = komentoFactory.getPoistokomennot();
            komennot = komentoFactory.getPaavalikonkomennot();

        } catch (Exception e) {
            virhe(e);
        }
    }

    public void kaynnista() {
        io.print("\nTervetuloa käyttämään Lukuvinkkiä!\n");
        kysy();
    }

    private void virhe(Exception e) {
        io.print("Virhe: " + e.getMessage());
    }

    private void listaaValikko(HashMap<String, Komento> komennot) {
        io.print("\nValitse alta haluamasi toiminto:\n");
        for (Komento komento : komennot.values()) {
            io.print(komento.toString());
        }
        io.print("q) lopeta ohjelma\n");
    }

    private void kysy() {

        while (true) {
            listaaValikko(komennot);

            String vastaus = io.readLine("Anna komento: ");
            if (vastaus.equalsIgnoreCase("q")) {
                try {
                    tietokanta.suljeYhteys();
                    io.print("Heippa!");
                    break;
                } catch (SQLException ex) {
                    virhe(ex);
                }
            }

            alaValikonValinnat(komennot, vastaus);
        }
    }


    private void alaValikonValinnat(HashMap<String, Komento> komennot, String valinta) {
        Komento komento = komennot.get(valinta);
        if (komento == null) {
            io.print("Väärä valinta");
            return;
        }
        try {
            komento.komento();
        } catch (Exception e) {
            virhe(e);
        }

    }
}
