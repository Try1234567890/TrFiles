package me.tr.trfiles.configuration.memory;

import me.tr.trfiles.Validator;
import me.tr.trfiles.configuration.Config;
import me.tr.trfiles.configuration.Section;
import me.tr.trfiles.configuration.implementations.FileConfiguration;
import me.tr.trfiles.exceptions.UnknownImplementationException;
import me.tr.trfiles.management.FileManager;
import me.tr.trfiles.management.io.reader.file.FilesReader;
import me.tr.trfiles.management.io.writer.file.FilesWriter;
import me.tr.trfiles.registries.MemoryRegistry;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * This class is the base of all MemoryConfigurations classes.
 * <p>
 * This class is the configuration saved in memory and contains: options and defaults.
 * <p>
 * This class doesn't have constructors, but use static methods to create new instances for practice.
 */
@SuppressWarnings("unchecked")
public abstract class MemoryConfiguration extends MemorySection implements Config {
    private MemoryOptions options;
    private final MemoryEntry entry;

    protected MemoryConfiguration(MemoryEntry entry) {
        Validator.isNull(entry, "Entry cannot be null.");
        this.entry = entry;
    }

    public static <T extends MemoryConfiguration> T empty(Class<T> reference) {
        return (T) getMemory(reference);
    }

    public static <T extends MemoryConfiguration> T fromReader(Reader r, Class<T> reference) {
        return (T) getMemory(reference).fillReader(r);
    }

    public static <T extends MemoryConfiguration> T fromInputStream(InputStream is, Class<T> reference) {
        return (T) getMemory(reference).fillInputStream(is);
    }

    public static <T extends MemoryConfiguration> T fromMap(Map<?, ?> map, Class<T> reference) {
        return (T) getMemory(reference).fillMap(map);
    }

    public static <T extends MemoryConfiguration> T fromFile(File file, Class<T> reference) {
        return (T) getMemory(reference).fillFile(file);
    }

    public static <T extends MemoryConfiguration> T fromPath(Path path, Class<T> reference) {
        return (T) getMemory(reference).fillPath(path);
    }

    public static <T extends MemoryConfiguration> T fromPath(String path, Class<T> reference) {
        return (T) getMemory(reference).fillPath(path);
    }

    public static <T extends MemoryConfiguration> T fromContent(String content, Class<T> reference) {
        return (T) getMemory(reference).fillContent(content);
    }

    public static <T extends MemoryConfiguration> T fromBytes(byte[] bytes, Class<T> reference) {
        return (T) getMemory(reference).fillBytes(bytes);
    }

    public <T extends MemoryConfiguration> T convert(Class<T> to) {
        MemoryConfiguration newMemory = getMemory(to);

        newMemory.fillMap(asMap());

        return (T) newMemory;
    }

    public FileConfiguration toConfiguration(File file) {
        checkFile(file);

        Optional<FileConfiguration> opt = MemoryRegistry.getMemory(this.getClass()).map(entry -> entry.newInstance(file));

        if (opt.isPresent()) {
            FileConfiguration configuration = opt.get();
            configuration.setMemory(this);
            return configuration;
        }

        return null;
    }

    public MemoryOptions getOptions() {
        if (options == null)
            options = new MemoryOptions(this);
        return options;
    }

    /*
     * --==--==--==--==--==--==--==--==--
     *     CONFIGURATION FILL METHODS
     * --==--==--==--==--==--==--==--==--
     */

    public MemoryConfiguration fillContent(String content) {
        if (Validator.isNull(content))
            content = getEntry().getAsEmpty();

        Map<?, ?> configMap = loadFromString(content);

        if (Validator.isNull(configMap))
            return this;

        toConfiguration(configMap, this);

        return this;
    }

    public MemoryConfiguration fillReader(Reader r) {
        Validator.isNull(r, "The provided reader is null");
        try {
            BufferedReader br = r instanceof BufferedReader ? (BufferedReader) r :
                    new BufferedReader(r);

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line)
                        .append(System.lineSeparator());
            }

            return fillContent(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading memory configuration.", e);
        }
    }

    public MemoryConfiguration fillInputStream(InputStream is) {
        Validator.isNull(is, "The provided input stream is null");

        return fillReader(new BufferedReader(new InputStreamReader(is)));
    }

    public MemoryConfiguration fillMap(Map<?, ?> map) {
        Validator.isNull(map, "The provided map is null");

        toConfiguration(map, this);
        return this;
    }

    public MemoryConfiguration fillFile(File file) {
        checkFile(file);
        try {
            fillReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while loading memory from: " + file.getPath(), e);
        }

        return this;
    }

    public MemoryConfiguration fillPath(Path path) {
        Validator.isNull(path, "The provided path is null.");

        return fillFile(path.toFile());
    }

    public MemoryConfiguration fillPath(String path) {
        Validator.isNull(path, "The provided path is null.");

        return fillFile(new File(path));
    }

    public MemoryConfiguration fillBytes(byte[] bytes) {
        Validator.isNull(bytes, "The provided bytes is null.");

        return fillContent(new String(bytes));
    }

    protected Object toConfiguration(Map<?, ?> input, Section section) {
        if (input == null) {
            throw new NullPointerException("The provided input map is null.");
        } else if (section == null) {
            throw new NullPointerException("The provided section is null.");
        } else {
            for (Map.Entry<?, ?> entry : input.entrySet()) {
                String key = entry.getKey().toString();
                Object value = entry.getValue();
                section.set(key, toConfiguration(value, section.createSection(key)));
            }
        }

        return section;
    }

    protected Object toConfiguration(Object value, Section section) {
        if (value instanceof Map<?, ?> subMap) {
            return toConfiguration(subMap, section);
        } else if (value instanceof Collection<?> collection) {
            return toConfiguration((Collection<Object>) collection, section);
        } else if (value.getClass().isArray()) {
            return toConfiguration((Object[]) value, section);
        } else {
            return value;
        }
    }

    protected Object toConfiguration(Collection<Object> collection, Section section) {

        List<Object> result = new ArrayList<>();
        for (Object value : collection) {
            result.add(toConfiguration(value, section));
        }

        return result;
    }

    protected Object toConfiguration(Object[] array, Section section) {
        for (int i = 0; i < array.length; i++) {
            Object value = array[i];
            array[i] = toConfiguration(value, section);
        }

        return array;
    }

    /*
     * --==--==--==--==--==--==--==--==--
     *      CONFIGURATION OPERATIONS
     * --==--==--==--==--==--==--==--==--
     */

    /**
     * Write the current configuration to file.
     *
     * @param file the file to write to.
     */
    @Override
    public void save(File file) {
        FileManager.createAsFile(file);
        FilesWriter.writerString(file, saveAsString());
    }

    /**
     * Reload the configuration from file.
     *
     * @param file the file to reload from.
     */
    @Override
    public void reload(File file) {
        FilesReader.readAsString(file).ifPresent(this::fillContent);
    }

    /**
     * Move a file to another location.
     *
     * @param file the file to move.
     * @param to   the destination file.
     */
    @Override
    public void move(File file, File to) {
        copy(file, to);
        delete(file);
    }

    /**
     * Copy a file to another location.
     *
     * @param file the file to copy.
     * @param to   the destination file.
     */
    @Override
    public void copy(File file, File to) {
        FilesReader.readAsString(file).ifPresent((content) -> FilesWriter.writerString(file, content));
    }

    /**
     * Delete a file.
     *
     * @param file the file to delete.
     */
    @Override
    public void delete(File file) {
        FileManager.delete(file);
    }

    public abstract Map<?, ?> loadFromString(String content);

    public abstract String saveAsString();

    public MemoryEntry getEntry() {
        return entry;
    }

    /*
     * --==--==--==--==--==--==--==--==--
     *   CONFIGURATION UTILITY METHODS
     * --==--==--==--==--==--==--==--==--
     */

    private static void checkFile(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.exists(), "The provided file does not exist.");
        Validator.checkIf(file.isFile(), "The provided file is not a file.");
        Validator.checkIf(file.canRead(), "The provided file is not readable.");
        Validator.checkIf(file.canWrite(), "The provided file is not writable.");
    }

    private static MemoryConfiguration getMemory(Class<? extends MemoryConfiguration> reference) {
        Validator.isNull(reference, "reference is null");

        Optional<MemoryEntry> entry = MemoryRegistry.getMemory(reference);

        if (entry.isEmpty()) {
            throw new UnknownImplementationException("The implementation of " + reference.getName() + " is not in registry.");
        }

        return entry.get().newInstance();
    }
}
