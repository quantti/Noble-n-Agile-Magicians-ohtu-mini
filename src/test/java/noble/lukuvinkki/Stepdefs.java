package noble.lukuvinkki;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.StubIO;
import noble.lukuvinkki.main.App;
import static org.junit.Assert.*;

public class Stepdefs {

    App app;
    StubIO io;
    String tietokantaURL = "jdbc:sqlite:tietokanta/testaus.sqlite3";
//    UserDao userDao = new InMemoryUserDao();
//    AuthenticationService auth = new AuthenticationService(userDao);
    List<String> inputLines = new ArrayList<>();
    @Before
    public void alustaTestikanta() {
        TietokantaSetup.alustaTestiTietokanta();
    }
    
    @Given("^Komento poista valitaan$")
    public void komento_poista_valitaan() throws Throwable {
        inputLines.add("d");
        
    }
    
    @When("^Poistetaan vinkki id:llä \"([^\"]*)\"$")
    public void poistetaan_vinkki_id_llä(String arg1) throws Throwable {
        inputLines.add(arg1);
        inputLines.add("k");
        inputLines.add("q");
        io = new StubIO(inputLines);
        app = new App(io, tietokantaURL);
        app.kaynnista();
    }

    @Then("^Vinkkiä id:llä \"([^\"]*)\" ei löydy$")
    public void vinkkiä_id_llä_ei_löydy(String arg1) throws Throwable {
        inputLines.clear();
        inputLines.add("c");
        inputLines.add(arg1);
        inputLines.add("q");
        io = new StubIO(inputLines);
        app = new App(io, tietokantaURL);
        app.kaynnista();
        assertEquals("Vinkkiä ei löytynyt, tarkista id-numero", io.getPrints().get(io.getPrints().size() - 9));
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
        io = new StubIO(inputLines);
        app = new App(io, tietokantaURL);
        app.kaynnista();

    }
    


    @Then("^Sovellus vastaa \"([^\"]*)\"$")
    public void sovellus_vastaa(String arg1) throws Throwable {

        assertEquals(arg1, io.getPrints().get(io.getPrints().size() - 9));
    }

}
