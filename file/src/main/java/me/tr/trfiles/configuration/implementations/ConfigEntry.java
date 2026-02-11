package me.tr.trfiles.configuration.implementations;

import me.tr.trfiles.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.configuration.memory.MemoryEntry;

import java.io.File;

public interface ConfigEntry {

    String[] getExtensions();

    MemoryEntry getMemoryEntry();

    default FileConfiguration newInstance(File file) {
        return getMemoryEntry().newInstance(file);
    }

    default MemoryConfiguration newInstance() {
        return getMemoryEntry().newInstance();
    }

}
