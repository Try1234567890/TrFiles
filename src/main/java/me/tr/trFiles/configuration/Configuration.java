package me.tr.trFiles.configuration;

import me.tr.trFiles.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Map;

/**
 * Represents a source of configurable options and settings
 */
public interface Configuration extends Section {

    public void setFile(File file);

    public File getFile();

    public FileConfiguration getFileConfiguration();

    public void setFileConfiguration(FileConfiguration config);

    public void addDefault(String path, Object value);

    public void addDefaults(Map<String, Object> defaults);

    public void addDefaults(Configuration defaults);

    public void setDefaults(Configuration defaults);

    public Configuration getDefaults();

    public Options options();

}
