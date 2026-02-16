package me.tr.trfiles.registries;

import me.tr.trfiles.Validator;
import me.tr.trfiles.memory.MemoryImage;
import me.tr.trfiles.memory.MemoryImageEntry;
import me.tr.trfiles.memory.implementations.MemoryICO;
import me.tr.trfiles.memory.implementations.MemoryJPG;
import me.tr.trfiles.memory.implementations.MemoryPNG;

import java.util.Optional;

public class MemoryImagesRegistry extends Registry<Class<? extends MemoryImage>, MemoryImageEntry> {
    private static MemoryImagesRegistry instance;

    public static MemoryImagesRegistry getInstance() {
        if (instance == null) {
            instance = new MemoryImagesRegistry();
            instance.register(MemoryPNG.class, MemoryPNG.ENTRY);
            instance.register(MemoryJPG.class, MemoryJPG.ENTRY);
            instance.register(MemoryICO.class, MemoryICO.ENTRY);
        }
        return instance;
    }

    private MemoryImagesRegistry() {
    }


    public Optional<MemoryImageEntry> getImageEntry(Class<? extends MemoryImage> clazz) {
        Validator.isNull(clazz, "The provided class is null.");
        return Optional.ofNullable(getRegistry().get(clazz));
    }

    public static Optional<MemoryImageEntry> getImage(Class<? extends MemoryImage> clazz) {
        return getInstance().getImageEntry(clazz);
    }
}
