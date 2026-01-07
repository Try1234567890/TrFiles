package me.tr.trfiles.file.configuration.memory.implementations;

import com.google.gson.Gson;
import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import me.tr.trfiles.file.configuration.implementations.json.JsonConfiguration;
import me.tr.trfiles.file.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.Validator;
import me.tr.trfiles.file.configuration.memory.MemoryEntry;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MemoryJsonConfiguration extends MemoryConfiguration {
    public static final MemoryEntry ENTRY = new MemoryEntry() {
        @Override
        public @NotNull FileConfiguration newInstance(File file) {
            return JsonConfiguration.empty(file);
        }

        @Override
        public @NotNull String getAsEmpty() {
            return "{}";
        }

        @Override
        public @NotNull MemoryConfiguration newInstance() {
            return MemoryJsonConfiguration.empty();
        }
    };
    private final @NotNull Gson gson;

    public MemoryJsonConfiguration(@NotNull Gson gson) {
        super(ENTRY);
        Validator.isNull(gson, "gson is null.");
        this.gson = gson;
    }

    private MemoryJsonConfiguration() {
        super(ENTRY);
        this.gson = new Gson();
    }

    public static MemoryJsonConfiguration empty() {
        return new MemoryJsonConfiguration();
    }

    public static MemoryJsonConfiguration fromReader(Reader r) {
        return fromReader(r, MemoryJsonConfiguration.class);
    }

    public static MemoryJsonConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, MemoryJsonConfiguration.class);
    }

    public static MemoryJsonConfiguration fromMap(Map<?, ?> map) {
        return fromMap(map, MemoryJsonConfiguration.class);
    }

    public static MemoryJsonConfiguration fromFile(File file) {
        return fromFile(file, MemoryJsonConfiguration.class);
    }

    public static MemoryJsonConfiguration fromPath(Path path) {
        return fromPath(path, MemoryJsonConfiguration.class);
    }

    public static MemoryJsonConfiguration fromPath(String path) {
        return fromPath(path, MemoryJsonConfiguration.class);
    }

    public static MemoryJsonConfiguration fromContent(String content) {
        return fromContent(content, MemoryJsonConfiguration.class);
    }

    public static MemoryJsonConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, MemoryJsonConfiguration.class);
    }

    public JsonConfiguration toConfiguration(File file) {
        return (JsonConfiguration) super.toConfiguration(file);
    }

    /**
     * @param content The content to load Json from.
     * @return a Map containing the loaded file.
     */
    @Override
    public Map<?, ?> loadFromString(String content) {
        try {
            Map<?, ?> configMap = getGson().fromJson(content, Map.class);

            if (configMap == null)
                return new HashMap<>();

            return configMap;
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while loading: " + content + " as Json config.", e);
        }
    }

    /**
     * @return the content of the current configuration as string.
     */
    @Override
    public String saveAsString() {
        try {
            return getGson().toJson(getValues(true));
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while saving: " + asMap() + " as Json file.", e);
        }
    }

    public @NotNull Gson getGson() {
        return gson;
    }
}
