
package noble.lukuvinkki.io;

import java.util.Scanner;

public class KonsoliIO implements IO {
    private Scanner skanneri = new Scanner(System.in);
    
    public void tulosta(String tulostettava) {
        System.out.println(tulostettava);
    }

    public String lueRivi(String kehote) {
        System.out.println(kehote);
        return skanneri.nextLine();
    }
    
}
