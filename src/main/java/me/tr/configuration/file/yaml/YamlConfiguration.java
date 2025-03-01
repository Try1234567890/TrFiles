package me.tr.configuration.file.yaml;

import me.tr.configuration.Section;
import me.tr.configuration.file.FileConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {
    private Yaml yaml = new Yaml();


    @Override
    protected String saveToString() {
        return yaml.dump(getValues(true));
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

    private YamlConfiguration(File file, DumperOptions options) {
        options.setDefaultFlowStyle(options.getDefaultFlowStyle());
        options.setIndent(options.getIndent());
        yaml = new Yaml(options);
        load(file);
    }

    private YamlConfiguration(File file) {
        load(file);
    }

    private YamlConfiguration() {}

    public static YamlConfiguration loadConfiguration(File file) {
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        return config;
    }


    public static YamlConfiguration loadConfiguration(String file) {
        return loadConfiguration(new File(file));
    }

    @Override
    public YamlOptions options() {
        return new YamlOptions(this);
    }

}
