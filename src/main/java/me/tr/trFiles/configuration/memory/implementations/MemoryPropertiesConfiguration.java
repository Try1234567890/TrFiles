package me.tr.trFiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.implementations.properties.PropertiesConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipFile;

public class MemoryPropertiesConfiguration extends MemoryConfiguration {
    private @NotNull JavaPropsMapper javaPropsMapper;

    private MemoryPropertiesConfiguration(@NotNull JavaPropsMapper javaPropsMapper) {
        this.javaPropsMapper = javaPropsMapper;
    }

    private MemoryPropertiesConfiguration() {
        this.javaPropsMapper = new JavaPropsMapper();
    }


    @Override
    public @NotNull MemoryPropertiesConfiguration newMemoryConfiguration() {
        return new MemoryPropertiesConfiguration();
    }

    @Override
    public @NotNull PropertiesConfiguration newConfiguration(File file) {
        return PropertiesConfiguration.emptyProperties(file);
    }

    @Override
    public @NotNull Class<PropertiesConfiguration> getConfigurationReference() {
        return PropertiesConfiguration.class;
    }

    @Override
    public boolean hasFileConfiguration() {
        return true;
    }

    public static MemoryPropertiesConfiguration emptyProperties(JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).empty();
    }

    public static MemoryPropertiesConfiguration fromMemory(MemoryConfiguration memoryConfiguration, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).copy(memoryConfiguration);
    }

    public static MemoryPropertiesConfiguration fromReader(Reader reader, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).from(reader);
    }

    public static MemoryPropertiesConfiguration fromInputStream(InputStream is, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).from(is);
    }

    public static MemoryPropertiesConfiguration fromMap(Map<String, Object> map, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).from(map);
    }

    public static MemoryPropertiesConfiguration fromFile(File file, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).from(file);
    }

    public static MemoryPropertiesConfiguration fromPath(Path path, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).from(path);
    }

    public static MemoryPropertiesConfiguration fromString(String path, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).from(path);
    }

    public static MemoryPropertiesConfiguration fromContent(String content, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).fromC(content);
    }

    public static MemoryPropertiesConfiguration fromArchive(ZipFile archive, File inside, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).from(archive, inside);
    }

    public static MemoryPropertiesConfiguration fromBytes(byte[] bytes, JavaPropsMapper javaPropsMapper) {
        return (MemoryPropertiesConfiguration) new MemoryPropertiesConfiguration(javaPropsMapper != null ? javaPropsMapper : new JavaPropsMapper()).from(bytes);
    }

    public static MemoryPropertiesConfiguration emptyProperties() {
        return emptyProperties(new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromMemory(MemoryConfiguration memoryConfiguration) {
        return fromMemory(memoryConfiguration, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromReader(Reader reader) {
        return fromReader(reader, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromMap(Map<String, Object> map) {
        return fromMap(map, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromFile(File file) {
        return fromFile(file, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromPath(Path path) {
        return fromPath(path, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromString(String path) {
        return fromString(path, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromContent(String content) {
        return fromContent(content, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromArchive(ZipFile archive, File inside) {
        return fromArchive(archive, inside, new JavaPropsMapper());
    }

    public static MemoryPropertiesConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, new JavaPropsMapper());
    }

    @Override
    public void loadFromString(String contents) {
        if (Validator.isNull(contents, null)) contents = getEmptyConfig();
        Map<?, ?> input;
        try {
            Properties prop = new Properties();
            prop.load(new StringReader(contents));
            input = javaPropsMapper.readPropertiesAs(prop, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading Properties configuration. ", e);
        }
        convertMapsToSections(input, this);
    }

    @Override
    public String saveToString() {
        String contents;
        try {
            contents = javaPropsMapper.writeValueAsString(getValues(true));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading Properties configuration. ", e);
        }
        return contents;
    }

    public void setJavaPropsMapper(@NotNull JavaPropsMapper javaPropsMapper) {
        this.javaPropsMapper = javaPropsMapper;
    }
}
