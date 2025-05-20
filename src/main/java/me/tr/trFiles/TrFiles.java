package me.tr.trFiles;

import me.tr.trFiles.general.FileManager;

public class TrFiles {
    private static TrFiles instance;
    private FileManager fileManager;

    public FileManager getFileManager() {
        if (fileManager == null) {
            fileManager = new FileManager();
        }
        return fileManager;
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
    }

}