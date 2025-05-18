package me.tr.trFiles.configuration.file;

import me.tr.trFiles.TrFiles;
import me.tr.trFiles.configuration.Section;
import me.tr.trFiles.configuration.file.json.JsonConfiguration;
import me.tr.trFiles.configuration.file.xml.XMLConfiguration;
import me.tr.trFiles.configuration.file.yaml.YamlConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.general.utility.FileUtility;
import me.tr.trFiles.general.utility.Validate;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public abstract class FileConfiguration extends MemoryConfiguration {
    protected final TrFiles main = TrFiles.getInstance();
    protected final String BLANK_FILE = "{}\n";

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

    protected String build(String headerOrFooter) {
        String[] lines = headerOrFooter.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines)
            sb.append(options().commentPrefix()).append(line).append("\n").append(options().commentSuffix());
        return sb.toString();
    }


    @Override
    public FileOptions options() {
        if (options == null) {
            options = new FileOptions(this);
        }
        return (FileOptions) options;
    }


    public static FileConfiguration loadConfiguration(File file) {
        Validate.notNull(file != null, "File cannot be null.");
        Validate.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file.");
        if (FileUtility.hasFileExtension(file)) {
            return loadConfigByExtension(file);
        } else {
            return loadConfigWithoutExtension(file);
        }
    }

    protected static FileConfiguration loadConfigWithoutExtension(File file) {
        File parent = file.getParentFile();
        Validate.notNull(parent != null, "Parent directory cannot be null.");
        Validate.checkIf(parent.exists(), "Folder at " + parent.getPath() + " does not exist.");
        Validate.checkIf(parent.isDirectory(), "Object at " + parent.getPath() + " is not a directory.");
        File[] files = parent.listFiles();
        Validate.notNull(files != null, "Files at " + parent.getPath() + " cannot be null.");
        Optional<File> optFile = Arrays.stream(files)
                .filter(f -> f.getName().equals(file.getName()))
                .findFirst();
        if (optFile.isEmpty()) {
            throw new RuntimeException("File at " + file.getName() + " not found into " + parent.getPath());
        }
        return loadConfigByExtension(optFile.get());
    }

    protected static @Nullable FileConfiguration loadConfigByExtension(File file) {
        return switch (FileUtility.getExtension(file)) {
            case "json" -> new JsonConfiguration(file);
            case "xml" -> new XMLConfiguration(file);
            case "yaml", "yml" -> new YamlConfiguration(file);
            default -> null;
        };
    }

    /**
     * Reload {@link FileConfiguration} by deleting all data and loading again
     * from the specified {@link File}.
     *
     * @param file The file to reload from.
     */
    public void reload(File file) {
        map.clear();
        loadConfiguration(file);
    }

    /**
     * Reload {@link FileConfiguration} by calling {@link #reload(File)},
     * used file is the same used to load this {@code FileConfiguration}
     */
    public void reload() {
        reload(getFile());
    }
}
