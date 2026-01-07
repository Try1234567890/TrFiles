package me.tr.trfiles.file.configuration.memory;

import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface MemoryEntry {

    @NotNull String getAsEmpty();

    @NotNull FileConfiguration newInstance(File file);

    @NotNull MemoryConfiguration newInstance();


}
