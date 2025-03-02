package me.tr.configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 * Represents a section of a {@link Configuration}
 */
public interface Section {
    public Set<String> getKeys(boolean recursive);

    public Map<String, Object> getValues(boolean recursive);

    public boolean contains(String path);

    public boolean isSet(String path);

    public String getCurrentPath();

    public String getName();

    public Configuration getRoot();

    public Section getParent();

    public Object get(String path);

    public Object get(String path, Object def);

    public void set(String path, Object value);

    public Section createSection(String path);

    public Section createSection(String path, Map<String, Object> map);

    public String getString(String path);

    public String getString(String path, String def);

    public boolean isString(String path);

    public int getInt(String path);

    public int getInt(String path, int def);

    public boolean isInt(String path);

    public boolean getBoolean(String path);

    public boolean getBoolean(String path, boolean def);

    public boolean isBoolean(String path);

    public double getDouble(String path);

    public double getDouble(String path, double def);

    public boolean isDouble(String path);

    public long getLong(String path);

    public long getLong(String path, long def);

    public boolean isLong(String path);

    public BigInteger getBigInteger(String path);

    public BigInteger getBigInteger(String path, BigInteger def);

    public boolean isBigInteger(String path);

    public BigDecimal getBigDecimal(String path);

    public BigDecimal getBigDecimal(String path, BigDecimal def);

    public boolean isBigDecimal(String path);

    public List<?> getList(String path);

    public List<?> getList(String path, List<?> def);

    public boolean isList(String path);

    public List<String> getStringList(String path);

    public List<Integer> getIntegerList(String path);

    public List<Boolean> getBooleanList(String path);

    public List<Double> getDoubleList(String path);

    public List<Float> getFloatList(String path);

    public List<Long> getLongList(String path);

    public List<Byte> getByteList(String path);

    public List<Character> getCharacterList(String path);

    public List<Short> getShortList(String path);

    public List<BigInteger> getBigIntegerList(String path);

    public List<BigDecimal> getBigDecimalList(String path);

    public Section getSection(String path);

    public boolean isSection(String path);

    public void addDefault(String path, Object value);
}