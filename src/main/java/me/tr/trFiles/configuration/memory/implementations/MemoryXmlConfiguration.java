package me.tr.trFiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

public class MemoryXmlConfiguration extends MemoryConfiguration {
    private XmlMapper xmlMapper;
    private String rootName;

    public MemoryXmlConfiguration() {
        this.xmlMapper = new XmlMapper();
    }

    public MemoryXmlConfiguration(Reader reader) {
        super(reader);
        this.xmlMapper = new XmlMapper();
    }

    public MemoryXmlConfiguration(InputStream is) {
        super(is);
        this.xmlMapper = new XmlMapper();
    }

    public MemoryXmlConfiguration(Map<String, Object> map) {
        super(map);
        this.xmlMapper = new XmlMapper();
    }

    public MemoryXmlConfiguration(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public MemoryXmlConfiguration(Reader reader, XmlMapper xmlMapper) {
        super(reader);
        this.xmlMapper = xmlMapper;
    }

    public MemoryXmlConfiguration(InputStream is, XmlMapper xmlMapper) {
        super(is);
        this.xmlMapper = xmlMapper;
    }

    public MemoryXmlConfiguration(Map<String, Object> map, XmlMapper xmlMapper) {
        super(map);
        this.xmlMapper = xmlMapper;
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

    @Override
    public String getEmptyConfig() {
        return "<" + rootName + ">" + "</" + rootName + ">";
    }

    public String getRootName() {
        return rootName;
    }

    public void setXmlMapper(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
}
