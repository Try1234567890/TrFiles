package me.tr.trfiles.registries;

import me.tr.trfiles.Validator;
import me.tr.trfiles.implementations.TrImageEntry;
import me.tr.trfiles.implementations.TrImage;
import me.tr.trfiles.implementations.ico.ICO;
import me.tr.trfiles.implementations.jpg.JPG;
import me.tr.trfiles.implementations.png.PNG;
import me.tr.trfiles.management.FileUtility;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class ImagesRegistry extends Registry<Class<? extends TrImage>, TrImageEntry> {
    private static ImagesRegistry instance;

    public static ImagesRegistry getInstance() {
        if (instance == null) {
            instance = new ImagesRegistry();
            instance.register(PNG.class, PNG.ENTRY);
            instance.register(JPG.class, JPG.ENTRY);
            instance.register(ICO.class, ICO.ENTRY);
        }
        return instance;
    }

    private ImagesRegistry() {
    }

    public Optional<TrImageEntry> getImageEntry(Class<? extends TrImage> clazz) {
        Validator.isNull(clazz, "The provided class is null.");
        return Optional.ofNullable(getRegistry().get(clazz));
    }

    public Optional<TrImageEntry> getImageEntry(File file) {
        String ext = FileUtility.getExtensionWithPoint(file.getName());

        if (ext.isEmpty())
            return Optional.empty();

        return getRegistry().values()
                .stream()
                .filter(entry -> List.of(entry.getExtensions()).contains(ext))
                .findFirst();
    }

    public static Optional<TrImageEntry> getImage(File file) {
        return getInstance().getImageEntry(file);
    }

    public static Optional<TrImageEntry> getImage(Class<? extends TrImage> clazz) {
        return getInstance().getImageEntry(clazz);
    }
}
