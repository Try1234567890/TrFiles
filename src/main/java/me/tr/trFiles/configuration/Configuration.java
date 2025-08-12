package me.tr.trFiles.configuration;

import java.io.File;
import java.util.Map;

/**
 * Represents a source of configurable options and settings
 */
public interface Configuration extends Section {

    void setFile(File file);

    File getFile();

    Configuration getFileConfiguration();

    void setFileConfiguration(Configuration config);

    void addDefault(String path, Object value);

    void addDefaults(Map<String, Object> defaults);

    void addDefaults(Configuration defaults);

    void setDefaults(Configuration defaults);

    Configuration getDefaults();

    Options options();

}
