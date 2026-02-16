package me.tr.trfiles.management.connection.downloader;

import java.io.IOException;
import java.net.URL;

public interface Downloader<D> {

    D downloadOrThrown(URL url, D destination) throws IOException;

    default D download(URL url, D destination) {
        try {
            return downloadOrThrown(url, destination);
        } catch (IOException ignored) {
            return destination;
        }
    }
}
