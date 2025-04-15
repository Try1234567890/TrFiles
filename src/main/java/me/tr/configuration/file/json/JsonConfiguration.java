package me.tr.configuration.file.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import me.tr.configuration.file.FileConfiguration;
import me.tr.general.utilities.FileUtilities;

import java.io.File;
import java.util.Map;

/**
 * This class represents the extension of FileConfiguration File for JSON.
 */
public class JsonConfiguration extends FileConfiguration {
    private final Gson gson;

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

    @Override
    protected String saveToString() {
        return gson.toJson(getValues(true));
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void loadFromString(String contents) {
        if (contents.isEmpty()) {
            map.clear();
            return;
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

    public static JsonConfiguration loadConfiguration(File file) {
        if (!FileUtilities.isJson(file)) {
            throw new IllegalArgumentException("File is not a JSON file: " + file.getName());
        }
        JsonConfiguration config = new JsonConfiguration();
        config.load(file);
        return config;
    }

    public void reload(File file) {
        map.clear();
        loadConfiguration(file);
    }

    @Override
    protected void reload() {
        reload(getFile());
    }

    public static JsonConfiguration loadConfiguration(String file) {
        return loadConfiguration(new File(file));
    }

    @Override
    public JsonOptions options() {
        if (options == null) {
            options = new JsonOptions(this);
        }
        return (JsonOptions) options;
    }
}
