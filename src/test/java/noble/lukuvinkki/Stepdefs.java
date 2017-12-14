package noble.lukuvinkki;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.dao.Dao;
import noble.lukuvinkki.dao.KirjaVinkkiDao;
import noble.lukuvinkki.dao.PodcastVinkkiDao;
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.TynkaIO;
import noble.lukuvinkki.main.App;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import static org.junit.Assert.*;

public class Stepdefs {

    App app;
    TynkaIO io;
    Dao kvDao;
    Dao pcvDao;
    String tietokantaURL = "jdbc:sqlite:tietokanta/cucumberTestaus.sqlite3";
    List<String> inputLines;
    Tietokanta kanta;

    @Before
    public void alustaTestikanta() throws SQLException {
        inputLines = new ArrayList<>();
        TietokantaSetup.alustaTestiTietokanta(tietokantaURL);
        this.kanta = new Tietokanta(tietokantaURL);
        kvDao = new KirjaVinkkiDao(kanta);
        pcvDao = new PodcastVinkkiDao(kanta);
        KirjaVinkki kv = new KirjaVinkki(1, "Kirja", "Kirjailija");
        PodcastVinkki pcv = new PodcastVinkki(1, "Podcast", "Url");
        kvDao.tallenna(kv);
        pcvDao.tallenna(pcv);

    }

    @Given("^Komento poista valitaan$")
    public void komento_poista_valitaan() throws Throwable {
        inputLines.add("g");

    }

    @When("^Poistetaan vinkki id:llä \"([^\"]*)\"$")
    public void poistetaan_vinkki_id_llä(String arg1) throws Throwable {

        inputLines.add("1");
        inputLines.add("1");
        inputLines.add("k");
        inputLines.add("q");
        kaynnista();

    }

    @Given("^Komento lisää kirja valitaan$")
    public void lisaaValittu() throws Throwable {

        inputLines.add("b");

    }

    @When("^Kirjoittaja \"([^\"]*)\" ja kirjan nimi \"([^\"]*)\" annetaan$")
    public void kirjoittaja_ja_kirjan_nimi_annetaan(String kirja, String kirjailija) throws Throwable {
        inputLines.add(kirjailija);
        inputLines.add(kirja);
        inputLines.add("");

        inputLines.add("q");
        kaynnista();

    }

    @When("^Kirjoittaja \"([^\"]*)\", kirjan nimi \"([^\"]*)\" ja seuraavat tagit annetaan:$")
    public void kirjoittaja_kirjan_nimi_ja_seuraavat_tagit_annetaan(String kirja, String kirjailija, List<String> arg3) throws Throwable {
        inputLines.add(kirjailija);
        inputLines.add(kirja);
        for (String string : arg3) {
            inputLines.add(string);
        }
        inputLines.add("");
        inputLines.add("q");
        kaynnista();

    }

    @When("^Nimi \"([^\"]*)\", url \"([^\"]*)\" annetaan ja  seuraavat tagit annetaan:$")
    public void nimi_url_annetaan_ja_seuraavat_tagit_annetaan(String nimi, String url, List<String> arg3) throws Throwable {
        inputLines.add(nimi);
        inputLines.add(url);
        for (String string : arg3) {
            inputLines.add(string);
        }
        inputLines.add("");
        inputLines.add("q");
        kaynnista();
    }

    @Then("^Sovellus vastaa \"([^\"]*)\"$")
    public void sovellus_vastaa(String arg1) throws Throwable {
        List<String> rivit = io.getTulosteet();
        assertEquals(rivit.get(rivit.size() - 12), arg1);
    }

    @Given("^Komento lisää blogi valitaan$")
    public void komento_lisää_blogi_valitaan() throws Throwable {
        inputLines.add("e");
    }

    @Given("^Komenta muokkaa valitaan$")
    public void komenta_muokkaa_valitaan() throws Throwable {
        inputLines.add("f");
        inputLines.add("1");

    }

    @When("^Muokataan vinkkiä id:llä \"([^\"]*)\"$")
    public void muokataan_vinkkiä_id_llä(String arg1) throws Throwable {
        inputLines.add("1");
    }

    @When("^Annetaan tyhjät kentät$")
    public void annetaan_tyhjät_kentät() throws Throwable {
        inputLines.add("");
        inputLines.add("");
        inputLines.add("q");
        kaynnista();

    }

    @When("^Annetaan uudeksi nimeksi \"([^\"]*)\" ja kirjoittajaksi \"([^\"]*)\"$")
    public void annetaan_uudeksi_nimeksi_ja_kirjoittajaksi(String kirja, String kirjoittaja) throws Throwable {
        inputLines.add(kirjoittaja);
        inputLines.add(kirja);
        inputLines.add("q");
        kaynnista();
    }

    @Given("^Komento listaa valitaan$")
    public void komento_listaa_valitaan() throws Throwable {
        inputLines.add("a");
    }

    @When("^Valitaan listattavaksi kirjat$")
    public void valitaan_listattavaksi_kirjat() throws Throwable {
        inputLines.add("2");
        inputLines.add("q");
        kaynnista();
    }

    @Then("^Vain kirjat näytetään$")
    public void vain_kirjat_näytetään() throws Throwable {
        assertTrue(io.getTulosteet().contains("\nId: 1\nKirjailija: Kirja\nTagit: "));
    }

    @Given("^Komento listaa vinkit valitaan$")
    public void komento_listaa_vinkit_valitaan() throws Throwable {
        inputLines.add("a");
        inputLines.add("1");
        inputLines.add("4");
        inputLines.add("q");
        kaynnista();
    }

    @When("^Nimi \"([^\"]*)\" ja url \"([^\"]*)\" annetaan$")
    public void nimi_ja_url_annetaan(String nimi, String url) throws Throwable {
        inputLines.add(nimi);
        inputLines.add(url);
        inputLines.add("");
        inputLines.add("q");
        kaynnista();
    }

    @Given("^Komento lisää podcast valitaan$")
    public void komento_lisää_podcast_valitaan() throws Throwable {
        inputLines.add("c");
    }

    @Given("^Komento lisää video valitaan$")
    public void komento_lisää_video_valitaan() throws Throwable {
        inputLines.add("d");
    }

    @Then("^Ohjelma listaa kaikki vinkit$")
    public void ohjelma_listaa_kaikki_vinkit() throws Throwable {
        assertEquals(37, io.getTulosteet().size());
    }

    @When("^Valitaan listattavaksi podcastit$")
    public void valitaan_listattavaksi_podcastit() throws Throwable {
        inputLines.add("4");
        inputLines.add("2");
        inputLines.add("q");
        kaynnista();
    }

    @Then("^Vain podcastit näytetään$")
    public void vain_podcastit_näytetään() throws Throwable {
        assertEquals(io.getTulosteet().get(19), "\nId: 1\nPodcast: Url\nTagit: ");
    }

    public void kaynnista() {
        io = new TynkaIO(inputLines);
        app = new App(io, kanta);
        app.kaynnista();
    }

    @After
    public void suljeKantaYhteys() throws SQLException {
        kanta.suljeYhteys();

    }

}
