package noble.lukuvinkki.tietokohteet;

import java.util.ArrayList;
import java.util.List;

public class PodcastVinkki implements Vinkki {

    private int id;
    private String nimi;
    private String url;
    private String tekija;
    private List<String> tagit;

    public PodcastVinkki() {

    }

    public PodcastVinkki(int id, String nimi, String url) {
        this.id = id;
        this.nimi = nimi;
        this.url = url;
        this.tagit = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getNimi() {
        return nimi;
    }

    @Override
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    //  @Override
    public String getUrl() {
        return url;
    }

    //  @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getTekija() {
        return this.tekija;
    }

    @Override
    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    @Override
    public String toString() {
        return "Id: " + this.getId() + "\n" + this.getNimi() + ": " + this.getUrl();
    }

    /**
     * @return the tagit
     */
    public List<String> getTagit() {
        return tagit;
    }

    /**
     * @param tagit the tagit to set
     */
    public void setTagit(List<String> tagit) {
        this.tagit = tagit;
    }
}
