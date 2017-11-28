package noble.lukuvinkki.dao;

import java.util.List;
import noble.lukuvinkki.tietokohteet.Vinkki;
import java.sql.SQLException;

public interface Dao<T> {


    int tallenna(T vinkki) throws SQLException;

    Vinkki haeYksi(String id) throws SQLException;

    List<Vinkki> haeKaikki() throws SQLException;

    boolean poistaVinkki(String id) throws SQLException;

    boolean muokkaa(Vinkki vinkki) throws SQLException;
}
