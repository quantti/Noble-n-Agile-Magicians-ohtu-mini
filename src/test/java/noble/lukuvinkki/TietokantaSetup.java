package noble.lukuvinkki;

import noble.lukuvinkki.dao.Tietokanta;
import java.sql.SQLException;

public class TietokantaSetup {

    public static Tietokanta alustaTestiTietokanta(String osoite) {
        try {
            Tietokanta tietokanta = new Tietokanta(osoite);
            String sql1 = "DROP TABLE IF EXISTS kirja_vinkki;";

            String sql2 = "DROP TABLE IF EXISTS video_vinkki";
            String sql3 = "DROP TABLE IF EXISTS podcast_vinkki";
            String sql4 = "CREATE TABLE kirja_vinkki (id INTEGER PRIMARY KEY, kirjan_nimi TEXT, kirjan_kirjoittaja TEXT);";
            String sql5 = "CREATE TABLE video_vinkki (id INTEGER PRIMARY KEY, videon_nimi TEXT, videon_url TEXT)";
            String sql6 = "CREATE TABLE podcast_vinkki (id INTEGER PRIMARY KEY, podcastin_nimi TEXT, podcastin_url TEXT)";
            tietokanta.yhteys().createStatement().execute(sql1);
            tietokanta.yhteys().createStatement().execute(sql2);
            tietokanta.yhteys().createStatement().execute(sql3);
            tietokanta.yhteys().createStatement().execute(sql4);
            tietokanta.yhteys().createStatement().execute(sql5);
            tietokanta.yhteys().createStatement().execute(sql6);

            return tietokanta;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
