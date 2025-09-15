package me.tr.trFiles.configuration.memory.implementations;

import me.tr.trFiles.configuration.memory.MemoryConfiguration;

public class FileConfigurationNotFound extends RuntimeException {
    public FileConfigurationNotFound(String message) {
        super(message);
    }

    public FileConfigurationNotFound(MemoryConfiguration memory) {
        super("The FileConfiguration of " + memory.getClass() + " is not found.");
    }
}
