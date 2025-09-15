package me.tr.trFiles.configuration.memory.implementations;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.implementations.json.JsonConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

public class MemoryJsonConfiguration extends MemoryConfiguration {
    private @NotNull Gson gson;

    public MemoryJsonConfiguration(@NotNull Gson gson) {
        this.gson = gson;
    }

    private MemoryJsonConfiguration() {
        this.gson = new Gson();
    }


    @Override
    public @NotNull MemoryJsonConfiguration newMemoryConfiguration() {
        return new MemoryJsonConfiguration();
    }

    @Override
    public @NotNull JsonConfiguration newConfiguration(File file) {
        return JsonConfiguration.emptyJson(file);
    }

    @Override
    public boolean hasFileConfiguration() {
        return true;
    }

    @Override
    public String getEmptyConfig() {
        return "{}\n";
    }

    @Override
    public @NotNull Class<JsonConfiguration> getConfigurationReference() {
        return JsonConfiguration.class;
    }

    public static MemoryJsonConfiguration emptyJson(Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).empty();
    }

    public static MemoryJsonConfiguration fromMemory(MemoryConfiguration memoryConfiguration, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).copy(memoryConfiguration);
    }

    public static MemoryJsonConfiguration fromReader(Reader reader, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).from(reader);
    }

    public static MemoryJsonConfiguration fromInputStream(InputStream is, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).from(is);
    }

    public static MemoryJsonConfiguration fromMap(Map<String, Object> map, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).from(map);
    }

    public static MemoryJsonConfiguration fromFile(File file, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).from(file);
    }

    public static MemoryJsonConfiguration fromPath(Path path, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).from(path);
    }

    public static MemoryJsonConfiguration fromString(String path, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).from(path);
    }

    public static MemoryJsonConfiguration fromContent(String content, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).fromC(content);
    }

    public static MemoryJsonConfiguration fromArchive(ZipFile archive, File inside, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).from(archive, inside);
    }

    public static MemoryJsonConfiguration fromBytes(byte[] bytes, Gson gson) {
        return (MemoryJsonConfiguration) new MemoryJsonConfiguration(gson != null ? gson : new Gson()).from(bytes);
    }

    public static MemoryJsonConfiguration emptyJson() {
        return emptyJson(new Gson());
    }

    public static MemoryJsonConfiguration fromMemory(MemoryConfiguration memoryConfiguration) {
        return fromMemory(memoryConfiguration, new Gson());
    }

    public static MemoryJsonConfiguration fromReader(Reader reader) {
        return fromReader(reader, new Gson());
    }

    public static MemoryJsonConfiguration fromInputStream(InputStream is) {
        return fromInputStream(is, new Gson());
    }

    public static MemoryJsonConfiguration fromMap(Map<String, Object> map) {
        return fromMap(map, new Gson());
    }

    public static MemoryJsonConfiguration fromFile(File file) {
        return fromFile(file, new Gson());
    }

    public static MemoryJsonConfiguration fromPath(Path path) {
        return fromPath(path, new Gson());
    }

    public static MemoryJsonConfiguration fromString(String path) {
        return fromString(path, new Gson());
    }

    public static MemoryJsonConfiguration fromContent(String content) {
        return fromContent(content, new Gson());
    }

    public static MemoryJsonConfiguration fromArchive(ZipFile archive, File inside) {
        return fromArchive(archive, inside, new Gson());
    }

    public static MemoryJsonConfiguration fromBytes(byte[] bytes) {
        return fromBytes(bytes, new Gson());
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

    public void setGson(@NotNull Gson gson) {
        this.gson = gson;
    }
}
