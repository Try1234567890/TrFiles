package me.tr.configuration.file.yaml;

import me.tr.configuration.file.FileConfigurationOptions;
import me.tr.configuration.memory.MemoryConfiguration;

public class YamlConfigurationOptions extends FileConfigurationOptions {

    protected YamlConfigurationOptions(MemoryConfiguration configuration) {
        super(configuration);
    }

    public YamlConfiguration configuration() {
        return (YamlConfiguration) super.configuration();
    }

    public YamlConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }

    public YamlConfigurationOptions pathSeparator(char value) {
        super.pathSeparator(value);
        return this;
    }
}
