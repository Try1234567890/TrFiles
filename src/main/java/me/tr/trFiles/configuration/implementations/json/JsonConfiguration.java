package me.tr.trFiles.configuration.implementations.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryJsonConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

public class JsonConfiguration extends FileConfiguration {
    private JsonOptions options;


    @Override
    public String[] getExtensions() {
        return new String[]{".json"};
    }

    @Override
    protected MemoryJsonConfiguration newConfiguration() {
        return new MemoryJsonConfiguration(buildGson());
    }

    public JsonConfiguration(File file, Map<String, Object> map) {
        super(file, map);
        setConfiguration(new MemoryJsonConfiguration(getFile(), buildGson()));
    }

    public JsonConfiguration(File file, Reader reader) {
        super(file, reader);
        setConfiguration(new MemoryJsonConfiguration(getFile(), buildGson()));
    }

    public JsonConfiguration(File file, InputStream is) {
        super(file, is);
        setConfiguration(new MemoryJsonConfiguration(getFile(), buildGson()));
    }

    public JsonConfiguration(File file) {
        super(file);
        setConfiguration(new MemoryJsonConfiguration(getFile(), buildGson()));
    }

    public JsonConfiguration(Path path) {
        super(path);
        setConfiguration(new MemoryJsonConfiguration(getFile(), buildGson()));
    }

    public JsonConfiguration(String path) {
        super(path);
        setConfiguration(new MemoryJsonConfiguration(getFile(), buildGson()));
    }

    public JsonConfiguration(ZipFile archive, File inside, File to) {
        super(archive, inside, to);
        setConfiguration(new MemoryJsonConfiguration(getFile(), buildGson()));
    }

    public JsonConfiguration(File archive, File inside, File to) {
        super(archive, inside, to);
        setConfiguration(new MemoryJsonConfiguration(getFile(), buildGson()));
    }

    public static JsonConfiguration fromMap(File file, Map<String, Object> map) {
        return new JsonConfiguration(file, map);
    }

    public static JsonConfiguration fromReader(File file, Reader reader) {
        return new JsonConfiguration(file, reader);
    }

    public static JsonConfiguration fromInputStream(File file, InputStream is) {
        return new JsonConfiguration(file, is);
    }

    public static JsonConfiguration fromFile(File file) {
        return new JsonConfiguration(file);
    }

    public static JsonConfiguration fromPath(Path path) {
        return new JsonConfiguration(path);
    }

    public static JsonConfiguration fromString(String path) {
        return new JsonConfiguration(path);
    }

    public static JsonConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return new JsonConfiguration(archive, inside, to);
    }

    public static JsonConfiguration fromArchive(File archive, File inside, File to) {
        return new JsonConfiguration(archive, inside, to);
    }

    private Gson buildGson() {
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

    @Override
    public JsonOptions options() {
        if (options == null) {
            options = new JsonOptions(this);
        }
        return options;
    }
}
