package me.tr.configuration.file;

import me.tr.configuration.memory.MemoryConfiguration;
import me.tr.configuration.memory.MemoryConfigurationOptions;

public class FileConfigurationOptions extends MemoryConfigurationOptions {

    protected FileConfigurationOptions(MemoryConfiguration configuration) {
        super(configuration);
    }

    public FileConfiguration configuration() {
        return (FileConfiguration) super.configuration();
    }

    public FileConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }

    public FileConfigurationOptions pathSeparator(char value) {
        super.pathSeparator(value);
        return this;
    }


}
