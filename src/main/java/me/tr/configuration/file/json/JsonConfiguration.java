package me.tr.configuration.file.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import me.tr.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JsonConfiguration extends FileConfiguration {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

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


    public static JsonConfiguration loadConfiguration(File file) {
        JsonConfiguration config = new JsonConfiguration();
        config.load(file);
        return config;
    }


    public static JsonConfiguration loadConfiguration(String file) {
        return loadConfiguration(new File(file));
    }
}
