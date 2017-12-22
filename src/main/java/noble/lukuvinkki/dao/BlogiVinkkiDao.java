/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import noble.lukuvinkki.tietokohteet.BlogiVinkki;
import noble.lukuvinkki.tietokohteet.VideoVinkki;
import noble.lukuvinkki.tietokohteet.Vinkki;

/**
 *
 * @author vankari
 */
public class BlogiVinkkiDao implements Dao<BlogiVinkki> {

    private final Connection yhteys;

    public BlogiVinkkiDao(Tietokanta tietokanta) {
        this.yhteys = tietokanta.yhteys();
    }

    @Override
    public int tallenna(BlogiVinkki vinkki) throws SQLException {
        int id = -1;

        if (vinkki.getUrl().isEmpty() || vinkki.getNimi().isEmpty()) {
            return -1;
        }

        String sql = "INSERT INTO blogi_vinkki(blogin_nimi, blogin_url) VALUES (?, ?)";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setString(1, vinkki.getNimi());
        kysely.setString(2, vinkki.getUrl());
        kysely.executeUpdate();
        ResultSet rs = yhteys.createStatement().executeQuery("SELECT last_insert_rowid() as id");
        if (rs.next()) {
            id = rs.getInt("id");
        }
        vinkki.setId(id);
        tallennaTagit(vinkki);
        return id;
    }

    @Override
    public BlogiVinkki haeYksi(int id) throws SQLException {
        String query = "SELECT * FROM blogi_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setInt(1, id);
        ResultSet rs = kysely.executeQuery();
        if (rs.next()) {
            BlogiVinkki blogiVinkki = keraa(rs);
            return blogiVinkki;
        }
        return null;
    }

    private BlogiVinkki keraa(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nimi = rs.getString("blogin_nimi");
        String url = rs.getString("blogin_url");
        BlogiVinkki v = new BlogiVinkki(id, nimi, url);
        v.setTagit(haeTagit(v));
        return v;
    }

    @Override
    public List<Vinkki> haeKaikki() throws SQLException {
        List<Vinkki> blogiVinkit = new ArrayList<>();
        String query = "SELECT * FROM blogi_vinkki";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            BlogiVinkki vinkki = keraa(rs);
            blogiVinkit.add(vinkki);
        }
        return blogiVinkit;
    }

    @Override
    public boolean poistaVinkki(int id) throws SQLException {
        String sql = "DELETE FROM blogi_vinkki WHERE id = ?";
        PreparedStatement kysely = yhteys.prepareStatement(sql);
        kysely.setInt(1, id);
        int tulos = kysely.executeUpdate();
        return tulos > 0;
    }

    @Override
    public boolean muokkaa(Vinkki vinkki) throws SQLException {
        BlogiVinkki blogiVinkki = (BlogiVinkki) vinkki;
        int id = blogiVinkki.getId();
        String nimi = blogiVinkki.getNimi();
        String url = blogiVinkki.getUrl();
        String sql = "UPDATE blogi_vinkki SET blogin_nimi = ?, blogin_url = ? WHERE id = ?";
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
        String query = "SELECT * FROM blogi_vinkki WHERE blogin_nimi LIKE ?";
        PreparedStatement kysely = yhteys.prepareStatement(query);
        kysely.setString(1, "%" + hakutermi + "%");
        ResultSet rs = kysely.executeQuery();
        while (rs.next()) {
            BlogiVinkki vinkki = keraa(rs);
            vinkit.add(vinkki);
        }
        return vinkit;
    }

    private List<String> haeTagit(BlogiVinkki vinkki) throws SQLException {
        List<String> tagit = new ArrayList<>();
        String sql = "SELECT tagi.*"
                + " FROM blogi_vinkki, blogi_tagit, tagi"
                + " WHERE blogi_vinkki.id = ?"
                + " AND blogi_tagit.blogi_id = blogi_vinkki.id"
                + " AND blogi_tagit.tagi_id = tagi.id";
        PreparedStatement st = yhteys.prepareStatement(sql);
        st.setInt(1, vinkki.getId());
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            tagit.add(rs.getString("tagin_nimi"));
        }
        return tagit;
    }

    public List<Vinkki> haeTageilla(List<String> tagit) throws SQLException {
        if (tagit.isEmpty()) {
            return new ArrayList<>();
        }
        StringBuilder build = new StringBuilder("SELECT DISTINCT blogi_vinkki.* FROM blogi_vinkki,tagi,blogi_tagit"
                + " WHERE blogi_vinkki.id = blogi_tagit.blogi_id"
                + " AND tagi.id = blogi_tagit.tagi_id"
                + " AND tagi.tagin_nimi LIKE ?");
        for (int i = 1; i < tagit.size(); i++) { //ensimmäinen tagi on jo käytetty
            build.append(" INTERSECT SELECT DISTINCT blogi_vinkki.* FROM blogi_vinkki,tagi,blogi_tagit"
                    + " WHERE blogi_vinkki.id = blogi_tagit.blogi_id"
                    + " AND tagi.id = blogi_tagit.tagi_id"
                    + " AND tagi.tagin_nimi LIKE ?");
        }
        PreparedStatement st = yhteys.prepareStatement(build.toString());
        for (int i = 1; i <= tagit.size(); i++) {
            st.setString(i, tagit.get(i - 1));
        }
        ResultSet rs = st.executeQuery();
        List<Vinkki> vinkit = new ArrayList<>();
        while (rs.next()) {
            BlogiVinkki v = keraa(rs);
            vinkit.add(v);
        }
        return vinkit;
    }

    private void tallennaTagit(BlogiVinkki vinkki) throws SQLException {
        for (String s : vinkki.getTagit()) {
            String sql = "INSERT OR IGNORE INTO tagi(tagin_nimi) VALUES (?)";
            PreparedStatement st = yhteys.prepareStatement(sql);
            st.setString(1, s);
            st.executeUpdate();
        }
        for (String s : vinkki.getTagit()) {
            String sql = "INSERT INTO blogi_tagit(blogi_id, tagi_id)"
                    + " SELECT ?, tagi.id FROM tagi WHERE tagi.tagin_nimi LIKE ?";
            PreparedStatement st = yhteys.prepareStatement(sql);
            st.setInt(1, vinkki.getId());
            st.setString(2, s);
            st.executeUpdate();
        }
    }

}
