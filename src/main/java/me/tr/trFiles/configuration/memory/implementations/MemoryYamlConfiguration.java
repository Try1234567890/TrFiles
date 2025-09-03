package me.tr.trFiles.configuration.memory.implementations;

import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

public class MemoryYamlConfiguration extends MemoryConfiguration {
    private @NotNull Yaml yaml;

    public MemoryYamlConfiguration() {
        this.yaml = new Yaml();
    }

    public MemoryYamlConfiguration(Reader reader) {
        super(reader);
        this.yaml = new Yaml();
    }

    public MemoryYamlConfiguration(InputStream is) {
        super(is);
        this.yaml = new Yaml();
    }

    public MemoryYamlConfiguration(Map<String, Object> map) {
        super(map);
        this.yaml = new Yaml();
    }

    public MemoryYamlConfiguration(File file) {
        super(file);
        this.yaml = new Yaml();
    }

    public MemoryYamlConfiguration(@NotNull Yaml yaml) {
        this.yaml = yaml;
    }

    public MemoryYamlConfiguration(Reader reader, @NotNull Yaml yaml) {
        super(reader);
        this.yaml = yaml;
    }

    public MemoryYamlConfiguration(InputStream is, @NotNull Yaml yaml) {
        super(is);
        this.yaml = yaml;
    }

    public MemoryYamlConfiguration(Map<String, Object> map, @NotNull Yaml yaml) {
        super(map);
        this.yaml = yaml;
    }

    public MemoryYamlConfiguration(File file, @NotNull Yaml yaml) {
        super(file);
        this.yaml = yaml;
    }


    @Override
    public void loadFromString(String contents) {
        if (Validator.isNull(contents, null)) contents = getEmptyConfig();
        Map<?, ?> input;
        try {
            input = yaml.load(contents);
        } catch (YAMLException e) {
            throw new RuntimeException("An error occurs while loading YAML configuration. ", e);
        }
        convertMapsToSections(input, this);
    }

    @Override
    public String saveToString() {
        String contents;
        try {
            contents = yaml.dump(getValues(true));
        } catch (YAMLException e) {
            throw new RuntimeException("An error occurs while saving YAML configuration. ", e);
        }
        return contents;
    }

    public void setYaml(@NotNull Yaml yaml) {
        this.yaml = yaml;
    }
}
