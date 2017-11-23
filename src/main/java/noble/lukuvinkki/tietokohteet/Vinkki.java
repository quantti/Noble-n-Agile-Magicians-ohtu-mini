package noble.lukuvinkki.tietokohteet;

public interface Vinkki {

    public String getNimi();
    //todo

    public String getKirjoittaja();

    public void setKirjoittaja(String kirjoittaja);

    public void setNimi(String nimi);

    public void setId(int aInt);
    
    public int getId();
}
