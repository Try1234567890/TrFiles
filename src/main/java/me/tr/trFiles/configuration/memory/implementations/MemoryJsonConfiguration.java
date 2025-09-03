package me.tr.trFiles.configuration.memory.implementations;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

public class MemoryJsonConfiguration extends MemoryConfiguration {
    private Gson gson;

    public MemoryJsonConfiguration() {
        this.gson = new Gson();
    }

    public MemoryJsonConfiguration(Reader reader) {
        super(reader);
        this.gson = new Gson();
    }

    public MemoryJsonConfiguration(InputStream is) {
        this.gson = new Gson();
        super.from(is);
    }

    public MemoryJsonConfiguration(Map<String, Object> map) {
        this.gson = new Gson();
        super.from(map);
    }

    public MemoryJsonConfiguration(File file) {
        this.gson = new Gson();
        super.from(file);
    }

    public MemoryJsonConfiguration(Gson gson) {
        this.gson = gson;
    }

    public MemoryJsonConfiguration(Reader reader, Gson gson) {
        this.gson = gson;
        super.from(reader);
    }

    public MemoryJsonConfiguration(InputStream is, Gson gson) {
        this.gson = gson;
        super.from(is);
    }

    public MemoryJsonConfiguration(Map<String, Object> map, Gson gson) {
        this.gson = gson;
        super.from(map);
    }

    public MemoryJsonConfiguration(File file, Gson gson) {
        this.gson = gson;
        super.from(file);
    }

    @Override
    public void loadFromString(String contents) {
        if (Validator.isNull(contents, null)) contents = getEmptyConfig();

        Map<?, ?> input;
        try {
            input = gson.fromJson(contents, Map.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("An error occurs while loading JSON configuration.", e);
        }
        convertMapsToSections(input, this);
    }

    @Override
    public String saveToString() {
        return gson.toJson(getValues(true));
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }
}
