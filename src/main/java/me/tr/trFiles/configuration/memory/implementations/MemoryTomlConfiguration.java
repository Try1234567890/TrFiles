package me.tr.trFiles.configuration.memory.implementations;

import com.fasterxml.jackson.dataformat.toml.TomlMapper;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.implementations.toml.TomlConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

public class MemoryTomlConfiguration extends MemoryConfiguration {
    private @NotNull TomlMapper tomlMapper;

    private MemoryTomlConfiguration(@NotNull TomlMapper tomlMapper) {
        this.tomlMapper = tomlMapper;
    }

    private MemoryTomlConfiguration() {
        this.tomlMapper = new TomlMapper();
    }

    @Override
    public @NotNull MemoryTomlConfiguration newMemoryConfiguration() {
        return new MemoryTomlConfiguration();
    }

    @Override
    public @NotNull TomlConfiguration newConfiguration(File file) {
        return TomlConfiguration.emptyToml(file);
    }

    @Override
    public boolean hasFileConfiguration() {
        return true;
    }

    @Override
    public @NotNull Class<TomlConfiguration> getConfigurationReference() {
        return TomlConfiguration.class;
    }

    public static MemoryTomlConfiguration emptyToml(TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).empty();
    }

    public static MemoryTomlConfiguration fromMemory(MemoryConfiguration memoryConfiguration, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).copy(memoryConfiguration);
    }

    public static MemoryTomlConfiguration fromReader(Reader reader, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).from(reader);
    }

    public static MemoryTomlConfiguration fromInputStream(InputStream is, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).from(is);
    }

    public static MemoryTomlConfiguration fromMap(Map<String, Object> map, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).from(map);
    }

    public static MemoryTomlConfiguration fromFile(File file, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).from(file);
    }

    public static MemoryTomlConfiguration fromPath(Path path, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).from(path);
    }

    public static MemoryTomlConfiguration fromString(String path, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).from(path);
    }

    public static MemoryTomlConfiguration fromContent(String content, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).fromC(content);
    }

    public static MemoryTomlConfiguration fromArchive(ZipFile archive, File inside, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).from(archive, inside);
    }

    public static MemoryTomlConfiguration fromBytes(byte[] bytes, TomlMapper tomlMapper) {
        return (MemoryTomlConfiguration) new MemoryTomlConfiguration(tomlMapper != null ? tomlMapper : new TomlMapper()).from(bytes);
    }

    public static MemoryTomlConfiguration emptyToml() {
        return emptyToml(new TomlMapper());
    }

    public static MemoryTomlConfiguration fromMemory(MemoryConfiguration memoryConfiguration) {
        return fromMemory(memoryConfiguration, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromReader(Reader reader) {
        return fromReader(reader, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromMap(Map<String, Object> map) {
        return fromMap(map, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromFile(File file) {
        return fromFile(file, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromPath(Path path) {
        return fromPath(path, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromString(String path) {
        return fromString(path, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromContent(String content) {
        return fromContent(content, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromArchive(ZipFile archive, File inside) {
        return fromArchive(archive, inside, new TomlMapper());
    }

    public static MemoryTomlConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, new TomlMapper());
    }

    @Override
    public void loadFromString(String contents) {
        if (Validator.isNull(contents != null, null)) contents = getEmptyConfig();


        Map<?, ?> input;
        try {
            input = tomlMapper.readValue(contents, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading Toml configuration. ", e);
        }
        convertMapsToSections(input, this);
    }

    @Override
    public String saveToString() {
        String contents;
        try {
            contents = tomlMapper.writeValueAsString(getValues(true));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while saving Toml configuration. ", e);
        }
        return contents;
    }

    public void setTomlMapper(@NotNull TomlMapper tomlMapper) {
        this.tomlMapper = tomlMapper;
    }
}
