package me.tr.trfiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import me.tr.trfiles.configuration.implementations.FileConfiguration;
import me.tr.trfiles.configuration.implementations.toml.TomlConfiguration;
import me.tr.trfiles.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.Validator;
import me.tr.trfiles.configuration.memory.MemoryEntry;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MemoryTomlConfiguration extends MemoryConfiguration {
    public static final MemoryEntry ENTRY = new MemoryEntry() {
        @Override
        public FileConfiguration newInstance(File file) {
            return TomlConfiguration.empty(file);
        }

        @Override
        public String getAsEmpty() {
            return "#Empty";
        }

        @Override
        public MemoryConfiguration newInstance() {
            return MemoryTomlConfiguration.empty();
        }
    };
    private final TomlMapper tomlMapper;

    private MemoryTomlConfiguration(TomlMapper tomlMapper) {
        super(ENTRY);
        Validator.isNull(tomlMapper, "tomlMapper is null.");
        this.tomlMapper = tomlMapper;
    }

    private MemoryTomlConfiguration() {
        super(ENTRY);
        this.tomlMapper = new TomlMapper();
    }

    public static MemoryTomlConfiguration empty() {
        return new MemoryTomlConfiguration();
    }

    public static MemoryTomlConfiguration fromReader(Reader r) {
        return fromReader(r, MemoryTomlConfiguration.class);
    }

    public static MemoryTomlConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, MemoryTomlConfiguration.class);
    }

    public static MemoryTomlConfiguration fromMap(Map<?, ?> map) {
        return fromMap(map, MemoryTomlConfiguration.class);
    }

    public static MemoryTomlConfiguration fromFile(File file) {
        return fromFile(file, MemoryTomlConfiguration.class);
    }

    public static MemoryTomlConfiguration fromPath(Path path) {
        return fromPath(path, MemoryTomlConfiguration.class);
    }

    public static MemoryTomlConfiguration fromPath(String path) {
        return fromPath(path, MemoryTomlConfiguration.class);
    }

    public static MemoryTomlConfiguration fromContent(String content) {
        return fromContent(content, MemoryTomlConfiguration.class);
    }

    public static MemoryTomlConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, MemoryTomlConfiguration.class);
    }

    public TomlConfiguration toConfiguration(File file) {
        return (TomlConfiguration) super.toConfiguration(file);
    }

    /**
     * @param content The content to load Toml from.
     * @return a Map containing the loaded file.
     */
    @Override
    public Map<?, ?> loadFromString(String content) {
        try {
            Map<?, ?> configMap = getTomlMapper().readValue(content, Map.class);

            if (configMap == null)
                return new HashMap<>();

            return configMap;
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while loading: " + content + " as Toml config.", e);
        }
    }

    /**
     * @return the content of the current configuration as string.
     */
    @Override
    public String saveAsString() {
        try {
            return getTomlMapper().writeValueAsString(getValues(true));
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while saving: " + asMap() + " as Toml file.", e);
        }
    }

    public TomlMapper getTomlMapper() {
        return tomlMapper;
    }
}