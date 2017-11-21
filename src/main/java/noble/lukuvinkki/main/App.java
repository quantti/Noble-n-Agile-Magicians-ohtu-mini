package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaIO;
import java.util.List;
import java.util.Scanner;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class App {

    public static KayttoliittymaIO kayttisIO;

    public static void main(String[] args) throws Exception {
        kayttisIO = new KayttoliittymaIO();

        listaaValikko();
    }

    private static String kysy() {
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
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
                    lisaaVinkki();
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
        for (Vinkki vinkki : kaikkiVinkit) {
            System.out.println(vinkki);
        }
    }

    private static void lisaaVinkki() {
        //TODO
    }

    private static void muokkaaVinkkia() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void poistaVinkki() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
