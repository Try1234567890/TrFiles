package me.tr.trfiles.management.archives.zip.zipper;

import java.io.IOException;

public interface Zipper<T, V> {

    void zipOrThrown(T zip, V value) throws IOException;

    default void zip(T zip, V value) {
        try {
            zipOrThrown(zip, value);
        } catch (IOException ignore) {
        }
    }
}
