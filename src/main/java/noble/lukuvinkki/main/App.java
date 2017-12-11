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
    
    public App(IO io, String tietokantaURL) {
        try {
            this.tietokanta = new Tietokanta(tietokantaURL);
            kayttisIO = new KayttoliittymaInterface(tietokanta);
            this.io = io;
            KomentoFactory komentoFactory = new KomentoFactory(io, kayttisIO);
            listausKomennot = komentoFactory.getListauskomennot();
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
    
    private void listaaVinkkejäValikko() {
        for (Komento komento : listausKomennot.values()) {
            io.print(komento.toString());
        }
        io.print("7) Palaa päävalikkoon");
    }
    
    private void valitseListattavatVinkit() {
        listaaVinkkejäValikko();
        String valinta = io.readLine("Anna valintasi: ");
        vinkkiValikonValinnat(valinta);
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
            List<String> tagit = lisaaTagit();
            KirjaVinkki kirjaVinkki = new KirjaVinkki();
            kirjaVinkki.setTekija(kirjoittaja);
            kirjaVinkki.setNimi(nimi);
            kirjaVinkki.setTagit(tagit);
            if (kayttisIO.lisaaKirja(kirjaVinkki) != -1) {
                io.print("Vinkki lisätty!");
            } else {
                io.print("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }
    
    private void lisaaPodcastVinkki() {
        try {
            String nimi = io.readLine("Syötä podcastin nimi: ");
            String url = io.readLine("Syötä podcastin url: ");
            List<String> tagit = lisaaTagit();
            PodcastVinkki podcastVinkki = new PodcastVinkki();
            podcastVinkki.setUrl(url);
            podcastVinkki.setNimi(nimi);
            podcastVinkki.setTagit(tagit);
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
            List<String> tagit = lisaaTagit();
            VideoVinkki videoVinkki = new VideoVinkki();
            videoVinkki.setUrl(url);
            videoVinkki.setNimi(nimi);
            videoVinkki.setTagit(tagit);
            if (kayttisIO.lisaaVideo(videoVinkki) != -1) {
                io.print("Vinkki lisätty!");
            } else {
                io.print("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }
    
    private List<String> lisaaTagit() {
        List<String> tagit = new ArrayList<>();
        String tag = "";
        while (true) {
            tag = io.readLine("Syötä tagi, tyhjä lopettaa.");
            if (tag.equalsIgnoreCase("")) {
                break;
            }
            tagit.add(tag);
        }
        return tagit;
    }
    
    private void muokkaaVinkkia() {
        io.print("1) Muokkaa kirjavinkkiä");
        io.print("2) Muokkaa videovinkkiä");
        io.print("3) Muokkaa podcastvinkkiä");
        io.print("4) Muokkaa blogivinkkiä");
        io.print("5) Palaa päävalikkoon");
        String valinta = io.readLine("Syötä valintasi");
        switch (valinta) {
            case "1":
                muokkaaKirjaVinkkia();
                break;
            case "2":
                muokkaaVideoVinkkia();
                break;
            case "3":
                muokkaaPodcastVinkkia();
                break;
            case "4":
                break;
            case "5":
                break;
        }
    }
    
    private void muokkaaKirjaVinkkia() {
        try {
            int id = Integer.parseInt(io.readLine("Syötä muokattavan vinkin id-numero:"));
            KirjaVinkki vinkki = kayttisIO.haeYksiKirja(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String kirjoittaja = io.readLine("Vinkin kirjoittaja on " + vinkki.getTekija() + ". Syötä uusi kirjoittaja tai"
                    + " jätä tyhjäksi jos haluat säilyttää saman.");
            if (!kirjoittaja.isEmpty()) {
                vinkki.setTekija(kirjoittaja);
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
    
    private void muokkaaVideoVinkkia() {
        try {
            int id = Integer.parseInt(io.readLine("Syötä muokattavan vinkin id-numero:"));
            VideoVinkki vinkki = kayttisIO.haeYksiVideo(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String nimi = io.readLine("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan nimen.");
            if (!nimi.isEmpty()) {
                vinkki.setNimi(nimi);
                io.print("Vinkin nimeksi on vaihdettu " + nimi + ".");
            }
            String url = io.readLine("Vinkin url on " + vinkki.getUrl() + ". Syötä uusi url tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan");
            if (!url.isEmpty()) {
                vinkki.setUrl(url);
                io.print("Vinkin urliksi on vaihdettu " + url + ".");
            }
            if (kayttisIO.muokkaaVideota(vinkki)) {
                io.print("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.print("Vinkin muokkaaminen epäonnistui");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }
    
    private void muokkaaPodcastVinkkia() {
        try {
            int id = Integer.parseInt(io.readLine("Syötä muokattavan vinkin id-numero:"));
            PodcastVinkki vinkki = kayttisIO.haeYksiPodcast(id);
            if (vinkki == null) {
                io.print("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String nimi = io.readLine("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan nimen.");
            if (!nimi.isEmpty()) {
                vinkki.setNimi(nimi);
                io.print("Vinkin nimeksi on vaihdettu " + nimi + ".");
            }
            String url = io.readLine("Vinkin url on " + vinkki.getUrl() + ". Syötä uusi url tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan");
            if (!url.isEmpty()) {
                vinkki.setUrl(url);
                io.print("Vinkin urliksi on vaihdettu " + url + ".");
            }
            if (kayttisIO.muokkaaPodcastia(vinkki)) {
                io.print("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.print("Vinkin muokkaaminen epäonnistui");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }
    
    private void poistaVinkki() {
        io.print("\n1) Poista kirjavinkki");
        io.print("2) Poista podcastvinkki");
        io.print("3) Poista videovinkki");
        String valinta = io.readLine("Anna valintasi");
        switch (valinta) {
            case "1":
                poistaKirjaVinkki();
                break;
            case "2":
                poistaPodcastVinkki();
                break;
            case "3":
                poistaVideoVinkki();
                break;
        }
    }
    
    private void poistaKirjaVinkki() {
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
        Komento komento = listausKomennot.get(valinta);
        if (komento == null) {
            io.print("Väärä valinta");
            return;
        }
        komento.komento();
        
    }
    
}
