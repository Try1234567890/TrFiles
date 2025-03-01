package me.tr;


import me.tr.configuration.file.json.JsonConfiguration;

import java.io.File;

public class TrFiles {

    public static void main(String[] args) {
        File file = new File("C:/Programming/Java/TrFiles/src/main/resources/config.json");
        JsonConfiguration json = JsonConfiguration.loadConfiguration(file);

        System.out.println(json);
        System.out.println(json.isInt("server.port")); // Outputs: 25565
        json.set("server.address", "0.0.0.0");
        json.save(file);
    }
}