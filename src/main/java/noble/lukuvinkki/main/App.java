package noble.lukuvinkki.main;

import noble.lukuvinkki.io.KayttoliittymaIO;
import java.util.List;
import java.util.Scanner;

public class App {
    public static KayttoliittymaIO kayttisIO;

    public static void main(String[] args) throws Exception {
        kayttisIO = new KayttoliittymaIO();
        
        listaaValikko();
    }

    private static String kysy() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private static void listaaValikko() {
        System.out.println("Tervetuloa käyttämään Lukuvinkkiä!\n\nValitse alta haluamasi toiminto:\n");
        System.out.println("a) Listaa kaikki vinkit");
        System.out.println("b) lisää uusi kirjavinkki");
        System.out.println("c) muokkaa vinkkiä");
        System.out.println("d) poista vinkki");
        System.out.println("q) lopeta ohjelma");

        while (true) {
            System.out.println("");
            System.out.print(">");
            System.out.println("");
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
                    System.out.println("lisää");
                    break;
                case "c":
                    System.out.println("muokkaa");
                    break;
                case "d":
                    System.out.println("poista");
                    break;
                default:
                    System.out.println("Väärä komento");
            }
        }
    }

    private static void listaaKaikkiVinkit() {
        List kaikkiVinkit = kayttisIO.haeKaikkiVinkit();
        for (Object object : kaikkiVinkit) {
            System.out.println(object);
        }
    }

}
