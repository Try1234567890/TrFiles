package me.tr.trfiles.file.configuration.memory.implementations;

import me.tr.trfiles.Validator;
import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import me.tr.trfiles.file.configuration.implementations.yaml.YamlConfiguration;
import me.tr.trfiles.file.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.file.configuration.memory.MemoryEntry;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MemoryYamlConfiguration extends MemoryConfiguration {
    public static final MemoryEntry ENTRY = new MemoryEntry() {
        @Override
        public @NotNull FileConfiguration newInstance(File file) {
            return YamlConfiguration.empty(file);
        }

        @Override
        public @NotNull String getAsEmpty() {
            return "{}";
        }

        @Override
        public @NotNull MemoryConfiguration newInstance() {
            return MemoryYamlConfiguration.empty();
        }
    };
    private final @NotNull Yaml yaml;

    public MemoryYamlConfiguration(@NotNull Yaml yaml) {
        super(ENTRY);
        Validator.isNull(yaml, "yaml is null.");
        this.yaml = yaml;
    }

    public MemoryYamlConfiguration() {
        super(ENTRY);
        this.yaml = new Yaml();
    }

    public static MemoryYamlConfiguration empty() {
        return new MemoryYamlConfiguration();
    }

    public static MemoryYamlConfiguration fromReader(Reader r) {
        return fromReader(r, MemoryYamlConfiguration.class);
    }

    public static MemoryYamlConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, MemoryYamlConfiguration.class);
    }

    public static MemoryYamlConfiguration fromMap(Map<?, ?> map) {
        return fromMap(map, MemoryYamlConfiguration.class);
    }

    public static MemoryYamlConfiguration fromFile(File file) {
        return fromFile(file, MemoryYamlConfiguration.class);
    }

    public static MemoryYamlConfiguration fromPath(Path path) {
        return fromPath(path, MemoryYamlConfiguration.class);
    }

    public static MemoryYamlConfiguration fromPath(String path) {
        return fromPath(path, MemoryYamlConfiguration.class);
    }

    public static MemoryYamlConfiguration fromContent(String content) {
        return fromContent(content, MemoryYamlConfiguration.class);
    }

    public static MemoryYamlConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, MemoryYamlConfiguration.class);
    }

    public YamlConfiguration toConfiguration(File file) {
        return (YamlConfiguration) super.toConfiguration(file);
    }

    /**
     * @param content The content to load yaml from.
     * @return a Map containing the loaded file.
     */
    @Override
    public Map<?, ?> loadFromString(String content) {
        try {
            Map<?, ?> configMap = yaml.loadAs(content, Map.class);

            if (configMap == null)
                return new HashMap<>();

            return configMap;
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while loading: " + content + " as YAML config.", e);
        }
    }

    /**
     * @return the content of the current configuration as string.
     */
    @Override
    public String saveAsString() {
        try {
            return yaml.dump(getValues(true));
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while saving: " + asMap() + " as YAML file.", e);
        }
    }
}
