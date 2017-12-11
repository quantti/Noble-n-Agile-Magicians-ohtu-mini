package noble.lukuvinkki.main;

import java.util.HashMap;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;

public class KomentoFactory {

    private IO io;
    private KayttoliittymaInterface kayttisIO;
    private HashMap<String, Komento> listauskomennot;
    private HashMap<String, Komento> muokkauskomennot;

    public KomentoFactory(IO io, KayttoliittymaInterface kayttisIO) {
        this.io = io;
        this.kayttisIO = kayttisIO;
        listauskomennot = new HashMap();
        muokkauskomennot = new HashMap();
        alustaListausKomennot();
        alustaMuokkausKomennot();

    }

    private void alustaListausKomennot() {
        listauskomennot.put("1", new ListaaKaikkiKomento("listaaKaikki", "1", "Listaa kaikki vinkit", io, kayttisIO));
        listauskomennot.put("2", new ListaaKirjatKomento("listaaKirjat", "2", "Listaa kaikki kirjat", io, kayttisIO));
        listauskomennot.put("3", new ListaaVideotKomento("listaaVideot", "3", "Listaa kaikki videot", io, kayttisIO));
        listauskomennot.put("4", new ListaaPodcastitKomento("listaaPodcastit", "4", "Listaa kaikki podcastit", io, kayttisIO));
        listauskomennot.put("5", new HaeTageillaKomento("haeTageilla", "5", "Hae tageilla", io, kayttisIO));
        listauskomennot.put("6", new HaeOtsikollaKomento("haeOtsikolla", "6", "Hae otsikolla", io, kayttisIO));
    }

    public HashMap<String, Komento> getListauskomennot() {
        return listauskomennot;
    }

    private void alustaMuokkausKomennot() {
        muokkauskomennot.put("1", new MuokkaaKirjaa("muokkaaKirjaa", "1", "Muokkaa kirjavinkkiä", io, kayttisIO));
        muokkauskomennot.put("2", new MuokkaaVideota("muokkaaVideota", "2", "Muokkaa videovinkkiä", io, kayttisIO));
        muokkauskomennot.put("3", new MuokkaaPodcastia("muokkaaPodcastia", "3", "Muokkaa podcastvinkkiä", io, kayttisIO));
    }

    public HashMap<String, Komento> getMuokkauskomennot() {
        return muokkauskomennot;
    }

}
