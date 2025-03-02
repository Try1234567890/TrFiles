package me.tr.configuration.file;

import me.tr.configuration.Section;
import me.tr.configuration.memory.MemoryConfiguration;

import java.io.*;
import java.util.Map;

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

    protected void convertMapsToSections(Map<?, ?> input, Section section) {
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    protected abstract String buildHeader();

    protected abstract String buildFooter();

    protected abstract String saveToString();

    protected abstract void loadFromString(String contents);

    @Override
    public FileOptions options() {
        if (options == null) {
            options = new FileOptions(this);
        }
        return (FileOptions) options;
    }
}
