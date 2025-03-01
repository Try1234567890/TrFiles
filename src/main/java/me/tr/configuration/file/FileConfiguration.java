package me.tr.configuration.file;

import me.tr.configuration.memory.MemoryConfiguration;

import java.io.*;

public abstract class FileConfiguration extends MemoryConfiguration {

    public void load(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            loadFromString(sb.toString());
        } catch (IOException e) {
            // todo logger
        }
    }


    public void save(File file) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
            String data = saveToString();
            writer.write(data);
        } catch (IOException e) {
            // todo logger
        }
    }


    protected abstract String saveToString();

    protected abstract void loadFromString(String contents);
}
