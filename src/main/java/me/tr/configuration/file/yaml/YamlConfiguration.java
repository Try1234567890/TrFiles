package me.tr.configuration.file.yaml;

import me.tr.configuration.Section;
import me.tr.configuration.file.FileConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {
    private final Yaml yaml = new Yaml();
    private final String BLANK_FILE = "{}\n";


    @Override
    protected String saveToString() {
        return yaml.dump(map.toString());
    }

    @Override
    protected void loadFromString(String contents) {
        if (contents.isEmpty()) {
            map.clear();
            return;
        }
        Map<String, Object> fileMap;
        try {
            fileMap = yaml.load(contents);
        } catch (YAMLException e) {
            throw new YAMLException(e);
        }
        if (fileMap != null) {
            convertMapsToSections(fileMap, this);
        }
        return;
    }

    protected void convertMapsToSections(Map<?, ?> input, Section section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    public static YamlConfiguration loadConfiguration(File file) {
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        return config;
    }


    public static YamlConfiguration loadConfiguration(String file) {
        return loadConfiguration(new File(file));
    }


}
