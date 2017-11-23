package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

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
            yhteys.close();
        } catch (SQLException ex) {
            System.out.println("Kirjan tallennuksessa tapahtui virhe: " + ex.getMessage());
        }
    }

    @Override
    public Vinkki haeYksi(String id) {
        try {
            Connection yhteys = tietokanta.yhteys();
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
            Connection yhteys = tietokanta.yhteys();
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
    public void poistaVinkki(String id) {
        String sql = "DELETE FROM kirja_vinkki WHERE id=" + id;
        try {
            Connection yhteys = tietokanta.yhteys();
            Statement kysely = yhteys.createStatement();
            kysely.executeUpdate(sql);
            yhteys.close();
        } catch (SQLException ex) {
            System.out.println("Poistaminen epännistui" + ex.getMessage());
        }
    }

    @Override
    public void muokkaa(Vinkki vinkki) {
        int id = vinkki.getId();
        String nimi = vinkki.getNimi();
        String kirjoittaja = vinkki.getKirjoittaja();
        String sql = "UPDATE kirja_vinkki SET kirjan_nimi = ?, kirjan_kirjoittaja = ? WHERE id = ?";

        try {
            Connection yhteys = tietokanta.yhteys();
            PreparedStatement kysely = yhteys.prepareStatement(sql);
            kysely.setString(1, nimi);
            kysely.setString(2, kirjoittaja);
            kysely.setInt(3, id);
            kysely.executeUpdate();
            yhteys.close();
        } catch (SQLException ex) {
            System.out.println("Virhe päivityksessä: " + ex.getMessage());
        }

    }
}
