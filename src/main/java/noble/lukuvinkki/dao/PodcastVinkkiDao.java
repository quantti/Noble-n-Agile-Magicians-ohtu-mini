package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.tietokohteet.PodcastVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

public class PodcastVinkkiDao implements Dao<PodcastVinkki> {

    private final Connection yhteys;

    public PodcastVinkkiDao(Tietokanta tietokanta) {
        this.yhteys = tietokanta.yhteys();
    }

    @Override
    public int tallenna(PodcastVinkki vinkki) throws SQLException {
        int id = -1;
        String sql = "INSERT INTO podcast_vinkki(podcastin_nimi, podcastin_url) VALUES (?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, vinkki.getNimi());
        kysely.setString(2, vinkki.getUrl());
        kysely.executeUpdate();
        ResultSet rs = yhteys.createStatement().executeQuery("SELECT last_insert_rowid() as id");
        if (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }

    @Override
    public PodcastVinkki haeYksi(int id) throws SQLException {
        String query = "SELECT * FROM podcast_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        if (rs.next()) {
            PodcastVinkki podcastVinkki = keraa(rs);
            return podcastVinkki;
        }
        return null;
    }

    private PodcastVinkki keraa(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nimi = rs.getString("podcastin_nimi");
        String url = rs.getString("podcastin_url");
        return new PodcastVinkki(id, nimi, url);
    }

    @Override
    public List haeKaikki() throws SQLException {
        List<Vinkki> podcastVinkit = new ArrayList<>();
        String query = "SELECT * FROM podcast_vinkki";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            PodcastVinkki vinkki = new PodcastVinkki();
            vinkki.setId(rs.getInt("id"));
            vinkki.setNimi(rs.getString("podcastin_nimi"));
            vinkki.setUrl(rs.getString("podcastin_url"));
            podcastVinkit.add(vinkki);
        }
        return podcastVinkit;
    }

    @Override
    public boolean poistaVinkki(int id) throws SQLException {
        String sql = "DELETE FROM podcast_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        int tulos = kysely.executeUpdate();
        return tulos > 0;
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        PodcastVinkki podcastVinkki = (PodcastVinkki) vinkki;
        int id = podcastVinkki.getId();
        String nimi = podcastVinkki.getNimi();
        String url = podcastVinkki.getUrl();
        String sql = "UPDATE podcast_vinkki SET podcastin_nimi = ?, podcastin_url = ? WHERE id = ?";
        PreparedStatement paivitys = yhteys.prepareStatement(sql);
        paivitys.setString(1, nimi);
        paivitys.setString(2, url);
        paivitys.setInt(3, id);
        int tulos = paivitys.executeUpdate();
        return tulos > 0;
    }

    @Override
    public List<Vinkki> haeOtsikolla(String hakutermi) throws SQLException {
        List<Vinkki> vinkit = new ArrayList<>();
        String query = "SELECT * FROM podcast_vinkki WHERE podcastin_nimi LIKE ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setString(1, "%" + hakutermi + "%");
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {            
            PodcastVinkki podcastVinkki = keraa(rs);
            vinkit.add(podcastVinkki);
        }
        return vinkit;
    }

}
