package me.tr.trfiles.management.writer;

import java.io.IOException;
import java.util.Optional;

public interface Writer<T, V> {

    void writeOrThrown(T out, V value) throws IOException;

    default void write(T out, V value) {
        try {
            writeOrThrown(out, value);
        } catch (IOException ignore) {
        }
    }
}
