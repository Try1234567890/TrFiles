package me.tr.configuration.memory;

import me.tr.configuration.Configuration;
import me.tr.configuration.file.FileConfiguration;

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
        if (defaults == null) {
            defaults = new MemoryConfiguration();
        }
        defaults.set(path, value);
    }

    @Override
    public void addDefaults(Map<String, Object> defaults) {
        for (Map.Entry<String, Object> entry : defaults.entrySet()) {
            addDefault(entry.getKey(), entry.getValue());
        }
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
        return this.defaults;
    }

    public MemoryConfiguration() {}

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
