package me.tr.trfiles.file.management.reader;

import java.io.IOException;
import java.util.Optional;

public interface Reader<T, R> {

    R readOrThrown(T file) throws IOException;

    default Optional<R> read(T file) {
        try {
            return Optional.of(readOrThrown(file));
        } catch (IOException ignore) {
        }
        return Optional.empty();
    }

    default R readOrNull(T file) {
        return read(file).orElse(null);
    }

    default R readOrDefault(T file, R def) {
        return read(file).orElse(def);
    }
}
