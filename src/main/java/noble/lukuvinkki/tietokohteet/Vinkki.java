package noble.lukuvinkki.tietokohteet;

import java.util.List;

public interface Vinkki {

    public String getNimi();
    //todo

    public void setNimi(String nimi);

    public void setId(int aInt);

    public int getId();
    
    public void setTekija(String tekija);
    
    public String getTekija();
    
    public List<String> getTagit();
    
    public void setTagit(List<String> tagit);
}
