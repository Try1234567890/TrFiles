package me.tr.trfiles.implementations.jpg;

import me.tr.trfiles.implementations.TrImageEntry;
import me.tr.trfiles.implementations.TrImage;
import me.tr.trfiles.memory.MemoryImage;
import me.tr.trfiles.memory.MemoryImageEntry;
import me.tr.trfiles.memory.implementations.MemoryJPG;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class JPG extends TrImage {
    public static final TrImageEntry ENTRY = new TrImageEntry() {
        @Override
        public MemoryImageEntry getMemoryEntry() {
            return MemoryJPG.ENTRY;
        }

        @Override
        public String[] getExtensions() {
            return new String[]{".jpg", ".jpeg"};
        }

        @Override
        public TrImage newInstance(File file) throws IOException {
            return JPG.fromOrThrown(file);
        }
    };


    protected JPG(File file, MemoryImage memory) {
        super(ENTRY, file, memory);
    }
    

    public static JPG fromOrThrown(File file) throws IOException {
        return new JPG(file, new MemoryJPG(file));
    }

    public static JPG fromOrThrown(String file) throws IOException {
        return fromOrThrown(new File(file));
    }

    public static Optional<JPG> from(File file) {
        try {
            return Optional.of(fromOrThrown(file));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<JPG> from(String file) {
        return from(new File(file));
    }

    public static JPG fromOrNull(File file) {
        return from(file).orElse(null);
    }

    public static JPG fromOrNull(String file) {
        return from(file).orElse(null);
    }

    public static boolean isValid(File file) {
        return ENTRY.isValid(file);
    }
}
