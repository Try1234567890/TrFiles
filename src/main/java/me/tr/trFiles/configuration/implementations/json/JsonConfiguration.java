package me.tr.trFiles.configuration.implementations.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import me.tr.trFiles.TrFiles;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.implementations.Implementations;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.general.utility.Validate;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class JsonConfiguration extends FileConfiguration {
    private Gson gson;
    private JsonOptions options;


    @Override
    protected FileConfiguration loadFromString(String contents) {
        Validate.notNull(contents != null, "Contents cannot be null.");
        Map<?, ?> input;
        try {
            input = gson.fromJson(contents, Map.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Error while loading configuration. ", e);
        }
        convertMapsToSections(input, this);
        setImplementation(Implementations.JSON);
        return this;
    }

    @Override
    protected String saveToString() {
        String contents = gson.toJson(getValues(true));
        setImplementation(Implementations.JSON);
        return buildHeader() + contents + buildFooter();
    }

    @Override
    protected JsonConfiguration newInstance() {
        return new JsonConfiguration();
    }

    /**
     * Create a new empty instance of {@link JsonConfiguration}.
     */
    public JsonConfiguration() {
        buildGson();
    }

    /**
     * Create a new instance of {@link JsonConfiguration} with provided configuration content.
     */
    public JsonConfiguration(MemoryConfiguration configuration) {
        super(configuration);
        buildGson();
    }

    public static JsonConfiguration from(File file) {
        return (JsonConfiguration) FileConfiguration.from(file);
    }

    public static JsonConfiguration fromMap(Map<String, Object> map) {
        return (JsonConfiguration) fromMap(map, Implementations.JSON);
    }

    private void buildGson() {
        setImplementation(Implementations.JSON);

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
        gson = gsonBuilder.create();
    }


    @Override
    public JsonOptions options() {
        if (options == null) {
            options = new JsonOptions(this);
        }
        return options;
    }
}
