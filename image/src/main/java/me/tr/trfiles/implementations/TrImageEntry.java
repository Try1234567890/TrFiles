package me.tr.trfiles.implementations;

import me.tr.trfiles.memory.MemoryImageEntry;

import java.io.File;
import java.io.IOException;

public interface TrImageEntry {

    MemoryImageEntry getMemoryEntry();

    String[] getExtensions();

    TrImage newInstance(File file) throws IOException;

    default boolean isValid(File file) {
        for (String extension : getExtensions()) {
            if (file.getName().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
