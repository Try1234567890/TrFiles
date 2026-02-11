package me.tr.trfiles.configuration.memory;

import me.tr.trfiles.configuration.implementations.FileConfiguration;
import java.io.File;

public interface MemoryEntry {

    String getAsEmpty();

    FileConfiguration newInstance(File file);

    MemoryConfiguration newInstance();


}
