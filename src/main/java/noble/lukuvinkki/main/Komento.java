package noble.lukuvinkki.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;
import noble.lukuvinkki.tietokohteet.Vinkki;

public abstract class Komento {

    protected String nimi;
    protected String komento;
    protected String teksti;
    protected IO io;
    protected KayttoliittymaInterface kayttisIO;

    public Komento(String nimi, String komento, String teksti, IO io, KayttoliittymaInterface kayttisIO) {
        this.nimi = nimi;
        this.komento = komento;
        this.teksti = teksti;
        this.io = io;
        this.kayttisIO = kayttisIO;
    }

    public abstract void komento();

    protected void virhe(Exception e) {
        io.tulosta("Virhe: " + e.getMessage());
    }

    protected boolean tarkistaOnkoListaTyhjaTaiNull(List<Vinkki> lista) {
        if (lista == null || lista.isEmpty()) {
            io.tulosta("Vinkkejä ei löytynyt\n");
            return true;
        }
        return false;
    }

    protected List<String> lisaaTagit() {
        List<String> tagit = new ArrayList<>();
        String tag = "";
        while (true) {
            tag = io.lueRivi("Syötä tagi, tyhjä lopettaa.");
            if (tag.equalsIgnoreCase("")) {
                break;
            }
            tagit.add(tag);
        }
        return tagit;
    }

    protected void alaValikko(HashMap<String, Komento> komennot) {
        for (Komento komento : komennot.values()) {
            io.tulosta(komento.toString());
        }
        int viimInd = komennot.size();
        io.tulosta(viimInd + ") Palaa päävalikkoon");
    }
    
    protected void alaValikonValinnat(HashMap<String, Komento> komennot, String valinta) {
        Komento komento = komennot.get(valinta);
        if (komento == null) {
            io.tulosta("Väärä valinta");
            return;
        }
        komento.komento();
        
    }
    
    @Override
    public String toString() {
        return komento + ") " + teksti;
    }
}
