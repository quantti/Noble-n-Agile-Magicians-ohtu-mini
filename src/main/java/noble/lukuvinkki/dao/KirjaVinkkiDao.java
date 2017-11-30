package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.tietokohteet.KirjaVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class KirjaVinkkiDao implements Dao<KirjaVinkki> {

    
    private final Connection yhteys;

    public KirjaVinkkiDao(Tietokanta tietokanta) {
        this.yhteys = tietokanta.yhteys();

    }

    private KirjaVinkki keraa(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nimi = rs.getString("kirjan_nimi");
        String kirjoittaja = rs.getString("kirjan_kirjoittaja");
        return new KirjaVinkki(id, nimi, kirjoittaja);
    }

    @Override
    public int tallenna(KirjaVinkki vinkki) throws SQLException {
        int id = -1;
        String sql = "INSERT INTO kirja_vinkki(kirjan_nimi, kirjan_kirjoittaja) VALUES (?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, vinkki.getNimi());
        kysely.setString(2, vinkki.getTekija());
        kysely.executeUpdate();
        ResultSet rs = yhteys.createStatement().executeQuery("SELECT last_insert_rowid() as id");
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }
    
    @Override
    public KirjaVinkki haeYksi(int id) throws SQLException {
        String query = "SELECT * FROM kirja_vinkki WHERE id = ?";
        PreparedStatement preparedStatement = yhteys.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        
        if (rs.next()) {
            KirjaVinkki vinkki = keraa(rs);
            return vinkki;
        }
        return null;
    }

    @Override
    public List<Vinkki> haeKaikki() throws SQLException {
        List<Vinkki> kirjavinkit = new ArrayList<>();
        String query = "SELECT * FROM kirja_vinkki";
        PreparedStatement preparedStatement = yhteys.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            KirjaVinkki vinkki = new KirjaVinkki();
            vinkki.setId(rs.getInt("id"));
            vinkki.setNimi(rs.getString("kirjan_nimi"));
            vinkki.setTekija(rs.getString("kirjan_kirjoittaja"));
            kirjavinkit.add(vinkki);
        }
        return kirjavinkit;
    }

    @Override
    public boolean poistaVinkki(int id) throws SQLException {
        String sql = "DELETE FROM kirja_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        int result = kysely.executeUpdate();
        return result > 0;
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        int id = vinkki.getId();
        String nimi = vinkki.getNimi();
        String kirjoittaja = ((KirjaVinkki) vinkki).getTekija();
        String sql = "UPDATE kirja_vinkki SET kirjan_nimi = ?, kirjan_kirjoittaja = ? WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, nimi);
        kysely.setString(2, kirjoittaja);
        kysely.setInt(3, id);
        int result = kysely.executeUpdate();
        return result > 0;
    }
}
