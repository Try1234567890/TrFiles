package me.tr.trfiles.file.configuration.implementations.toon;

import me.tr.trfiles.file.configuration.implementations.ConfigEntry;
import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import me.tr.trfiles.file.configuration.memory.MemoryEntry;
import me.tr.trfiles.file.configuration.memory.implementations.MemoryToonConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;

public class ToonConfiguration extends FileConfiguration {
    public static final ConfigEntry ENTRY = new ConfigEntry() {
        @Override
        public String[] getExtensions() {
            return new String[]{".toon"};
        }

        @Override
        public @NotNull MemoryEntry getMemoryEntry() {
            return MemoryToonConfiguration.ENTRY;
        }
    };

    private ToonConfiguration(File file) {
        super(ENTRY, file);
    }

    public static ToonConfiguration empty(File file) {
        return new ToonConfiguration(file);
    }

    public static ToonConfiguration fromReader(File file, Reader r) {
        return fromReader(file, r, ToonConfiguration.class);
    }

    public static ToonConfiguration fromInputStream(File file, InputStream is) {
        return fromInputStream(file, is, ToonConfiguration.class);
    }

    public static ToonConfiguration fromMap(File file, Map<?, ?> map) {
        return fromMap(file, map, ToonConfiguration.class);
    }

    public static ToonConfiguration fromContent(File file, String content) {
        return fromContent(file, content, ToonConfiguration.class);
    }

    public static ToonConfiguration fromBytes(File file, byte[] bytes) {
        return fromBytes(file, bytes, ToonConfiguration.class);
    }

    public static ToonConfiguration fromFile(File file) {
        return fromFile(file, ToonConfiguration.class);
    }

    public static ToonConfiguration fromPath(Path path) {
        return fromPath(path, ToonConfiguration.class);
    }

    public static ToonConfiguration fromPath(String path) {
        return fromPath(path, ToonConfiguration.class);
    }

    public void setMemory(MemoryToonConfiguration memory) {
        super.setMemory(memory);
    }

    @Override
    public ToonOptions getOptions() {
        if (super.getOptions() == null) {
            super.setOptions(new ToonOptions(this));
        }
        return (ToonOptions) super.getOptions();
    }
}
