package me.tr.trfiles.file.configuration;

import me.tr.trfiles.file.configuration.memory.DataType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

/**
 * Represents a section of a {@link Config}
 */
public interface Section {

    /**
     * Get the keys contained in this section.
     *
     * @param recursive if true include keys from nested sections (full paths), otherwise only immediate keys
     * @return list of keys (may be empty)
     */
    List<String> getKeys(boolean recursive);

    /**
     * Get the values contained in this section.
     *
     * @param recursive if true include values from nested sections (with section maps), otherwise only immediate values
     * @return map of keys to values (may be empty)
     */
    Map<String, Object> getValues(boolean recursive);

    /**
     * Check whether this section contains the specified path.
     *
     * @param path path relative to this section
     * @return true if a value exists at the path (including null values set), false otherwise
     */
    boolean contains(String path);

    /**
     * Check whether the value at {@code path} is explicitly set.
     *
     * @param path path relative to this section
     * @return true if a value is set (not inherited/virtual), false otherwise
     */
    boolean isSet(String path);

    /**
     * Get the current path of this section relative to the root configuration.
     *
     * @return current path (empty string or root identifier for root section)
     */
    String getCurrentPath();

    /**
     * Get the name of this section (last path component).
     *
     * @return section name
     */
    String getName();

    /**
     * Get the root configuration that owns this section.
     *
     * @return root {@link Config}
     */
    Config getRoot();

    /**
     * Get the parent section of this section.
     *
     * @return parent {@link Section}, or null if this is the root section
     */
    Section getParent();

    /**
     * Get the full path of this section from the root.
     *
     * @return full path (may be same as {@link #getCurrentPath()})
     */
    String getFullPath();

    /**
     * Get the raw object at the specified path.
     *
     * @param path path relative to this section
     * @return the value found or null if not present
     */
    Object get(String path);

    /**
     * Get the raw object at the specified path or return a default.
     *
     * @param path path relative to this section
     * @param def  default value to return if no value is present
     * @return the value found or {@code def} if not present
     */
    Object get(String path, Object def);

    /**
     * Set a value at the specified path. Passing null typically unsets the path.
     *
     * @param path  path relative to this section
     * @param value value to set
     */
    void set(String path, Object value);

    /**
     * Create a nested section at the given path.
     *
     * @param path path relative to this section
     * @return the created or existing {@link Section}
     */
    Section createSection(String path);

    /**
     * Create a nested section at the given path and populate it with the given map.
     *
     * @param path path relative to this section
     * @param map  initial values to set in the created section
     * @return the created or existing {@link Section}
     */
    Section createSection(String path, Map<String, Object> map);

    /**
     * Get a string value at the path.
     *
     * @param path path relative to this section
     * @return string value or null if missing or not convertible
     */
    String getString(String path);

    /**
     * Get a string value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default string to return if not present
     * @return found string or {@code def}
     */
    String getString(String path, String def);

    /**
     * Check whether the value at {@code path} can be represented as a string.
     *
     * @param path path relative to this section
     * @return true if present and convertible to string
     */
    boolean isString(String path);

    /**
     * Get a char value at the path.
     *
     * @param path path relative to this section
     * @return char value
     */
    char getChar(String path);

    /**
     * Get a char value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default char to return if not present or not convertible
     * @return found char or {@code def}
     */
    char getChar(String path, char def);

    /**
     * Check whether the value at {@code path} can be represented as a char.
     *
     * @param path path relative to this section
     * @return true if present and convertible to char
     */
    boolean isChar(String path);

    /**
     * Get an int value at the path.
     *
     * @param path path relative to this section
     * @return int value
     */
    int getInt(String path);

    /**
     * Get an int value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default int to return if not present or not convertible
     * @return found int or {@code def}
     */
    int getInt(String path, int def);

    /**
     * Check whether the value at {@code path} can be represented as an int.
     *
     * @param path path relative to this section
     * @return true if present and convertible to int
     */
    boolean isInt(String path);

    /**
     * Get a numeric value at the path.
     *
     * @param path path relative to this section
     * @return {@link Number} instance or null if not present/convertible
     */
    Number getNumber(String path);

    /**
     * Get a numeric value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default number to return if not present or not convertible
     * @return found number or {@code def}
     */
    Number getNumber(String path, Number def);

    /**
     * Check whether the value at {@code path} can be represented as a number.
     *
     * @param path path relative to this section
     * @return true if present and convertible to {@link Number}
     */
    boolean isNumber(String path);

    /**
     * Get a boolean value at the path.
     *
     * @param path path relative to this section
     * @return boolean value
     */
    boolean getBoolean(String path);

    /**
     * Get a boolean value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default boolean to return if not present or not convertible
     * @return found boolean or {@code def}
     */
    boolean getBoolean(String path, boolean def);

    /**
     * Check whether the value at {@code path} can be represented as a boolean.
     *
     * @param path path relative to this section
     * @return true if present and convertible to boolean
     */
    boolean isBoolean(String path);

    /**
     * Get a double value at the path.
     *
     * @param path path relative to this section
     * @return double value
     */
    double getDouble(String path);

    /**
     * Get a double value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default double to return if not present or not convertible
     * @return found double or {@code def}
     */
    double getDouble(String path, double def);

    /**
     * Check whether the value at {@code path} can be represented as a double.
     *
     * @param path path relative to this section
     * @return true if present and convertible to double
     */
    boolean isDouble(String path);

    /**
     * Get a float value at the path.
     *
     * @param path path relative to this section
     * @return float value
     */
    float getFloat(String path);

    /**
     * Get a float value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default float to return if not present or not convertible
     * @return found float or {@code def}
     */
    float getFloat(String path, float def);

    /**
     * Check whether the value at {@code path} can be represented as a float.
     *
     * @param path path relative to this section
     * @return true if present and convertible to float
     */
    boolean isFloat(String path);

    /**
     * Get a long value at the path.
     *
     * @param path path relative to this section
     * @return long value
     */
    long getLong(String path);

    /**
     * Get a long value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default long to return if not present or not convertible
     * @return found long or {@code def}
     */
    long getLong(String path, long def);

    /**
     * Check whether the value at {@code path} can be represented as a long.
     *
     * @param path path relative to this section
     * @return true if present and convertible to long
     */
    boolean isLong(String path);

    /**
     * Get a short value at the path.
     *
     * @param path path relative to this section
     * @return short value
     */
    short getShort(String path);

    /**
     * Get a short value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default short to return if not present or not convertible
     * @return found short or {@code def}
     */
    short getShort(String path, short def);

    /**
     * Check whether the value at {@code path} can be represented as a short.
     *
     * @param path path relative to this section
     * @return true if present and convertible to short
     */
    boolean isShort(String path);

    /**
     * Get a byte value at the path.
     *
     * @param path path relative to this section
     * @return byte value
     */
    byte getByte(String path);

    /**
     * Get a byte value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default byte to return if not present or not convertible
     * @return found byte or {@code def}
     */
    byte getByte(String path, byte def);

    /**
     * Check whether the value at {@code path} can be represented as a byte.
     *
     * @param path path relative to this section
     * @return true if present and convertible to byte
     */
    boolean isByte(String path);

    /**
     * Get a {@link BigInteger} value at the path.
     *
     * @param path path relative to this section
     * @return {@link BigInteger} or null if missing/not convertible
     */
    BigInteger getBigInteger(String path);

    /**
     * Get a {@link BigInteger} value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default to return if not present or not convertible
     * @return found {@link BigInteger} or {@code def}
     */
    BigInteger getBigInteger(String path, BigInteger def);

    /**
     * Check whether the value at {@code path} can be represented as a {@link BigInteger}.
     *
     * @param path path relative to this section
     * @return true if present and convertible to {@link BigInteger}
     */
    boolean isBigInteger(String path);

    /**
     * Get a {@link BigDecimal} value at the path.
     *
     * @param path path relative to this section
     * @return {@link BigDecimal} or null if missing/not convertible
     */
    BigDecimal getBigDecimal(String path);

    /**
     * Get a {@link BigDecimal} value at the path or a default.
     *
     * @param path path relative to this section
     * @param def  default to return if not present or not convertible
     * @return found {@link BigDecimal} or {@code def}
     */
    BigDecimal getBigDecimal(String path, BigDecimal def);

    /**
     * Check whether the value at {@code path} can be represented as a {@link BigDecimal}.
     *
     * @param path path relative to this section
     * @return true if present and convertible to {@link BigDecimal}
     */
    boolean isBigDecimal(String path);

    /**
     * Get a nested section at the specified path.
     *
     * @param path path relative to this section
     * @return {@link Section} or null if missing/not a section
     */
    Section getSection(String path);

    /**
     * Get a nested section at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default section to return if missing
     * @return found {@link Section} or {@code def}
     */
    Section getSection(String path, Section def);

    /**
     * Get a nested section at the specified path or create/return a section built from the provided map.
     *
     * @param path path relative to this section
     * @param def  map to use as fallback section values if missing
     * @return found {@link Section} or a section created/populated from {@code def}
     */
    Section getSection(String path, Map<String, Object> def);

    /**
     * Check whether the value at {@code path} is a section.
     *
     * @param path path relative to this section
     * @return true if present and is a section
     */
    boolean isSection(String path);

    /**
     * Get the list value of this section (when the section itself represents a list).
     *
     * @return list or empty list if none
     */
    List<?> getList();

    /**
     * Get a list value at the specified path.
     *
     * @param path path relative to this section
     * @return list or null if missing/not a list
     */
    List<?> getList(String path);

    /**
     * Get a list value at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<?> getList(String path, List<?> def);

    /**
     * Check whether the value at {@code path} is a list.
     *
     * @param path path relative to this section
     * @return true if present and is a list
     */
    boolean isList(String path);

    /**
     * Get a list of objects from this section (when the section itself represents a list).
     *
     * @return list of objects
     */
    List<Object> getObjectList();

    /**
     * Get a list of objects at the specified path.
     *
     * @param path path relative to this section
     * @return list of objects or null if missing/not a list
     */
    List<Object> getObjectList(String path);

    /**
     * Get a list of objects at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Object> getObjectList(String path, List<Object> def);

    /**
     * Get a list of subsections contained in this section (when this section itself represents a list).
     *
     * @return list of subsections
     */
    List<Section> getSectionList();

    /**
     * Get a list of subsections at the specified path.
     *
     * @param path path relative to this section
     * @return list of subsections or null if missing/not a list of sections
     */
    List<Section> getSectionList(String path);

    /**
     * Get a list of subsections at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Section> getSectionList(String path, List<Section> def);

    /**
     * Get a list of strings from this section (when the section itself represents a list).
     *
     * @return list of strings
     */
    List<String> getStringList();

    /**
     * Get a list of strings at the specified path.
     *
     * @param path path relative to this section
     * @return list of strings or null if missing/not a list of strings
     */
    List<String> getStringList(String path);

    /**
     * Get a list of strings at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<String> getStringList(String path, List<String> def);

    /**
     * Get a list of integers from this section (when the section itself represents a list).
     *
     * @return list of integers
     */
    List<Integer> getIntegerList();

    /**
     * Get a list of integers at the specified path.
     *
     * @param path path relative to this section
     * @return list of integers or null if missing/not a list of integers
     */
    List<Integer> getIntegerList(String path);

    /**
     * Get a list of integers at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Integer> getIntegerList(String path, List<Integer> def);

    /**
     * Get a list of numbers from this section.
     *
     * @return list of numbers
     */
    List<Number> getNumberList();

    /**
     * Get a list of numbers at the specified path.
     *
     * @param path path relative to this section
     * @return list of numbers or null if missing/not a list of numbers
     */
    List<Number> getNumberList(String path);

    /**
     * Get a list of numbers at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Number> getNumberList(String path, List<Number> def);

    /**
     * Get a list of booleans from this section.
     *
     * @return list of booleans
     */
    List<Boolean> getBooleanList();

    /**
     * Get a list of booleans at the specified path.
     *
     * @param path path relative to this section
     * @return list of booleans or null if missing/not a list of booleans
     */
    List<Boolean> getBooleanList(String path);

    /**
     * Get a list of booleans at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Boolean> getBooleanList(String path, List<Boolean> def);

    /**
     * Get a list of doubles from this section.
     *
     * @return list of doubles
     */
    List<Double> getDoubleList();

    /**
     * Get a list of doubles at the specified path.
     *
     * @param path path relative to this section
     * @return list of doubles or null if missing/not a list of doubles
     */
    List<Double> getDoubleList(String path);

    /**
     * Get a list of doubles at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Double> getDoubleList(String path, List<Double> def);

    /**
     * Get a list of floats from this section.
     *
     * @return list of floats
     */
    List<Float> getFloatList();

    /**
     * Get a list of floats at the specified path.
     *
     * @param path path relative to this section
     * @return list of floats or null if missing/not a list of floats
     */
    List<Float> getFloatList(String path);

    /**
     * Get a list of floats at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Float> getFloatList(String path, List<Float> def);

    /**
     * Get a list of longs from this section.
     *
     * @return list of longs
     */
    List<Long> getLongList();

    /**
     * Get a list of longs at the specified path.
     *
     * @param path path relative to this section
     * @return list of longs or null if missing/not a list of longs
     */
    List<Long> getLongList(String path);

    /**
     * Get a list of longs at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Long> getLongList(String path, List<Long> def);

    /**
     * Get a list of bytes from this section.
     *
     * @return list of bytes
     */
    List<Byte> getByteList();

    /**
     * Get a list of bytes at the specified path.
     *
     * @param path path relative to this section
     * @return list of bytes or null if missing/not a list of bytes
     */
    List<Byte> getByteList(String path);

    /**
     * Get a list of bytes at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Byte> getByteList(String path, List<Byte> def);

    /**
     * Get a list of characters from this section.
     *
     * @return list of characters
     */
    List<Character> getCharacterList();

    /**
     * Get a list of characters at the specified path.
     *
     * @param path path relative to this section
     * @return list of characters or null if missing/not a list of characters
     */
    List<Character> getCharacterList(String path);

    /**
     * Get a list of characters at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Character> getCharacterList(String path, List<Character> def);

    /**
     * Get a list of shorts from this section.
     *
     * @return list of shorts
     */
    List<Short> getShortList();

    /**
     * Get a list of shorts at the specified path.
     *
     * @param path path relative to this section
     * @return list of shorts or null if missing/not a list of shorts
     */
    List<Short> getShortList(String path);

    /**
     * Get a list of shorts at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<Short> getShortList(String path, List<Short> def);

    /**
     * Get a list of {@link BigInteger} from this section.
     *
     * @return list of {@link BigInteger}
     */
    List<BigInteger> getBigIntegerList();

    /**
     * Get a list of {@link BigInteger} at the specified path.
     *
     * @param path path relative to this section
     * @return list of {@link BigInteger} or null if missing/not a list of big integers
     */
    List<BigInteger> getBigIntegerList(String path);

    /**
     * Get a list of {@link BigInteger} at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<BigInteger> getBigIntegerList(String path, List<BigInteger> def);

    /**
     * Get a list of {@link BigDecimal} from this section.
     *
     * @return list of {@link BigDecimal}
     */
    List<BigDecimal> getBigDecimalList();

    /**
     * Get a list of {@link BigDecimal} at the specified path.
     *
     * @param path path relative to this section
     * @return list of {@link BigDecimal} or null if missing/not a list of big decimals
     */
    List<BigDecimal> getBigDecimalList(String path);

    /**
     * Get a list of {@link BigDecimal} at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default list to return if missing
     * @return found list or {@code def}
     */
    List<BigDecimal> getBigDecimalList(String path, List<BigDecimal> def);

    /**
     * Get an array of subsections contained in this section.
     *
     * @return array of sections (may be empty)
     */
    Section[] getSectionArray();

    /**
     * Get an array of subsections at the specified path.
     *
     * @param path path relative to this section
     * @return array of sections or null if missing/not an array of sections
     */
    Section[] getSectionArray(String path);

    /**
     * Get an array of subsections at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Section[] getSectionArray(String path, Section[] def);

    /**
     * Get an object array from this section.
     *
     * @return object array
     */
    Object[] getObjectArray();

    /**
     * Get an object array at the specified path.
     *
     * @param path path relative to this section
     * @return object array or null if missing/not an array
     */
    Object[] getObjectArray(String path);

    /**
     * Get an object array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Object[] getObjectArray(String path, Object[] def);

    /**
     * Get a string array from this section.
     *
     * @return string array
     */
    String[] getStringArray();

    /**
     * Get a string array at the specified path.
     *
     * @param path path relative to this section
     * @return string array or null if missing/not an array
     */
    String[] getStringArray(String path);

    /**
     * Get a string array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    String[] getStringArray(String path, String[] def);

    /**
     * Get an integer array from this section.
     *
     * @return integer array
     */
    Integer[] getIntegerArray();

    /**
     * Get an integer array at the specified path.
     *
     * @param path path relative to this section
     * @return integer array or null if missing/not an array
     */
    Integer[] getIntegerArray(String path);

    /**
     * Get an integer array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Integer[] getIntegerArray(String path, Integer[] def);

    /**
     * Get a number array from this section.
     *
     * @return number array
     */
    Number[] getNumberArray();

    /**
     * Get a number array at the specified path.
     *
     * @param path path relative to this section
     * @return number array or null if missing/not an array
     */
    Number[] getNumberArray(String path);

    /**
     * Get a number array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Number[] getNumberArray(String path, Number[] def);

    /**
     * Get a boolean array from this section.
     *
     * @return boolean array
     */
    Boolean[] getBooleanArray();

    /**
     * Get a boolean array at the specified path.
     *
     * @param path path relative to this section
     * @return boolean array or null if missing/not an array
     */
    Boolean[] getBooleanArray(String path);

    /**
     * Get a boolean array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Boolean[] getBooleanArray(String path, Boolean[] def);

    /**
     * Get a double array from this section.
     *
     * @return double array
     */
    Double[] getDoubleArray();

    /**
     * Get a double array at the specified path.
     *
     * @param path path relative to this section
     * @return double array or null if missing/not an array
     */
    Double[] getDoubleArray(String path);

    /**
     * Get a double array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Double[] getDoubleArray(String path, Double[] def);

    /**
     * Get a float array from this section.
     *
     * @return float array
     */
    Float[] getFloatArray();

    /**
     * Get a float array at the specified path.
     *
     * @param path path relative to this section
     * @return float array or null if missing/not an array
     */
    Float[] getFloatArray(String path);

    /**
     * Get a float array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Float[] getFloatArray(String path, Float[] def);

    /**
     * Get a long array from this section.
     *
     * @return long array
     */
    Long[] getLongArray();

    /**
     * Get a long array at the specified path.
     *
     * @param path path relative to this section
     * @return long array or null if missing/not an array
     */
    Long[] getLongArray(String path);

    /**
     * Get a long array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Long[] getLongArray(String path, Long[] def);

    /**
     * Get a byte array from this section.
     *
     * @return byte array
     */
    Byte[] getByteArray();

    /**
     * Get a byte array at the specified path.
     *
     * @param path path relative to this section
     * @return byte array or null if missing/not an array
     */
    Byte[] getByteArray(String path);

    /**
     * Get a byte array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Byte[] getByteArray(String path, Byte[] def);

    /**
     * Get a character array from this section.
     *
     * @return character array
     */
    Character[] getCharacterArray();

    /**
     * Get a character array at the specified path.
     *
     * @param path path relative to this section
     * @return character array or null if missing/not an array
     */
    Character[] getCharacterArray(String path);

    /**
     * Get a character array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Character[] getCharacterArray(String path, Character[] def);

    /**
     * Get a short array from this section.
     *
     * @return short array
     */
    Short[] getShortArray();

    /**
     * Get a short array at the specified path.
     *
     * @param path path relative to this section
     * @return short array or null if missing/not an array
     */
    Short[] getShortArray(String path);

    /**
     * Get a short array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    Short[] getShortArray(String path, Short[] def);

    /**
     * Get a {@link BigInteger} array from this section.
     *
     * @return array of {@link BigInteger}
     */
    BigInteger[] getBigIntegerArray();

    /**
     * Get a {@link BigInteger} array at the specified path.
     *
     * @param path path relative to this section
     * @return array of {@link BigInteger} or null if missing/not an array
     */
    BigInteger[] getBigIntegerArray(String path);

    /**
     * Get a {@link BigInteger} array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    BigInteger[] getBigIntegerArray(String path, BigInteger[] def);

    /**
     * Get a {@link BigDecimal} array from this section.
     *
     * @return array of {@link BigDecimal}
     */
    BigDecimal[] getBigDecimalArray();

    /**
     * Get a {@link BigDecimal} array at the specified path.
     *
     * @param path path relative to this section
     * @return array of {@link BigDecimal} or null if missing/not an array
     */
    BigDecimal[] getBigDecimalArray(String path);

    /**
     * Get a {@link BigDecimal} array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default array to return if missing
     * @return found array or {@code def}
     */
    BigDecimal[] getBigDecimalArray(String path, BigDecimal[] def);

    /**
     * Get a primitive int array representing integers from this section.
     *
     * @return primitive int array
     */
    int[] getPrimitiveIntegerArray();

    /**
     * Get a primitive int array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive int array or null if missing/not an array
     */
    int[] getPrimitiveIntegerArray(String path);

    /**
     * Get a primitive int array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    int[] getPrimitiveIntegerArray(String path, int[] def);

    /**
     * Get a primitive boolean array from this section.
     *
     * @return primitive boolean array
     */
    boolean[] getPrimitiveBooleanArray();

    /**
     * Get a primitive boolean array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive boolean array or null if missing/not an array
     */
    boolean[] getPrimitiveBooleanArray(String path);

    /**
     * Get a primitive boolean array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    boolean[] getPrimitiveBooleanArray(String path, boolean[] def);

    /**
     * Get a primitive double array from this section.
     *
     * @return primitive double array
     */
    double[] getPrimitiveDoubleArray();

    /**
     * Get a primitive double array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive double array or null if missing/not an array
     */
    double[] getPrimitiveDoubleArray(String path);

    /**
     * Get a primitive double array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    double[] getPrimitiveDoubleArray(String path, double[] def);

    /**
     * Get a primitive float array from this section.
     *
     * @return primitive float array
     */
    float[] getPrimitiveFloatArray();

    /**
     * Get a primitive float array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive float array or null if missing/not an array
     */
    float[] getPrimitiveFloatArray(String path);

    /**
     * Get a primitive float array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    float[] getPrimitiveFloatArray(String path, float[] def);

    /**
     * Get a primitive long array from this section.
     *
     * @return primitive long array
     */
    long[] getPrimitiveLongArray();

    /**
     * Get a primitive long array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive long array or null if missing/not an array
     */
    long[] getPrimitiveLongArray(String path);

    /**
     * Get a primitive long array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    long[] getPrimitiveLongArray(String path, long[] def);

    /**
     * Get a primitive byte array from this section.
     *
     * @return primitive byte array
     */
    byte[] getPrimitiveByteArray();

    /**
     * Get a primitive byte array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive byte array or null if missing/not an array
     */
    byte[] getPrimitiveByteArray(String path);

    /**
     * Get a primitive byte array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    byte[] getPrimitiveByteArray(String path, byte[] def);

    /**
     * Get a primitive char array from this section.
     *
     * @return primitive char array
     */
    char[] getPrimitiveCharacterArray();

    /**
     * Get a primitive char array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive char array or null if missing/not an array
     */
    char[] getPrimitiveCharacterArray(String path);

    /**
     * Get a primitive char array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    char[] getPrimitiveCharacterArray(String path, char[] def);

    /**
     * Get a primitive short array from this section.
     *
     * @return primitive short array
     */
    short[] getPrimitiveShortArray();

    /**
     * Get a primitive short array at the specified path.
     *
     * @param path path relative to this section
     * @return primitive short array or null if missing/not an array
     */
    short[] getPrimitiveShortArray(String path);

    /**
     * Get a primitive short array at the specified path or a default.
     *
     * @param path path relative to this section
     * @param def  default primitive array to return if missing
     * @return found array or {@code def}
     */
    short[] getPrimitiveShortArray(String path, short[] def);

    /**
     * Get this section as a map of key to raw value.
     *
     * @return map representation of this section
     */
    Map<String, Object> asMap();

    /**
     * Get the data type of the value at the specified path.
     *
     * @param path path relative to this section
     * @return {@link DataType} describing the stored value or null if not present
     */
    DataType getType(String path);
}