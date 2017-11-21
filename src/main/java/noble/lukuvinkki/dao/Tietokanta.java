package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Tietokanta {
    private String url;

    public Tietokanta() {
        url = "jdbc:sqlite:tietokanta/vinkit.sqlite3";
    }

    public Connection yhteys() throws SQLException {
        Connection yhteys = DriverManager.getConnection(url);
        return yhteys;
    }
}
