package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;

public class KirjaVinkkiDao implements Dao<KirjaVinkki> {

    public Tietokanta tietokanta;

    public KirjaVinkkiDao(Tietokanta tietokanta) {
        this.tietokanta = tietokanta;
    }

    @Override
    public void tallenna(KirjaVinkki vinkki) {
        String sql = String.format("INSERT INTO kirja_vinkki(kirjan_nimi, kirjan_kirjoittaja) VALUES ('%s', '%s')", vinkki.getNimi(), vinkki.getKirjoittaja());
        try {
            Connection yhteys = tietokanta.yhteys();
            Statement kysely = yhteys.createStatement();
            kysely.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println("Kirjan tallennuksessa tapahtui virhe: " + ex.getMessage());
        }
    }

}
