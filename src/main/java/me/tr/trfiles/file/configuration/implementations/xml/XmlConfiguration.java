package me.tr.trfiles.file.configuration.implementations.xml;

import me.tr.trfiles.file.configuration.implementations.ConfigEntry;
import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import me.tr.trfiles.file.configuration.memory.MemoryEntry;
import me.tr.trfiles.file.configuration.memory.implementations.MemoryXmlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;

public class XmlConfiguration extends FileConfiguration {
    private String rootName = "configuration";

    public static final ConfigEntry ENTRY = new ConfigEntry() {
        @Override
        public String[] getExtensions() {
            return new String[]{".xml"};
        }

        @Override
        public @NotNull MemoryEntry getMemoryEntry() {
            return MemoryXmlConfiguration.ENTRY;
        }
    };

    private XmlConfiguration(File file) {
        super(ENTRY, file);
    }

    public static XmlConfiguration empty(File file) {
        return new XmlConfiguration(file);
    }

    public static XmlConfiguration fromReader(File file, Reader r) {
        return fromReader(file, r, XmlConfiguration.class);
    }

    public static XmlConfiguration fromInputStream(File file, InputStream is) {
        return fromInputStream(file, is, XmlConfiguration.class);
    }

    public static XmlConfiguration fromMap(File file, Map<?, ?> map) {
        return fromMap(file, map, XmlConfiguration.class);
    }

    public static XmlConfiguration fromContent(File file, String content) {
        return fromContent(file, content, XmlConfiguration.class);
    }

    public static XmlConfiguration fromBytes(File file, byte[] bytes) {
        return fromBytes(file, bytes, XmlConfiguration.class);
    }

    public static XmlConfiguration fromFile(File file) {
        return fromFile(file, XmlConfiguration.class);
    }

    public static XmlConfiguration fromPath(Path path) {
        return fromPath(path, XmlConfiguration.class);
    }

    public static XmlConfiguration fromPath(String path) {
        return fromPath(path, XmlConfiguration.class);
    }

    public void setMemory(MemoryXmlConfiguration memory) {
        super.setMemory(memory);
    }

    @Override
    public XmlOptions getOptions() {
        if (super.getOptions() == null) {
            super.setOptions(new XmlOptions(this));
        }
        return (XmlOptions) super.getOptions();
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
}
