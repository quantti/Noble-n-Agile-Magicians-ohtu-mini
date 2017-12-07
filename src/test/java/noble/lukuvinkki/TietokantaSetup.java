package noble.lukuvinkki;

import noble.lukuvinkki.dao.Tietokanta;
import java.sql.SQLException;

public class TietokantaSetup {

    public static Tietokanta alustaTestiTietokanta(String osoite) {
        try {
            Tietokanta tietokanta = new Tietokanta(osoite);
            String sql1 = "DROP TABLE IF EXISTS kirja_vinkki;";
            String sql2 = "CREATE TABLE kirja_vinkki (id INTEGER PRIMARY KEY, kirjan_nimi TEXT, kirjan_kirjoittaja TEXT);";
            String sql3 = "CREATE TABLE podcast_vinkki (id INTEGER PRIMARY KEY, kirjan_nimi TEXT, kirjan_kirjoittaja TEXT);";
            tietokanta.yhteys().createStatement().execute(sql1);
            tietokanta.yhteys().createStatement().execute(sql2);
            tietokanta.yhteys().createStatement().execute(sql3);
            return tietokanta;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
