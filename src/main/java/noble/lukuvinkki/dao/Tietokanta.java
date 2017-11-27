package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Tietokanta {

    private Connection yhteys;

    public Tietokanta(String url) throws SQLException {
        this.yhteys = DriverManager.getConnection(url);
    }

    public Connection yhteys() {
        return yhteys;
    }

    // lisäsin tällaisen sulkemismetodin -Jaakko
    public void suljeYhteys() throws SQLException {
        yhteys.close();
    }
}
