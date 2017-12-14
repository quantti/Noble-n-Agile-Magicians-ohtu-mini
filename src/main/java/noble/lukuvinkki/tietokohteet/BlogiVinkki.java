/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package noble.lukuvinkki.tietokohteet;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author vankari
 */
public class BlogiVinkki implements Vinkki {

    private int id;
    private String nimi;
    private String url;
    private String tekija;
    private List<String> tagit;

    public BlogiVinkki(int id, String nimi, String url) {
        this.id = id;
        this.nimi = nimi;
        this.url = url;
        this.tagit = new ArrayList<>();
    }

    public BlogiVinkki() {
        this(0, "testi", "www.tyhja.com");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getNimi() {
        return nimi;
    }

    @Override
    public String getTekija() {
        return tekija;
    }

    @Override
    public List<String> getTagit() {
        return tagit;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    @Override
    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    @Override
    public void setTagit(List<String> tagit) {
        this.tagit = tagit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    
    @Override
    public String toString() {
        String tagitS = "";
        if (getTagit() != null) {
            tagitS = StringUtils.join(getTagit(), ",");
        }
        return "\nId: " + this.getId() + "\n" + this.getNimi() + ": " + this.getUrl() + "\nTagit: " + tagitS;
    }
}
