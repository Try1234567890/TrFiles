package me.tr.trFiles.configuration.file.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import me.tr.trFiles.configuration.file.FileConfiguration;
import me.tr.trFiles.general.utility.FileUtility;
import me.tr.trFiles.general.utility.Validate;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.jar.JarFile;

/**
 * This class represents the extension of FileConfiguration File for JSON.
 */
public class JsonConfiguration extends FileConfiguration {
    public static final String[] FILE_EXTENSIONS = new String[]{"json"};
    private final Gson gson;


    @Override
    protected String saveToString() {
        return gson.toJson(getValues(true));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected JsonConfiguration loadFromString(String contents) {
        if (contents.isEmpty()) {
            map.clear();
            return this;
        }
        Map<String, Object> fileMap;
        try {
            fileMap = gson.fromJson(contents, Map.class);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Failed to parse JSON configuration", e);
        }
        if (fileMap != null) {
            convertMapsToSections(fileMap, this);
        }
        return this;
    }

    public JsonConfiguration(File file) {
        GsonBuilder jsonBuilder = new GsonBuilder();
        if (options().prettyPrinting()) {
            jsonBuilder.setPrettyPrinting();
        }
        jsonBuilder.setDateFormat(options().dateFormat());
        jsonBuilder.setVersion(options().version());
        gson = jsonBuilder.create();
        loadConfiguration(file);
    }

    protected JsonConfiguration() {
        GsonBuilder jsonBuilder = new GsonBuilder();
        if (options().prettyPrinting())
            jsonBuilder.setPrettyPrinting();
        jsonBuilder.setDateFormat(options().dateFormat());
        jsonBuilder.setVersion(options().version());
        gson = jsonBuilder.create();
    }

    public static JsonConfiguration loadConfiguration(String file) {
        return loadConfiguration(main.getFileManager().getFileFromString(file));
    }

    public static JsonConfiguration loadConfiguration(File file) {
        if (!FileUtility.isJson(file)) {
            throw new IllegalArgumentException("File is not a JSON file: " + file.getName());
        }
        JsonConfiguration config = new JsonConfiguration();
        config.load(file);
        return config;
    }


    @Override
    public JsonOptions options() {
        if (options == null) {
            options = new JsonOptions(this);
        }
        return (JsonOptions) options;
    }


    /**
     * @return <b>{@code NOTHING, JSON NOT SUPPORT COMMENTS}</b>
     */
    @Override
    public String buildHeader() {
        return "";
    }

    /**
     * @return <b>{@code NOTHING, JSON NOT SUPPORT COMMENTS}</b>
     */
    @Override
    public String buildFooter() {
        return "";
    }
}
