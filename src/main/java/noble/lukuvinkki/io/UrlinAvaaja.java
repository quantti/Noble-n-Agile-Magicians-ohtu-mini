package noble.lukuvinkki.io;

import java.awt.Desktop;
import java.net.URI;

public class UrlinAvaaja {

    private String url;

    public UrlinAvaaja(String url) {
        this.url = url;
        avaaUrl();
    }

    private void avaaUrl() {
        try {
            if (Desktop.isDesktopSupported()) {
                // Windows
                Desktop.getDesktop().browse(new URI(url));
            } else {
                // Ubuntu
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("/usr/bin/firefox -new-window " + url);
            }
        } catch (Exception e) {

        }
    }

}
