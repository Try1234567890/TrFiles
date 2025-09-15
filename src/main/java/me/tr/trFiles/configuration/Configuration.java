package me.tr.trFiles.configuration;

import me.tr.trFiles.configuration.memory.MemoryConfiguration;

import java.io.File;
import java.util.Map;

/**
 * Represents a source of configurable options and settings
 */
public interface Configuration extends Section {

    void save(File file);

    void reload(File file);

    void move(File file, File to);

    void copy(File file, File to);

    void delete(File file);

    void zip(File zip, File file);

    MemoryConfiguration convert(MemoryConfiguration to);

    ConfigurationOptions options();


}
