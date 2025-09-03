package me.tr.trFiles.configuration.implementations.yaml;

import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryXmlConfiguration;
import me.tr.trFiles.configuration.memory.implementations.MemoryYamlConfiguration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipFile;

public class YamlConfiguration extends FileConfiguration {
    private YamlOptions options;


    public String[] getExtensions() {
        return new String[]{".yml", ".yaml"};
    }

    @Override
    protected MemoryYamlConfiguration newConfiguration() {
        return new MemoryYamlConfiguration(buildYaml());
    }

    public YamlConfiguration(File file, Map<String, Object> map) {
        super(file, map);
    }

    public YamlConfiguration(File file, Reader reader) {
        super(file, reader);
    }

    public YamlConfiguration(File file, InputStream is) {
        super(file, is);
    }

    public YamlConfiguration(File file) {
        super(file);
    }

    public YamlConfiguration(Path path) {
        super(path);
    }

    public YamlConfiguration(String path) {
        super(path);
    }

    public YamlConfiguration(ZipFile archive, File inside, File to) {
        super(archive, inside, to);
    }

    public YamlConfiguration(File archive, File inside, File to) {
        super(archive, inside, to);
    }

    public static YamlConfiguration fromMap(File file, Map<String, Object> map) {
        return new YamlConfiguration(file, map);
    }

    public static YamlConfiguration fromReader(File file, Reader reader) {
        return new YamlConfiguration(file, reader);
    }

    public static YamlConfiguration fromInputStream(File file, InputStream is) {
        return new YamlConfiguration(file, is);
    }

    public static YamlConfiguration fromFile(File file) {
        return new YamlConfiguration(file);
    }

    public static YamlConfiguration fromPath(Path path) {
        return new YamlConfiguration(path);
    }

    public static YamlConfiguration fromString(String path) {
        return new YamlConfiguration(path);
    }

    public static YamlConfiguration fromArchive(ZipFile archive, File inside, File to) {
        return new YamlConfiguration(archive, inside, to);
    }

    public static YamlConfiguration fromArchive(File archive, File inside, File to) {
        return new YamlConfiguration(archive, inside, to);
    }


    @Override
    public YamlOptions options() {
        if (options == null) {
            options = new YamlOptions(this);
        }
        return options;
    }

    private Yaml buildYaml() {
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
        return new Yaml(loaderOptions, dumperOptions);
    }
}
