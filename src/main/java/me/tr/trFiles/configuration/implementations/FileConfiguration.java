package me.tr.trFiles.configuration.implementations;

import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.Configuration;
import me.tr.trFiles.configuration.Section;
import me.tr.trFiles.configuration.management.FileUtility;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

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

    protected FileConfiguration(File file, MemoryConfiguration configuration) {
        this.configuration = configuration;
        this.file = file;
    }

    public FileConfiguration(File file, Map<String, Object> map) {
        from(file, map);
    }

    public FileConfiguration(File file, Reader reader) {
        from(file, reader);
    }

    public FileConfiguration(File file, InputStream is) {
        from(file, is);
    }

    public FileConfiguration(File file) {
        from(file);
    }

    public FileConfiguration(Path path) {
        from(path);
    }

    public FileConfiguration(String path) {
        from(path);
    }

    public FileConfiguration(ZipFile archive, File inside, File to) {
        from(archive, inside, to);
    }

    public FileConfiguration(File archive, File inside, File to) {
        from(archive, inside, to);
    }

    protected FileConfiguration from(File file, MemoryConfiguration configuration) {
        this.configuration = configuration;
        this.file = file;
        return this;
    }

    public FileConfiguration from(File file, Map<String, Object> map) {
        getConfiguration().from(map);
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file, Reader reader) {
        getConfiguration().from(reader);
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file, InputStream is) {
        getConfiguration().from(is);
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.isFile(), "The saving file at " + file.getPath() + " not exists or is not a file.");
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

        Validator.checkIf(to.canRead(), "Cannot read the saving file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write the saving file at " + to.getPath());
        Validator.checkIf(to.isFile(), "The saving file at " + to.getPath() + " not exists or is not a file.");

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

        Validator.checkIf(archiveFile.canRead(), "Cannot read the archive file.");
        Validator.checkIf(FileUtility.isZip(archiveFile), "The provided archive file at" + archiveFile.getPath() + " not exists or is not a archive.");

        try (ZipFile archive = new ZipFile(archiveFile)) {
            return from(archive, inside, to);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided archive", e);
        }
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
        getConfiguration().convert(getFile(), to.getConfiguration());
        to.setFile(getFile());
        return to;
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

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


    public String[] getExtensions() {
        return new String[]{};
    }

    public MemoryConfiguration getConfiguration() {
        if (configuration == null) {
            configuration = newConfiguration();
        }
        return configuration;
    }

    protected void setConfiguration(MemoryConfiguration configuration) {
        this.configuration = configuration;
    }

    private boolean isValid(File file) {
        for (String extension : getExtensions()) {
            if (FileUtility.getExtension(file).equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    protected abstract MemoryConfiguration newConfiguration();
















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
    public void addDefault(String path, Object value) {
        getConfiguration().addDefault(path, value);
    }

    @Override
    public Object getDefault(String path) {
        return getConfiguration().getDefault(path);
    }

    @Override
    public Map<String, Object> asMap() {
        return getConfiguration().asMap();
    }
}
