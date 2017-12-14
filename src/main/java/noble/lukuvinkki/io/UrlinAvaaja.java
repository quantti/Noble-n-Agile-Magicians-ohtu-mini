package noble.lukuvinkki.io;

import java.awt.Desktop;
import java.net.URI;

public class UrlinAvaaja {

    private String url;

    public UrlinAvaaja(String url) {
        this.url = urlinKorjaaja(url);
    }

    public void avaa() throws Exception {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI(this.url));
    }
    
    private String urlinKorjaaja(String url) {
        if (url.startsWith("http")) {
            return url;
        }
        url = "http://" + url;
        return url;
    }
}
