package noble.lukuvinkki.tietokohteet;

public class KirjaVinkki implements Vinkki {

    private int id;
    private String nimi;
    private String kirjoittaja;

    public KirjaVinkki() {

    }

    public KirjaVinkki(int id, String nimi, String kirjoittaja) {
        this.id = id;
        this.nimi = nimi;
        this.kirjoittaja = kirjoittaja;
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

   // @Override
    public String getKirjoittaja() {
        return kirjoittaja;
    }

   // @Override
    public void setKirjoittaja(String kirjoittaja) {
        this.kirjoittaja = kirjoittaja;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        KirjaVinkki v = (KirjaVinkki) o;
        return this.getId() == v.getId() 
            && this.getNimi().equals(v.getNimi())
            && this.getKirjoittaja().equals(v.getKirjoittaja());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("KirjaVinkki{");
        sb.append("id = ").append(getId());
        sb.append(", nimi = ").append(getNimi());
        sb.append(", kirjoittaja = ").append(getKirjoittaja());
        return sb.append("}").toString();
    }
}
