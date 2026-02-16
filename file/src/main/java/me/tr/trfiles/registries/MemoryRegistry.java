package me.tr.trfiles.registries;

import me.tr.trfiles.Validator;
import me.tr.trfiles.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.configuration.memory.MemoryEntry;
import me.tr.trfiles.configuration.memory.implementations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryRegistry extends Registry<Class<? extends MemoryConfiguration>, MemoryEntry> {
    private static MemoryRegistry instance;

    private MemoryRegistry() {
    }

    public static MemoryRegistry getInstance() {
        if (instance == null) {
            instance = new MemoryRegistry();
            instance.getRegistry().putAll(
                    Map.ofEntries(
                            Map.entry(MemoryYamlConfiguration.class, MemoryYamlConfiguration.ENTRY),
                            Map.entry(MemoryXmlConfiguration.class, MemoryXmlConfiguration.ENTRY),
                            Map.entry(MemoryToonConfiguration.class, MemoryToonConfiguration.ENTRY),
                            Map.entry(MemoryTomlConfiguration.class, MemoryTomlConfiguration.ENTRY),
                            Map.entry(MemoryPropertiesConfiguration.class, MemoryPropertiesConfiguration.ENTRY),
                            Map.entry(MemoryJsonConfiguration.class, MemoryJsonConfiguration.ENTRY)
                    )
            );
        }
        return instance;
    }

    public Optional<MemoryEntry> getMemoryEntry(Class<? extends MemoryConfiguration> clazz) {
        Validator.isNull(clazz, "The provided class is null.");
        return Optional.ofNullable(getRegistry().get(clazz));
    }

    public static Optional<MemoryEntry> getMemory(Class<? extends MemoryConfiguration> clazz) {
        return getInstance().getMemoryEntry(clazz);
    }
}
