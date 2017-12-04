package noble.lukuvinkki.dao;

import java.util.List;
import noble.lukuvinkki.tietokohteet.Vinkki;
import java.sql.SQLException;

public interface Dao<T> {


    int tallenna(T vinkki) throws SQLException;

    Vinkki haeYksi(int id) throws SQLException;

    List<Vinkki> haeKaikki() throws SQLException;

    boolean poistaVinkki(int id) throws SQLException;

    boolean muokkaa(Vinkki vinkki) throws SQLException;
    
    List<Vinkki> haeOtsikolla(String hakutermi) throws SQLException;
}
