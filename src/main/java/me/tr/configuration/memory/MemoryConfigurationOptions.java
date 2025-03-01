package me.tr.configuration.memory;

import me.tr.configuration.Options;

public class MemoryConfigurationOptions extends Options {

    protected MemoryConfigurationOptions(MemoryConfiguration configuration) {
        super(configuration);
    }

    public MemoryConfiguration configuration() {
        return (MemoryConfiguration) super.configuration();
    }

    public MemoryConfigurationOptions copyDefaults(boolean value) {
        super.copyDefaults(value);
        return this;
    }
    
    public MemoryConfigurationOptions pathSeparator(char value) {
        super.pathSeparator(value);
        return this;
    }
}
