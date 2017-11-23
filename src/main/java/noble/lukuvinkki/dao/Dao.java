package noble.lukuvinkki.dao;

import java.util.List;
import noble.lukuvinkki.tietokohteet.Vinkki;

public interface Dao<T> {

    void tallenna(T vinkki);

    Vinkki haeYksi(String id);

    List<Vinkki> haeKaikki();

    void poistaVinkki(String id);

    void muokkaa(Vinkki vinkki);
}
