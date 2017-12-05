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
import noble.lukuvinkki.dao.Tietokanta;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.StubIO;
import noble.lukuvinkki.main.App;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import static org.junit.Assert.*;

public class Stepdefs {

    App app;
    StubIO io;
    Dao dao;
    String tietokantaURL = "jdbc:sqlite:tietokanta/cucumberTestaus.sqlite3";
    List<String> inputLines;
    Tietokanta kanta;

    @Before
    public void alustaTestikanta() throws SQLException {
        inputLines = new ArrayList<>();
        TietokantaSetup.alustaTestiTietokanta(tietokantaURL);
        this.kanta = new Tietokanta(tietokantaURL);
        dao = new KirjaVinkkiDao(kanta);
        KirjaVinkki kv = new KirjaVinkki(1, "Kirja", "Kirjailija");
        dao.tallenna(kv);

    }

    @Given("^Komento poista valitaan$")
    public void komento_poista_valitaan() throws Throwable {
        inputLines.add("f");

    }

    @When("^Poistetaan vinkki id:llä \"([^\"]*)\"$")
    public void poistetaan_vinkki_id_llä(String arg1) throws Throwable {

        inputLines.add("1");
        inputLines.add("k");
        inputLines.add("q");
        kaynnista();

    }

    @Then("^Vinkkiä id:llä \"([^\"]*)\" ei löydy$")
    public void vinkkiä_id_llä_ei_löydy(String arg1) throws Throwable {
        inputLines.add("e");
        inputLines.add(arg1);
        inputLines.add("q");

        kaynnista();
        assertTrue(io.getPrints().contains("Vinkkiä ei löytynyt, tarkista id-numero"));
    }

    @Given("^Komento lisää valitaan$")
    public void lisaaValittu() throws Throwable {

        inputLines.add("b");
    }

    @When("^Kirjoittaja \"([^\"]*)\" ja kirjan nimi \"([^\"]*)\" annetaan$")
    public void kirjoittaja_ja_kirjan_nimi_annetaan(String kirja, String kirjailija) throws Throwable {
        inputLines.add(kirjailija);
        inputLines.add(kirja);
        inputLines.add("q");
        kaynnista();

    }

    @Then("^Sovellus vastaa \"([^\"]*)\"$")
    public void sovellus_vastaa(String arg1) throws Throwable {
        List<String> rivit = io.getPrints();
        assertEquals(rivit.get(rivit.size() - 11), arg1);
    }

    @Given("^Komenta muokkaa valitaan$")
    public void komenta_muokkaa_valitaan() throws Throwable {
        inputLines.add("e");
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

    public void kaynnista() {
        io = new StubIO(inputLines);
        app = new App(io, kanta);
        app.kaynnista();
    }

    @After
    public void suljeKantaYhteys() throws SQLException {
        kanta.suljeYhteys();

    }

}
