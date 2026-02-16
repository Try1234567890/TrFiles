package me.tr.trfiles.configuration.implementations;

import me.tr.trfiles.exceptions.UnknownImplementationException;
import me.tr.trfiles.Validator;
import me.tr.trfiles.configuration.Config;
import me.tr.trfiles.configuration.Section;
import me.tr.trfiles.configuration.memory.DataType;
import me.tr.trfiles.configuration.memory.MemoryConfiguration;
import me.tr.trfiles.management.FileUtility;
import me.tr.trfiles.registries.ConfigRegistry;
import me.tr.trfiles.size.Size;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class FileConfiguration implements Section {
    private MemoryConfiguration memory;
    private FileOptions options;
    private File file;
    private Size size;
    private final ConfigEntry entry;

    protected FileConfiguration(ConfigEntry entry, File file) {
        Validator.isNull(entry, "Entry cannot be null.");
        this.memory = entry.newInstance();
        this.options = new FileOptions(this);
        this.file = file;
        this.size = Size.file(file);
        this.entry = entry;
    }

    public static <T extends FileConfiguration> T empty(File file, Class<T> reference) {
        file = checkFile(file);

        return (T) getConfig(file, reference);
    }

    public static <T extends FileConfiguration> T fromReader(File file, Reader r, Class<T> reference) {
        file = checkFile(file);

        FileConfiguration configuration = getConfig(file, reference);
        configuration.getMemory().fillReader(r);

        return (T) configuration;
    }

    public static <T extends FileConfiguration> T fromInputStream(File file, InputStream is, Class<T> reference) {
        file = checkFile(file);

        FileConfiguration configuration = getConfig(file, reference);
        configuration.getMemory().fillInputStream(is);

        return (T) configuration;
    }

    public static <T extends FileConfiguration> T fromMap(File file, Map<?, ?> map, Class<T> reference) {
        file = checkFile(file);

        FileConfiguration configuration = getConfig(file, reference);
        configuration.getMemory().fillMap(map);

        return (T) configuration;
    }

    public static <T extends FileConfiguration> T fromContent(File file, String content, Class<T> reference) {
        file = checkFile(file);

        FileConfiguration configuration = getConfig(file, reference);
        configuration.getMemory().fillContent(content);


        return (T) configuration;
    }

    public static <T extends FileConfiguration> T fromBytes(File file, byte[] bytes, Class<T> reference) {
        file = checkFile(file);

        FileConfiguration configuration = getConfig(file, reference);
        configuration.getMemory().fillBytes(bytes);

        return (T) configuration;
    }

    public static <T extends FileConfiguration> T fromFile(File file, Class<T> reference) {
        file = checkFile(file);
        FileConfiguration configuration = getConfig(file, reference);
        configuration.getMemory().fillFile(file);
        return (T) configuration;
    }

    public static <T extends FileConfiguration> T fromPath(Path path, Class<T> reference) {
        return fromFile(path.toFile(), reference);
    }

    public static <T extends FileConfiguration> T fromPath(String path, Class<T> reference) {
        return fromFile(new File(path), reference);
    }

    public static <T extends FileConfiguration> T fromFile(File file) {
        file = checkFile(file);
        FileConfiguration configuration = getConfig(file);
        configuration.getMemory().fillFile(file);

        return (T) configuration;
    }

    public static <T extends FileConfiguration> T fromPath(Path path) {
        return fromFile(path.toFile());
    }

    public static <T extends FileConfiguration> T fromPath(String path) {
        return fromFile(new File(path));
    }

    public <T extends FileConfiguration> T convert(Class<T> to) {
        FileConfiguration newConfiguration = getConfig(getFile(), to);

        File newFile = new File(getFile().getParentFile(), FileUtility.getFileNameWithoutExtension(getFile()) + newConfiguration.getEntry().getExtensions()[0]);
        newConfiguration.setFile(newFile);
        newConfiguration.getMemory().fillMap(asMap());

        return (T) newConfiguration;
    }

    public MemoryConfiguration getMemory() {
        if (memory == null)
            memory = getEntry().newInstance();
        return memory;
    }

    public void setMemory(MemoryConfiguration memory) {
        if (memory == null)
            memory = getEntry().newInstance();
        this.memory = memory;
    }

    public FileOptions getOptions() {
        if (options == null)
            options = new FileOptions(this);
        return options;
    }

    protected void setOptions(FileOptions options) {
        this.options = options;
    }

    public File getFile() {
        return file;
    }

    private void setFile(File file) {
        this.file = file;
    }

    public Size getSize() {
        if (size == null)
            setSize(Size.file(file));
        return size;
    }

    private void setSize(Size size) {
        this.size = size;
    }

    public ConfigEntry getEntry() {
        return entry;
    }

    /**
     * Write the current configuration to file.
     */
    public void save() {
        getMemory().save(getFile());
    }

    /**
     * Reload the configuration from file.
     */
    public void reload() {
        getMemory().reload(getFile());
    }

    /**
     * Move the file to another location.
     *
     * @param to the destination file.
     */
    public void move(File to) {
        getMemory().move(getFile(), to);
    }

    /**
     * Copy the file to another location.
     *
     * @param to the destination file.
     */
    public void copy(File to) {
        getMemory().copy(getFile(), to);
    }

    /**
     * Delete the file.
     */
    public void delete() {
        getMemory().delete(getFile());
    }

    private static File checkFile(File file) {
        Validator.isNull(file, "The provided file is null.");

        if (!file.exists()) {
            Optional<File> maybeFile = tryToFindFile(file.getParentFile(), file.getName());
            if (maybeFile.isPresent()) {
                return maybeFile.get();
            }
        }

        Validator.checkIf(file.exists(), "The provided file does not exist.");
        Validator.checkIf(file.isFile(), "The provided file is not a file.");
        Validator.checkIf(file.canRead(), "The provided file is not readable.");
        Validator.checkIf(file.canWrite(), "The provided file is not writable.");
        return file;
    }

    private static Optional<File> tryToFindFile(File folder, String name) {
        for (ConfigEntry entry : ConfigRegistry.getInstance().values()) {
            String[] extensions = entry.getExtensions();
            for (String extension : extensions) {
                File current = new File(folder, name + extension);
                if (current.exists())
                    return Optional.of(current);
            }
        }
        return Optional.empty();
    }

    private static FileConfiguration getConfig(File file, Class<? extends FileConfiguration> reference) {
        Validator.isNull(reference, "reference is null");

        Optional<ConfigEntry> entry = ConfigRegistry.getConfig(reference);

        if (entry.isEmpty()) {
            throw new UnknownImplementationException("The implementation of " + reference.getName() + " is not found in registry.");
        }

        FileConfiguration configuration = entry.get().newInstance(file);

        configuration.setFile(file);
        configuration.setSize(Size.file(file));

        return configuration;
    }

    private static FileConfiguration getConfig(File file) {
        Optional<ConfigEntry> entry = ConfigRegistry.getConfig(file);

        if (entry.isEmpty()) {
            throw new UnknownImplementationException("The implementation of " + file.getName() + " is not found in registry.");
        }

        FileConfiguration configuration = entry.get().newInstance(file);

        configuration.setFile(file);
        configuration.setSize(Size.file(file));

        return configuration;
    }


    /**
     * Get the keys contained in this section.
     *
     * @param recursive if true include keys from nested sections (full paths), otherwise only immediate keys
     * @return list of keys (may be empty)
     */
    @Override
    public List<String> getKeys(boolean recursive) {
        return getMemory().getKeys(recursive);
    }

    /**
     * Get the values contained in this section.
     *
     * @param recursive if true include values from nested sections (with section maps), otherwise only immediate values
     * @return map of keys to values (may be empty)
     */
    @Override
    public Map<String, Object> getValues(boolean recursive) {
        return getMemory().getValues(recursive);
    }

    /**
     * Check whether this section contains the specified path.
     *
     * @param path path relative to this section
     * @return true if a value exists at the path (including null values set), false otherwise
     */
    @Override
    public boolean contains(String path) {
        return getMemory().contains(path);
    }

    /**
     * Check whether the value at {@code path} is explicitly set.
     *
     * @param path path relative to this section
     * @return true if a value is set (not inherited/virtual), false otherwise
     */
    @Override
    public boolean isSet(String path) {
        return getMemory().isSet(path);
    }

    /**
     * Get the current path of this section relative to the root configuration.
     *
     * @return current path (empty string or root identifier for root section)
     */
    @Override
    public String getCurrentPath() {
        return getMemory().getCurrentPath();
    }

    /**
     * Get the name of this section (last path component).
     *
     * @return section name
     */
    @Override
    public String getName() {
        return getMemory().getName();
    }

    /**
     * Get the root configuration that owns this section.
     *
     * @return root {@link Config}
     */
    @Override
    public Config getRoot() {
        return getMemory().getRoot();
    }

    /**
     * Get the parent section of this section.
     *
     * @return parent {@link Section}, or null if this is the root section
     */
    @Override
    public Section getParent() {
        return getMemory().getParent();
    }

    /**
     * Get the full path of this section from the root.
     *
     * @return full path (may be same as {@link #getCurrentPath()})
     */
    @Override
    public String getFullPath() {
        return getMemory().getFullPath();
    }

    /**
     * Get the raw object at the specified path.
     *
     * @param path path relative to this section
     * @return the value found or null if not present
     */
    @Override
    public Object get(String path) {
        return getMemory().get(path);
    }

    /**
     * Get the raw object at the specified path or return a default.
     *
     * @param path path relative to this section
     * @param def  default value to return if no value is present
     * @return the value found or {@code def} if not present
     */
    @Override
    public Object get(String path, Object def) {
        return getMemory().get(path, def);
    }

    /**
     * Set a value at the specified path. Passing null typically unsets the path.
     *
     * @param path  path relative to this section
     * @param value value to set
     */
    @Override
    public void set(String path, Object value) {
        getMemory().set(path, value);
    }

    /**
     * Create a nested section at the given path.
     *
     * @param path path relative to this section
     * @return the created or existing {@link Section}
     */
    @Override
    public Section createSection(String path) {
        return getMemory().createSection(path);
    }

    /**
     * Create a nested section at the given path and populate it with the given map.
     *
     * @param path path relative to this section
     * @param map  initial values to set in the created section
     * @return the created or existing {@link Section}
     */
    @Override
    public Section createSection(String path, Map<String, Object> map) {
        return getMemory().createSection(path);
    }

    /**
     * Get a string value at the path.
     *
     * @param path path relative to this section
     * @return string value or null if missing or not convertible
     */
    @Override
    public String getString(String path) {
        return getMemory().getString(path);
    }

    /**
     * Get a string value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default string to return if not present
     * @return found string or {@code def}
     */
    @Override
    public String getString(String path, String def) {
        return getMemory().getString(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a string.
     *
     * @param path path relative to this section
     * @return true if present and convertible to string
     */
    @Override
    public boolean isString(String path) {
        return getMemory().isString(path);
    }

    /**
     * Get a char value at the path.
     *
     * @param path path relative to this section
     * @return char value
     */
    @Override
    public char getChar(String path) {
        return getMemory().getChar(path);
    }

    /**
     * Get a char value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default char to return if not present or not convertible
     * @return found char or {@code def}
     */
    @Override
    public char getChar(String path, char def) {
        return getMemory().getChar(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a char.
     *
     * @param path path relative to this section
     * @return true if present and convertible to char
     */
    @Override
    public boolean isChar(String path) {
        return getMemory().isChar(path);
    }

    /**
     * Get an int value at the path.
     *
     * @param path path relative to this section
     * @return int value
     */
    @Override
    public int getInt(String path) {
        return getMemory().getInt(path);
    }

    /**
     * Get an int value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default int to return if not present or not convertible
     * @return found int or {@code def}
     */
    @Override
    public int getInt(String path, int def) {
        return getMemory().getInt(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as an int.
     *
     * @param path path relative to this section
     * @return true if present and convertible to int
     */
    @Override
    public boolean isInt(String path) {
        return getMemory().isInt(path);
    }

    /**
     * Get a numeric value at the path.
     *
     * @param path path relative to this section
     * @return {@link Number} instance or null if not present/convertible
     */
    @Override
    public Number getNumber(String path) {
        return getMemory().getNumber(path);
    }

    /**
     * Get a numeric value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default number to return if not present or not convertible
     * @return found number or {@code def}
     */
    @Override
    public Number getNumber(String path, Number def) {
        return getMemory().getNumber(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a number.
     *
     * @param path path relative to this section
     * @return true if present and convertible to {@link Number}
     */
    @Override
    public boolean isNumber(String path) {
        return getMemory().isNumber(path);
    }

    /**
     * Get a boolean value at the path.
     *
     * @param path path relative to this section
     * @return boolean value
     */
    @Override
    public boolean getBoolean(String path) {
        return getMemory().getBoolean(path);
    }

    /**
     * Get a boolean value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default boolean to return if not present or not convertible
     * @return found boolean or {@code def}
     */
    @Override
    public boolean getBoolean(String path, boolean def) {
        return getMemory().getBoolean(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a boolean.
     *
     * @param path path relative to this section
     * @return true if present and convertible to boolean
     */
    @Override
    public boolean isBoolean(String path) {
        return getMemory().isBoolean(path);
    }

    /**
     * Get a double value at the path.
     *
     * @param path path relative to this section
     * @return double value
     */
    @Override
    public double getDouble(String path) {
        return getMemory().getDouble(path);
    }

    /**
     * Get a double value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default double to return if not present or not convertible
     * @return found double or {@code def}
     */
    @Override
    public double getDouble(String path, double def) {
        return getMemory().getDouble(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a double.
     *
     * @param path path relative to this section
     * @return true if present and convertible to double
     */
    @Override
    public boolean isDouble(String path) {
        return getMemory().isDouble(path);
    }

    /**
     * Get a float value at the path.
     *
     * @param path path relative to this section
     * @return float value
     */
    @Override
    public float getFloat(String path) {
        return getMemory().getFloat(path);
    }

    /**
     * Get a float value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default float to return if not present or not convertible
     * @return found float or {@code def}
     */
    @Override
    public float getFloat(String path, float def) {
        return getMemory().getFloat(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a float.
     *
     * @param path path relative to this section
     * @return true if present and convertible to float
     */
    @Override
    public boolean isFloat(String path) {
        return getMemory().isFloat(path);
    }

    /**
     * Get a long value at the path.
     *
     * @param path path relative to this section
     * @return long value
     */
    @Override
    public long getLong(String path) {
        return getMemory().getLong(path);
    }

    /**
     * Get a long value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default long to return if not present or not convertible
     * @return found long or {@code def}
     */
    @Override
    public long getLong(String path, long def) {
        return getMemory().getLong(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a long.
     *
     * @param path path relative to this section
     * @return true if present and convertible to long
     */
    @Override
    public boolean isLong(String path) {
        return getMemory().isLong(path);
    }

    /**
     * Get a short value at the path.
     *
     * @param path path relative to this section
     * @return short value
     */
    @Override
    public short getShort(String path) {
        return getMemory().getShort(path);
    }

    /**
     * Get a short value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default short to return if not present or not convertible
     * @return found short or {@code def}
     */
    @Override
    public short getShort(String path, short def) {
        return getMemory().getShort(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a short.
     *
     * @param path path relative to this section
     * @return true if present and convertible to short
     */
    @Override
    public boolean isShort(String path) {
        return getMemory().isShort(path);
    }

    /**
     * Get a byte value at the path.
     *
     * @param path path relative to this section
     * @return byte value
     */
    @Override
    public byte getByte(String path) {
        return getMemory().getByte(path);
    }

    /**
     * Get a byte value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default byte to return if not present or not convertible
     * @return found byte or {@code def}
     */
    @Override
    public byte getByte(String path, byte def) {
        return getMemory().getByte(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a byte.
     *
     * @param path path relative to this section
     * @return true if present and convertible to byte
     */
    @Override
    public boolean isByte(String path) {
        return getMemory().isByte(path);
    }

    /**
     * Get a {@link BigInteger} value at the path.
     *
     * @param path path relative to this section
     * @return {@link BigInteger} or null if missing/not convertible
     */
    @Override
    public BigInteger getBigInteger(String path) {
        return getMemory().getBigInteger(path);
    }

    /**
     * Get a {@link BigInteger} value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default to return if not present or not convertible
     * @return found {@link BigInteger} or {@code def}
     */
    @Override
    public BigInteger getBigInteger(String path, BigInteger def) {
        return getMemory().getBigInteger(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a {@link BigInteger}.
     *
     * @param path path relative to this section
     * @return true if present and convertible to {@link BigInteger}
     */
    @Override
    public boolean isBigInteger(String path) {
        return getMemory().isBigInteger(path);
    }

    /**
     * Get a {@link BigDecimal} value at the path.
     *
     * @param path path relative to this section
     * @return {@link BigDecimal} or null if missing/not convertible
     */
    @Override
    public BigDecimal getBigDecimal(String path) {
        return getMemory().getBigDecimal(path);
    }

    /**
     * Get a {@link BigDecimal} value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default to return if not present or not convertible
     * @return found {@link BigDecimal} or {@code def}
     */
    @Override
    public BigDecimal getBigDecimal(String path, BigDecimal def) {
        return getMemory().getBigDecimal(path, def);
    }

    /**
     * Check whether the value at {@code path} can be represented as a {@link BigDecimal}.
     *
     * @param path path relative to this section
     * @return true if present and convertible to {@link BigDecimal}
     */
    @Override
    public boolean isBigDecimal(String path) {
        return getMemory().isBigDecimal(path);
    }

    /**
     * Get a nested section at the specified path.
     *
     * @param path path relative to this section
     * @return {@link Section} or null if missing/not a section
     */
    @Override
    public Section getSection(String path) {
        return getMemory().getSection(path);
    }

    /**
     * Get a nested section at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default section to return if missing
     * @return found {@link Section} or {@code def}
     */
    @Override
    public Section getSection(String path, Section def) {
        return getMemory().getSection(path, def);
    }

    /**
     * Get a nested section at the specified path or create/return a section built from the provided map.
     *
     * @param path path relative to this section
     * @param def  map to use as fallback section values if missing
     * @return found {@link Section} or a section created/populated from {@code def}
     */
    @Override
    public Section getSection(String path, Map<String, Object> def) {
        return getMemory().getSection(path, def);
    }

    /**
     * Check whether the value at {@code path} is a section.
     *
     * @param path path relative to this section
     * @return true if present and is a section
     */
    @Override
    public boolean isSection(String path) {
        return getMemory().isSection(path);
    }

    /**
     * Get the list value of this section (when the section itself represents a list).
     *
     * @return list or empty list if none
     */
    @Override
    public List<?> getList() {
        return getMemory().getList();
    }

    /**
     * Get a list value at the specified path.
     *
     * @param path path relative to this section
     * @return list or null if missing/not a list
     */
    @Override
    public List<?> getList(String path) {
        return getMemory().getList(path);
    }

    /**
     * Get a list value at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<?> getList(String path, List<?> def) {
        return getMemory().getList(path, def);
    }

    /**
     * Check whether the value at {@code path} is a list.
     *
     * @param path path relative to this section
     * @return true if present and is a list
     */
    @Override
    public boolean isList(String path) {
        return getMemory().isList(path);
    }

    /**
     * Get a list of objects from this section (when the section itself represents a list).
     *
     * @return list of objects
     */
    @Override
    public List<Object> getObjectList() {
        return getMemory().getObjectList();
    }

    /**
     * Get a list of objects at the specified path.
     *
     * @param path path relative to this section
     * @return list of objects or null if missing/not a list
     */
    @Override
    public List<Object> getObjectList(String path) {
        return getMemory().getObjectList(path);
    }

    /**
     * Get a list of objects at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Object> getObjectList(String path, List<Object> def) {
        return getMemory().getObjectList(path, def);
    }

    /**
     * Get a list of subsections contained in this section (when this section itself represents a list).
     *
     * @return list of subsections
     */
    @Override
    public List<Section> getSectionList() {
        return getMemory().getSectionList();
    }

    /**
     * Get a list of subsections at the specified path.
     *
     * @param path path relative to this section
     * @return list of subsections or null if missing/not a list of sections
     */
    @Override
    public List<Section> getSectionList(String path) {
        return getMemory().getSectionList(path);
    }

    /**
     * Get a list of subsections at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Section> getSectionList(String path, List<Section> def) {
        return getMemory().getSectionList(path, def);
    }

    /**
     * Get a list of strings from this section (when the section itself represents a list).
     *
     * @return list of strings
     */
    @Override
    public List<String> getStringList() {
        return getMemory().getStringList();
    }

    /**
     * Get a list of strings at the specified path.
     *
     * @param path path relative to this section
     * @return list of strings or null if missing/not a list of strings
     */
    @Override
    public List<String> getStringList(String path) {
        return getMemory().getStringList(path);
    }

    /**
     * Get a list of strings at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<String> getStringList(String path, List<String> def) {
        return getMemory().getStringList(path, def);
    }

    /**
     * Get a list of integers from this section (when the section itself represents a list).
     *
     * @return list of integers
     */
    @Override
    public List<Integer> getIntegerList() {
        return getMemory().getIntegerList();
    }

    /**
     * Get a list of integers at the specified path.
     *
     * @param path path relative to this section
     * @return list of integers or null if missing/not a list of integers
     */
    @Override
    public List<Integer> getIntegerList(String path) {
        return getMemory().getIntegerList(path);
    }

    /**
     * Get a list of integers at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Integer> getIntegerList(String path, List<Integer> def) {
        return getMemory().getIntegerList(path, def);
    }

    /**
     * Get a list of numbers from this section.
     *
     * @return list of numbers
     */
    @Override
    public List<Number> getNumberList() {
        return getMemory().getNumberList();
    }

    /**
     * Get a list of numbers at the specified path.
     *
     * @param path path relative to this section
     * @return list of numbers or null if missing/not a list of numbers
     */
    @Override
    public List<Number> getNumberList(String path) {
        return getMemory().getNumberList(path);
    }

    /**
     * Get a list of numbers at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Number> getNumberList(String path, List<Number> def) {
        return getMemory().getNumberList(path, def);
    }

    /**
     * Get a list of booleans from this section.
     *
     * @return list of booleans
     */
    @Override
    public List<Boolean> getBooleanList() {
        return getMemory().getBooleanList();
    }

    /**
     * Get a list of booleans at the specified path.
     *
     * @param path path relative to this section
     * @return list of booleans or null if missing/not a list of booleans
     */
    @Override
    public List<Boolean> getBooleanList(String path) {
        return getMemory().getBooleanList(path);
    }

    /**
     * Get a list of booleans at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Boolean> getBooleanList(String path, List<Boolean> def) {
        return getMemory().getBooleanList(path, def);
    }

    /**
     * Get a list of doubles from this section.
     *
     * @return list of doubles
     */
    @Override
    public List<Double> getDoubleList() {
        return getMemory().getDoubleList();
    }

    /**
     * Get a list of doubles at the specified path.
     *
     * @param path path relative to this section
     * @return list of doubles or null if missing/not a list of doubles
     */
    @Override
    public List<Double> getDoubleList(String path) {
        return getMemory().getDoubleList(path);
    }

    /**
     * Get a list of doubles at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Double> getDoubleList(String path, List<Double> def) {
        return getMemory().getDoubleList(path, def);
    }

    /**
     * Get a list of floats from this section.
     *
     * @return list of floats
     */
    @Override
    public List<Float> getFloatList() {
        return getMemory().getFloatList();
    }

    /**
     * Get a list of floats at the specified path.
     *
     * @param path path relative to this section
     * @return list of floats or null if missing/not a list of floats
     */
    @Override
    public List<Float> getFloatList(String path) {
        return getMemory().getFloatList(path);
    }

    /**
     * Get a list of floats at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Float> getFloatList(String path, List<Float> def) {
        return getMemory().getFloatList(path, def);
    }

    /**
     * Get a list of longs from this section.
     *
     * @return list of longs
     */
    @Override
    public List<Long> getLongList() {
        return getMemory().getLongList();
    }

    /**
     * Get a list of longs at the specified path.
     *
     * @param path path relative to this section
     * @return list of longs or null if missing/not a list of longs
     */
    @Override
    public List<Long> getLongList(String path) {
        return getMemory().getLongList(path);
    }

    /**
     * Get a list of longs at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Long> getLongList(String path, List<Long> def) {
        return getMemory().getLongList(path, def);
    }

    /**
     * Get a list of bytes from this section.
     *
     * @return list of bytes
     */
    @Override
    public List<Byte> getByteList() {
        return getMemory().getByteList();
    }

    /**
     * Get a list of bytes at the specified path.
     *
     * @param path path relative to this section
     * @return list of bytes or null if missing/not a list of bytes
     */
    @Override
    public List<Byte> getByteList(String path) {
        return getMemory().getByteList(path);
    }

    /**
     * Get a list of bytes at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Byte> getByteList(String path, List<Byte> def) {
        return getMemory().getByteList(path, def);
    }

    /**
     * Get a list of characters from this section.
     *
     * @return list of characters
     */
    @Override
    public List<Character> getCharacterList() {
        return getMemory().getCharacterList();
    }

    /**
     * Get a list of characters at the specified path.
     *
     * @param path path relative to this section
     * @return list of characters or null if missing/not a list of characters
     */
    @Override
    public List<Character> getCharacterList(String path) {
        return getMemory().getCharacterList(path);
    }

    /**
     * Get a list of characters at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Character> getCharacterList(String path, List<Character> def) {
        return getMemory().getCharacterList(path, def);
    }

    /**
     * Get a list of shorts from this section.
     *
     * @return list of shorts
     */
    @Override
    public List<Short> getShortList() {
        return getMemory().getShortList();
    }

    /**
     * Get a list of shorts at the specified path.
     *
     * @param path path relative to this section
     * @return list of shorts or null if missing/not a list of shorts
     */
    @Override
    public List<Short> getShortList(String path) {
        return getMemory().getShortList(path);
    }

    /**
     * Get a list of shorts at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<Short> getShortList(String path, List<Short> def) {
        return getMemory().getShortList(path, def);
    }

    /**
     * Get a list of {@link BigInteger} from this section.
     *
     * @return list of {@link BigInteger}
     */
    @Override
    public List<BigInteger> getBigIntegerList() {
        return getMemory().getBigIntegerList();
    }

    /**
     * Get a list of {@link BigInteger} at the specified path.
     *
     * @param path path relative to this section
     * @return list of {@link BigInteger} or null if missing/not a list of big integers
     */
    @Override
    public List<BigInteger> getBigIntegerList(String path) {
        return getMemory().getBigIntegerList(path);
    }

    /**
     * Get a list of {@link BigInteger} at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<BigInteger> getBigIntegerList(String path, List<BigInteger> def) {
        return getMemory().getBigIntegerList(path, def);
    }

    /**
     * Get a list of {@link BigDecimal} from this section.
     *
     * @return list of {@link BigDecimal}
     */
    @Override
    public List<BigDecimal> getBigDecimalList() {
        return getMemory().getBigDecimalList();
    }

    /**
     * Get a list of {@link BigDecimal} at the specified path.
     *
     * @param path path relative to this section
     * @return list of {@link BigDecimal} or null if missing/not a list of big decimals
     */
    @Override
    public List<BigDecimal> getBigDecimalList(String path) {
        return getMemory().getBigDecimalList(path);
    }

    /**
     * Get a list of {@link BigDecimal} at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    @Override
    public List<BigDecimal> getBigDecimalList(String path, List<BigDecimal> def) {
        return getMemory().getBigDecimalList(path, def);
    }

    /**
     * Get an array of subsections contained in this section.
     *
     * @return array of sections (may be empty)
     */
    @Override
    public Section[] getSectionArray() {
        return getMemory().getSectionArray();
    }

    /**
     * Get an array of subsections at the specified path.
     *
     * @param path path relative to this section
     * @return array of sections or null if missing/not an array of sections
     */
    @Override
    public Section[] getSectionArray(String path) {
        return getMemory().getSectionArray(path);
    }

    /**
     * Get an array of subsections at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Section[] getSectionArray(String path, Section[] def) {
        return getMemory().getSectionArray(path, def);
    }

    /**
     * Get an object array from this section.
     *
     * @return object array
     */
    @Override
    public Object[] getObjectArray() {
        return getMemory().getObjectArray();
    }

    /**
     * Get an object array at the specified path.
     *
     * @param path path relative to this section
     * @return object array or null if missing/not an array
     */
    @Override
    public Object[] getObjectArray(String path) {
        return getMemory().getObjectArray(path);
    }

    /**
     * Get an object array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Object[] getObjectArray(String path, Object[] def) {
        return getMemory().getObjectArray(path, def);
    }

    /**
     * Get a string array from this section.
     *
     * @return string array
     */
    @Override
    public String[] getStringArray() {
        return getMemory().getStringArray();
    }

    /**
     * Get a string array at the specified path.
     *
     * @param path path relative to this section
     * @return string array or null if missing/not an array
     */
    @Override
    public String[] getStringArray(String path) {
        return getMemory().getStringArray(path);
    }

    /**
     * Get a string array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public String[] getStringArray(String path, String[] def) {
        return getMemory().getStringArray(path, def);
    }

    /**
     * Get an integer array from this section.
     *
     * @return integer array
     */
    @Override
    public Integer[] getIntegerArray() {
        return getMemory().getIntegerArray();
    }

    /**
     * Get an integer array at the specified path.
     *
     * @param path path relative to this section
     * @return integer array or null if missing/not an array
     */
    @Override
    public Integer[] getIntegerArray(String path) {
        return getMemory().getIntegerArray(path);
    }

    /**
     * Get an integer array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Integer[] getIntegerArray(String path, Integer[] def) {
        return getMemory().getIntegerArray(path, def);
    }

    /**
     * Get a number array from this section.
     *
     * @return number array
     */
    @Override
    public Number[] getNumberArray() {
        return getMemory().getNumberArray();
    }

    /**
     * Get a number array at the specified path.
     *
     * @param path path relative to this section
     * @return number array or null if missing/not an array
     */
    @Override
    public Number[] getNumberArray(String path) {
        return getMemory().getNumberArray(path);
    }

    /**
     * Get a number array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Number[] getNumberArray(String path, Number[] def) {
        return getMemory().getNumberArray(path, def);
    }

    /**
     * Get a boolean array from this section.
     *
     * @return boolean array
     */
    @Override
    public Boolean[] getBooleanArray() {
        return getMemory().getBooleanArray();
    }

    /**
     * Get a boolean array at the specified path.
     *
     * @param path path relative to this section
     * @return boolean array or null if missing/not an array
     */
    @Override
    public Boolean[] getBooleanArray(String path) {
        return getMemory().getBooleanArray(path);
    }

    /**
     * Get a boolean array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Boolean[] getBooleanArray(String path, Boolean[] def) {
        return getMemory().getBooleanArray(path, def);
    }

    /**
     * Get a double array from this section.
     *
     * @return double array
     */
    @Override
    public Double[] getDoubleArray() {
        return getMemory().getDoubleArray();
    }

    /**
     * Get a double array at the specified path.
     *
     * @param path path relative to this section
     * @return double array or null if missing/not an array
     */
    @Override
    public Double[] getDoubleArray(String path) {
        return getMemory().getDoubleArray(path);
    }

    /**
     * Get a double array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Double[] getDoubleArray(String path, Double[] def) {
        return getMemory().getDoubleArray(path, def);
    }

    /**
     * Get a float array from this section.
     *
     * @return float array
     */
    @Override
    public Float[] getFloatArray() {
        return getMemory().getFloatArray();
    }

    /**
     * Get a float array at the specified path.
     *
     * @param path path relative to this section
     * @return float array or null if missing/not an array
     */
    @Override
    public Float[] getFloatArray(String path) {
        return getMemory().getFloatArray(path);
    }

    /**
     * Get a float array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Float[] getFloatArray(String path, Float[] def) {
        return getMemory().getFloatArray(path, def);
    }

    /**
     * Get a long array from this section.
     *
     * @return long array
     */
    @Override
    public Long[] getLongArray() {
        return getMemory().getLongArray();
    }

    /**
     * Get a long array at the specified path.
     *
     * @param path path relative to this section
     * @return long array or null if missing/not an array
     */
    @Override
    public Long[] getLongArray(String path) {
        return getMemory().getLongArray(path);
    }

    /**
     * Get a long array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Long[] getLongArray(String path, Long[] def) {
        return getMemory().getLongArray(path, def);
    }

    /**
     * Get a byte array from this section.
     *
     * @return byte array
     */
    @Override
    public Byte[] getByteArray() {
        return getMemory().getByteArray();
    }

    /**
     * Get a byte array at the specified path.
     *
     * @param path path relative to this section
     * @return byte array or null if missing/not an array
     */
    @Override
    public Byte[] getByteArray(String path) {
        return getMemory().getByteArray(path);
    }

    /**
     * Get a byte array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Byte[] getByteArray(String path, Byte[] def) {
        return getMemory().getByteArray(path, def);
    }

    /**
     * Get a character array from this section.
     *
     * @return character array
     */
    @Override
    public Character[] getCharacterArray() {
        return getMemory().getCharacterArray();
    }

    /**
     * Get a character array at the specified path.
     *
     * @param path path relative to this section
     * @return character array or null if missing/not an array
     */
    @Override
    public Character[] getCharacterArray(String path) {
        return getMemory().getCharacterArray(path);
    }

    /**
     * Get a character array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Character[] getCharacterArray(String path, Character[] def) {
        return getMemory().getCharacterArray(path, def);
    }

    /**
     * Get a short array from this section.
     *
     * @return short array
     */
    @Override
    public Short[] getShortArray() {
        return getMemory().getShortArray();
    }

    /**
     * Get a short array at the specified path.
     *
     * @param path path relative to this section
     * @return short array or null if missing/not an array
     */
    @Override
    public Short[] getShortArray(String path) {
        return getMemory().getShortArray(path);
    }

    /**
     * Get a short array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public Short[] getShortArray(String path, Short[] def) {
        return getMemory().getShortArray(path, def);
    }

    /**
     * Get a {@link BigInteger} array from this section.
     *
     * @return array of {@link BigInteger}
     */
    @Override
    public BigInteger[] getBigIntegerArray() {
        return getMemory().getBigIntegerArray();
    }

    /**
     * Get a {@link BigInteger} array at the specified path.
     *
     * @param path path relative to this section
     * @return array of {@link BigInteger} or null if missing/not an array
     */
    @Override
    public BigInteger[] getBigIntegerArray(String path) {
        return getMemory().getBigIntegerArray(path);
    }

    /**
     * Get a {@link BigInteger} array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public BigInteger[] getBigIntegerArray(String path, BigInteger[] def) {
        return getMemory().getBigIntegerArray(path, def);
    }

    /**
     * Get a {@link BigDecimal} array from this section.
     *
     * @return array of {@link BigDecimal}
     */
    @Override
    public BigDecimal[] getBigDecimalArray() {
        return getMemory().getBigDecimalArray();
    }

    /**
     * Get a {@link BigDecimal} array at the specified path.
     *
     * @param path path relative to this section
     * @return array of {@link BigDecimal} or null if missing/not an array
     */
    @Override
    public BigDecimal[] getBigDecimalArray(String path) {
        return getMemory().getBigDecimalArray(path);
    }

    /**
     * Get a {@link BigDecimal} array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public BigDecimal[] getBigDecimalArray(String path, BigDecimal[] def) {
        return getMemory().getBigDecimalArray(path, def);
    }

    /**
     * Get a primitive int array representing integers from this section.
     *
     * @return primitive int array
     */
    @Override
    public int[] getPrimitiveIntegerArray() {
        return getMemory().getPrimitiveIntegerArray();
    }

    /**
     * Get a primitive int array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive int array or null if missing/not an array
     */
    @Override
    public int[] getPrimitiveIntegerArray(String path) {
        return getMemory().getPrimitiveIntegerArray(path);
    }

    /**
     * Get a primitive int array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public int[] getPrimitiveIntegerArray(String path, int[] def) {
        return getMemory().getPrimitiveIntegerArray(path, def);
    }

    /**
     * Get a primitive boolean array from this section.
     *
     * @return primitive boolean array
     */
    @Override
    public boolean[] getPrimitiveBooleanArray() {
        return getMemory().getPrimitiveBooleanArray();
    }

    /**
     * Get a primitive boolean array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive boolean array or null if missing/not an array
     */
    @Override
    public boolean[] getPrimitiveBooleanArray(String path) {
        return getMemory().getPrimitiveBooleanArray(path);
    }

    /**
     * Get a primitive boolean array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public boolean[] getPrimitiveBooleanArray(String path, boolean[] def) {
        return getMemory().getPrimitiveBooleanArray(path, def);
    }

    /**
     * Get a primitive double array from this section.
     *
     * @return primitive double array
     */
    @Override
    public double[] getPrimitiveDoubleArray() {
        return getMemory().getPrimitiveDoubleArray();
    }

    /**
     * Get a primitive double array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive double array or null if missing/not an array
     */
    @Override
    public double[] getPrimitiveDoubleArray(String path) {
        return getMemory().getPrimitiveDoubleArray(path);
    }

    /**
     * Get a primitive double array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public double[] getPrimitiveDoubleArray(String path, double[] def) {
        return getMemory().getPrimitiveDoubleArray(path, def);
    }

    /**
     * Get a primitive float array from this section.
     *
     * @return primitive float array
     */
    @Override
    public float[] getPrimitiveFloatArray() {
        return getMemory().getPrimitiveFloatArray();
    }

    /**
     * Get a primitive float array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive float array or null if missing/not an array
     */
    @Override
    public float[] getPrimitiveFloatArray(String path) {
        return getMemory().getPrimitiveFloatArray(path);
    }

    /**
     * Get a primitive float array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public float[] getPrimitiveFloatArray(String path, float[] def) {
        return getMemory().getPrimitiveFloatArray(path, def);
    }

    /**
     * Get a primitive long array from this section.
     *
     * @return primitive long array
     */
    @Override
    public long[] getPrimitiveLongArray() {
        return getMemory().getPrimitiveLongArray();
    }

    /**
     * Get a primitive long array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive long array or null if missing/not an array
     */
    @Override
    public long[] getPrimitiveLongArray(String path) {
        return getMemory().getPrimitiveLongArray(path);
    }

    /**
     * Get a primitive long array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public long[] getPrimitiveLongArray(String path, long[] def) {
        return getMemory().getPrimitiveLongArray(path, def);
    }

    /**
     * Get a primitive byte array from this section.
     *
     * @return primitive byte array
     */
    @Override
    public byte[] getPrimitiveByteArray() {
        return getMemory().getPrimitiveByteArray();
    }

    /**
     * Get a primitive byte array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive byte array or null if missing/not an array
     */
    @Override
    public byte[] getPrimitiveByteArray(String path) {
        return getMemory().getPrimitiveByteArray(path);
    }

    /**
     * Get a primitive byte array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public byte[] getPrimitiveByteArray(String path, byte[] def) {
        return getMemory().getPrimitiveByteArray(path, def);
    }

    /**
     * Get a primitive char array from this section.
     *
     * @return primitive char array
     */
    @Override
    public char[] getPrimitiveCharacterArray() {
        return getMemory().getPrimitiveCharacterArray();
    }

    /**
     * Get a primitive char array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive char array or null if missing/not an array
     */
    @Override
    public char[] getPrimitiveCharacterArray(String path) {
        return getMemory().getPrimitiveCharacterArray(path);
    }

    /**
     * Get a primitive char array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public char[] getPrimitiveCharacterArray(String path, char[] def) {
        return getMemory().getPrimitiveCharacterArray(path, def);
    }

    /**
     * Get a primitive short array from this section.
     *
     * @return primitive short array
     */
    @Override
    public short[] getPrimitiveShortArray() {
        return getMemory().getPrimitiveShortArray();
    }

    /**
     * Get a primitive short array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive short array or null if missing/not an array
     */
    @Override
    public short[] getPrimitiveShortArray(String path) {
        return getMemory().getPrimitiveShortArray(path);
    }

    /**
     * Get a primitive short array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    @Override
    public short[] getPrimitiveShortArray(String path, short[] def) {
        return getMemory().getPrimitiveShortArray(path, def);
    }

    /**
     * Checks if the section at the provided path
     * is an array of section.
     *
     * @param path
     * @return {@code true} if is a section array otherwise {@code false}.
     */
    @Override
    public boolean isSectionArray(String path) {
        return getMemory().isSectionArray(path);
    }

    /**
     * Checks if the object at the provided path
     * is an array of object.
     *
     * @param path
     * @return {@code true} if is an object array otherwise {@code false}.
     */
    @Override
    public boolean isObjectArray(String path) {
        return getMemory().isObjectArray(path);
    }

    /**
     * Checks if the string at the provided path
     * is an array of string.
     *
     * @param path
     * @return {@code true} if is a string array otherwise {@code false}.
     */
    @Override
    public boolean isStringArray(String path) {
        return getMemory().isStringArray(path);
    }

    /**
     * Checks if the integer at the provided path
     * is an array of integer.
     *
     * @param path
     * @return {@code true} if is a integer array otherwise {@code false}.
     */
    @Override
    public boolean isIntegerArray(String path) {
        return getMemory().isIntegerArray(path);
    }

    /**
     * Checks if the number at the provided path
     * is an array of number.
     *
     * @param path
     * @return {@code true} if is a number array otherwise {@code false}.
     */
    @Override
    public boolean isNumberArray(String path) {
        return getMemory().isNumberArray(path);
    }

    /**
     * Checks if the boolean at the provided path
     * is an array of boolean.
     *
     * @param path
     * @return {@code true} if is a boolean array otherwise {@code false}.
     */
    @Override
    public boolean isBooleanArray(String path) {
        return getMemory().isBooleanArray(path);
    }

    /**
     * Checks if the double at the provided path
     * is an array of double.
     *
     * @param path
     * @return {@code true} if is a double array otherwise {@code false}.
     */
    @Override
    public boolean isDoubleArray(String path) {
        return getMemory().isDoubleArray(path);
    }

    /**
     * Checks if the float at the provided path
     * is an array of float.
     *
     * @param path
     * @return {@code true} if is a float array otherwise {@code false}.
     */
    @Override
    public boolean isFloatArray(String path) {
        return getMemory().isFloatArray(path);
    }

    /**
     * Checks if the long at the provided path
     * is an array of long.
     *
     * @param path
     * @return {@code true} if is a long array otherwise {@code false}.
     */
    @Override
    public boolean isLongArray(String path) {
        return getMemory().isLongArray(path);
    }

    /**
     * Checks if the byte at the provided path
     * is an array of byte.
     *
     * @param path
     * @return {@code true} if is a byte array otherwise {@code false}.
     */
    @Override
    public boolean isByteArray(String path) {
        return getMemory().isByteArray(path);
    }

    /**
     * Checks if the character at the provided path
     * is an array of character.
     *
     * @param path
     * @return {@code true} if is a character array otherwise {@code false}.
     */
    @Override
    public boolean isCharacterArray(String path) {
        return getMemory().isCharacterArray(path);
    }

    /**
     * Checks if the short at the provided path
     * is an array of short.
     *
     * @param path
     * @return {@code true} if is a short array otherwise {@code false}.
     */
    @Override
    public boolean isShortArray(String path) {
        return getMemory().isShortArray(path);
    }

    /**
     * Checks if the big integer at the provided path
     * is an array of big integer.
     *
     * @param path
     * @return {@code true} if is a big integer array otherwise {@code false}.
     */
    @Override
    public boolean isBigIntegerArray(String path) {
        return getMemory().isBigIntegerArray(path);
    }

    /**
     * Checks if the BigDecimal at the provided path
     * is an array of BigDecimal.
     *
     * @param path
     * @return {@code true} if is a BigDecimal array otherwise {@code false}.
     */
    @Override
    public boolean isBigDecimalArray(String path) {
        return getMemory().isBigDecimalArray(path);
    }

    /**
     * @param path
     * @return
     */
    @Override
    public boolean isPrimitiveIntegerArray(String path) {
        return getMemory().isPrimitiveIntegerArray(path);
    }

    /**
     * @param path
     * @return
     */
    @Override
    public boolean isPrimitiveBooleanArray(String path) {
        return getMemory().isPrimitiveBooleanArray(path);
    }

    /**
     * @param path
     * @return
     */
    @Override
    public boolean isPrimitiveDoubleArray(String path) {
        return getMemory().isPrimitiveDoubleArray(path);
    }

    /**
     * @param path
     * @return
     */
    @Override
    public boolean isPrimitiveFloatArray(String path) {
        return getMemory().isPrimitiveFloatArray(path);
    }

    /**
     * @param path
     * @return
     */
    @Override
    public boolean isPrimitiveLongArray(String path) {
        return getMemory().isPrimitiveLongArray(path);
    }

    /**
     * @param path
     * @return
     */
    @Override
    public boolean isPrimitiveByteArray(String path) {
        return getMemory().isPrimitiveByteArray(path);
    }

    /**
     * @param path
     * @return
     */
    @Override
    public boolean isPrimitiveCharacterArray(String path) {
        return getMemory().isPrimitiveCharacterArray(path);
    }

    /**
     * @param path
     * @return
     */
    @Override
    public boolean isPrimitiveShortArray(String path) {
        return getMemory().isPrimitiveShortArray(path);
    }

    /**
     * Get this section as a map of key to raw value.
     *
     * @return map representation of this section
     */
    @Override
    public Map<String, Object> asMap() {
        return getMemory().asMap();
    }

    /**
     * Get the data type of the value at the specified path.
     *
     * @param path path relative to this section
     * @return {@link DataType} describing the stored value or null if not present
     */
    @Override
    public DataType getType(String path) {
        return getMemory().getType(path);
    }

    @Override
    public String toString() {
        return getMemory().toString();
    }
}
