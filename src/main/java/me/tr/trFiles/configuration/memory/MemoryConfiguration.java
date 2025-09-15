package me.tr.trFiles.configuration.memory;

import me.tr.trFiles.ReflectionUtility;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.Configuration;
import me.tr.trFiles.configuration.Section;
import me.tr.trFiles.configuration.implementations.FileConfiguration;
import me.tr.trFiles.configuration.management.FileManager;
import me.tr.trFiles.configuration.management.FileUtility;
import me.tr.trFiles.configuration.memory.implementations.FileConfigurationNotFound;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * This class is the base of all MemoryConfigurations classes.
 * <p>
 * This class is the configuration saved in memory and contains: options and defaults.
 * <p>
 * This class doesn't have constructors, but use static methods to create new instances for practice.
 */
public abstract class MemoryConfiguration extends MemorySection implements Configuration {
    private MemoryOptions options;

    protected MemoryConfiguration() {
    }

    /**
     * @return an empty MemoryConfiguration.
     */
    public MemoryConfiguration empty() {
        loadFromString(getEmptyConfig());
        return this;
    }

    /**
     * Fill this MemoryConfiguration data by coping from an existing one.
     * <p>
     * <b>This method copy the defaults and options too.</b>
     *
     * @param memory The MemoryConfiguration to copy information from.
     * @return This instance with copied information.
     */
    public MemoryConfiguration copy(MemoryConfiguration memory) {
        super.map.clear();
        super.map.putAll(memory.asMap());
        this.options = memory.options();
        return this;
    }

    /**
     * Fill this MemoryConfiguration data by reading the reader content.
     *
     * @param reader The reader to read content from.
     * @return This instance with filled information.
     */
    public MemoryConfiguration from(Reader reader) {
        super.map.clear();
        Validator.isNull(reader, "The provided Reader is null");
        try {
            loadFromString(FileManager.readLines(reader));
            return this;
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided reader.", e);
        }
    }

    /**
     * Fill this MemoryConfiguration data by reading the input stream content.
     *
     * @param is The input stream to read content from.
     * @return This instance with filled information.
     */
    public MemoryConfiguration from(InputStream is) {
        super.map.clear();
        Validator.isNull(is, "The provided InputStream is null.");
        try (is) {
            return from(new BufferedReader(new InputStreamReader(is)));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided input stream.", e);
        }
    }

    /**
     * Fill this MemoryConfiguration data by reading the map content.
     *
     * @param map The map to read content from.
     * @return This instance with filled information.
     */
    public MemoryConfiguration from(Map<String, Object> map) {
        super.map.clear();
        Validator.isNull(map, "The provided Map is null");
        convertMapsToSections(map, this);
        return this;
    }

    /**
     * Fill this MemoryConfiguration data by reading the file content.
     *
     * @param file The file to read content from.
     * @return This instance with filled information.
     */
    public MemoryConfiguration from(File file) {
        super.map.clear();
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.canRead(), "Cannot read the provided file at " + file.getPath());
        Validator.checkIf(file.canWrite(), "Cannot write the provided file at " + file.getPath());
        Validator.checkIf(file.isFile(), "The saving file at " + file.getPath() + " not exists or is not a file.");

        try (InputStream is = new FileInputStream(file)) {
            return from(is);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided file: " + file.getPath(), e);
        }
    }

    /**
     * Fill this MemoryConfiguration data by reading the file at provided path.
     *
     * @param path The path to the file to read content from.
     * @return This instance with filled information.
     */
    public MemoryConfiguration from(Path path) {
        super.map.clear();
        Validator.isNull(path, "The provided path is null.");
        return from(path.toFile());
    }

    /**
     * Fill this MemoryConfiguration data by reading the file at provided path.
     *
     * @param path The path to the file to read content from.
     * @return This instance with filled information.
     */
    public MemoryConfiguration from(String path) {
        super.map.clear();
        Validator.isNull(path, "The path provided is null.");
        return from(FileUtility.getFileFromString(path));
    }

    /**
     * Fill this MemoryConfiguration data with the provided content.
     *
     * @param content The content to read.
     * @return This instance with filled information.
     */
    public MemoryConfiguration fromC(String content) {
        super.map.clear();
        Validator.isNull(content, "The content provided is null.");
        loadFromString(content);
        return this;
    }

    /**
     * Fill this MemoryConfiguration data with the provided bytes.
     *
     * @param bytes The bytes to read.
     * @return This instance with filled information.
     */
    public MemoryConfiguration from(byte[] bytes) {
        super.map.clear();
        Validator.isNull(bytes, "The provided bytes is null.");
        return fromC(new String(bytes));
    }

    /**
     * Fill this MemoryConfiguration data by reading the file inside the provided archive.
     *
     * @param archive The archive to search the file inside.
     * @param inside  The file to search inside the archive.
     * @return This instance with filled information.
     */
    public MemoryConfiguration from(ZipFile archive, File inside) {
        super.map.clear();
        Validator.isNull(archive, "The archive file cannot be null.");
        Validator.isNull(inside, "The inside archive file cannot be null.");

        try (archive) {
            ZipEntry entry = archive.getEntry(FileUtility.getStringPathFromFile(inside));
            if (entry == null) {
                throw new RuntimeException("The entry " + FileUtility.getStringPathFromFile(inside) + " does not exist inside the provided archive.");
            }
            return from(archive.getInputStream(entry));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided archive", e);
        }
    }


    /**
     * Create a new empty MemoryConfiguration of the provided one, used as reference.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @return a new empty instance of reference if not null.
     * @throws NullPointerException if the reference is null.
     */
    public static MemoryConfiguration emptyConfig(MemoryConfiguration reference) {
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.empty();
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the reader content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param reader    The reader to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the reader is null.
     */
    public static MemoryConfiguration fromReader(Reader reader, MemoryConfiguration reference) {
        Validator.isNull(reader, "The provided reader of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.from(reader);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the input stream content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param is        The input stream to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the reader is null.
     */
    public static MemoryConfiguration fromInputStream(InputStream is, MemoryConfiguration reference) {
        Validator.isNull(is, "The provided input stream of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.from(is);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the map content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param map       The map to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the input stream is null.
     */
    public static MemoryConfiguration fromMap(Map<String, Object> map, MemoryConfiguration reference) {
        Validator.isNull(map, "The provided map of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.from(map);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the file content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param file      The file to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the file is null.
     */
    public static MemoryConfiguration fromFile(File file, MemoryConfiguration reference) {
        Validator.isNull(file, "The provided file of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.from(file);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the file content at the provided path.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param path      The path to the file to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the path is null.
     */
    public static MemoryConfiguration fromPath(Path path, MemoryConfiguration reference) {
        Validator.isNull(path, "The provided path of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.from(path);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the file content at the provided string-path.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param path      The string-path to the file to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the string-path is null.
     */
    public static MemoryConfiguration fromString(String path, MemoryConfiguration reference) {
        Validator.isNull(path, "The provided string-path of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.from(path);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the provided content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param content   The content to read.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the reader is null.
     */
    public static MemoryConfiguration fromContent(String content, MemoryConfiguration reference) {
        Validator.isNull(content, "The provided content of MemoryConfiguration is null or empty. Use: emptyConfig().");
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.fromC(content);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the provided file inside the provided archive.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param archive   The archive to search the file inside.
     * @param inside    The file to search inside the archive.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of: the reference, the archive and inside file is null.
     */
    public static MemoryConfiguration fromArchive(ZipFile archive, File inside, MemoryConfiguration reference) {
        Validator.isNull(archive, "The provided archive of MemoryConfiguration is null.");
        Validator.isNull(inside, "The provided inside file of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.from(archive, inside);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the provided bytes.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param bytes     The bytes to read.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the bytes is null.
     */
    public static MemoryConfiguration fromBytes(byte[] bytes, MemoryConfiguration reference) {
        Validator.isNull(reference, "The provided reference instance of MemoryConfiguration is null.");
        return reference.from(bytes);
    }


    /**
     * Fill this MemoryConfiguration data by coping from an existing one.
     * <p>
     * <b>This method copy the defaults and options too.</b>
     *
     * @param memory The MemoryConfiguration to copy information from.
     * @return This instance with copied information.
     */
    public static MemoryConfiguration copy(MemoryConfiguration memory, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(memory, "The provided memory instance of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).copy(memory);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the reader content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param reader    The reader to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the reader is null.
     */
    public static MemoryConfiguration fromReader(Reader reader, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(reader, "The provided reader of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).from(reader);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the input stream content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param is        The input stream to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the reader is null.
     */
    public static MemoryConfiguration fromInputStream(InputStream is, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(is, "The provided input stream class of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).from(is);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the map content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param map       The map to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the input stream is null.
     */
    public static MemoryConfiguration fromMap(Map<String, Object> map, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(map, "The provided map of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).from(map);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the file content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param file      The file to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the file is null.
     */
    public static MemoryConfiguration fromFile(File file, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(file, "The provided file of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).from(file);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the file content at the provided path.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param path      The path to the file to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the path is null.
     */
    public static MemoryConfiguration fromPath(Path path, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(path, "The provided path of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).from(path);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the file content at the provided string-path.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param path      The string-path to the file to read content from.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the string-path is null.
     */
    public static MemoryConfiguration fromString(String path, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(path, "The provided string-path of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).from(path);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the provided content.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param content   The content to read.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the reader is null.
     */
    public static MemoryConfiguration fromContent(String content, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(content, "The provided content of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).fromC(content);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the provided file inside the provided archive.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param archive   The archive to search the file inside.
     * @param inside    The file to search inside the archive.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of: the reference, the archive and inside file is null.
     */
    public static MemoryConfiguration fromArchive(ZipFile archive, File inside, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(archive, "The provided archive of MemoryConfiguration is null.");
        Validator.isNull(inside, "The provided inside file of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).from(archive, inside);
    }

    /**
     * Create a new MemoryConfiguration of the provided one, used as reference.
     *
     * <p>
     * This configuration will be filled by reading the provided bytes.
     *
     * @param reference The MemoryConfiguration implementations to work with.
     * @param bytes     The bytes to read.
     * @return a new instance of reference filled with data, if not null.
     * @throws NullPointerException if at least one of the reference and the bytes is null.
     */
    public static MemoryConfiguration fromBytes(byte[] bytes, Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(bytes, "The provided bytes of MemoryConfiguration is null.");
        Validator.isNull(reference, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(reference).from(bytes);
    }

    /**
     * Convert the current MemoryConfiguration to a FileConfiguration.
     *
     * <p>
     * <b>
     * Make sure that the MemoryConfiguration from this method has called, has a FileConfiguration.
     * If the file does not exist, it will be created.
     * </b>
     *
     * @param file The file to assign to the new FileConfiguration.
     * @return a new FileConfiguration instance with this MemoryConfiguration and the provided file.
     * @throws FileConfigurationNotFound If this MemoryConfiguration doesn't have a FileConfiguration.
     * @throws NullPointerException      If the provided file is null.
     * @throws IllegalArgumentException  If file not exists or cannot be read.
     */
    public FileConfiguration toFileConfiguration(File file) {
        if (!hasFileConfiguration()) {
            throw new FileConfigurationNotFound(this);
        }

        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.isFile(), () -> FileManager.create(file));

        FileConfiguration configuration = newConfiguration(file);
        configuration.getConfiguration().copy(this);

        return configuration;
    }

    /**
     * Save the current MemoryConfiguration to the provided file.
     * <p>
     * <b>If file not exists, will be created</b>
     *
     * @param file The file to save the current MemoryConfiguration.
     * @throws NullPointerException     If the provided file is null.
     * @throws IllegalArgumentException if the file is not writable
     */
    @Override
    public void save(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.exists(), () -> FileManager.create(file));
        Validator.checkIf(file.canWrite(), "Cannot write the file at " + file.getPath());

        FileManager.writeLines(saveToString(), file);
    }

    /**
     * Reload the current MemoryConfiguration from the provided file.
     * <p>
     * <b>If file not exists, will be created</b>
     *
     * @param file The file to reload the current MemoryConfiguration from.
     * @throws NullPointerException     If the provided file is null.
     * @throws IllegalArgumentException if the file is not readable.
     */
    @Override
    public void reload(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.canRead(), "Cannot read the file at " + file.getPath());
        Validator.checkIf(file.exists(), () -> FileManager.create(file));

        loadFromString(FileManager.readLines(file));
    }

    /**
     * Moves the specified source file to the given destination.
     * <p>
     * <b>If destination file not exists, will be created</b>
     *
     * @param file the source file to move
     * @param to   the destination file
     * @throws NullPointerException     if one of the provided file is null.
     * @throws IllegalArgumentException if source file cannot be read or not exists or if destination file cannot be written.
     */
    @Override
    public void move(File file, File to) {
        Validator.isNull(file, "The provided source file is null.");
        Validator.checkIf(file.exists(), "The provided source file at " + file.getPath() + " does not exist.");
        Validator.checkIf(file.canRead(), "Cannot read the file at " + file.getPath());

        Validator.isNull(to, "The provided destination file is null.");
        Validator.checkIf(to.exists(), () -> FileManager.create(to));
        Validator.checkIf(to.canWrite(), "Cannot write the file at " + to.getPath());

        copy(file, to);
        FileManager.delete(file);
    }

    /**
     * Copy the specified source file to the given destination.
     * <p>
     * <b>If destination file not exists, will be created</b>
     *
     * @param file the source file to move
     * @param to   the destination file
     * @throws NullPointerException     if one of the provided file is null.
     * @throws IllegalArgumentException if source file cannot be read or not exists or if destination file cannot be written.
     */
    @Override
    public void copy(File file, File to) {
        Validator.isNull(file, "The provided source file is null.");
        Validator.checkIf(file.exists(), "The provided source file at " + file.getPath() + " does not exist.");
        Validator.checkIf(file.canRead(), "Cannot read the file at " + file.getPath());

        Validator.isNull(to, "The provided destination file is null.");
        Validator.checkIf(to.exists(), () -> FileManager.create(to));
        Validator.checkIf(to.canWrite(), "Cannot write the file at " + to.getPath());

        FileManager.writeLines(FileManager.readLines(file), to);
    }

    /**
     * Delete the provided file if exists.
     *
     * @param file The file to delete.
     */
    @Override
    public void delete(File file) {
        FileManager.delete(file);
    }

    /**
     * Insert the destination file inside the provided zip.
     *
     * @param zip  The zip archive.
     * @param file The file to insert inside the archive.
     * @throws NullPointerException     if one of the provided file is null.
     * @throws IllegalArgumentException if the zip cannot be written or isn't a zip or destination cannot be read or is not a file.
     */
    @Override
    public void zip(File zip, File file) {
        Validator.isNull(zip, "The provided zip file is null.");
        Validator.checkIf(FileUtility.isZip(zip), "The provided zip file at " + zip.getPath() + " does not exist or is not a file.");
        Validator.checkIf(zip.canWrite(), "Cannot read the write zip file.");


        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.isFile(), "The provided file at " + file.getPath() + " does not exist or is not a file.");
        Validator.checkIf(file.canRead(), "Cannot read the file at " + file.getPath());

        FileManager.zip(zip, file);
    }

    /**
     * Convert the current MemoryConfiguration to the provided one.
     *
     * @param to The destination MemoryConfiguration to convert the current.
     * @return The new MemoryConfiguration converted.
     */
    @Override
    public MemoryConfiguration convert(MemoryConfiguration to) {
        Validator.isNull(to, "The provided implementation is null.");
        MemoryConfiguration newConfiguration = to.newMemoryConfiguration();
        newConfiguration.copy(to);
        return newConfiguration;
    }


    @Override
    public MemoryOptions options() {
        if (options == null) {
            options = new MemoryOptions(this);
        }
        return options;
    }


    protected void convertMapsToSections(Map<?, ?> input, Section section) {
        Validator.isNull(input, "Input map cannot be null.");
        Validator.isNull(section, "Section cannot be null.");
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

    public String getEmptyConfig() {
        return "\n";
    }

    /**
     * Load the data on the current MemoryConfiguration from a file content.
     *
     * @param contents The file contents to load configs from.
     */
    public abstract void loadFromString(String contents);

    /**
     * Save the data of the current MemoryConfiguration as a String.
     *
     * @return the String containing the data MemoryConfiguration.
     */
    public abstract String saveToString();

    public abstract @NotNull FileConfiguration newConfiguration(File file);

    /**
     * Checks if current MemoryConfiguration has a FileConfiguration.
     *
     * @return {@code true} if the current MemoryConfiguration has a FileConfiguration.
     */
    public abstract boolean hasFileConfiguration();

    public abstract @NotNull MemoryConfiguration newMemoryConfiguration();

    public abstract @NotNull Class<? extends FileConfiguration> getConfigurationReference();


}
