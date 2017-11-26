package noble.lukuvinkki.dao;

import java.util.List;
import noble.lukuvinkki.tietokohteet.Vinkki;

public interface Dao<T> {

    int tallenna(T vinkki);

    Vinkki haeYksi(String id);

    List<Vinkki> haeKaikki();

    boolean poistaVinkki(String id);

    boolean muokkaa(Vinkki vinkki);
}
