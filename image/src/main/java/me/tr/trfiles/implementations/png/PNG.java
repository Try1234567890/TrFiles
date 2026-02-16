package me.tr.trfiles.implementations.png;

import me.tr.trfiles.implementations.TrImageEntry;
import me.tr.trfiles.implementations.TrImage;
import me.tr.trfiles.memory.MemoryImage;
import me.tr.trfiles.memory.MemoryImageEntry;
import me.tr.trfiles.memory.implementations.MemoryPNG;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class PNG extends TrImage {
    public static final TrImageEntry ENTRY = new TrImageEntry() {
        @Override
        public MemoryImageEntry getMemoryEntry() {
            return MemoryPNG.ENTRY;
        }

        @Override
        public String[] getExtensions() {
            return new String[]{".png"};
        }

        @Override
        public TrImage newInstance(File file) throws IOException {
            return PNG.fromOrThrown(file);
        }
    };

    protected PNG(File file, MemoryImage memory) {
        super(ENTRY, file, memory);
    }

    public static PNG fromOrThrown(File file) throws IOException {
        return new PNG(file, new MemoryPNG(file));
    }

    public static PNG fromOrThrown(String file) throws IOException {
        return fromOrThrown(new File(file));
    }

    public static Optional<PNG> from(File file) {
        try {
            return Optional.of(fromOrThrown(file));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<PNG> from(String file) {
        return from(new File(file));
    }

    public static PNG fromOrNull(File file) {
        return from(file).orElse(null);
    }

    public static PNG fromOrNull(String file) {
        return from(file).orElse(null);
    }

    public static boolean isValid(File file) {
        return ENTRY.isValid(file);
    }
}
