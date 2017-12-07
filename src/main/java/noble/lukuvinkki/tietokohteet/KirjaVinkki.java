package noble.lukuvinkki.tietokohteet;

public class KirjaVinkki implements Vinkki {

    private int id;
    private String nimi;
    private String tekija;

    public KirjaVinkki() {

    }

    public KirjaVinkki(int id, String nimi, String tekija) {
        this.id = id;
        this.nimi = nimi;
        this.tekija = tekija;
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
            && this.tekija.equals(v.getTekija());
    }

    @Override
    public void setTekija(String tekija) {
        this.tekija = tekija;
    }

    @Override
    public String getTekija() {
        return tekija;
    }

    @Override
    public String toString() {
        return "Id: " + this.getId() + "\n" + this.getTekija() + ": " + this.getNimi() + "";
    }
}
