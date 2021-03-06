package noble.lukuvinkki.main;

import java.util.HashMap;
import noble.lukuvinkki.io.IO;
import noble.lukuvinkki.io.KayttoliittymaInterface;

public class KomentoFactory {

    private IO io;
    private KayttoliittymaInterface kayttisIO;
    private HashMap<String, Komento> listauskomennot;
    private HashMap<String, Komento> muokkauskomennot;
    private HashMap<String, Komento> poistokomennot;
    private HashMap<String, Komento> paavalikonkomennot;

    public KomentoFactory(IO io, KayttoliittymaInterface kayttisIO) {
        this.io = io;
        this.kayttisIO = kayttisIO;
        listauskomennot = new HashMap();
        muokkauskomennot = new HashMap();
        poistokomennot = new HashMap();
        paavalikonkomennot = new HashMap();
        alustaListausKomennot();
        alustaMuokkausKomennot();
        alustaPoistoKomennot();
        alustaPaavalikonKomennot();

    }

    private void alustaListausKomennot() {
        listauskomennot.put("1", new ListaaKaikkiKomento("listaaKaikki", "1", "Listaa kaikki vinkit", io, kayttisIO));
        listauskomennot.put("2", new ListaaKirjatKomento("listaaKirjat", "2", "Listaa kaikki kirjat", io, kayttisIO));
        listauskomennot.put("3", new ListaaVideotKomento("listaaVideot", "3", "Listaa kaikki videot", io, kayttisIO));
        listauskomennot.put("4", new ListaaPodcastitKomento("listaaPodcastit", "4", "Listaa kaikki podcastit", io, kayttisIO));
        listauskomennot.put("5", new ListaaBlogitKomento("listaaBlogit", "5", "Listaa kaikki blogit", io, kayttisIO));
        listauskomennot.put("6", new HaeOtsikollaKomento("haeOtsikolla", "6", "Hae otsikolla", io, kayttisIO));       
        listauskomennot.put("7", new HaeTageillaKomento("haeTageilla", "7", "Hae tageilla", io, kayttisIO));

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

    private void alustaPoistoKomennot() {
        poistokomennot.put("1", new PoistaKirja("poistaKirja", "1", "Poista kirjavinkki", io, kayttisIO));
        poistokomennot.put("2", new PoistaPodcast("poistaPodcast", "2", "Poista podcastvinkki", io, kayttisIO));
        poistokomennot.put("3", new PoistaVideo("poistaVideo", "3", "Poista videovinkki", io, kayttisIO));
        poistokomennot.put("4", new PoistaBlogi("poistaBlogi", "4", "Poista blogivinkki", io, kayttisIO));
    }

    public HashMap<String, Komento> getPoistokomennot() {
        return poistokomennot;
    }

    private void alustaPaavalikonKomennot() {
        paavalikonkomennot.put("a", new Valikko("listaaVinkit", "a", "Listaa vinkit", io, kayttisIO, this.listauskomennot));
        paavalikonkomennot.put("b", new LisaaKirja("lisaaKirja", "b", "Lisää kirjavinkki", io, kayttisIO));
        paavalikonkomennot.put("c", new LisaaPodcast("lisaaPodcast", "c", "Lisää podcastvinkki", io, kayttisIO));
        paavalikonkomennot.put("d", new LisaaVideo("lisaaVideo", "d", "Lisää videovinkki", io, kayttisIO));
        paavalikonkomennot.put("e", new LisaaBlogi("lisaaBLogi", "e", "Lisää blogivinkki", io, kayttisIO));
        paavalikonkomennot.put("f", new Valikko("muokkaaVinkit", "f", "Muokkaa vinkkejä", io, kayttisIO, this.muokkauskomennot));
        paavalikonkomennot.put("g", new Valikko("poistaVinkit", "g", "Poista vinkkejä", io, kayttisIO, this.poistokomennot));
    }

    public HashMap<String, Komento> getPaavalikonkomennot() {
        return paavalikonkomennot;
    }

}
