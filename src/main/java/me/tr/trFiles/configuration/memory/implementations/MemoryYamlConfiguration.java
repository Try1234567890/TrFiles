package me.tr.trFiles.configuration.memory.implementations;

import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.implementations.yaml.YamlConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

public class MemoryYamlConfiguration extends MemoryConfiguration {
    private @NotNull Yaml yaml;

    private MemoryYamlConfiguration(@NotNull Yaml yaml) {
        this.yaml = yaml;
    }

    private MemoryYamlConfiguration() {
        this.yaml = new Yaml();
    }

    @Override
    public @NotNull MemoryYamlConfiguration newMemoryConfiguration() {
        return new MemoryYamlConfiguration();
    }

    @Override
    public @NotNull YamlConfiguration newConfiguration(File file) {
        return YamlConfiguration.emptyYaml(file);
    }

    @Override
    public @NotNull Class<YamlConfiguration> getConfigurationReference() {
        return YamlConfiguration.class;
    }

    @Override
    public boolean hasFileConfiguration() {
        return true;
    }

    @Override
    public String getEmptyConfig() {
        return "{}\n";
    }

    public static MemoryYamlConfiguration emptyYaml(Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).empty();
    }

    public static MemoryYamlConfiguration fromMemory(MemoryConfiguration memoryConfiguration, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).copy(memoryConfiguration);
    }

    public static MemoryYamlConfiguration fromReader(Reader reader, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).from(reader);
    }

    public static MemoryYamlConfiguration fromInputStream(InputStream is, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).from(is);
    }

    public static MemoryYamlConfiguration fromMap(Map<String, Object> map, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).from(map);
    }

    public static MemoryYamlConfiguration fromFile(File file, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).from(file);
    }

    public static MemoryYamlConfiguration fromPath(Path path, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).from(path);
    }

    public static MemoryYamlConfiguration fromString(String path, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).from(path);
    }

    public static MemoryYamlConfiguration fromContent(String content, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).fromC(content);
    }

    public static MemoryYamlConfiguration fromArchive(ZipFile archive, File inside, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).from(archive, inside);
    }

    public static MemoryYamlConfiguration fromBytes(byte[] bytes, Yaml yaml) {
        return (MemoryYamlConfiguration) new MemoryYamlConfiguration(yaml != null ? yaml : new Yaml()).from(bytes);
    }

    public static MemoryYamlConfiguration emptyYaml() {
        return emptyYaml(new Yaml());
    }

    public static MemoryYamlConfiguration fromMemory(MemoryConfiguration memoryConfiguration) {
        return fromMemory(memoryConfiguration, new Yaml());
    }

    public static MemoryYamlConfiguration fromReader(Reader reader) {
        return fromReader(reader, new Yaml());
    }

    public static MemoryYamlConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, new Yaml());
    }

    public static MemoryYamlConfiguration fromMap(Map<String, Object> map) {
        return fromMap(map, new Yaml());
    }

    public static MemoryYamlConfiguration fromFile(File file) {
        return fromFile(file, new Yaml());
    }

    public static MemoryYamlConfiguration fromPath(Path path) {
        return fromPath(path, new Yaml());
    }

    public static MemoryYamlConfiguration fromString(String path) {
        return fromString(path, new Yaml());
    }

    public static MemoryYamlConfiguration fromContent(String content) {
        return fromContent(content, new Yaml());
    }

    public static MemoryYamlConfiguration fromArchive(ZipFile archive, File inside) {
        return fromArchive(archive, inside, new Yaml());
    }

    public static MemoryYamlConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, new Yaml());
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
