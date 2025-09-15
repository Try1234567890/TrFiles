package me.tr.trFiles.configuration.implementations;

import me.tr.trFiles.ReflectionUtility;
import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.ConfigRegistry;
import me.tr.trFiles.configuration.Configuration;
import me.tr.trFiles.configuration.Section;
import me.tr.trFiles.configuration.management.FileManager;
import me.tr.trFiles.configuration.management.FileUtility;
import me.tr.trFiles.configuration.memory.DataType;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class FileConfiguration implements Section {
    private MemoryConfiguration configuration;
    private File file;
    private FileOptions options;

    protected FileConfiguration() {
    }

    public static FileConfiguration emptyConfig(File file) {
        FileConfiguration foundConfig = getConfigurationOf(file);
        if (foundConfig == null) {
            throw new UnknownImplementationException(file);
        }
        return foundConfig;
    }

    public static FileConfiguration fromReader(File file, Reader reader) {
        FileConfiguration config = getConfigurationOf(file);
        if (config == null) return null;
        return config.from(file, reader);
    }


    public static FileConfiguration fromInputStream(File file, InputStream is) {
        FileConfiguration config = getConfigurationOf(file);
        if (config == null) return null;
        return config.from(file, is);
    }

    public static FileConfiguration fromFile(File file) {
        FileConfiguration config = getConfigurationOf(file);
        if (config == null) return null;
        return config.from(file);
    }

    public static FileConfiguration fromPath(Path path) {
        FileConfiguration config = getConfigurationOf(path);
        if (config == null) return null;
        return config.from(path);
    }

    public static FileConfiguration fromString(String path) {
        FileConfiguration config = getConfigurationOf(path);
        if (config == null) return null;
        return config.from(path);
    }

    public static FileConfiguration fromContent(File file, String content) {
        FileConfiguration config = getConfigurationOf(file);
        if (config == null) return null;
        return config.fromC(file, content);
    }

    public static FileConfiguration fromArchive(ZipFile archive, File inside, File to) {
        FileConfiguration config = getConfigurationOf(to);
        if (config == null) return null;
        return config.from(archive, inside, to);
    }

    public static FileConfiguration fromBytes(File file, byte[] bytes) {
        FileConfiguration config = getConfigurationOf(file);
        if (config == null) return null;
        return config.from(file, bytes);
    }

    private static @Nullable FileConfiguration getConfigurationOf(File file) {
        FileConfiguration config = ConfigRegistry.getConfigByFile(file);
        if (config == null) {
            throw new UnknownImplementationException(file);
        }
        return config.newConfiguration(file);
    }


    private static @Nullable FileConfiguration getConfigurationOf(Path path) {
        return getConfigurationOf(path.toFile());
    }

    private static @Nullable FileConfiguration getConfigurationOf(String path) {
        return getConfigurationOf(FileUtility.getFileFromString(path));
    }


    public static FileConfiguration fromMemory(File file, MemoryConfiguration memory) {
        return memory.toFileConfiguration(file);
    }

    public static FileConfiguration emptyConfig(File file, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(file, "The provided reference instance of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided clazz of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).empty(file);
    }

    public static FileConfiguration fromReader(File file, Reader reader, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(reader, "The provided reader of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).from(file, reader);
    }


    public static FileConfiguration fromInputStream(File file, InputStream is, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(file, "The provided map of MemoryConfiguration is null.");
        Validator.isNull(is, "The provided input stream of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).from(file, is);
    }

    public static FileConfiguration fromMap(File file, Map<String, Object> map, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(file, "The provided file of MemoryConfiguration is null.");
        Validator.isNull(map, "The provided map of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).from(file, map);
    }

    public static FileConfiguration fromFile(File file, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(file, "The provided file of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).from(file);
    }

    public static FileConfiguration fromPath(Path path, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(path, "The provided path of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).from(path);
    }

    public static FileConfiguration fromString(String path, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(path, "The provided path of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).from(path);
    }

    public static FileConfiguration fromContent(File file, String content, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(file, "The provided file of MemoryConfiguration is null.");
        Validator.isNull(content, "The provided content of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).fromC(file, content);
    }

    public static FileConfiguration fromArchive(ZipFile archive, File inside, File to, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(archive, "The provided archive of MemoryConfiguration is null.");
        Validator.isNull(inside, "The provided inside file of MemoryConfiguration is null.");
        Validator.isNull(to, "The provided destination file of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).from(archive, inside, to);
    }

    public static FileConfiguration fromBytes(File file, byte[] bytes, Class<? extends FileConfiguration> clazz) {
        Validator.isNull(file, "The provided file of MemoryConfiguration is null.");
        Validator.isNull(bytes, "The provided bytes of MemoryConfiguration is null.");
        Validator.isNull(clazz, "The provided reference class of MemoryConfiguration is null.");
        return ReflectionUtility.createInstance(clazz).from(file, bytes);
    }

    public FileConfiguration empty(File file) {
        Validator.isNull(isValid(file), "The provided file of MemoryConfiguration is null.");
        this.file = file;
        this.configuration = getConfiguration();
        return this;
    }

    public FileConfiguration from(FileConfiguration configuration) {
        Validator.isNull(configuration, "The provided configuration is null.");
        this.configuration = configuration.getConfiguration();
        this.file = configuration.getFile();
        return this;
    }

    public FileConfiguration from(File file, MemoryConfiguration configuration) {
        Validator.checkIf(isValid(file), "The provided file " + file.getPath() + " not exists or has not the expected implementation or is not supported.");
        this.configuration = configuration;
        this.file = file;
        return this;
    }

    public FileConfiguration from(File file, Map<String, Object> map) {
        Validator.checkIf(isValid(file), "The provided file " + file.getPath() + " not exists or has not the expected implementation or is not supported.");
        getConfiguration().from(map);
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file, Reader reader) {
        Validator.checkIf(isValid(file), "The provided file " + file.getPath() + " not exists or has not the expected implementation or is not supported.");
        getConfiguration().from(reader);
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file, InputStream is) {
        Validator.checkIf(isValid(file), "The provided file " + file.getPath() + " not exists or has not the expected implementation or is not supported.");
        getConfiguration().from(is);
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(isValid(file), "The provided file " + file.getPath() + " not exists or has not the expected implementation or is not supported.");
        Validator.checkIf(file.canRead(), "Cannot read the provided file at " + file.getPath());
        Validator.checkIf(file.canWrite(), "Cannot write the provided file at " + file.getPath());

        try (InputStream is = new FileInputStream(file)) {
            return from(file, is);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided file: " + file.getPath(), e);
        }
    }

    public FileConfiguration from(Path path) {
        Validator.isNull(path, "The provided path is null.");
        return from(path.toFile());
    }

    public FileConfiguration from(String path) {
        Validator.isNull(path, "The path provided is null.");
        return from(FileUtility.getFileFromString(path));
    }

    public FileConfiguration from(ZipFile archive, File inside, File to) {
        Validator.isNull(archive, "The archive file cannot be null.");
        Validator.isNull(inside, "The inside archive file cannot be null.");
        Validator.isNull(to, "The saving file cannot be null.");

        Validator.checkIf(isValid(to), "The provided file " + to.getPath() + " not exists or has not the expected implementation or is not supported.");
        Validator.checkIf(to.canRead(), "Cannot read the saving file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write the saving file at " + to.getPath());

        Validator.checkIf(FileUtility.getExtension(inside).equalsIgnoreCase(FileUtility.getExtension(to)), "Inside archive file and saving file implementations not corresponds.");
        try (archive) {
            ZipEntry entry = archive.getEntry(FileUtility.getStringPathFromFile(inside));
            if (entry == null) {
                throw new RuntimeException("The entry " + FileUtility.getStringPathFromFile(inside) + " does not exist inside the provided archive.");
            }
            return from(to, archive.getInputStream(entry));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided archive", e);
        }
    }

    public FileConfiguration from(File archiveFile, File inside, File to) {
        Validator.isNull(archiveFile, "The archive cannot be null.");
        Validator.isNull(inside, "The inside archive file cannot be null.");
        Validator.isNull(to, "The saving file cannot be null.");
        Validator.checkIf(isValid(to), "The provided file " + to.getPath() + " not exists or has not the expected implementation or is not supported.");

        Validator.checkIf(archiveFile.canRead(), "Cannot read the archive file.");
        Validator.checkIf(FileUtility.isZip(archiveFile), "The provided archive file at" + archiveFile.getPath() + " not exists or is not a archive.");

        try (ZipFile archive = new ZipFile(archiveFile)) {
            return from(archive, inside, to);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided archive", e);
        }
    }

    public FileConfiguration fromC(File file, String content) {
        Validator.isNull(file, "The provided file cannot be null.");
        Validator.checkIf(isValid(file), "The provided file " + file.getPath() + " not exists or has not the expected implementation or is not supported.");
        Validator.checkIf(file.canRead(), "Cannot read the archive file.");
        Validator.isNull(content, "The provided content cannot be null.");

        setFile(file);
        getConfiguration().loadFromString(content);

        return this;
    }

    public FileConfiguration from(File file, byte[] bytes) {
        Validator.isNull(file, "The provided file cannot be null.");
        Validator.checkIf(isValid(file), "The provided file " + file.getPath() + " not exists or has not the expected implementation or is not supported.");
        Validator.checkIf(file.canRead(), "Cannot read the archive file.");
        Validator.isNull(bytes, "The provided bytes cannot be null.");
        return from(file, new ByteArrayInputStream(bytes));
    }

    public void save() {
        getConfiguration().save(getFile());
    }

    public void reload() {
        getConfiguration().reload(getFile());
    }

    public void move(File to) {
        getConfiguration().move(getFile(), to);
    }

    public void copy(File to) {
        getConfiguration().copy(getFile(), to);
    }

    public void delete() {
        getConfiguration().delete(getFile());
    }

    public void zip(File zip) {
        getConfiguration().zip(getFile(), zip);
    }

    public FileConfiguration convert(FileConfiguration to) {
        Validator.isNull(to, "Target configuration cannot be null.");
        File newFile = new File(getFile().getParentFile(), FileUtility.getFileNameWithoutExtension(getFile()) + to.getExtensions()[0]);
        FileConfiguration newConfiguration = to.newConfiguration(newFile);
        FileManager.create(newFile);
        MemoryConfiguration newMemory = to.newMemoryConfiguration().copy(getConfiguration());
        newConfiguration.from(newFile, newMemory);
        return newConfiguration;
    }


    public FileConfiguration convert(Class<? extends FileConfiguration> to) {
        return convert(ReflectionUtility.createInstance(to));
    }


    public String buildHeader() {
        return buildComments(options().getHeader());
    }

    public String buildFooter() {
        return buildComments(options().getFooter());
    }

    public String buildComments(String comments) {
        String[] lines = comments.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines)
            sb.append(options().getCommentPrefix()).append(line).append(options().getCommentSuffix()).append("\n");
        return sb.toString();
    }

    public FileOptions options() {
        if (options == null) {
            options = new FileOptions(this);
        }
        return options;
    }

    public FileConfiguration options(FileOptions options) {
        this.options = options;
        return this;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public MemoryConfiguration getConfiguration() {
        if (configuration == null) {
            configuration = newMemoryConfiguration();
        }
        return configuration;
    }

    protected void setConfiguration(MemoryConfiguration configuration) {
        this.configuration = configuration;
    }

    private boolean isValid(File file) {
        Validator.isNull(file, "The provided file cannot be null.");
        if (!Validator.checkIf(file.isFile(), (String) null)) return false;
        for (String extension : getExtensions()) {
            if (FileUtility.getExtension(file).equalsIgnoreCase(extension)
                    || FileUtility.getExtensionWithPoint(file).equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    public abstract FileConfiguration newConfiguration(File file);

    public abstract MemoryConfiguration newMemoryConfiguration();

    /**
     * Retrieve the valid extensions that the
     * file of the current FileConfiguration
     * can have.
     *
     * @return A String array contains all valid extensions.
     */
    public abstract String[] getExtensions();

    /**
     * Registers a new {@link FileConfiguration} with the {@link ConfigRegistry}.
     * <p>
     * A {@link FileConfiguration} must be registered before it can be used.
     * Attempting to access an unregistered configuration will result in an error.
     */
    public abstract void register();

    public Class<? extends MemoryConfiguration> getMemoryReference() {
        return getConfiguration().getClass();
    }

    @Override
    public String toString() {
        return "File: " + file + ": " + configuration;
    }

    @Override
    public List<String> getKeys(boolean recursive) {
        return getConfiguration().getKeys(recursive);
    }

    @Override
    public Map<String, Object> getValues(boolean recursive) {
        return getConfiguration().getValues(recursive);
    }

    @Override
    public boolean contains(String path) {
        return getConfiguration().contains(path);
    }

    @Override
    public boolean isSet(String path) {
        return getConfiguration().isSet(path);
    }

    @Override
    public String getCurrentPath() {
        return getConfiguration().getCurrentPath();
    }

    @Override
    public String getName() {
        return getConfiguration().getName();
    }

    @Override
    public Configuration getRoot() {
        return getConfiguration().getRoot();
    }

    @Override
    public Section getParent() {
        return getConfiguration().getParent();
    }

    @Override
    public String getFullPath() {
        return getConfiguration().getFullPath();
    }

    @Override
    public Object get(String path) {
        return getConfiguration().get(path);
    }

    @Override
    public Object get(String path, Object def) {
        return getConfiguration().get(path, def);
    }

    @Override
    public void set(String path, Object value) {
        getConfiguration().set(path, value);
    }

    @Override
    public Section createSection(String path) {
        return getConfiguration().createSection(path);
    }

    @Override
    public Section createSection(String path, Map<String, Object> map) {
        return getConfiguration().createSection(path, map);
    }

    @Override
    public String getString(String path) {
        return getConfiguration().getString(path);
    }

    @Override
    public String getString(String path, String def) {
        return getConfiguration().getString(path, def);
    }

    @Override
    public boolean isString(String path) {
        return getConfiguration().isString(path);
    }

    @Override
    public char getChar(String path) {
        return getConfiguration().getChar(path);
    }

    @Override
    public char getChar(String path, char def) {
        return getConfiguration().getChar(path, def);
    }

    @Override
    public boolean isChar(String path) {
        return getConfiguration().isChar(path);
    }

    @Override
    public int getInt(String path) {
        return getConfiguration().getInt(path);
    }

    @Override
    public int getInt(String path, int def) {
        return getConfiguration().getInt(path, def);
    }

    @Override
    public boolean isInt(String path) {
        return getConfiguration().isInt(path);
    }

    @Override
    public Number getNumber(String path) {
        return getConfiguration().getNumber(path);
    }

    @Override
    public Number getNumber(String path, Number def) {
        return getConfiguration().getNumber(path, def);
    }

    @Override
    public boolean isNumber(String path) {
        return getConfiguration().isNumber(path);
    }

    @Override
    public boolean getBoolean(String path) {
        return getConfiguration().getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return getConfiguration().getBoolean(path, def);
    }

    @Override
    public boolean isBoolean(String path) {
        return getConfiguration().isBoolean(path);
    }

    @Override
    public double getDouble(String path) {
        return getConfiguration().getDouble(path);
    }

    @Override
    public double getDouble(String path, double def) {
        return getConfiguration().getDouble(path, def);
    }

    @Override
    public boolean isDouble(String path) {
        return getConfiguration().isDouble(path);
    }

    @Override
    public float getFloat(String path) {
        return getConfiguration().getFloat(path);
    }

    @Override
    public float getFloat(String path, float def) {
        return getConfiguration().getFloat(path, def);
    }

    @Override
    public boolean isFloat(String path) {
        return getConfiguration().isFloat(path);
    }

    @Override
    public long getLong(String path) {
        return getConfiguration().getLong(path);
    }

    @Override
    public long getLong(String path, long def) {
        return getConfiguration().getLong(path, def);
    }

    @Override
    public boolean isLong(String path) {
        return getConfiguration().isLong(path);
    }

    @Override
    public short getShort(String path) {
        return getConfiguration().getShort(path);
    }

    @Override
    public short getShort(String path, short def) {
        return getConfiguration().getShort(path, def);
    }

    @Override
    public boolean isShort(String path) {
        return getConfiguration().isShort(path);
    }

    @Override
    public BigInteger getBigInteger(String path) {
        return getConfiguration().getBigInteger(path);
    }

    @Override
    public BigInteger getBigInteger(String path, BigInteger def) {
        return getConfiguration().getBigInteger(path, def);
    }

    @Override
    public boolean isBigInteger(String path) {
        return getConfiguration().isBigInteger(path);
    }

    @Override
    public BigDecimal getBigDecimal(String path) {
        return getConfiguration().getBigDecimal(path);
    }

    @Override
    public BigDecimal getBigDecimal(String path, BigDecimal def) {
        return getConfiguration().getBigDecimal(path, def);
    }

    @Override
    public boolean isBigDecimal(String path) {
        return getConfiguration().isBigDecimal(path);
    }

    @Override
    public List<?> getList(String path) {
        return getConfiguration().getList(path);
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        return getConfiguration().getList(path, def);
    }

    @Override
    public List<Object> getObjectList(String path) {
        return getConfiguration().getObjectList(path);
    }

    @Override
    public boolean isList(String path) {
        return getConfiguration().isList(path);
    }

    @Override
    public List<Section> getSectionList() {
        return getConfiguration().getSectionList();
    }

    @Override
    public List<Section> getSectionList(String path) {
        return getConfiguration().getSectionList(path);
    }

    @Override
    public List<String> getStringList(String path) {
        return getConfiguration().getStringList(path);
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        return getConfiguration().getIntegerList(path);
    }

    @Override
    public List<Number> getNumberList(String path) {
        return getConfiguration().getNumberList(path);
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        return getConfiguration().getBooleanList(path);
    }

    @Override
    public List<Double> getDoubleList(String path) {
        return getConfiguration().getDoubleList(path);
    }

    @Override
    public List<Float> getFloatList(String path) {
        return getConfiguration().getFloatList(path);
    }

    @Override
    public List<Long> getLongList(String path) {
        return getConfiguration().getLongList(path);
    }

    @Override
    public List<Byte> getByteList(String path) {
        return getConfiguration().getByteList(path);
    }

    @Override
    public List<Character> getCharacterList(String path) {
        return getConfiguration().getCharacterList(path);
    }

    @Override
    public List<Short> getShortList(String path) {
        return getConfiguration().getShortList(path);
    }

    @Override
    public List<BigInteger> getBigIntegerList(String path) {
        return getConfiguration().getBigIntegerList(path);
    }

    @Override
    public List<BigDecimal> getBigDecimalList(String path) {
        return getConfiguration().getBigDecimalList(path);
    }

    @Override
    public Section[] getSectionsArray() {
        return getConfiguration().getSectionsArray();
    }

    @Override
    public Section[] getSectionArray(String path) {
        return getConfiguration().getSectionArray(path);
    }

    @Override
    public Object[] getObjectArray(String path) {
        return getConfiguration().getObjectArray(path);
    }

    @Override
    public String[] getStringArray(String path) {
        return getConfiguration().getStringArray(path);
    }

    @Override
    public Integer[] getIntegerArray(String path) {
        return getConfiguration().getIntegerArray(path);
    }

    @Override
    public Number[] getNumberArray(String path) {
        return getConfiguration().getNumberArray(path);
    }

    @Override
    public Boolean[] getBooleanArray(String path) {
        return getConfiguration().getBooleanArray(path);
    }

    @Override
    public Double[] getDoubleArray(String path) {
        return getConfiguration().getDoubleArray(path);
    }

    @Override
    public Float[] getFloatArray(String path) {
        return getConfiguration().getFloatArray(path);
    }

    @Override
    public Long[] getLongArray(String path) {
        return getConfiguration().getLongArray(path);
    }

    @Override
    public Byte[] getByteArray(String path) {
        return getConfiguration().getByteArray(path);
    }

    @Override
    public Character[] getCharacterArray(String path) {
        return getConfiguration().getCharacterArray(path);
    }

    @Override
    public Short[] getShortArray(String path) {
        return getConfiguration().getShortArray(path);
    }

    @Override
    public BigInteger[] getBigIntegerArray(String path) {
        return getConfiguration().getBigIntegerArray(path);
    }

    @Override
    public BigDecimal[] getBigDecimalArray(String path) {
        return getConfiguration().getBigDecimalArray(path);
    }

    @Override
    public int[] getPrimitiveIntegerArray(String path) {
        return getConfiguration().getPrimitiveIntegerArray(path);
    }

    @Override
    public boolean[] getPrimitiveBooleanArray(String path) {
        return getConfiguration().getPrimitiveBooleanArray(path);
    }

    @Override
    public double[] getPrimitiveDoubleArray(String path) {
        return getConfiguration().getPrimitiveDoubleArray(path);
    }

    @Override
    public float[] getPrimitiveFloatArray(String path) {
        return getConfiguration().getPrimitiveFloatArray(path);
    }

    @Override
    public long[] getPrimitiveLongArray(String path) {
        return getConfiguration().getPrimitiveLongArray(path);
    }

    @Override
    public byte[] getPrimitiveByteArray(String path) {
        return getConfiguration().getPrimitiveByteArray(path);
    }

    @Override
    public char[] getPrimitiveCharacterArray(String path) {
        return getConfiguration().getPrimitiveCharacterArray(path);
    }

    @Override
    public short[] getPrimitiveShortArray(String path) {
        return getConfiguration().getPrimitiveShortArray(path);
    }

    @Override
    public Section getSection(String path) {
        return getConfiguration().getSection(path);
    }

    @Override
    public boolean isSection(String path) {
        return getConfiguration().isSection(path);
    }

    @Override
    public DataType getType(String path) {
        return getConfiguration().getType(path);
    }

    @Override
    public Map<String, Object> asMap() {
        return getConfiguration().asMap();
    }
}
