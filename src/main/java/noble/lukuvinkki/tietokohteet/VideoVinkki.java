package noble.lukuvinkki.tietokohteet;

public class VideoVinkki implements Vinkki {

    private int id;
    private String nimi;
    private String url;

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
        final StringBuilder sb = new StringBuilder("KirjaVinkki{");
        sb.append("nimi = ").append(getNimi());
        sb.append(", url = ").append(getUrl());
        return sb.append("}").toString();
    }
}
