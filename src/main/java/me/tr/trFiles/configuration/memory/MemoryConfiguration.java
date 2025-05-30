package me.tr.trFiles.configuration.memory;

import me.tr.trFiles.configuration.Configuration;
import me.tr.trFiles.configuration.Section;
import me.tr.trFiles.configuration.file.FileConfiguration;

import java.io.File;
import java.util.Map;

public class MemoryConfiguration extends MemorySection implements Configuration {
    protected File file;
    protected FileConfiguration config;
    protected Configuration defaults;
    protected MemoryOptions options;

    @Override
    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public FileConfiguration getFileConfiguration() {
        return config;
    }

    @Override
    public void setFileConfiguration(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public void addDefault(String path, Object value) {
        getDefaults().set(path, value);
    }

    @Override
    public void addDefaults(Map<String, Object> defaults) {
        convertMapsToSections(defaults, getDefaults());
    }

    @Override
    public void addDefaults(Configuration defaults) {
        this.defaults = defaults;

    }

    @Override
    public void setDefaults(Configuration defaults) {
        this.defaults = defaults;
    }

    @Override
    public Configuration getDefaults() {
        if (this.defaults == null) {
            this.defaults = new MemoryConfiguration();
        }
        return this.defaults;
    }

    public MemoryConfiguration() {
    }

    /**
     * Convert {@link Map} into {@link Section} by cycling all
     * {@link Map.Entry} that map contains, if the entry value
     * is an instance of {@code Map}, call recursive this method by passing
     * as {@code Map} the cast value as it and as {@code Section}
     * the result of {@link Section#createSection(String)} using
     * as parameter the entry key, else if is not an instance of {@code Map}
     * call method {@link Section#set(String, Object)} by using entry key as first
     * parameter and entry value as second parameter.
     *
     * @param input   Map to convert into sections.
     * @param section Root section to start insert values in.
     */
    protected void convertMapsToSections(Map<?, ?> input, Section section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    public MemoryConfiguration(Configuration defaults) {
        this.defaults = defaults;
    }

    @Override
    public MemoryOptions options() {
        if (options == null) {
            options = new MemoryOptions(this);
        }
        return options;
    }
}
