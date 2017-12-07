package noble.lukuvinkki;

import noble.lukuvinkki.dao.Tietokanta;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class TietokantaSetup {

    public static Tietokanta alustaTestiTietokanta(String osoite) {
        try {
            Tietokanta tietokanta = new Tietokanta(osoite);
	    List<String> sql = new ArrayList<>();
            sql.add("DROP TABLE IF EXISTS kirja_vinkki;");
            sql.add("DROP TABLE IF EXISTS video_vinkki");
            sql.add("DROP TABLE IF EXISTS podcast_vinkki");
	    sql.add("DROP TABLE IF EXISTS vinkki");
	    sql.add("CREATE TABLE vinkki (id INTEGER PRIMARY KEY, otsikko TEXT, kuvaus TEXT)");
            sql.add("CREATE TABLE kirja_vinkki (id INTEGER PRIMARY KEY, kirjan_nimi TEXT, kirjan_kirjoittaja TEXT);");
            sql.add("CREATE TABLE video_vinkki (id INTEGER PRIMARY KEY, videon_nimi TEXT, videon_url TEXT)");
            sql.add("CREATE TABLE podcast_vinkki (id INTEGER PRIMARY KEY, podcastin_nimi TEXT, podcastin_url TEXT)");
	    for (String s : sql) {
		tietokanta.yhteys().createStatement().execute(s);
	    }
	    return tietokanta;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
