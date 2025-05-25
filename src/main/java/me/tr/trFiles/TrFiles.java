package me.tr.trFiles;

import me.tr.trFiles.general.managers.FileManager;
import me.tr.trFiles.general.managers.ImageManager;

public class TrFiles {
    private static TrFiles instance;
    private FileManager fileManager;
    private ImageManager imageManager;

    public FileManager getFileManager() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
    }
    public ImageManager getImageManager() {
        if (imageManager == null) {
            imageManager = new ImageManager();
        }
        return imageManager;
    }

    public static TrFiles getInstance() {
        if (instance == null) {
            instance = new TrFiles();
        }
        return instance;
    }

    public TrFiles() {
        instance = this;
        fileManager = new FileManager();
        imageManager = new ImageManager();
    }

}