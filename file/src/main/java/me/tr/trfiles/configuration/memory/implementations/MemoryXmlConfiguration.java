package me.tr.trfiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.tr.trfiles.configuration.implementations.FileConfiguration;
import me.tr.trfiles.configuration.implementations.xml.XmlConfiguration;
import me.tr.trfiles.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.Validator;
import me.tr.trfiles.configuration.memory.MemoryEntry;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MemoryXmlConfiguration extends MemoryConfiguration {
    public static final MemoryEntry ENTRY = new MemoryEntry() {
        @Override
        public FileConfiguration newInstance(File file) {
            return XmlConfiguration.empty(file);
        }

        @Override
        public String getAsEmpty() {
            return "<empty></empty>";
        }

        @Override
        public MemoryConfiguration newInstance() {
            return MemoryXmlConfiguration.empty();
        }
    };
    private final XmlMapper xmlMapper;
    private String rootName = "configuration";

    private MemoryXmlConfiguration(XmlMapper xmlMapper) {
        super(ENTRY);
        Validator.isNull(xmlMapper, "xmlMapper is null.");
        this.xmlMapper = xmlMapper;
    }

    private MemoryXmlConfiguration() {
        super(ENTRY);
        this.xmlMapper = new XmlMapper();
    }

    public static MemoryXmlConfiguration empty() {
        return new MemoryXmlConfiguration();
    }

    public static MemoryXmlConfiguration fromReader(Reader r) {
        return fromReader(r, MemoryXmlConfiguration.class);
    }

    public static MemoryXmlConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, MemoryXmlConfiguration.class);
    }

    public static MemoryXmlConfiguration fromMap(Map<?, ?> map) {
        return fromMap(map, MemoryXmlConfiguration.class);
    }

    public static MemoryXmlConfiguration fromFile(File file) {
        return fromFile(file, MemoryXmlConfiguration.class);
    }

    public static MemoryXmlConfiguration fromPath(Path path) {
        return fromPath(path, MemoryXmlConfiguration.class);
    }

    public static MemoryXmlConfiguration fromPath(String path) {
        return fromPath(path, MemoryXmlConfiguration.class);
    }

    public static MemoryXmlConfiguration fromContent(String content) {
        return fromContent(content, MemoryXmlConfiguration.class);
    }

    public static MemoryXmlConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, MemoryXmlConfiguration.class);
    }

    public XmlConfiguration toConfiguration(File file) {
        return (XmlConfiguration) super.toConfiguration(file);
    }

    /**
     * @param content The content to load yaml from.
     * @return a Map containing the loaded file.
     */
    @Override
    public Map<?, ?> loadFromString(String content) {
        try {
            Map<?, ?> configMap = xmlMapper.readValue(content, Map.class);

            if (configMap == null) {
                return new HashMap<>();
            }

            return configMap;
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while loading: " + content + " as XML config", e);
        }
    }

    /**
     * @return the content of the current configuration as string.
     */
    @Override
    public String saveAsString() {
        try {
            return Validator.isNull(getRootName()) ?
                    xmlMapper.writer().withoutRootName().writeValueAsString(getValues(false)) :
                    xmlMapper.writer().withRootName(getRootName()).writeValueAsString(getValues(false));
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while saving: " + asMap() + " as XML config", e);
        }
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
}