package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Tietokanta {

    private String url;
    private Connection yhteys;

    public Tietokanta() {
        url = "jdbc:sqlite:tietokanta/vinkit.sqlite3";
    }

    public Connection yhteys() throws SQLException {
        this.yhteys = DriverManager.getConnection(url);
        return yhteys;
    }

    // lisäsin tällaisen sulkemismetodin -Jaakko
    public void suljeYhteys() {
        try {
            yhteys.close();
        } catch (SQLException ex) {
            System.out.println("Sulkeminen epännistui" + ex.getMessage());
        }
    }
}
