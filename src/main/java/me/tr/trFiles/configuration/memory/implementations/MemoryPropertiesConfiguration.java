package me.tr.trFiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;

public class MemoryPropertiesConfiguration extends MemoryConfiguration {
    private JavaPropsMapper propertiesMapper;

    public MemoryPropertiesConfiguration() {
        this.propertiesMapper = new JavaPropsMapper();
    }

    public MemoryPropertiesConfiguration(Reader reader) {
        super(reader);
        this.propertiesMapper = new JavaPropsMapper();
    }

    public MemoryPropertiesConfiguration(InputStream is) {
        super(is);
        this.propertiesMapper = new JavaPropsMapper();
    }

    public MemoryPropertiesConfiguration(Map<String, Object> map) {
        super(map);
        this.propertiesMapper = new JavaPropsMapper();
    }

    public MemoryPropertiesConfiguration(JavaPropsMapper propertiesMapper) {
        this.propertiesMapper = propertiesMapper;
    }

    public MemoryPropertiesConfiguration(Reader reader, JavaPropsMapper propertiesMapper) {
        super(reader);
        this.propertiesMapper = propertiesMapper;
    }

    public MemoryPropertiesConfiguration(InputStream is, JavaPropsMapper propertiesMapper) {
        super(is);
        this.propertiesMapper = propertiesMapper;
    }

    public MemoryPropertiesConfiguration(Map<String, Object> map, JavaPropsMapper propertiesMapper) {
        super(map);
        this.propertiesMapper = propertiesMapper;
    }

    @Override
    public void loadFromString(String contents) {
        if (Validator.isNull(contents, null)) contents = getEmptyConfig();
        Map<?, ?> input;
        try {
            Properties prop = new Properties();
            prop.load(new StringReader(contents));
            input = propertiesMapper.readPropertiesAs(prop, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading Properties configuration. ", e);
        }
        convertMapsToSections(input, this);
    }

    @Override
    public String saveToString() {
        String contents;
        try {
            contents = propertiesMapper.writeValueAsString(getValues(true));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading Properties configuration. ", e);
        }
        return contents;
    }

    public void setPropertiesMapper(JavaPropsMapper propertiesMapper) {
        this.propertiesMapper = propertiesMapper;
    }
}
