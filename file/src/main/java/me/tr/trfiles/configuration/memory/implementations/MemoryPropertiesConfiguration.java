package me.tr.trfiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import me.tr.trfiles.configuration.implementations.FileConfiguration;
import me.tr.trfiles.configuration.implementations.properties.PropertiesConfiguration;
import me.tr.trfiles.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.Validator;
import me.tr.trfiles.configuration.memory.MemoryEntry;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MemoryPropertiesConfiguration extends MemoryConfiguration {
    public static final MemoryEntry ENTRY = new MemoryEntry() {
        @Override
        public FileConfiguration newInstance(File file) {
            return PropertiesConfiguration.empty(file);
        }

        @Override
        public String getAsEmpty() {
            return "";
        }

        @Override
        public MemoryConfiguration newInstance() {
            return MemoryPropertiesConfiguration.empty();
        }
    };
    private final JavaPropsMapper javaPropsMapper;

    private MemoryPropertiesConfiguration(JavaPropsMapper javaPropsMapper) {
        super(ENTRY);
        Validator.isNull(javaPropsMapper, "javaPropsMapper is null.");
        this.javaPropsMapper = javaPropsMapper;
    }

    private MemoryPropertiesConfiguration() {
        super(ENTRY);
        this.javaPropsMapper = new JavaPropsMapper();
    }

    public static MemoryPropertiesConfiguration empty() {
        return new MemoryPropertiesConfiguration();
    }

    public static MemoryPropertiesConfiguration fromReader(Reader r) {
        return fromReader(r, MemoryPropertiesConfiguration.class);
    }

    public static MemoryPropertiesConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, MemoryPropertiesConfiguration.class);
    }

    public static MemoryPropertiesConfiguration fromMap(Map<?, ?> map) {
        return fromMap(map, MemoryPropertiesConfiguration.class);
    }

    public static MemoryPropertiesConfiguration fromFile(File file) {
        return fromFile(file, MemoryPropertiesConfiguration.class);
    }

    public static MemoryPropertiesConfiguration fromPath(Path path) {
        return fromPath(path, MemoryPropertiesConfiguration.class);
    }

    public static MemoryPropertiesConfiguration fromPath(String path) {
        return fromPath(path, MemoryPropertiesConfiguration.class);
    }

    public static MemoryPropertiesConfiguration fromContent(String content) {
        return fromContent(content, MemoryPropertiesConfiguration.class);
    }

    public static MemoryPropertiesConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, MemoryPropertiesConfiguration.class);
    }

    public PropertiesConfiguration toConfiguration(File file) {
        return (PropertiesConfiguration) super.toConfiguration(file);
    }

    /**
     * @param content The content to load Properties from.
     * @return a Map containing the loaded file.
     */
    @Override
    public Map<?, ?> loadFromString(String content) {
        try {
            Map<?, ?> configMap = getJavaPropsMapper().readValue(content, Map.class);

            if (configMap == null)
                return new HashMap<>();

            return configMap;
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while loading: " + content + " as Properties config.", e);
        }
    }

    /**
     * @return the content of the current configuration as string.
     */
    @Override
    public String saveAsString() {
        try {
            return getJavaPropsMapper().writeValueAsString(getValues(true));
        } catch (Throwable e) {
            throw new RuntimeException("An error occurs while saving: " + asMap() + " as Properties file.", e);
        }
    }

    public JavaPropsMapper getJavaPropsMapper() {
        return javaPropsMapper;
    }
}
