package me.tr.configuration.file.yaml;

import me.tr.configuration.file.FileConfiguration;
import me.tr.general.utility.FileUtility;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.util.Map;

/**
 * This class represents the extension of FileConfiguration File for YAML.
 */
public class YamlConfiguration extends FileConfiguration {
    private final String BLANK_FILE = "{}\n";
    private final String COMMENT_PREFIX = "# ";
    private DumperOptions yamlOptions;
    private Yaml yaml;

    @Override
    protected String saveToString() {
        String header = buildHeader();
        String footer = buildFooter();
        String dump = yaml.dump(getValues(true));
        if (dump.equals(BLANK_FILE)) {
            return "";
        }
        if (options().copyHeader()) {
            dump = header + dump;
        }
        if (options().copyFooter()) {
            dump += footer;
        }
        return dump;
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
    }

    public YamlConfiguration(File file, DumperOptions options) {
        yaml = new Yaml(options);
        loadConfiguration(file);
    }

    protected YamlConfiguration() {
        yamlOptions = new DumperOptions();
        yamlOptions.setDefaultFlowStyle(options().flowStyle());
        yamlOptions.setIndent(options().indent());
        yaml = new Yaml(yamlOptions);
    }

    public YamlConfiguration(File file) {
        loadConfiguration(file);
    }


    public static YamlConfiguration loadConfiguration(String file) {
        return loadConfiguration(new File(file));
    }

    public static YamlConfiguration loadConfiguration(File file) {
        if (!FileUtility.isYaml(file)) {
            throw new IllegalArgumentException("File is not a YAML file: " + file.getName());
        }
        YamlConfiguration config = new YamlConfiguration();
        config.load(file);
        return config;
    }

    @Override
    protected String buildHeader() {
        String header = options().header();
        return build(header);
    }

    @Override
    protected String buildFooter() {
        String footer = options().footer();
        return build(footer);
    }

    private String build(String headerOrFooter) {
        String[] lines = headerOrFooter.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines)
            sb.append(COMMENT_PREFIX).append(line).append("\n");
        return sb.toString();
    }

    @Override
    public YamlOptions options() {
        if (options == null) {
            options = new YamlOptions(this);
        }
        return (YamlOptions) options;
    }
}