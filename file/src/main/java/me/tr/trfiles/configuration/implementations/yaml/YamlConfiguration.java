package me.tr.trfiles.configuration.implementations.yaml;

import me.tr.trfiles.configuration.implementations.ConfigEntry;
import me.tr.trfiles.configuration.implementations.FileConfiguration;
import me.tr.trfiles.configuration.memory.MemoryEntry;
import me.tr.trfiles.configuration.memory.implementations.MemoryYamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {
    public static final ConfigEntry ENTRY = new ConfigEntry() {
        @Override
        public String[] getExtensions() {
            return new String[]{".yml", ".yaml"};
        }

        @Override
        public MemoryEntry getMemoryEntry() {
            return MemoryYamlConfiguration.ENTRY;
        }
    };

    private YamlConfiguration(File file) {
        super(ENTRY, file);
    }

    public static YamlConfiguration empty(File file) {
        return new YamlConfiguration(file);
    }

    public static YamlConfiguration fromReader(File file, Reader r) {
        return fromReader(file, r, YamlConfiguration.class);
    }

    public static YamlConfiguration fromInputStream(File file, InputStream is) {
        return fromInputStream(file, is, YamlConfiguration.class);
    }

    public static YamlConfiguration fromMap(File file, Map<?, ?> map) {
        return fromMap(file, map, YamlConfiguration.class);
    }

    public static YamlConfiguration fromContent(File file, String content) {
        return fromContent(file, content, YamlConfiguration.class);
    }

    public static YamlConfiguration fromBytes(File file, byte[] bytes) {
        return fromBytes(file, bytes, YamlConfiguration.class);
    }

    public static YamlConfiguration fromFile(File file) {
        return fromFile(file, YamlConfiguration.class);
    }

    public static YamlConfiguration fromPath(Path path) {
        return fromPath(path, YamlConfiguration.class);
    }

    public static YamlConfiguration fromPath(String path) {
        return fromPath(path, YamlConfiguration.class);
    }

    public void setMemory(MemoryYamlConfiguration memory) {
        super.setMemory(memory);
    }

    @Override
    public YamlOptions getOptions() {
        if (super.getOptions() == null) {
            super.setOptions(new YamlOptions(this));
        }
        return (YamlOptions) super.getOptions();
    }
}
