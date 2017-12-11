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
    private Tietokanta tietokanta;

    public App(IO io, String tietokantaURL) throws SQLException {
        this(io, new Tietokanta(tietokantaURL));
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
        io.tulosta("\nTervetuloa käyttämään Lukuvinkkiä!\n");
        kysy();
    }

    private void virhe(Exception e) {
        io.tulosta("Virhe: " + e.getMessage());
    }

    private void listaaValikko() {
        io.tulosta("\nValitse alta haluamasi toiminto:\n");
        io.tulosta("a) Listaa vinkkejä");
        io.tulosta("b) lisää uusi kirjavinkki");
        io.tulosta("c) lisää uusi podcastvinkki");
        io.tulosta("d) lisää uusi videovinkki");
        io.tulosta("e) muokkaa vinkkiä");
        io.tulosta("f) poista vinkki");
        io.tulosta("q) lopeta ohjelma\n");
    }

    private void kysy() {

        while (true) {
            listaaValikko();

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

            paaValikonValinnat(vastaus);
        }
    }

    private void listaaVinkkejäValikko() {
        for (Komento komento : listausKomennot.values()) {
            io.tulosta(komento.toString());
        }
        io.tulosta("7) Palaa päävalikkoon");
    }

    private void valitseListattavatVinkit() {
        listaaVinkkejäValikko();
        String valinta = io.lueRivi("Anna valintasi: ");
        vinkkiValikonValinnat(valinta);
    }

    private void lisaaKirjaVinkki() {
        try {
            String kirjoittaja = io.lueRivi("Syötä kirjan kirjoittaja: ");
            String nimi = io.lueRivi("Syötä kirjan nimi: ");
            List<String> tagit = lisaaTagit();
            KirjaVinkki kirjaVinkki = new KirjaVinkki();
            kirjaVinkki.setTekija(kirjoittaja);
            kirjaVinkki.setNimi(nimi);
            kirjaVinkki.setTagit(tagit);
            if (kayttisIO.lisaaKirja(kirjaVinkki) != -1) {
                io.tulosta("Vinkki lisätty!");
            } else {
                io.tulosta("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private void lisaaPodcastVinkki() {
        try {
            String nimi = io.lueRivi("Syötä podcastin nimi: ");
            String url = io.lueRivi("Syötä podcastin url: ");
            List<String> tagit = lisaaTagit();
            PodcastVinkki podcastVinkki = new PodcastVinkki();
            podcastVinkki.setUrl(url);
            podcastVinkki.setNimi(nimi);
            podcastVinkki.setTagit(tagit);
            if (kayttisIO.lisaaPodcast(podcastVinkki) != -1) {
                io.tulosta("Vinkki lisätty!");
            } else {
                io.tulosta("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private void lisaaVideoVinkki() {
        try {
            String nimi = io.lueRivi("Syötä videon nimi: ");
            String url = io.lueRivi("Syötä videon url: ");
            List<String> tagit = lisaaTagit();
            VideoVinkki videoVinkki = new VideoVinkki();
            videoVinkki.setUrl(url);
            videoVinkki.setNimi(nimi);
            videoVinkki.setTagit(tagit);
            if (kayttisIO.lisaaVideo(videoVinkki) != -1) {
                io.tulosta("Vinkki lisätty!");
            } else {
                io.tulosta("Vinkin lisääminen epäonnistui.");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private List<String> lisaaTagit() {
        List<String> tagit = new ArrayList<>();
        String tag = "";
        while (true) {
            tag = io.lueRivi("Syötä tagi, tyhjä lopettaa.");
            if (tag.equalsIgnoreCase("")) {
                break;
            }
            tagit.add(tag);
        }
        return tagit;
    }

    private void muokkaaVinkkia() {
        io.tulosta("1) Muokkaa kirjavinkkiä");
        io.tulosta("2) Muokkaa videovinkkiä");
        io.tulosta("3) Muokkaa podcastvinkkiä");
        io.tulosta("4) Muokkaa blogivinkkiä");
        io.tulosta("5) Palaa päävalikkoon");
        String valinta = io.lueRivi("Syötä valintasi");
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
            default:
                break;
        }
    }

    private void muokkaaKirjaVinkkia() {
        try {
            int id = Integer.parseInt(io.lueRivi("Syötä muokattavan vinkin id-numero:"));
            KirjaVinkki vinkki = kayttisIO.haeYksiKirja(id);
            if (vinkki == null) {
                io.tulosta("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String kirjoittaja = io.lueRivi("Vinkin kirjoittaja on " + vinkki.getTekija() + ". Syötä uusi kirjoittaja tai"
                    + " jätä tyhjäksi jos haluat säilyttää saman.");
            if (!kirjoittaja.isEmpty()) {
                vinkki.setTekija(kirjoittaja);
                io.tulosta("Vinkin kirjoittajaksi on vaihdettu " + kirjoittaja + ".");
            }

            String nimi = io.lueRivi("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan nimen.");
            if (!nimi.isEmpty()) {
                vinkki.setNimi(nimi);
                io.tulosta("Vinkin nimeksi on vaihdettu " + nimi + ".");
            }
            if (kayttisIO.muokkaaKirjaa(vinkki)) {
                io.tulosta("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.tulosta("Vinkin muokkaaminen epäonnistui");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private void muokkaaVideoVinkkia() {
        try {
            int id = Integer.parseInt(io.lueRivi("Syötä muokattavan vinkin id-numero:"));
            VideoVinkki vinkki = kayttisIO.haeYksiVideo(id);
            if (vinkki == null) {
                io.tulosta("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String nimi = io.lueRivi("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan nimen.");
            if (!nimi.isEmpty()) {
                vinkki.setNimi(nimi);
                io.tulosta("Vinkin nimeksi on vaihdettu " + nimi + ".");
            }
            String url = io.lueRivi("Vinkin url on " + vinkki.getUrl() + ". Syötä uusi url tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan");
            if (!url.isEmpty()) {
                vinkki.setUrl(url);
                io.tulosta("Vinkin urliksi on vaihdettu " + url + ".");
            }
            if (kayttisIO.muokkaaVideota(vinkki)) {
                io.tulosta("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.tulosta("Vinkin muokkaaminen epäonnistui");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private void muokkaaPodcastVinkkia() {
        try {
            int id = Integer.parseInt(io.lueRivi("Syötä muokattavan vinkin id-numero:"));
            PodcastVinkki vinkki = kayttisIO.haeYksiPodcast(id);
            if (vinkki == null) {
                io.tulosta("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String nimi = io.lueRivi("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan nimen.");
            if (!nimi.isEmpty()) {
                vinkki.setNimi(nimi);
                io.tulosta("Vinkin nimeksi on vaihdettu " + nimi + ".");
            }
            String url = io.lueRivi("Vinkin url on " + vinkki.getUrl() + ". Syötä uusi url tai jätä tyhjäksi jos "
                    + "haluat säilyttää vanhan");
            if (!url.isEmpty()) {
                vinkki.setUrl(url);
                io.tulosta("Vinkin urliksi on vaihdettu " + url + ".");
            }
            if (kayttisIO.muokkaaPodcastia(vinkki)) {
                io.tulosta("Vinkkiä muokattu onnistuneesti!");
            } else {
                io.tulosta("Vinkin muokkaaminen epäonnistui");
            }
        } catch (SQLException e) {
            virhe(e);
        }
    }

    private void poistaVinkki() {
        io.tulosta("\n1) Poista kirjavinkki");
        io.tulosta("2) Poista podcastvinkki");
        io.tulosta("3) Poista videovinkki");
        String valinta = io.lueRivi("Anna valintasi");
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
            default:
                break;
        }
    }

    private void poistaKirjaVinkki() {
        try {
            int id = Integer.parseInt(io.lueRivi("Anna poistettavan vinkin id-numero:"));
            Vinkki vinkki = kayttisIO.haeYksiKirja(id);
            if (vinkki == null) {
                io.tulosta("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String vastaus = io.lueRivi("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
            if (vastaus.contentEquals("k") && kayttisIO.poistaKirja(id)) {
                io.tulosta("Vinkki poistettu");
            } else {
                io.tulosta("Vinkkiä ei poistettu");
            }
        } catch (SQLException ex) {
            virhe(ex);
        }
    }

    private void poistaVideoVinkki() {
        try {
            int id = Integer.parseInt(io.lueRivi("Anna poistettavan vinkin id-numero:"));
            Vinkki vinkki = kayttisIO.haeYksiVideo(id);
            if (vinkki == null) {
                io.tulosta("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String vastaus = io.lueRivi("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
            if (vastaus.contentEquals("k") && kayttisIO.poistaVideo(id)) {
                io.tulosta("Vinkki poistettu");
            } else {
                io.tulosta("Vinkkiä ei poistettu");
            }
        } catch (SQLException ex) {
            virhe(ex);
        }
    }

    private void poistaPodcastVinkki() {
        try {
            int id = Integer.parseInt(io.lueRivi("Anna poistettavan vinkin id-numero:"));
            Vinkki vinkki = kayttisIO.haeYksiPodcast(id);
            if (vinkki == null) {
                io.tulosta("Vinkkiä ei löytynyt, tarkista id-numero");
                return;
            }
            String vastaus = io.lueRivi("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
            if (vastaus.contentEquals("k") && kayttisIO.poistaPodcast(id)) {
                io.tulosta("Vinkki poistettu");
            } else {
                io.tulosta("Vinkkiä ei poistettu");
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
                io.tulosta("Väärä valinta");
        }
    }

    private void vinkkiValikonValinnat(String valinta) {
        Komento komento = listausKomennot.get(valinta);
        if (komento == null) {
            io.tulosta("Väärä valinta");
            return;
        }
        komento.komento();
    }

    private void avaaUrl() {
        io.tulosta("1) Avaa video");
        io.tulosta("2) Avaa podcast");
        io.tulosta("3) Palaa päävalikkoon");
        String valinta = io.lueRivi("Anna valintasi");
        switch (valinta) {
            case "1":
                avaaVideo();
                break;
            case "2":
                avaaPodcast();
                break;
            default:
                break;
        }
    }

    private void avaaPodcast() {
        try {
            int id = Integer.parseInt(io.lueRivi("Anna id-numero"));
            PodcastVinkki vinkki = kayttisIO.haeYksiPodcast(id);
            if (vinkki != null) {
                kayttisIO.avaaPodcast(vinkki);
            } else {
                io.tulosta("Podcastia ei löytynyt, tarkista id-numero");
            }
        } catch (SQLException ex) {
            virhe(ex);
        } catch (Exception ex) {
            virhe(ex);
        }
    }

    private void avaaVideo() {
        try {
            int id = Integer.parseInt(io.lueRivi("Anna id-numero"));
            VideoVinkki vinkki = kayttisIO.haeYksiVideo(id);
            if (vinkki != null) {
                kayttisIO.avaaVideo(vinkki);
            } else {
                io.tulosta("Videot ei löytynyt, tarkista id-numero");
            }
        } catch (SQLException ex) {
            virhe(ex);
        } catch (Exception ex) {
            virhe(ex);
        }
    }

}
