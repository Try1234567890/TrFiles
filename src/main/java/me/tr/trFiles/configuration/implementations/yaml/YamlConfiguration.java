package me.tr.trFiles.configuration.implementations.yaml;

import me.tr.trFiles.configuration.ConfigRegistry;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryYamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.zip.ZipFile;

public class YamlConfiguration extends FileConfiguration {
    private YamlOptions options;

    private YamlConfiguration() {
    }

    public String[] getExtensions() {
        return new String[]{".yml", ".yaml"};
    }

    @Override
    public YamlConfiguration newConfiguration(File file) {
        return YamlConfiguration.emptyYaml(file);
    }

    @Override
    public MemoryYamlConfiguration newMemoryConfiguration() {
        return MemoryYamlConfiguration.emptyYaml(buildYaml());
    }

    @Override
    public void register() {
        ConfigRegistry.register(getClass());
    }

    @Override
    public MemoryYamlConfiguration getConfiguration() {
        return (MemoryYamlConfiguration) super.getConfiguration();
    }

    public static YamlConfiguration emptyYaml(File file) {
        return (YamlConfiguration) emptyConfig(file, YamlConfiguration.class);
    }

    public static YamlConfiguration fromReader(File file, Reader reader) {
        return (YamlConfiguration) fromReader(file, reader, YamlConfiguration.class);
    }


    public static YamlConfiguration fromInputStream(File file, InputStream is) {
        return (YamlConfiguration) fromInputStream(file, is, YamlConfiguration.class);
    }

    public static YamlConfiguration fromFile(File file) {
        return (YamlConfiguration) fromFile(file, YamlConfiguration.class);
    }

    public static YamlConfiguration fromPath(Path path) {
        return (YamlConfiguration) fromPath(path, YamlConfiguration.class);
    }

    public static YamlConfiguration fromString(String path) {
        return (YamlConfiguration) fromString(path, YamlConfiguration.class);
    }

    public static YamlConfiguration fromContent(File file, String content) {
        return (YamlConfiguration) fromContent(file, content, YamlConfiguration.class);
    }

    public static YamlConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return (YamlConfiguration) fromArchive(archive, inside, to, YamlConfiguration.class);
    }

    public static YamlConfiguration fromBytes(File file, byte[] bytes) {
        return (YamlConfiguration) fromBytes(file, bytes, YamlConfiguration.class);
    }

    @Override
    public YamlOptions options() {
        if (options == null) {
            options = new YamlOptions(this);
        }
        return options;
    }

    public void options(YamlOptions options) {
        this.options = options;
    }

    private Yaml buildYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();

        loaderOptions.setAllowDuplicateKeys(options().isAllowDuplicateKeys());
        loaderOptions.setWrappedToRootException(options().isWrappedToRootException());
        loaderOptions.setMaxAliasesForCollections(options().getMaxAliasesForCollections());
        loaderOptions.setAllowRecursiveKeys(options().isAllowRecursiveKeys());
        loaderOptions.setEnumCaseSensitive(options().isEnumCaseSensitive());
        loaderOptions.setNestingDepthLimit(options().getNestingDepthLimit());
        loaderOptions.setCodePointLimit(options().getCodePointLimit());
        loaderOptions.setMergeOnCompose(options().isMergeOnCompose());
        loaderOptions.setTagInspector(options().getTagInspector());

        DumperOptions dumperOptions = new DumperOptions();

        dumperOptions.setDefaultScalarStyle(options().getDefaultStyle());
        dumperOptions.setAllowUnicode(options().isAllowUnicode());
        dumperOptions.setIndent(options().getIndent());
        dumperOptions.setIndicatorIndent(options().getIndicatorIndent());
        dumperOptions.setIndentWithIndicator(options().isIndentWithIndicator());
        dumperOptions.setVersion(options().getVersion());
        dumperOptions.setCanonical(options().isCanonical());
        dumperOptions.setPrettyFlow(options().getPrettyFlow());
        dumperOptions.setWidth(options().getWidth());
        dumperOptions.setSplitLines(options().isSplitLines());
        dumperOptions.setDefaultFlowStyle(options().getDefaultFlowStyle());
        dumperOptions.setLineBreak(options().getLineBreak());
        dumperOptions.setExplicitStart(options().isExplicitStart());
        dumperOptions.setExplicitEnd(options().isExplicitEnd());
        dumperOptions.setTags(options().getTags());
        dumperOptions.setAllowReadOnlyProperties(options().isAllowReadOnlyProperties());
        dumperOptions.setTimeZone(options().getTimeZone());
        dumperOptions.setAnchorGenerator(options().getAnchorGenerator());
        dumperOptions.setMaxSimpleKeyLength(options().getMaxSimpleKeyLength());
        dumperOptions.setProcessComments(options().isProcessComments());
        dumperOptions.setNonPrintableStyle(options().getNonPrintableStyle());
        dumperOptions.setDereferenceAliases(options().isDereferenceAliases());

        return new Yaml(loaderOptions, dumperOptions);
    }
}
