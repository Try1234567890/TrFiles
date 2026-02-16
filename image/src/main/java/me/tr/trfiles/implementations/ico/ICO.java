package me.tr.trfiles.implementations.ico;

import me.tr.trfiles.implementations.TrImageEntry;
import me.tr.trfiles.implementations.TrImage;
import me.tr.trfiles.memory.MemoryImage;
import me.tr.trfiles.memory.MemoryImageEntry;
import me.tr.trfiles.memory.implementations.MemoryICO;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ICO extends TrImage {
    public static final TrImageEntry ENTRY = new TrImageEntry() {
        @Override
        public MemoryImageEntry getMemoryEntry() {
            return MemoryICO.ENTRY;
        }

        @Override
        public String[] getExtensions() {
            return new String[]{".ico"};
        }

        @Override
        public TrImage newInstance(File file) throws IOException {
            return ICO.fromOrThrown(file);
        }
    };


    protected ICO(File file, MemoryImage memory) {
        super(ENTRY, file, memory);
    }


    public static ICO fromOrThrown(File file) throws IOException {
        return new ICO(file, new MemoryICO(file));
    }

    public static ICO fromOrThrown(String file) throws IOException {
        return fromOrThrown(new File(file));
    }

    public static Optional<ICO> from(File file) {
        try {
            return Optional.of(fromOrThrown(file));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<ICO> from(String file) {
        return from(new File(file));
    }

    public static ICO fromOrNull(File file) {
        return from(file).orElse(null);
    }

    public static ICO fromOrNull(String file) {
        return from(file).orElse(null);
    }

    public static boolean isValid(File file) {
        return ENTRY.isValid(file);
    }
}
