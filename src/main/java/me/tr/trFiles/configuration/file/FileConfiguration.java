package me.tr.trFiles.configuration.file;

import me.tr.trFiles.TrFiles;
import me.tr.trFiles.configuration.Section;
import me.tr.trFiles.configuration.file.json.JsonConfiguration;
import me.tr.trFiles.configuration.file.xml.XMLConfiguration;
import me.tr.trFiles.configuration.file.yaml.YamlConfiguration;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.general.utility.FileUtility;
import me.tr.trFiles.general.utility.Validate;

import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public abstract class FileConfiguration extends MemoryConfiguration {
    public static final String[] FILE_EXTENSIONS = new String[]{"json", "xml", "yaml", "yml" };
    protected static final TrFiles main = TrFiles.getInstance();
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
    public FileConfiguration load(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            loadFromString(sb.toString());
            setFile(file);
            setFileConfiguration(this);
            return this;
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
        if (!file.exists())
            main.getFileManager().createFile(file);
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
     * Reload {@link FileConfiguration} by deleting all data and loading again
     * from the specified {@link File}.
     *
     * @param file The file to reload from.
     */
    public FileConfiguration reload(File file) {
        map.clear();
        setFile(file);
        FileConfiguration config = loadConfiguration(file);
        setFileConfiguration(config);
        return config;
    }

    /**
     * Reload {@link FileConfiguration} by calling {@link #reload(File)},
     * used file is the same used to load this {@code FileConfiguration}
     */
    public FileConfiguration reload() {
        return reload(getFile());
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

    /**
     * Build header or footer of current {@link FileConfiguration}
     * by splitting it in lines and adding respective character to
     * make lines as comments in file.
     *
     * @param headerOrFooter String representing header or footer to string
     * @return String representing header or footer formatted with options of current {@link FileConfiguration}
     */
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


    protected abstract String buildHeader();

    protected abstract String buildFooter();

    protected abstract String saveToString();

    protected abstract FileConfiguration loadFromString(String contents);

    /**
     * Loads a new {@link FileConfiguration} from the file into jar.
     *
     * @param jar     Jar file instance.
     * @param intoJar File representing the path inside the jar.
     * @param to      File representing the path to save the content file into.
     * @return The new {@link FileConfiguration} instance loaded from the file into jar.
     * @see #loadFromJar(JarFile, File)
     */
    public static FileConfiguration loadFromJar(JarFile jar, File intoJar, File to) {
        if (!to.exists())
            main.getFileManager().createFile(to);
        InputStream is = main.getFileManager().getFileInJar(jar, intoJar);
        if (is == null)
            return null;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to)))) {
            String line;
            while ((line = in.readLine()) != null) {
                out.write(line + "\n");
            }
            jar.close();
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file into jar", e);
        }
        return FileConfiguration.loadConfiguration(to);
    }

    /**
     * Loads a new {@link FileConfiguration} from the file into jar.
     *
     * @param jar     Jar file instance.
     * @param intoJar File representing the path inside the jar and path to file to save content into.
     * @return The new {@link FileConfiguration} instance loaded from the file into jar.
     * @see #loadFromJar(JarFile, File, File)
     */
    public static FileConfiguration loadFromJar(JarFile jar, File intoJar) {
        return loadFromJar(jar, intoJar, intoJar);
    }

    /**
     * Loads a new {@link FileConfiguration} from the file into jar.
     *
     * @param jar     Jar file instance.
     * @param intoJar File representing the path inside the jar and path to file to save content into.
     * @return The new {@link FileConfiguration} instance loaded from the file into jar.
     * @see #loadFromJar(JarFile, File, File)
     */
    public static FileConfiguration loadFromJar(File jar, File intoJar, File to) {
        Validate.checkIf(jar.isFile(), "Object at " + main.getFileManager().getStringPathFromFile(jar) + " is not a file");
        Validate.checkIf(FileUtility.isJar(jar), "File at " + main.getFileManager().getStringPathFromFile(jar) + " is not a jar file");
        try {
            JarFile jarFile = new JarFile(jar);
            return loadFromJar(jarFile, intoJar, to);
        } catch (IOException e) {
            throw new RuntimeException("Error while loading file " + main.getFileManager().getStringPathFromFile(intoJar) + " from " + main.getFileManager().getStringPathFromFile(jar), e);
        }
    }

    /**
     * Loads a new {@link FileConfiguration} from the file into jar.
     *
     * @param jar     Jar file instance.
     * @param intoJar File representing the path inside the jar and path to file to save content into.
     * @return The new {@link FileConfiguration} instance loaded from the file into jar.
     * @see #loadFromJar(JarFile, File, File)
     */
    public static FileConfiguration loadFromJar(File jar, File intoJar) {
        return loadFromJar(jar, intoJar, intoJar);
    }

    /**
     * Loads a new {@link FileConfiguration} from the file into jar.
     *
     * @param jar     Jar file instance.
     * @param intoJar File representing the path inside the jar and path to file to save content into.
     * @return The new {@link FileConfiguration} instance loaded from the file into jar.
     * @see #loadFromJar(JarFile, File, File)
     */
    public static FileConfiguration loadFromJar(String jar, String intoJar, String to) {
        return loadFromJar(main.getFileManager().getFileFromString(jar), main.getFileManager().getFileFromString(intoJar),
                main.getFileManager().getFileFromString(to));
    }

    /**
     * Loads a new {@link FileConfiguration} from the file into jar.
     *
     * @param jar     Jar file instance.
     * @param intoJar File representing the path inside the jar and path to file to save content into.
     * @return The new {@link FileConfiguration} instance loaded from the file into jar.
     * @see #loadFromJar(JarFile, File, File)
     */
    public static FileConfiguration loadFromJar(String jar, String intoJar) {
        return loadFromJar(main.getFileManager().getFileFromString(jar), main.getFileManager().getFileFromString(intoJar));
    }

    /**
     * Loads a new {@link FileConfiguration} from the specified file.
     * <p>
     * If the provided file does not have an extension, this method attempts to auto-detect
     * the correct file by searching for a matching file name in the parent directory.
     *
     * @param file The file to load the {@link FileConfiguration} from.
     * @return The loaded {@link FileConfiguration} instance.
     * @throws IllegalArgumentException if the specified path does not point to a valid file.
     * @throws NullPointerException     if the file or required objects are null or do not exist.
     */
    public static FileConfiguration loadConfiguration(File file) {
        Validate.notNull(file != null, "File cannot be null.");
        if (FileUtility.hasFileExtension(file)) {
            return loadConfigurationByExtension(file);
        } else {
            return loadConfigurationWithoutExtension(file);
        }
    }

    /**
     * Loads a new {@link FileConfiguration} from the specified file by
     * creating new {@link File} with file param and delegating to {@link #loadConfiguration(File)}
     *
     * @param file The file to load the {@link FileConfiguration} from.
     * @return The loaded {@link FileConfiguration} instance.
     * @throws IllegalArgumentException if the specified path does not point to a valid file.
     * @throws NullPointerException     if the file or required objects are null or do not exist.
     * @see #loadConfiguration(File)
     */
    public static FileConfiguration loadConfiguration(String file) {
        Validate.notNull(file != null, "File string cannot be null.");
        return loadConfiguration(main.getFileManager().getFileFromString(file));
    }

    /**
     * Load a new {@link FileConfiguration} from the specified file without extension.
     *
     * @param file The file to load the {@link FileConfiguration} from.
     * @return The loaded {@link FileConfiguration} instance.
     */
    protected static FileConfiguration loadConfigurationWithoutExtension(File file) {
        File parent = file.getParentFile();
        Validate.notNull(parent != null, "Parent directory cannot be null.");
        Validate.checkIf(parent.exists(), "Folder at " + main.getFileManager().getStringPathFromFile(parent) + " does not exist.");
        Validate.checkIf(parent.isDirectory(), "Object at " + main.getFileManager().getStringPathFromFile(parent) + " is not a directory.");
        File[] files = parent.listFiles();
        Validate.notNull(files != null, "Files at " + main.getFileManager().getStringPathFromFile(parent) + " cannot be null.");
        Optional<File> optFile = Arrays.stream(files)
                .filter(File::isFile)
                .filter(f -> FileUtility.getFileNameWithoutExtension(f.getName()).equals(file.getName()))
                .findFirst();
        if (optFile.isEmpty()) {
            throw new NullPointerException("File " + file.getName() + " not found into " + main.getFileManager().getStringPathFromFile(parent));
        }
        return loadConfigurationByExtension(optFile.get());
    }

    /**
     * Load a new {@link FileConfiguration} from the specified file.
     *
     * @param file The file to load the {@link FileConfiguration} from.
     * @return The loaded {@link FileConfiguration} instance.
     * @throws IllegalStateException if the file extension is not recognized
     */
    protected static FileConfiguration loadConfigurationByExtension(File file) {
        return switch (FileUtility.getExtension(file)) {
            case "json" -> JsonConfiguration.loadConfiguration(file);
            case "xml" -> XMLConfiguration.loadConfiguration(file);
            case "yaml", "yml" -> YamlConfiguration.loadConfiguration(file);
            default ->
                    throw new IllegalStateException("Extension " + FileUtility.getExtension(file.getName()) + " of " + main.getFileManager().getStringPathFromFile(file) + " is not supported");
        };
    }
}
