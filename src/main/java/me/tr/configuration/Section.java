package me.tr.configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.List;

/**
 * Represents a section of a {@link Configuration}
 */
public interface Section {
    List<String> getKeys(boolean recursive);

    Map<String, Object> getValues(boolean recursive);

    boolean contains(String path);

    boolean isSet(String path);

    String getCurrentPath();

    String getName();

    Configuration getRoot();

    Section getParent();

    String getFullPath();

    Object get(String path);

    Object get(String path, Object def);

    void set(String path, Object value);

    Section createSection(String path);

    Section createSection(String path, Map<String, Object> map);

    String getString(String path);

    String getString(String path, String def);

    boolean isString(String path);

    int getInt(String path);

    int getInt(String path, int def);

    boolean isInt(String path);

    boolean getBoolean(String path);

    boolean getBoolean(String path, boolean def);

    boolean isBoolean(String path);

    double getDouble(String path);

    double getDouble(String path, double def);

    boolean isDouble(String path);

    float getFloat(String path);

    float getFloat(String path, float def);

    boolean isFloat(String path);

    long getLong(String path);

    long getLong(String path, long def);

    boolean isLong(String path);

    BigInteger getBigInteger(String path);

    BigInteger getBigInteger(String path, BigInteger def);

    boolean isBigInteger(String path);

    BigDecimal getBigDecimal(String path);

    BigDecimal getBigDecimal(String path, BigDecimal def);

    boolean isBigDecimal(String path);

    List<?> getList(String path);

    List<?> getList(String path, List<?> def);

    boolean isList(String path);

    List<String> getStringList(String path);

    List<Integer> getIntegerList(String path);

    List<Boolean> getBooleanList(String path);

    List<Double> getDoubleList(String path);

    List<Float> getFloatList(String path);

    List<Long> getLongList(String path);

    List<Byte> getByteList(String path);

    List<Character> getCharacterList(String path);

    List<Short> getShortList(String path);

    List<BigInteger> getBigIntegerList(String path);

    List<BigDecimal> getBigDecimalList(String path);

    Section getSection(String path);

    boolean isSection(String path);

    void addDefault(String path, Object value);
}