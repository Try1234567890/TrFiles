package me.tr.trfiles.configuration.implementations.json;

import me.tr.trfiles.configuration.implementations.ConfigEntry;
import me.tr.trfiles.configuration.implementations.FileConfiguration;
import me.tr.trfiles.configuration.memory.MemoryEntry;
import me.tr.trfiles.configuration.memory.implementations.MemoryJsonConfiguration;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;

public class JsonConfiguration extends FileConfiguration {
    public static final ConfigEntry ENTRY = new ConfigEntry() {
        @Override
        public String[] getExtensions() {
            return new String[]{".json"};
        }

        @Override
        public MemoryEntry getMemoryEntry() {
            return MemoryJsonConfiguration.ENTRY;
        }
    };

    private JsonConfiguration(File file) {
        super(ENTRY, file);
    }

    public static JsonConfiguration empty(File file) {
        return new JsonConfiguration(file);
    }

    public static JsonConfiguration fromReader(File file, Reader r) {
        return fromReader(file, r, JsonConfiguration.class);
    }

    public static JsonConfiguration fromInputStream(File file, InputStream is) {
        return fromInputStream(file, is, JsonConfiguration.class);
    }

    public static JsonConfiguration fromMap(File file, Map<?, ?> map) {
        return fromMap(file, map, JsonConfiguration.class);
    }

    public static JsonConfiguration fromContent(File file, String content) {
        return fromContent(file, content, JsonConfiguration.class);
    }

    public static JsonConfiguration fromBytes(File file, byte[] bytes) {
        return fromBytes(file, bytes, JsonConfiguration.class);
    }

    public static JsonConfiguration fromFile(File file) {
        return fromFile(file, JsonConfiguration.class);
    }

    public static JsonConfiguration fromPath(Path path) {
        return fromPath(path, JsonConfiguration.class);
    }

    public static JsonConfiguration fromPath(String path) {
        return fromPath(path, JsonConfiguration.class);
    }

    public void setMemory(MemoryJsonConfiguration memory) {
        super.setMemory(memory);
    }

    @Override
    public JsonOptions getOptions() {
        if (super.getOptions() == null) {
            super.setOptions(new JsonOptions(this));
        }
        return (JsonOptions) super.getOptions();
    }
}
