package me.tr.trFiles.configuration.implementations;

import me.tr.trFiles.configuration.implementations.json.JsonConfiguration;
import me.tr.trFiles.configuration.implementations.properties.PropertiesConfiguration;
import me.tr.trFiles.configuration.implementations.toml.TomlConfiguration;
import me.tr.trFiles.configuration.implementations.xml.XmlConfiguration;
import me.tr.trFiles.configuration.implementations.yaml.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;


/**
 * This enum contains all supported file types and
 * some useful information.
 */
public enum Implementations {

    YAML(new YamlConfiguration(), ".yaml", ".yml"),
    JSON(new JsonConfiguration(), ".json"),
    XML(new XmlConfiguration(), ".xml"),
    TOML(new TomlConfiguration(), ".toml"),
    PROPERTIES(new PropertiesConfiguration(), ".properties");

    private final String[] extensions;
    private final FileConfiguration reference;

    Implementations(FileConfiguration reference, String... extensions) {
        this.reference = reference;
        this.extensions = extensions;
    }


    public FileConfiguration getReference() {
        return reference;
    }

    public String[] getExtensions() {
        return extensions;
    }

    /**
     * Check if the provided file is valid
     * for the current implementation.
     * <p>
     * For example, some valid files for the YAML implementation are:
     * example.yaml or test.yml
     *
     * @param file The file to check
     * @return true if is a valid file, otherwise false.
     */
    public boolean isValid(File file) {
        for (String extension : extensions) {
            if (file.getName().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }


    public static @Nullable Implementations fromExtension(String extension) {
        try {
            return valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException _) {
            for (Implementations impl : Implementations.values()) {
                for (String exte : impl.getExtensions()) {
                    exte = exte.substring(1);
                    if (exte.equalsIgnoreCase(extension)) {
                        return impl;
                    }
                }
            }
        }
        return null;
    }

    public String toString() {
        return String.join(", ", extensions);
    }

    public static String listToString() {
        return String.join(", ", Arrays.stream(values()).map(impl -> impl.getExtensions()[0]).toList());
    }
}
