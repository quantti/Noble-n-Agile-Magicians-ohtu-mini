package noble.lukuvinkki;

import noble.lukuvinkki.dao.Tietokanta;
import java.sql.SQLException;

public class TietokantaSetup {

    public static Tietokanta alustaTestiTietokanta() {
        try {
            Tietokanta tietokanta = new Tietokanta("jdbc:sqlite:tietokanta/testaus.sqlite3");
            String sql1 = "DROP TABLE IF EXISTS kirja_vinkki;";
            String sql2 = "DROP TABLE IF EXISTS video_vinkki";
            String sql3 = "CREATE TABLE kirja_vinkki (id INTEGER PRIMARY KEY, kirjan_nimi TEXT, kirjan_kirjoittaja TEXT);";
            String sql4 = "CREATE TABLE video_vinkki (id INTEGER PRIMARY KEY, videon_nimi TEXT, videon_url TEXT)";
            tietokanta.yhteys().createStatement().execute(sql1);
            tietokanta.yhteys().createStatement().execute(sql2);
            tietokanta.yhteys().createStatement().execute(sql3);
            tietokanta.yhteys().createStatement().execute(sql4);
            return tietokanta;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
