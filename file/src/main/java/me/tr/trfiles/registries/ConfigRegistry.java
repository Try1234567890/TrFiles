package me.tr.trfiles.registries;

import me.tr.trfiles.Validator;
import me.tr.trfiles.configuration.implementations.ConfigEntry;
import me.tr.trfiles.configuration.implementations.FileConfiguration;
import me.tr.trfiles.configuration.implementations.json.JsonConfiguration;
import me.tr.trfiles.configuration.implementations.properties.PropertiesConfiguration;
import me.tr.trfiles.configuration.implementations.toml.TomlConfiguration;
import me.tr.trfiles.configuration.implementations.toon.ToonConfiguration;
import me.tr.trfiles.configuration.implementations.xml.XmlConfiguration;
import me.tr.trfiles.configuration.implementations.yaml.YamlConfiguration;
import me.tr.trfiles.management.FileUtility;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ConfigRegistry extends Registry<Class<? extends FileConfiguration>, ConfigEntry> {
    private static ConfigRegistry instance;
    private static final Map<Class<? extends FileConfiguration>, ConfigEntry> registry = new HashMap<>();

    private ConfigRegistry() {
    }

    public static ConfigRegistry getInstance() {
        if (instance == null) {
            instance = new ConfigRegistry();
            instance.getRegistry().putAll(
                    Map.ofEntries(
                            Map.entry(YamlConfiguration.class, YamlConfiguration.ENTRY),
                            Map.entry(XmlConfiguration.class, XmlConfiguration.ENTRY),
                            Map.entry(ToonConfiguration.class, ToonConfiguration.ENTRY),
                            Map.entry(TomlConfiguration.class, TomlConfiguration.ENTRY),
                            Map.entry(PropertiesConfiguration.class, PropertiesConfiguration.ENTRY),
                            Map.entry(JsonConfiguration.class, JsonConfiguration.ENTRY)
                    )
            );
        }
        return instance;
    }

    @Override
    protected Map<Class<? extends FileConfiguration>, ConfigEntry> getRegistry() {
        return registry;
    }

    public Optional<ConfigEntry> getConfigEntry(Class<? extends FileConfiguration> clazz) {
        Validator.isNull(clazz, "The provided class is null.");
        return Optional.ofNullable(getRegistry().get(clazz));
    }

    public Optional<ConfigEntry> getConfigEntry(File file) {
        String ext = FileUtility.getExtensionWithPoint(file.getName());

        return getRegistry().values()
                .stream()
                .filter(entry -> List.of(entry.getExtensions()).contains(ext))
                .findFirst();
    }

    public static Optional<ConfigEntry> getConfig(File file) {
        return getInstance().getConfigEntry(file);
    }

    public static Optional<ConfigEntry> getConfig(Class<? extends FileConfiguration> clazz) {
        return getInstance().getConfigEntry(clazz);
    }
}
