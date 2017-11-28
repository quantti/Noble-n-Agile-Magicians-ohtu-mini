package noble.lukuvinkki;

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
    IO io;
//    UserDao userDao = new InMemoryUserDao();
//    AuthenticationService auth = new AuthenticationService(userDao);
    List<String> inputLines = new ArrayList<>();

    @Given("^Komento lisää valitaan$")
    public void lisaaValittu() throws Throwable {

        inputLines.add("b");
    }

    @When("^Kirjan nimi \"([^\"]*)\" ja kirjoittaja \"([^\"]*)\" annetaan$")
    public void kirjanNimiJaKirjailijaAnnettu(String kirja, String kirjailija) throws Throwable {
        inputLines.add(kirja);
        inputLines.add(kirjailija);
        inputLines.add("q");
        io = new StubIO(inputLines);
        app = new App(io);
        app.kaynnista();

    }

    @Then("^Sovellus vastaa \"([^\"]*)\"$")
    public void sovellus_vastaa(String arg1) throws Throwable {

        assertEquals("Lisätty", arg1);
    }

}
