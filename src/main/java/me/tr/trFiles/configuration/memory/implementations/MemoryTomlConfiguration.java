package me.tr.trFiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

public class MemoryTomlConfiguration extends MemoryConfiguration {
    private TomlMapper mapper;

    public MemoryTomlConfiguration() {
        this.mapper = new TomlMapper();
    }

    public MemoryTomlConfiguration(Reader reader) {
        super(reader);
        this.mapper = new TomlMapper();
    }

    public MemoryTomlConfiguration(InputStream is) {
        super(is);
        this.mapper = new TomlMapper();
    }

    public MemoryTomlConfiguration(Map<String, Object> map) {
        super(map);
        this.mapper = new TomlMapper();
    }

    public MemoryTomlConfiguration(TomlMapper mapper) {
        this.mapper = mapper;
    }

    public MemoryTomlConfiguration(Reader reader, TomlMapper mapper) {
        super(reader);
        this.mapper = mapper;
    }

    public MemoryTomlConfiguration(InputStream is, TomlMapper mapper) {
        super(is);
        this.mapper = mapper;
    }

    public MemoryTomlConfiguration(Map<String, Object> map, TomlMapper mapper) {
        super(map);
        this.mapper = mapper;
    }

    @Override
    public void loadFromString(String contents) {
        if (Validator.isNull(contents != null, null)) contents = getEmptyConfig();


        Map<?, ?> input;
        try {
            input = mapper.readValue(contents, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading Toml configuration. ", e);
        }
        convertMapsToSections(input, this);
    }

    @Override
    public String saveToString() {
        String contents;
        try {
            contents = mapper.writeValueAsString(getValues(true));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while saving Toml configuration. ", e);
        }
        return contents;
    }

    public void setTomlMapper(TomlMapper mapper) {
        this.mapper = mapper;
    }
}
