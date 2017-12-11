package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaInterface;
import java.util.List;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.VideoVinkki;

public class App {

    private KayttoliittymaInterface kayttisIO;
    private IO io;
    private HashMap<String, Komento> listausKomennot;
    private HashMap<String, Komento> muokkausKomennot;

    public App(IO io, String tietokantaURL) {
        try {
            Tietokanta tietokanta = new Tietokanta(tietokantaURL);
            kayttisIO = new KayttoliittymaInterface(tietokanta);
            this.io = io;
            KomentoFactory komentoFactory = new KomentoFactory(io, kayttisIO);
            listausKomennot = komentoFactory.getListauskomennot();
            muokkausKomennot = komentoFactory.getMuokkauskomennot();
        } catch (Exception e) {
            virhe(e);
        }
    }

    public App(IO io, Tietokanta tietokanta) {
        try {
            kayttisIO = new KayttoliittymaInterface(tietokanta);
            this.io = io;
            KomentoFactory komentoFactory = new KomentoFactory(io, kayttisIO);
            listausKomennot = komentoFactory.getListauskomennot();
            muokkausKomennot = komentoFactory.getMuokkauskomennot();
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

    private void listaaValikko() {
        io.print("\nValitse alta haluamasi toiminto:\n");
        io.print("a) Listaa vinkkejä");
        io.print("b) lisää uusi kirjavinkki");
        io.print("c) lisää uusi podcastvinkki");
        io.print("d) lisää uusi videovinkki");
        io.print("e) muokkaa vinkkiä");
        io.print("f) poista vinkki");
        io.print("q) lopeta ohjelma\n");
    }

    private void kysy() {

        while (true) {
            listaaValikko();

            String vastaus = io.readLine("Anna komento: ");
            if (vastaus.equalsIgnoreCase("q")) {
                io.print("Heippa!");
                break;
            }

            paaValikonValinnat(vastaus);
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
        try {
            int id = Integer.parseInt(io.readLine("Anna poistettavan vinkin id-numero:"));
            Vinkki vinkki = kayttisIO.haeYksiKirja(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String vastaus = io.readLine("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
            if (vastaus.contentEquals("k") && kayttisIO.poistaKirja(id)) {
                io.print("Vinkki poistettu");
            } else {
                io.print("Vinkkiä ei poistettu");
            }
        } catch (SQLException ex) {
            virhe(ex);
        }
    }
    
    private void poistaVideoVinkki() {
        try {
            int id = Integer.parseInt(io.readLine("Anna poistettavan vinkin id-numero:"));
            Vinkki vinkki = kayttisIO.haeYksiVideo(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String vastaus = io.readLine("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
            if (vastaus.contentEquals("k") && kayttisIO.poistaVideo(id)) {
                io.print("Vinkki poistettu");
            } else {
                io.print("Vinkkiä ei poistettu");
            }
        } catch (SQLException ex) {
            virhe(ex);
        }
    }
    
    private void poistaPodcastVinkki() {
        try {
            int id = Integer.parseInt(io.readLine("Anna poistettavan vinkin id-numero:"));
            Vinkki vinkki = kayttisIO.haeYksiPodcast(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String vastaus = io.readLine("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
            if (vastaus.contentEquals("k") && kayttisIO.poistaPodcast(id)) {
                io.print("Vinkki poistettu");
            } else {
                io.print("Vinkkiä ei poistettu");
            }
        } catch (SQLException ex) {
            virhe(ex);
        }
        
    }

    private void paaValikonValinnat(String valinta) {
        switch (valinta) {
            case "a":
                valitseListattavatVinkit();
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
                valitseMuokkattavatVinkit();
                break;
            case "f":
                poistaVinkki();
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
        komento.komento();

    }


}
