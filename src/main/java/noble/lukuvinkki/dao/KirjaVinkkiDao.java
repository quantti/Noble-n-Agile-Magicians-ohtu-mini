package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class KirjaVinkkiDao implements Dao<KirjaVinkki> {
    private Tietokanta tietokanta;
    private Connection yhteys;

    public KirjaVinkkiDao(Tietokanta tietokanta) {
        this.tietokanta = tietokanta;
        try {
            this.yhteys = tietokanta.yhteys();
        } catch (SQLException ex) {
            System.out.println("Tietokanta yhteydessä havaittiin virhe: " + ex.getLocalizedMessage());
        }
    }

    @Override
    public boolean tallenna(KirjaVinkki vinkki) {
        String sql = String.format("INSERT INTO kirja_vinkki(kirjan_nimi, kirjan_kirjoittaja) VALUES ('%s', '%s')", vinkki.getNimi(), vinkki.getKirjoittaja());
        try {
            Statement kysely = yhteys.createStatement();
            return kysely.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Kirjan tallennuksessa tapahtui virhe: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public Vinkki haeYksi(String id) {
        try {
            String query = "SELECT * FROM kirja_vinkki WHERE id = ?";
            PreparedStatement preparedStatement = yhteys.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            Vinkki vinkki = new KirjaVinkki();
            if (rs.next()) {
                String nimi = rs.getString("kirjan_nimi");
                String kirjoittaja = rs.getString("kirjan_kirjoittaja");
                vinkki.setNimi(nimi);
                vinkki.setKirjoittaja(kirjoittaja);
                vinkki.setId(Integer.parseInt(id));
                return vinkki;
            }
        } catch (SQLException ex) {
            System.out.println("Tietoa hakiessa tapahtui virhe: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public List<Vinkki> haeKaikki() {
        List<Vinkki> kirjavinkit = new ArrayList<>();
        try {
            String query = "SELECT * FROM kirja_vinkki";
            PreparedStatement preparedStatement = yhteys.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Vinkki vinkki = new KirjaVinkki();
                vinkki.setId(rs.getInt("id"));
                vinkki.setNimi(rs.getString("kirjan_nimi"));
                vinkki.setKirjoittaja(rs.getString("kirjan_kirjoittaja"));
                kirjavinkit.add(vinkki);
            }
            return kirjavinkit;
        } catch (SQLException ex) {
            System.out.println("Tietoa hakiessa tapahtui virhe: " + ex.getMessage());
        }
        return null;
    }

    @Override
    public boolean poistaVinkki(String id) {
        String sql = "DELETE FROM kirja_vinkki WHERE id=" + id;
        try {
            Statement kysely = yhteys.createStatement();
            return kysely.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Poistaminen epännistui" + ex.getMessage());
        }
        return false;
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) {
        int id = vinkki.getId();
        String nimi = vinkki.getNimi();
        String kirjoittaja = vinkki.getKirjoittaja();
        String sql = "UPDATE kirja_vinkki SET kirjan_nimi = ?, kirjan_kirjoittaja = ? WHERE id = ?";
        try {
            PreparedStatement kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            kysely.setString(2, kirjoittaja);
            kysely.setInt(3, id);
            return kysely.execute();
        } catch (SQLException ex) {
            System.out.println("Virhe päivityksessä: " + ex.getMessage());
        }
        return false;
    }
}
