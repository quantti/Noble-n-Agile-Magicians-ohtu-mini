package noble.lukuvinkki.tietokohteet;


public class KirjaVinkki implements Vinkki {
    private int id;
    private String nimi;
    private String kirjoittaja;

    public KirjaVinkki() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getKirjoittaja() {
        return kirjoittaja;
    }

    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KirjaVinkki{");
        sb.append("nimi = ").append(getNimi());
        sb.append(", kirjoittaja = ").append(getKirjoittaja());
        return sb.append("}").toString();
    }
}
