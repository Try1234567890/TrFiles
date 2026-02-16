package me.tr.trfiles.management.io.writer.streaming;

import me.tr.trfiles.management.io.writer.Writer;

import java.io.IOException;

public interface Streaming<F, D> extends Writer<F, D> {
    void writeOrThrown(F from, D destination) throws IOException;

    default void write(F from, D destination) {
        try {
            writeOrThrown(from, destination);
        } catch (IOException ignore) {
        }
    }
}
