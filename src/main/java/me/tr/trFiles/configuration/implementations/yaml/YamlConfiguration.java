package me.tr.trFiles.configuration.implementations.yaml;

import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.implementations.Implementations;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.general.utility.Validate;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

public class YamlConfiguration extends FileConfiguration {
    private Yaml yaml;
    private YamlOptions options;

    @Override
    protected FileConfiguration loadFromString(String contents) {
        Validate.notNull(contents != null, "Contents cannot be null.");
        Map<?, ?> input;
        try {
            input = yaml.load(contents);
        } catch (YAMLException e) {
            throw new RuntimeException("Error while loading configuration. ", e);
        }
        convertMapsToSections(input, this);
        setImplementation(Implementations.YAML);
        return this;
    }

    @Override
    protected String saveToString() {
        String contents;
        try {
            contents = yaml.dump(getValues(true));
        } catch (YAMLException e) {
            throw new RuntimeException("Error while saving configuration. ", e);
        }
        setImplementation(Implementations.YAML);
        return buildHeader() + contents + buildFooter();

    }

    @Override
    protected YamlConfiguration newInstance() {
        return new YamlConfiguration();
    }

    /**
     * Create a new empty instance of {@link YamlConfiguration}.
     */
    public YamlConfiguration() {
        buildYaml();
    }

    /**
     * Create a new instance of {@link YamlConfiguration} with provided configuration content.
     */
    public YamlConfiguration(MemoryConfiguration configuration) {
        super(configuration);
        buildYaml();
    }

    public static YamlConfiguration from(File file) {
        return (YamlConfiguration) FileConfiguration.from(file);
    }

    public static YamlConfiguration fromMap(Map<String, Object> map) {
        return (YamlConfiguration) fromMap(map, Implementations.YAML);
    }

    @Override
    public YamlOptions options() {
        if (options == null) {
            options = new YamlOptions(this);
        }
        return options;
    }

    private void buildYaml() {
        setImplementation(Implementations.YAML);

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(options().isAllowDuplicateKeys());
        loaderOptions.setAllowRecursiveKeys(options().isAllowRecursiveKeys());
        loaderOptions.setProcessComments(options().isProcessComments());
        loaderOptions.setEnumCaseSensitive(options().isEnumCaseSensitive());


        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultScalarStyle(options().getDefaultStyle());
        dumperOptions.setDefaultFlowStyle(options().getDefaultFlowStyle());
        dumperOptions.setCanonical(options().isCanonical());
        dumperOptions.setAllowUnicode(options().isAllowUnicode());
        dumperOptions.setAllowReadOnlyProperties(options().isAllowReadOnlyProperties());
        dumperOptions.setIndent(options().getIndent());
        dumperOptions.setSplitLines(options().isSplitLines());
        dumperOptions.setLineBreak(options().getLineBreak());
        dumperOptions.setExplicitStart(options().isExplicitStart());
        dumperOptions.setExplicitEnd(options().isExplicitEnd());
        dumperOptions.setTimeZone(options().getTimeZone());
        dumperOptions.setNonPrintableStyle(options().getNonPrintableStyle());
        dumperOptions.setVersion(options().getVersion());
        dumperOptions.setPrettyFlow(options().isPrettyFlow());


        yaml = new Yaml(loaderOptions, dumperOptions);
    }
}
