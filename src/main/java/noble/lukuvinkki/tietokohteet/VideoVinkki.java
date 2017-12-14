package noble.lukuvinkki.tietokohteet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

public class VideoVinkki implements Vinkki {

    private int id;
    private String nimi;
    private String url;
    private String tekija;
    private List<String> tagit;

    public VideoVinkki() {
        this.tagit = new ArrayList<>();
    }

    public VideoVinkki(int id, String nimi, String url) {
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
    public String toString() {
        String tagitS = "";
        if (getTagit() != null) {
            tagitS = StringUtils.join(getTagit(), ",");
        }
        return "\nId: " + this.getId() + "\n" + this.getNimi() + ": " + this.getUrl() + "\nTagit: " + tagitS;
    }

    @Override
    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    @Override
    public String getTekija() {
        return this.tekija;
    }

    /**
     * @return the tagit
     */
    @Override
    public List<String> getTagit() {
        return tagit;
    }

    /**
     * @param tagit the tagit to set
     */
    @Override
    public void setTagit(List<String> tagit) {
        this.tagit = tagit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VideoVinkki other = (VideoVinkki) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nimi, other.nimi)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        return true;
    }
    
}
