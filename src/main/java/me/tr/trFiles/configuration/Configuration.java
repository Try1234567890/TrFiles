package me.tr.trFiles.configuration;

import me.tr.trFiles.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Map;

/**
 * Represents a source of configurable options and settings
 */
public interface Configuration extends Section {

    void setFile(File file);

    File getFile();

    FileConfiguration getFileConfiguration();

    void setFileConfiguration(FileConfiguration config);

    void addDefault(String path, Object value);

    void addDefaults(Map<String, Object> defaults);

    void addDefaults(Configuration defaults);

    void setDefaults(Configuration defaults);

    Configuration getDefaults();

    Options options();

}
