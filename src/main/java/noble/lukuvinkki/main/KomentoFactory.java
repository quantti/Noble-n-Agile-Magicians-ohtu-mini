
package noble.lukuvinkki.main;

import java.util.HashMap;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;


public class KomentoFactory {
    private IO io;
    private KayttoliittymaInterface kayttisIO;
    private HashMap<String, Komento> listauskomennot;

    public KomentoFactory(IO io, KayttoliittymaInterface kayttisIO) {
        this.io = io;
        this.kayttisIO = kayttisIO;
        listauskomennot = new HashMap();
        
    }
    
    private void alustaListausKomennot() {
        listauskomennot.put("1", new ListaaKaikkiKomento("listaaKaikki", "1", "Listaa kaikki vinkit", io, kayttisIO));
        listauskomennot.put("2", new ListaaKirjatKomento("listaaKirjat", "2", "Listaa kaikki kirjat", io, kayttisIO));
        listauskomennot.put("3", new ListaaVideotKomento("listaaVideot", "3", "Listaa kaikki videot", io, kayttisIO));
        listauskomennot.put("4", new ListaaPodcastitKomento("listaaPodcastit", "4", "Listaa kaikki podcastit", io, kayttisIO));
        listauskomennot.put("7", new HaeOtsikollaKomento("haeOtsikolla", "7", "Hae otsikolla", io, kayttisIO));
    }

    public HashMap<String, Komento> getListauskomennot() {
        return listauskomennot;
    }
    
    
    
}
