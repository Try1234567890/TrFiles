package me.tr.configuration;

import java.util.Map;

/**
 * Represents a source of configurable options and settings
 */
public interface Configuration extends Section {

    public void addDefault(String path, Object value);

    public void addDefaults(Map<String, Object> defaults);

    public void addDefaults(Configuration defaults);

    public void setDefaults(Configuration defaults);

    public Configuration getDefaults();

    public Options options();

}
