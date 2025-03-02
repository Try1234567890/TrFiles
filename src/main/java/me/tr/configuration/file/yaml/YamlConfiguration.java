package me.tr.configuration.file.yaml;

import me.tr.configuration.file.FileConfiguration;
import me.tr.general.utilities.FileUtilities;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {
    private final String BLANK_FILE = "{}\n";
    private final String COMMENT_PREFIX = "# ";
    private DumperOptions yamlOptions;
    private Yaml yaml;

    public YamlConfiguration() {
        this.yamlOptions = new DumperOptions();
        this.yaml = new Yaml(yamlOptions);
    }

    @Override
    protected String saveToString() {
        this.yamlOptions = loadOptions();
        this.yaml = new Yaml(yamlOptions);
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

    private YamlConfiguration(File file, DumperOptions options) {
        this();
        options.setDefaultFlowStyle(options.getDefaultFlowStyle());
        options.setIndent(options.getIndent());
        yaml = new Yaml(options);
        loadConfiguration(file);
    }

    private YamlConfiguration(File file) {
        this();
        loadConfiguration(file);
    }

    public static YamlConfiguration loadConfiguration(File file) {
        if (!FileUtilities.isYaml(file)) {
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

    public static YamlConfiguration loadConfiguration(String file) {
        return loadConfiguration(new File(file));
    }

    @Override
    public YamlOptions options() {
        if (options == null) {
            options = new YamlOptions(this);
        }
        return (YamlOptions) options;
    }

    private DumperOptions loadOptions() {
        if (yamlOptions == null) {
            yamlOptions = new DumperOptions();
        }
        yamlOptions.setIndent(options().indent());
        yamlOptions.setDefaultFlowStyle(options().flowStyle());
        return yamlOptions;
    }
}