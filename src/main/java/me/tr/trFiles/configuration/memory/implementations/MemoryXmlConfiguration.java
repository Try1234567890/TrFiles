package me.tr.trFiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.implementations.xml.XmlConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

public class MemoryXmlConfiguration extends MemoryConfiguration {
    private @NotNull XmlMapper xmlMapper;
    private @NotNull String rootName = "configuration";

    private MemoryXmlConfiguration(@NotNull XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    private MemoryXmlConfiguration() {
        this.xmlMapper = new XmlMapper();
    }


    @Override
    public @NotNull MemoryXmlConfiguration newMemoryConfiguration() {
        return new MemoryXmlConfiguration();
    }

    @Override
    public @NotNull XmlConfiguration newConfiguration(File file) {
        return XmlConfiguration.emptyXml(file);
    }

    @Override
    public boolean hasFileConfiguration() {
        return true;
    }

    @Override
    public String getEmptyConfig() {
        return "<" + rootName + ">" + "</" + rootName + ">";
    }

    @Override
    public @NotNull Class<XmlConfiguration> getConfigurationReference() {
        return XmlConfiguration.class;
    }


    public static MemoryXmlConfiguration emptyXml(XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).empty();
    }

    public static MemoryXmlConfiguration fromMemory(MemoryConfiguration memoryConfiguration, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).copy(memoryConfiguration);
    }

    public static MemoryXmlConfiguration fromReader(Reader reader, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).from(reader);
    }

    public static MemoryXmlConfiguration fromInputStream(InputStream is, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).from(is);
    }

    public static MemoryXmlConfiguration fromMap(Map<String, Object> map, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).from(map);
    }

    public static MemoryXmlConfiguration fromFile(File file, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).from(file);
    }

    public static MemoryXmlConfiguration fromPath(Path path, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).from(path);
    }

    public static MemoryXmlConfiguration fromString(String path, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).from(path);
    }

    public static MemoryXmlConfiguration fromContent(String content, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).fromC(content);
    }

    public static MemoryXmlConfiguration fromArchive(ZipFile archive, File inside, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).from(archive, inside);
    }

    public static MemoryXmlConfiguration fromBytes(byte[] bytes, XmlMapper xmlMapper) {
        return (MemoryXmlConfiguration) new MemoryXmlConfiguration(xmlMapper != null ? xmlMapper : new XmlMapper()).from(bytes);
    }

    public static MemoryXmlConfiguration emptyXml() {
        return emptyXml(new XmlMapper());
    }

    public static MemoryXmlConfiguration fromMemory(MemoryConfiguration memoryConfiguration) {
        return fromMemory(memoryConfiguration, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromReader(Reader reader) {
        return fromReader(reader, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromMap(Map<String, Object> map) {
        return fromMap(map, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromFile(File file) {
        return fromFile(file, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromPath(Path path) {
        return fromPath(path, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromString(String path) {
        return fromString(path, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromContent(String content) {
        return fromContent(content, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return fromArchive(archive, inside, new XmlMapper());
    }

    public static MemoryXmlConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, new XmlMapper());
    }

    @Override
    public void loadFromString(String contents) {
        if (Validator.isNull(contents, null)) contents = getEmptyConfig();

        Map<?, ?> input;
        try {
            input = xmlMapper.reader().readValue(contents, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading XML configuration. ", e);
        }
        convertMapsToSections(input, this);
    }

    @Override
    public String saveToString() {
        String contents;
        try {
            contents = xmlMapper.writer().withRootName(getRootName()).writeValueAsString(getValues(true));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while saving XML configuration. ", e);
        }
        return contents;
    }

    public @NotNull String getRootName() {
        return rootName;
    }

    public void setXmlMapper(@NotNull XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public void setRootName(@NotNull String rootName) {
        this.rootName = rootName;
    }
}
