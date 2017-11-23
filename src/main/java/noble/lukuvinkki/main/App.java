package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaInterface;
import java.util.List;
import java.util.Scanner;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class App {

    public static KayttoliittymaInterface kayttisIO;
    public static Scanner scanner;

    public static void main(String[] args) throws Exception {
        kayttisIO = new KayttoliittymaInterface();
        scanner = new Scanner(System.in);
        listaaValikko();
    }

    private static String kysy() {
        System.out.println("Anna komento:");
        return scanner.nextLine();
    }

    private static void listaaValikko() {

        while (true) {
            System.out.println("Tervetuloa käyttämään Lukuvinkkiä!\n\nValitse alta haluamasi toiminto:\n");
            System.out.println("a) Listaa kaikki vinkit");
            System.out.println("b) lisää uusi kirjavinkki");
            System.out.println("c) muokkaa vinkkiä");
            System.out.println("d) poista vinkki");
            System.out.println("q) lopeta ohjelma\n");

            String vastaus = kysy();
            System.out.println("");
            if (vastaus.equalsIgnoreCase("q")) {
                System.out.println("Heippa!");
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
                    System.out.println("Väärä komento");
            }
        }
    }

    private static void listaaKaikkiVinkit() {
        List<Vinkki> kaikkiVinkit = kayttisIO.haeKaikkiVinkit();
        if (kaikkiVinkit == null || kaikkiVinkit.isEmpty()) {
            System.out.println("Vinkkejä ei löytynyt\n");
            return;
        }
        for (Vinkki vinkki : kaikkiVinkit) {
            System.out.println("Id: " + vinkki.getId() + "\n" + vinkki.getKirjoittaja() + ": " + vinkki.getNimi());
        }
    }

    private static void lisaaKirjaVinkki() {
        System.out.println("Syötä kirjan kirjoittaja: ");
        String kirjoittaja = scanner.nextLine();
        System.out.println("Syötä kirjan nimi: ");
        String nimi = scanner.nextLine();
        KirjaVinkki kirjaVinkki = new KirjaVinkki();
        kirjaVinkki.setKirjoittaja(kirjoittaja);
        kirjaVinkki.setNimi(nimi);
        kayttisIO.lisaaVinkki(kirjaVinkki);
        //TODO
    }

    private static void muokkaaVinkkia() {
        System.out.println("Syötä muokattavan vinkin id-numero:");
        String id = scanner.nextLine();
        Vinkki vinkki = kayttisIO.haeYksiVinkki(id);
        if (vinkki == null) {
            System.out.println("Vinkkiä ei löytynyt, tarkista id-numero");
            return;
        }
        System.out.println("Vinkin kirjoittaja on " + vinkki.getKirjoittaja() + ". Syötä uusi kirjoittaja tai"
                + " jätä tyhjäksi jos haluat säilyttää saman.");
        String kirjoittaja = scanner.nextLine();
        if (!kirjoittaja.isEmpty()) {
            vinkki.setKirjoittaja(kirjoittaja);
            System.out.println("Vinkin kirjoittajaksi on vaihdettu " + kirjoittaja + ".");
        }
        System.out.println("Vinkin nimi on " + vinkki.getNimi() + ". Syötä uusi nimi tai jätä tyhjäksi jos "
                + "haluat säilyttää vanhan nimen.");
        String nimi = scanner.nextLine();
        if (!nimi.isEmpty()) {
            vinkki.setNimi(nimi);
            System.out.println("Vinkin nimeksi on vaihdettu " + nimi + ".");
        }
        kayttisIO.muokkaa(vinkki);
    }

    private static void poistaVinkki() {
        System.out.println("Anna poistettavan vinkin id-numero:");
        String id = scanner.nextLine();
        Vinkki vinkki = kayttisIO.haeYksiVinkki(id);
        if (vinkki == null) {
            System.out.println("Vinkkiä ei löytynyt, tarkista id-numero");
            return;
        }
        System.out.println("Haluatko varmasti poistaa vinkin " + vinkki.getNimi() + "? (k/e)");
        String vastaus = scanner.nextLine();
        if (vastaus.contentEquals("k")) {
            kayttisIO.poistaVinkki(id);
            System.out.println("Vinkki poistettu");
        } else {
            System.out.println("Vinkkiä ei poistettu");
        }
    }

}
