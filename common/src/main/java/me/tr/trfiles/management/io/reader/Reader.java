package me.tr.trfiles.management.io.reader;

import java.io.IOException;
import java.util.Optional;

public interface Reader<T, R> {

    R readOrThrown(T file, long from, long to) throws IOException;

    default R readOrThrown(T file, long from) throws IOException {
        return readOrThrown(file, from, -1);
    }

    default R readOrThrown(T file) throws IOException {
        return readOrThrown(file, 0);
    }

    default Optional<R> read(T file, long from, long to) {
        try {
            return Optional.of(readOrThrown(file, from, to));
        } catch (IOException ignore) {
        }
        return Optional.empty();
    }

    default Optional<R> read(T file, long from) {
        return read(file, from, -1);
    }

    default Optional<R> read(T file) {
        return read(file, 0);
    }

    default R readOrNull(T file, long from, long to) {
        return read(file, from, to).orElse(null);
    }

    default R readOrNull(T file, long from) {
        return readOrNull(file, from, -1);
    }

    default R readOrNull(T file) {
        return readOrNull(file, 0);
    }

    default R readOrDefault(T file, long from, long to, R def) {
        return read(file, from, to).orElse(def);
    }

    default R readOrDefault(T file, long from, R def) {
        return readOrDefault(file, from, -1, def);
    }

    default R readOrDefault(T file, R def) {
        return readOrDefault(file, 0, def);
    }
}
