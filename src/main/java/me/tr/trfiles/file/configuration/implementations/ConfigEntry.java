package me.tr.trfiles.file.configuration.implementations;

import me.tr.trfiles.file.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.file.configuration.memory.MemoryEntry;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface ConfigEntry {

    String[] getExtensions();

    @NotNull MemoryEntry getMemoryEntry();

    default FileConfiguration newInstance(File file) {
        return getMemoryEntry().newInstance(file);
    }

    default MemoryConfiguration newInstance() {
        return getMemoryEntry().newInstance();
    }

}
