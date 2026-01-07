package me.tr.trfiles.file.configuration.implementations.properties;

import me.tr.trfiles.file.configuration.implementations.ConfigEntry;
import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import me.tr.trfiles.file.configuration.memory.MemoryEntry;
import me.tr.trfiles.file.configuration.memory.implementations.MemoryPropertiesConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;

public class PropertiesConfiguration extends FileConfiguration {
    public static final ConfigEntry ENTRY = new ConfigEntry() {
        @Override
        public String[] getExtensions() {
            return new String[]{".properties"};
        }

        @Override
        public @NotNull MemoryEntry getMemoryEntry() {
            return MemoryPropertiesConfiguration.ENTRY;
        }
    };

    private PropertiesConfiguration(File file) {
        super(ENTRY, file);
    }

    public static PropertiesConfiguration empty(File file) {
        return new PropertiesConfiguration(file);
    }

    public static PropertiesConfiguration fromReader(File file, Reader r) {
        return fromReader(file, r, PropertiesConfiguration.class);
    }

    public static PropertiesConfiguration fromInputStream(File file, InputStream is) {
        return fromInputStream(file, is, PropertiesConfiguration.class);
    }

    public static PropertiesConfiguration fromMap(File file, Map<?, ?> map) {
        return fromMap(file, map, PropertiesConfiguration.class);
    }

    public static PropertiesConfiguration fromContent(File file, String content) {
        return fromContent(file, content, PropertiesConfiguration.class);
    }

    public static PropertiesConfiguration fromBytes(File file, byte[] bytes) {
        return fromBytes(file, bytes, PropertiesConfiguration.class);
    }

    public static PropertiesConfiguration fromFile(File file) {
        return fromFile(file, PropertiesConfiguration.class);
    }

    public static PropertiesConfiguration fromPath(Path path) {
        return fromPath(path, PropertiesConfiguration.class);
    }

    public static PropertiesConfiguration fromPath(String path) {
        return fromPath(path, PropertiesConfiguration.class);
    }

    public void setMemory(MemoryPropertiesConfiguration memory) {
        super.setMemory(memory);
    }

    @Override
    public PropertiesOptions getOptions() {
        if (super.getOptions() == null) {
            super.setOptions(new PropertiesOptions(this));
        }
        return (PropertiesOptions) super.getOptions();
    }

}
