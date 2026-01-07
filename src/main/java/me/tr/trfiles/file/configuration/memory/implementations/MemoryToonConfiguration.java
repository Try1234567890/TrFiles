package me.tr.trfiles.file.configuration.memory.implementations;

import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import me.tr.trfiles.file.configuration.implementations.toon.JToonSer;
import me.tr.trfiles.file.configuration.implementations.toon.ToonConfiguration;
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

public class MemoryToonConfiguration extends MemoryConfiguration {
    public static final MemoryEntry ENTRY = new MemoryEntry() {
        @Override
        public @NotNull FileConfiguration newInstance(File file) {
            return ToonConfiguration.empty(file);
        }

        @Override
        public @NotNull String getAsEmpty() {
            return "";
        }

        @Override
        public @NotNull MemoryConfiguration newInstance() {
            return MemoryToonConfiguration.empty();
        }
    };
    private final @NotNull JToonSer jToonSer;


    public MemoryToonConfiguration() {
        super(ENTRY);
        this.jToonSer = new JToonSer();
    }

    public MemoryToonConfiguration(@NotNull JToonSer jToonSer) {
        super(ENTRY);
        Validator.isNull(jToonSer, "jToonSer is null.");
        this.jToonSer = jToonSer;
    }

    public static MemoryToonConfiguration empty() {
        return new MemoryToonConfiguration();
    }

    public static MemoryToonConfiguration fromReader(Reader r) {
        return fromReader(r, MemoryToonConfiguration.class);
    }

    public static MemoryToonConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, MemoryToonConfiguration.class);
    }

    public static MemoryToonConfiguration fromMap(Map<?, ?> map) {
        return fromMap(map, MemoryToonConfiguration.class);
    }

    public static MemoryToonConfiguration fromFile(File file) {
        return fromFile(file, MemoryToonConfiguration.class);
    }

    public static MemoryToonConfiguration fromPath(Path path) {
        return fromPath(path, MemoryToonConfiguration.class);
    }

    public static MemoryToonConfiguration fromPath(String path) {
        return fromPath(path, MemoryToonConfiguration.class);
    }

    public static MemoryToonConfiguration fromContent(String content) {
        return fromContent(content, MemoryToonConfiguration.class);
    }

    public static MemoryToonConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, MemoryToonConfiguration.class);
    }

    public ToonConfiguration toConfiguration(File file) {
        return (ToonConfiguration) super.toConfiguration(file);
    }

    /**
     * @param content The content to load Toon from.
     * @return a Map containing the loaded file.
     */
    @Override
    public Map<?, ?> loadFromString(String content) {
        try {
            Map<?, ?> configMap = getjToonSer().decode(content);

            if (configMap == null)
                return new HashMap<>();

            return configMap;
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while loading: " + content + " as Toon config.", e);
        }
    }

    /**
     * @return the content of the current configuration as string.
     */
    @Override
    public String saveAsString() {
        try {
            return getjToonSer().encode(getValues(true));
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while saving: " + asMap() + " as Toon file.", e);
        }
    }

    public @NotNull JToonSer getjToonSer() {
        return jToonSer;
    }
}
