package me.tr.trFiles.configuration.implementations.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.tr.trFiles.configuration.ConfigRegistry;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryJsonConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.zip.ZipFile;

public class JsonConfiguration extends FileConfiguration {
    private JsonOptions options;

    private JsonConfiguration() {
    }

    @Override
    public String[] getExtensions() {
        return new String[]{".json"};
    }

    @Override
    public JsonConfiguration newConfiguration(File file) {
        return JsonConfiguration.emptyJson(file);
    }

    @Override
    public MemoryJsonConfiguration newMemoryConfiguration() {
        return MemoryJsonConfiguration.emptyJson(buildGson(options()));
    }

    @Override
    public void register() {
        ConfigRegistry.register(getClass());
    }

    @Override
    public MemoryJsonConfiguration getConfiguration() {
        return (MemoryJsonConfiguration) super.getConfiguration();
    }

    public static JsonConfiguration emptyJson(File file) {
        return (JsonConfiguration) emptyConfig(file, JsonConfiguration.class);
    }

    public static JsonConfiguration fromReader(File file, Reader reader) {
        return (JsonConfiguration) fromReader(file, reader, JsonConfiguration.class);
    }


    public static JsonConfiguration fromInputStream(File file, InputStream is) {
        return (JsonConfiguration) fromInputStream(file, is, JsonConfiguration.class);
    }

    public static JsonConfiguration fromFile(File file) {
        return (JsonConfiguration) fromFile(file, JsonConfiguration.class);
    }

    public static JsonConfiguration fromPath(Path path) {
        return (JsonConfiguration) fromPath(path, JsonConfiguration.class);
    }

    public static JsonConfiguration fromString(String path) {
        return (JsonConfiguration) fromString(path, JsonConfiguration.class);
    }

    public static JsonConfiguration fromContent(File file, String content) {
        return (JsonConfiguration) fromContent(file, content, JsonConfiguration.class);
    }

    public static JsonConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return (JsonConfiguration) fromArchive(archive, inside, to, JsonConfiguration.class);
    }

    public static JsonConfiguration fromBytes(File file, byte[] bytes) {
        return (JsonConfiguration) fromBytes(file, bytes, JsonConfiguration.class);
    }

    @Override
    public JsonOptions options() {
        if (options == null) {
            options = new JsonOptions(this);
        }
        return options;
    }

    public JsonConfiguration options(JsonOptions options) {
        this.options = options;
        return this;
    }

    private Gson buildGson(JsonOptions options) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        if (options().isPrettyPrinting()) gsonBuilder.setPrettyPrinting();
        if (options().isSerializeNulls()) gsonBuilder.serializeNulls();
        gsonBuilder.setDateFormat(options().getDatePattern());
        gsonBuilder.setVersion(options.getVersion());
        gsonBuilder.excludeFieldsWithModifiers(options.getExcludeFieldsWithModifiers());
        if (options().isExcludeFieldsWithoutExposeAnnotation()) gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        gsonBuilder.setExclusionStrategies(options().getExclusionStrategies());
        if (options().isDisableHtmlEscaping()) gsonBuilder.disableHtmlEscaping();
        if (options().isGenerateNonExecutableJson()) gsonBuilder.generateNonExecutableJson();
        if (options().isEnableComplexMapKeySerialization()) gsonBuilder.enableComplexMapKeySerialization();
        if (options().isSerializeSpecialFloatingPointValues()) gsonBuilder.serializeSpecialFloatingPointValues();
        gsonBuilder.setLongSerializationPolicy(options().getLongSerializationPolicy());
        gsonBuilder.setStrictness(options().getStrictness());
        return gsonBuilder.create();
    }
}
