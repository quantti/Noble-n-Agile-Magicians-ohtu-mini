package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import java.sql.SQLException;
import java.util.HashMap;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.VideoVinkki;

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
