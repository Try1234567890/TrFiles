package me.tr.trFiles.configuration;

import me.tr.trFiles.configuration.memory.DataType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

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

    char getChar(String path);

    char getChar(String path, char def);

    boolean isChar(String path);

    int getInt(String path);

    int getInt(String path, int def);

    boolean isInt(String path);

    Number getNumber(String path);

    Number getNumber(String path, Number def);

    boolean isNumber(String path);

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

    short getShort(String path);

    short getShort(String path, short def);

    boolean isShort(String path);

    BigInteger getBigInteger(String path);

    BigInteger getBigInteger(String path, BigInteger def);

    boolean isBigInteger(String path);

    BigDecimal getBigDecimal(String path);

    BigDecimal getBigDecimal(String path, BigDecimal def);

    boolean isBigDecimal(String path);

    List<?> getList(String path);

    List<?> getList(String path, List<?> def);

    List<Object> getObjectList(String path);

    boolean isList(String path);

    List<Section> getSectionList();

    List<Section> getSectionList(String path);

    List<String> getStringList(String path);

    List<Integer> getIntegerList(String path);

    List<Number> getNumberList(String path);

    List<Boolean> getBooleanList(String path);

    List<Double> getDoubleList(String path);

    List<Float> getFloatList(String path);

    List<Long> getLongList(String path);

    List<Byte> getByteList(String path);

    List<Character> getCharacterList(String path);

    List<Short> getShortList(String path);

    List<BigInteger> getBigIntegerList(String path);

    List<BigDecimal> getBigDecimalList(String path);

    Section[] getSectionsArray();

    Section[] getSectionArray(String path);

    Object[] getObjectArray(String path);

    String[] getStringArray(String path);

    Integer[] getIntegerArray(String path);

    Number[] getNumberArray(String path);

    Boolean[] getBooleanArray(String path);

    Double[] getDoubleArray(String path);

    Float[] getFloatArray(String path);

    Long[] getLongArray(String path);

    Byte[] getByteArray(String path);

    Character[] getCharacterArray(String path);

    Short[] getShortArray(String path);

    BigInteger[] getBigIntegerArray(String path);

    BigDecimal[] getBigDecimalArray(String path);

    int[] getPrimitiveIntegerArray(String path);

    boolean[] getPrimitiveBooleanArray(String path);

    double[] getPrimitiveDoubleArray(String path);

    float[] getPrimitiveFloatArray(String path);

    long[] getPrimitiveLongArray(String path);

    byte[] getPrimitiveByteArray(String path);

    char[] getPrimitiveCharacterArray(String path);

    short[] getPrimitiveShortArray(String path);

    Section getSection(String path);

    boolean isSection(String path);

    Map<String, Object> asMap();

    DataType getType(String path);

}