package me.tr.trfiles.file.configuration.implementations.toml;

import me.tr.trfiles.file.configuration.implementations.ConfigEntry;
import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import me.tr.trfiles.file.configuration.memory.MemoryEntry;
import me.tr.trfiles.file.configuration.memory.implementations.MemoryTomlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;

public class TomlConfiguration extends FileConfiguration {
    public static final ConfigEntry ENTRY = new ConfigEntry() {
        @Override
        public String[] getExtensions() {
            return new String[]{".toml"};
        }

        @Override
        public @NotNull MemoryEntry getMemoryEntry() {
            return MemoryTomlConfiguration.ENTRY;
        }
    };

    private TomlConfiguration(File file) {
        super(ENTRY, file);
    }

    public static TomlConfiguration empty(File file) {
        return new TomlConfiguration(file);
    }

    public static TomlConfiguration fromReader(File file, Reader r) {
        return fromReader(file, r, TomlConfiguration.class);
    }

    public static TomlConfiguration fromInputStream(File file, InputStream is) {
        return fromInputStream(file, is, TomlConfiguration.class);
    }

    public static TomlConfiguration fromMap(File file, Map<?, ?> map) {
        return fromMap(file, map, TomlConfiguration.class);
    }

    public static TomlConfiguration fromContent(File file, String content) {
        return fromContent(file, content, TomlConfiguration.class);
    }

    public static TomlConfiguration fromBytes(File file, byte[] bytes) {
        return fromBytes(file, bytes, TomlConfiguration.class);
    }

    public static TomlConfiguration fromFile(File file) {
        return fromFile(file, TomlConfiguration.class);
    }

    public static TomlConfiguration fromPath(Path path) {
        return fromPath(path, TomlConfiguration.class);
    }

    public static TomlConfiguration fromPath(String path) {
        return fromPath(path, TomlConfiguration.class);
    }

    public void setMemory(MemoryTomlConfiguration memory) {
        super.setMemory(memory);
    }

    @Override
    public TomlOptions getOptions() {
        if (super.getOptions() == null) {
            super.setOptions(new TomlOptions(this));
        }
        return (TomlOptions) super.getOptions();
    }
}
