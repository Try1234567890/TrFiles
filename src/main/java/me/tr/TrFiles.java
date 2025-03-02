package me.tr;

import me.tr.general.FileManager;

import java.io.File;
import java.io.InputStream;

public class TrFiles {

    public static void main(String[] args) {
        FileManager fm = new FileManager();
        String path = "C:\\Programming\\Java\\TrFiles\\src\\main\\resources";
        //File file = fm.createFile(path + "\\config.yml");
        //if (file == null) return;
        InputStream is = fm.getFileInJar(path + "\\CMI-9.7.9.2.jar", "plugin.json");
        if (is == null) return;
        File pluginYaml = fm.saveFile(is, path + "\\plugin.json", true);
        if (pluginYaml == null) return;
        File pluginYaml2 = fm.saveFile(pluginYaml, path + "\\plugin2.json", true);
        if (pluginYaml2 == null) return;
        String fileToString = fm.saveToString(pluginYaml);
    }
}