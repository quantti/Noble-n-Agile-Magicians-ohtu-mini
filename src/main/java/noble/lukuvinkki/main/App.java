package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaInterface;
import java.util.List;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.StubIO;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class App {

    private KayttoliittymaInterface kayttisIO;
    private Tietokanta tietokanta;
    private  IO io;
    
    public App(IO io) {
        tietokanta = new Tietokanta();
        kayttisIO = new KayttoliittymaInterface(tietokanta);
        this.io = io;
    }
    
    public App() {
        this.io = new StubIO();
        tietokanta = new Tietokanta();
        kayttisIO = new KayttoliittymaInterface(tietokanta);
    }
    
    public void kaynnista() {
        listaaValikko();
    }

    private void listaaValikko() {

        while (true) {
            io.print("Tervetuloa käyttämään Lukuvinkkiä!\n\nValitse alta haluamasi toiminto:\n");
            io.print("a) Listaa kaikki vinkit");
            io.print("b) lisää uusi kirjavinkki");
            io.print("c) muokkaa vinkkiä");
            io.print("d) poista vinkki");
            io.print("q) lopeta ohjelma\n");

            String vastaus = io.readLine("Anna komento: ");
            if (vastaus.equalsIgnoreCase("q")) {
                io.print("Heippa!");
                break;
            }
            switch (vastaus) {
                case "a":
                    listaaKaikkiVinkit();
                    break;
                case "b":
                    lisaaKirjaVinkki();
                    break;
                case "c":
                    muokkaaVinkkia();
                    break;
                case "d":
                    poistaVinkki();
                    break;
                default:
                    io.print("Väärä komento");
            }
        }
    }

    private void listaaKaikkiVinkit() {
        List<Vinkki> kaikkiVinkit = kayttisIO.haeKaikkiVinkit();
        if (kaikkiVinkit == null || kaikkiVinkit.isEmpty()) {
            io.print("Vinkkejä ei löytynyt\n");
            return;
        }
        for (Vinkki vinkki : kaikkiVinkit) {
            System.out.println("Id: " + vinkki.getId() + "\n" + vinkki.getKirjoittaja() + ": " + vinkki.getNimi());
        }
    }

    private void lisaaKirjaVinkki() {
        String kirjoittaja = io.readLine("Syötä kirjan kirjoittaja: ");
        String nimi = io.readLine("Syötä kirjan nimi: ");
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setKirjoittaja(kirjoittaja);
        kirjaVinkki.setNimi(nimi);
        if (kayttisIO.lisaaVinkki(kirjaVinkki)) {
            io.print("Vinkki lisätty!");
        } else {
            io.print("Vinkin lisääminen epäonnistui.");
        }
        //TODO
    }

    private void muokkaaVinkkia() {
        String id = io.readLine("Syötä muokattavan vinkin id-numero:");
        Vinkki vinkki = kayttisIO.haeYksiVinkki(id);
        if (vinkki == null) {
            io.print("Vinkkiä ei löytynyt, tarkista id-numero");
            return;
        }
        String kirjoittaja = io.readLine("Vinkin kirjoittaja on " + vinkki.getKirjoittaja() + ". Syötä uusi kirjoittaja tai"
                + " jätä tyhjäksi jos haluat säilyttää saman.");
        if (!kirjoittaja.isEmpty()) {
            vinkki.setKirjoittaja(kirjoittaja);
            io.print("Vinkin kirjoittajaksi on vaihdettu " + kirjoittaja + ".");
        }
        
        String nimi = io.readLine("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                + "haluat säilyttää vanhan nimen.");
        if (!nimi.isEmpty()) {
            vinkki.setNimi(nimi);
            io.print("Vinkin nimeksi on vaihdettu " + nimi + ".");
        }
        if (kayttisIO.muokkaa(vinkki)) {
            io.print("Vinkkiä muokattu onnistuneesti!");
        } else {
            io.print("Vinkin muokkaaminen epäonnistui");
        }
    }

    private void poistaVinkki() {
        
        String id = io.readLine("Anna poistettavan vinkin id-numero:");
        Vinkki vinkki = kayttisIO.haeYksiVinkki(id);
        if (vinkki == null) {
            io.print("Vinkkiä ei löytynyt, tarkista id-numero");
            return;
        }
        String vastaus = io.readLine("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
        if (vastaus.contentEquals("k") && kayttisIO.poistaVinkki(id)) {
            io.print("Vinkki poistettu");
        } else {
            io.print("Vinkkiä ei poistettu");
        }
    }

}
