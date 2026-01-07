package me.tr.trfiles.file.placeholders;

import me.tr.trfiles.file.configuration.implementations.FileConfiguration;
import me.tr.trformatter.palceholders.functions.Function;
import me.tr.trformatter.palceholders.params.Params;
import me.tr.trformatter.palceholders.placeholders.Placeholder;
import me.tr.trformatter.uids.UID;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Retrieve extends Placeholder {
    public Retrieve() {
        super(new UID("Retrieve"));
    }

    public Retrieve(Params params, Function[] functions, int start, int end) {
        super(new UID("Retrieve"), params, functions, start, end, 1);
    }

    @Override
    public String process(String str) {
        File configFile = getParams().asFile(0);
        String path = getParams().asString(1);
        if (!configFile.exists()) {
            return "FileNotExists";
        }
        if (!configFile.isFile()) {
            return "IsNotAFile";
        }
        if (!configFile.canRead()) {
            return "IsNotReadable";
        }
        if (path.isEmpty()) {
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                    content.append('\n');
                }
                return content.toString();
            } catch (IOException e) {
                return e.getClass().getSimpleName();
            }
        }
        FileConfiguration config = FileConfiguration.fromFile(configFile);
        return config.get(path, str).toString();
    }

    @Override
    public Retrieve newInstance(Params params, Function[] functions, int start, int end) {
        return new Retrieve(params, functions, start, end);
    }
}
