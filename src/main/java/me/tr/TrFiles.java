package me.tr;


import me.tr.configuration.Section;
import me.tr.configuration.file.FileConfiguration;
import me.tr.configuration.file.yaml.YamlConfiguration;

import java.io.File;

public class TrFiles {

    public static void main(String[] args) {
        File file = new File("C:/Programming/Java/TrFiles/src/main/resources/config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.createSection("example");
        config.set("example.key", "value");
        config.save(file);
    }
}