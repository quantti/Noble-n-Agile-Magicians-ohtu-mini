/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.io;

import java.util.List;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

/**
 *
 * @author kari
 */
public interface IO {

    List<Vinkki> haeKaikkiVinkit();

    Vinkki haeYksiVinkki(String id);

    void lisaaVinkki(KirjaVinkki kirjaVinkki);

    void muokkaa(Vinkki vinkki);

    void poistaVinkki(String id);

}
