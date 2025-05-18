package me.tr.trFiles;

import me.tr.trFiles.configuration.file.FileConfiguration;
import me.tr.trFiles.general.FileManager;

import java.io.File;

public class TrFiles {
    private static TrFiles instance;
    private FileManager fileManager;

    public static void main(String[] args) {
        File file = new File("E:\\", "test.yml");
        FileConfiguration config = FileConfiguration.loadConfiguration(file);
        System.out.println(config);
        System.out.println(config.getClass().getTypeName());
        System.out.println(config.getString("Test.GoGo"));

    }

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