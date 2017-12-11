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

    private void alaValikko(HashMap<String, Komento> komennot) {
        for (Komento komento : komennot.values()) {
            io.print(komento.toString());
        }
        int viimInd = komennot.size();
        io.print(viimInd + ") Palaa päävalikkoon");
    }

    private void valitseListattavatVinkit() {
        alaValikko(listausKomennot);
        String valinta = io.readLine("Anna valintasi: ");
        alaValikonValinnat(listausKomennot, valinta);
    }

    private void valitseMuokkattavatVinkit() {
        alaValikko(muokkausKomennot);
        String valinta = io.readLine("Anna valintasi: ");
        alaValikonValinnat(muokkausKomennot, valinta);
    }

    private void poistaVinkki() {
        alaValikko(poistoKomennot);
        String valinta = io.readLine("Anna valintasi: ");
        alaValikonValinnat(poistoKomennot, valinta);
    }

    private void paaValikonValinnat(String valinta) {
        switch (valinta) {
            case "a":
                new Valikko("listaaVinkit", "a", "Listaa vinkit", io, kayttisIO, listausKomennot).komento();
                break;
            case "b":
                new LisaaKirja("lisaaKirja", "b", "jotaan", io, kayttisIO).komento();
                break;
            case "c":
                new LisaaPodcast("lisaaKirja", "b", "jotaan", io, kayttisIO).komento();
                break;
            case "d":
                new LisaaVideo("lisaaVideo", "b", "jotaan", io, kayttisIO).komento();
                break;
            case "e":
                new Valikko("muokkaaVinkit", "e", "Muokkaa vinkkejä", io, kayttisIO, muokkausKomennot).komento();
                break;
            case "f":
                new Valikko("poistaVinkit", "f", "Poista vinkkejä", io, kayttisIO, poistoKomennot).komento();
                break;
            default:
                io.print("Väärä valinta");
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
