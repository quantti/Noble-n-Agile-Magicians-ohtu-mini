package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    
    private KayttoliittymaInterface kayttisIO;
    private IO io;
    private HashMap<String, Komento> listausKomennot;
    private Tietokanta tietokanta;
    private HashMap<String, Komento> muokkausKomennot;


    public App(IO io, String tietokantaURL) {
        try {
            this.tietokanta = new Tietokanta(tietokantaURL);
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
            this.tietokanta = tietokanta;
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
                try {
                    tietokanta.suljeYhteys();
                    io.print("Heippa!");
                    break;
                } catch (SQLException ex) {
                    virhe(ex);
                }
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
        io.print("\n1) Poista kirjavinkki");
        io.print("2) Poista podcastvinkki");
        io.print("3) Poista videovinkki");
        String valinta = io.readLine("Anna valintasi");
        switch (valinta) {
            case "1":
                new PoistaKirja("poistaKirja", "1", "Poista kirjavinkki", io, kayttisIO).komento();
                break;
            case "2":
                new PoistaPodcast("poistaKirja", "1", "Poista podcastvinkki", io, kayttisIO).komento();
                break;
            case "3":
                new PoistaVideo("poistaVideo", "3", "Poista videovinkki", io, kayttisIO).komento();
                break;
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
        try {
            komento.komento();
        } catch (SQLException e) {
            virhe(e);
        }
        
    }
}
