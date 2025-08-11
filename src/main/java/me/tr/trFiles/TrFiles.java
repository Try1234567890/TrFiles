package me.tr.trFiles;

import me.tr.trFiles.general.managers.FileManager;
import me.tr.trFiles.general.managers.ImageManager;

public class TrFiles {
    private static FileManager fileManager;
    private static ImageManager imageManager;

    public static FileManager getFileManager() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }
    public static ImageManager getImageManager() {
        if (imageManager == null) {
            imageManager = new ImageManager();
        }
        return imageManager;
    }

    private TrFiles() {}
}