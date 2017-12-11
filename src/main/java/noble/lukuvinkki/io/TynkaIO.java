package noble.lukuvinkki.io;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kari
 */
public class TynkaIO implements IO {

    private final List<String> rivit;
    private int i;
    private final ArrayList<String> tulosteet;
    
    public TynkaIO() {
        this.rivit = new ArrayList<>();
        this.tulosteet = new ArrayList<>();
    }

    public TynkaIO(List<String> values) {
        this.rivit = values;
        tulosteet = new ArrayList<>();
    }

    @Override
    public void tulosta(String toPrint) {
        tulosteet.add(toPrint);
    }

    public ArrayList<String> getTulosteet() {
        return tulosteet;
    }

    @Override
    public String lueRivi(String prompt) {
        tulosta(prompt);
        if (i < rivit.size()) {
            return rivit.get(i++);
        }
        return "";
    }
}
