package me.tr.configuration.file;

import me.tr.configuration.Section;
import me.tr.configuration.file.json.JsonConfiguration;
import me.tr.configuration.file.yaml.YamlConfiguration;
import me.tr.configuration.memory.MemoryConfiguration;
import me.tr.general.utility.FileUtility;
import me.tr.general.utility.Validate;

import java.io.*;
import java.util.Map;

public abstract class FileConfiguration extends MemoryConfiguration {

    /**
     * Load {@link FileConfiguration} from a file.
     * <p>
     * This method loads {@link FileConfiguration} from a file by creating a new {@link Reader},
     * while the file has line append the line + \n to create a new line.
     * When there aren't any other lines, abstract method {@link #loadFromString(String)} is called
     * and file and configuration of this FileConfiguration is set.
     *
     * @param file File to load {@link FileConfiguration} from.
     * @throws RuntimeException if an error occurs while loading the file.
     */
    public void load(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            loadFromString(sb.toString());
            setFile(file);
            setFileConfiguration(this);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading file: " + file.getName(), e);
        }
    }

    /**
     * Save a {@link FileConfiguration} into a file.
     * <p>
     * This method save {@link FileConfiguration} into a file by creating a new {@link Writer}
     * and calling abstract method {@link #saveToString()}.
     * At last, write all data got by saveToString() into file.
     *
     * @param file File to save {@link FileConfiguration} into.
     * @throws RuntimeException if an error occurs while saving the file.
     * @see #save()
     */
    public void save(File file) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file))) {
            String data = saveToString();
            writer.write(data);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving file: " + file.getName());
        }
    }

    /**
     * Save a {@link FileConfiguration} into a file.
     * <p>
     * This method uses as {@link File} to save into the same file as used one to load this FileConfiguration.
     *
     * @throws RuntimeException if an error occurs while saving the file.
     * @see #save(File)
     */
    public void save() {
        save(getFile());
    }

    /**
     * Convert {@link Map} into {@link Section} by cycling all
     * {@link Map.Entry} that map contains, if the entry value
     * is an instance of {@code Map}, call recursive this method by passing
     * as {@code Map} the cast value as it and as {@code Section}
     * the result of {@link Section#createSection(String)} using
     * as parameter the entry key, else if is not an instance of {@code Map}
     * call method {@link Section#set(String, Object)} by using entry key as first
     * parameter and entry value as second parameter.
     *
     * @param input   Map to convert into sections.
     * @param section Root section to start insert values in.
     */
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

    /**
     * Reload {@link FileConfiguration} by deleting all data and loading again
     * from the specified {@link File}.
     *
     * @param file The file to reload from.
     */
    protected void reload(File file) {
        map.clear();
        loadConfiguration(file);
    }

    /**
     * Reload {@link FileConfiguration} by calling {@link #reload(File)},
     * used file is the same used to load this {@code FileConfiguration}
     */
    protected void reload() {
        reload(getFile());
    }

    /**
     * Load a {@link FileConfiguration} by creating a new file
     * using the string file parameter and delegate to {@link #loadConfiguration(File)}
     *
     * @param file String path to file to load {@link FileConfiguration} from.
     * @return Loaded {@link FileConfiguration} if no error occurs, otherwise null.
     * @throws IllegalArgumentException if the file hasn't an extension or is not supported.
     * @see JsonConfiguration#loadConfiguration(File)
     * @see YamlConfiguration#loadConfiguration(File)
     * @see #loadConfiguration(File)
     */
    public static FileConfiguration loadConfiguration(String file) {
        return loadConfiguration(new File(file));
    }

    /**
     * Load a {@link FileConfiguration} from a file by
     * automatically detect which one supported file is
     * by getting extension and load the correlated one.
     *
     * @param file File to load {@link FileConfiguration} from.
     * @return Loaded {@link FileConfiguration} if no error occurs, otherwise null.
     * @throws IllegalArgumentException if the file hasn't an extension or is not supported.
     * @see JsonConfiguration#loadConfiguration(File)
     * @see YamlConfiguration#loadConfiguration(File)
     * @see #loadConfiguration(String)
     */
    public static FileConfiguration loadConfiguration(File file) {
        Validate.checkIf(FileUtility.hasFileExtension(file), "File " + file.getName() + " not contains an extension");
        Validate.checkIf(FileUtility.isSupportedExtension(file), "File " + file.getName() + " is not supported");
        String extension = FileUtility.getExtension(file);
        if ("json".equalsIgnoreCase(extension)) {
            return JsonConfiguration.loadConfiguration(file);
        } else {
            return YamlConfiguration.loadConfiguration(file);
        }
    }


    @Override
    public FileOptions options() {
        if (options == null) {
            options = new FileOptions(this);
        }
        return (FileOptions) options;
    }
}
