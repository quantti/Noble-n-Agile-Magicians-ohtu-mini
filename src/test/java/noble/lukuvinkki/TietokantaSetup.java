package noble.lukuvinkki;

import noble.lukuvinkki.dao.Tietokanta;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class TietokantaSetup {

    public static Tietokanta alustaTestiTietokanta(String osoite) {
        try {
            Tietokanta tietokanta = new Tietokanta(osoite);

            for (String s : sqlLauseet()) {
                tietokanta.yhteys().createStatement().execute(s);
            }
            return tietokanta;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> sqlLauseet() {
        List<String> sql = new ArrayList<>();
        sql.add("DROP TABLE IF EXISTS kirja_vinkki;");
        sql.add("DROP TABLE IF EXISTS video_vinkki");
        sql.add("DROP TABLE IF EXISTS podcast_vinkki");
        sql.add("DROP TABLE IF EXISTS podcast_tagit");
        sql.add("DROP TABLE IF EXISTS video_tagit");
        sql.add("DROP TABLE IF EXISTS kirja_tagit");
        sql.add("DROP TABLE IF EXISTS tagi");
        sql.add("CREATE TABLE kirja_vinkki (id INTEGER PRIMARY KEY, kirjan_nimi TEXT, kirjan_kirjoittaja TEXT);");
        sql.add("CREATE TABLE video_vinkki (id INTEGER PRIMARY KEY, videon_nimi TEXT, videon_url TEXT)");
        sql.add("CREATE TABLE podcast_vinkki (id INTEGER PRIMARY KEY, podcastin_nimi TEXT, podcastin_url TEXT)");
        sql.add("CREATE TABLE tagi(id INTEGER PRIMARY KEY, tagin_nimi TEXT)");
        sql.add("CREATE TABLE kirja_tagit(kirja_id INTEGER, tagi_id INTEGER, FOREIGN KEY(kirja_id) REFERENCES kirja_vinkki(id), FOREIGN KEY(tagi_id) REFERENCES tagi(id))");
        sql.add("CREATE TABLE video_tagit(video_id INTEGER, tagi_id INTEGER, FOREIGN KEY(video_id) REFERENCES video_vinkki(id), FOREIGN KEY(tagi_id) REFERENCES tagi(id))");
        sql.add("CREATE TABLE podcast_tagit(podcast_id INTEGER, tagi_id INTEGER, FOREIGN KEY(podcast_id) REFERENCES podcast_vinkki(id), FOREIGN KEY(tagi_id) REFERENCES tagi(id))");
        return sql;
    }
}
