package me.tr.trfiles.management.connection.uploader;

import java.io.IOException;
import java.net.URL;

public interface Uploader<D> {

    void uploadOrThrown(D source, URL url) throws IOException;

    default void upload(D source, URL url) {
        try {
            uploadOrThrown(source, url);
        } catch (IOException ignored) {
        }
    }
}