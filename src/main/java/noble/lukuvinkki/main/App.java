package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaInterface;
import java.util.List;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import noble.lukuvinkki.io.UrlinAvaaja;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.VideoVinkki;

public class App {

    private KayttoliittymaInterface kayttisIO;
    private IO io;

    public App(IO io, String tietokantaURL) {
        try {
            Tietokanta tietokanta;
            tietokanta = new Tietokanta(tietokantaURL);
            kayttisIO = new KayttoliittymaInterface(tietokanta);
            this.io = io;
        } catch (Exception e) {
            e.printStackTrace();
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

    private void listaaVinkkejäValikko() {
        io.print("1) Listaa kaikki vinkit");
        io.print("2) Listaa kaikki kirjat");
        io.print("3) Listaa kaikki videot");
        io.print("4) Listaa kaikki podcastit");
//        io.print("5) Listaa kaikki blogit");
        io.print("6) Hae Vinkkiä otsikolla");
        io.print("7) Palaa päävalikkoon");
    }

    private void valitseListattavatVinkit() {
        listaaVinkkejäValikko();
        String valinta = io.readLine("Anna valintasi: ");
        vinkkiValikonValinnat(valinta);
    }

    private void listaaKaikkiVinkit() {
        try {
            List<Vinkki> kaikkiVinkit = kayttisIO.haeKaikkiVinkit();
            if (tarkistaOnkoListaTyhjaTaiNull(kaikkiVinkit)) {
                return;
            }
            for (Vinkki vinkki : kaikkiVinkit) {
                System.out.println(vinkki);
            }
        } catch (SQLException ex) {
            virhe(ex);
        }
    }

    private void listaaKaikkiKirjat() {
        try {
            List<Vinkki> kaikkiKirjat = kayttisIO.haeKaikkiKirjat();
            if (tarkistaOnkoListaTyhjaTaiNull(kaikkiKirjat)) {
                return;
            }
            for (Vinkki vinkki : kaikkiKirjat) {
                System.out.println("Id: " + vinkki.getId() + "\n" + vinkki.getTekija() + ": " + vinkki.getNimi());
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private void listaaKaikkiVideot() {
        try {
            List<Vinkki> kaikkiVideot = kayttisIO.haeKaikkiVideot();
            if (tarkistaOnkoListaTyhjaTaiNull(kaikkiVideot)) {
                return;
            }
            for (Vinkki vinkki : kaikkiVideot) {
                System.out.println(vinkki);
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listaaKaikkiPodcastit() {
        try {
            List<Vinkki> kaikkiPodcastit = kayttisIO.haeKaikkiPodcastit();
            if (tarkistaOnkoListaTyhjaTaiNull(kaikkiPodcastit)) {
                return;
            }
            for (Vinkki vinkki : kaikkiPodcastit) {
                System.out.println(vinkki);
            }
        } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void haeOtsikolla() {
        try {
            String hakutermi = io.readLine("Anna hakutermi");
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

    private boolean tarkistaOnkoListaTyhjaTaiNull(List<Vinkki> lista) {
        if (lista == null || lista.isEmpty()) {
            io.print("Vinkkejä ei löytynyt\n");
            return true;
        }
        return false;
    }

    private void lisaaKirjaVinkki() {
        try {
            String kirjoittaja = io.readLine("Syötä kirjan kirjoittaja: ");
            String nimi = io.readLine("Syötä kirjan nimi: ");
            KirjaVinkki kirjaVinkki = new KirjaVinkki();
            kirjaVinkki.setTekija(kirjoittaja);
            kirjaVinkki.setNimi(nimi);
            if (kayttisIO.lisaaKirja(kirjaVinkki) != -1) {
                io.print("Vinkki lisätty!");
            } else {
                io.print("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
        //TODO
    }

    private void lisaaPodcastVinkki() {
        try {
            String nimi = io.readLine("Syötä podcastin nimi: ");
            String url = io.readLine("Syötä podcastin url: ");

            PodcastVinkki podcastVinkki = new PodcastVinkki();
            podcastVinkki.setUrl(url);
            podcastVinkki.setNimi(nimi);
            if (kayttisIO.lisaaPodcast(podcastVinkki) != -1) {
                io.print("Vinkki lisätty!");
            } else {
                io.print("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private void lisaaVideoVinkki() {
        try {
            String nimi = io.readLine("Syötä videon nimi: ");
            String url = io.readLine("Syötä videon url: ");

            VideoVinkki videoVinkki = new VideoVinkki();
            videoVinkki.setUrl(url);
            videoVinkki.setNimi(nimi);
            if (kayttisIO.lisaaVideo(videoVinkki) != -1) {
                io.print("Vinkki lisätty!");
            } else {
                io.print("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private void muokkaaVinkkia() {
        try {
            int id = Integer.parseInt(io.readLine("Syötä muokattavan vinkin id-numero:"));
            KirjaVinkki vinkki = (KirjaVinkki) kayttisIO.haeYksiKirja(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String kirjoittaja = io.readLine("Vinkin kirjoittaja on " + ((KirjaVinkki) vinkki).getTekija() + ". Syötä uusi kirjoittaja tai"
                    + " jätä tyhjäksi jos haluat säilyttää saman.");
            if (!kirjoittaja.isEmpty()) {
                ((KirjaVinkki) vinkki).setTekija(kirjoittaja);
                io.print("Vinkin kirjoittajaksi on vaihdettu " + kirjoittaja + ".");
            }

            String nimi = io.readLine("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan nimen.");
            if (!nimi.isEmpty()) {
                vinkki.setNimi(nimi);
                io.print("Vinkin nimeksi on vaihdettu " + nimi + ".");
            }
            if (kayttisIO.muokkaaKirjaa(vinkki)) {
                io.print("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.print("Vinkin muokkaaminen epäonnistui");
            }
        } catch (SQLException e) {
            virhe(e);
        }
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
        } catch (Exception e) {
            virhe(e);
        }
    }

    private void paaValikonValinnat(String valinta) {
        switch (valinta) {
            case "a":
                valitseListattavatVinkit();
                break;
            case "b":
                lisaaKirjaVinkki();
                break;
            case "c":
                lisaaPodcastVinkki();
                break;
            case "d":
                lisaaVideoVinkki();
                break;
            case "e":
                muokkaaVinkkia();
                break;
            case "f":
                poistaVinkki();
                break;
            default:
                io.print("Väärä valinta");
        }
    }

    private void vinkkiValikonValinnat(String valinta) {
        switch (valinta) {
            case "1":
                listaaKaikkiVinkit();
                break;
            case "2":
                listaaKaikkiKirjat();
                break;
            case "3":
                listaaKaikkiVideot();
                break;
            case "4":
                listaaKaikkiPodcastit();
                break;
//            case "5":
//                listaaKaikkiBlogit();
//                break;
            case "6":
                haeOtsikolla();
                break;
            case "7":
                return;
            default:
                io.print("Väärä valinta");
                break;
        }
    }

    
}
