package me.tr.trFiles.configuration.memory;

import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.Configuration;
import me.tr.trFiles.configuration.Section;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MemorySection implements Section {
    protected final Map<String, Object> map = new LinkedHashMap<>();
    private final Configuration root; // The root (First section) of the current Section
    private final Section parent; // The parent (The section on top) of the current Section
    private final String fullPath; // The path divided by separator (def: '.') from root to current section
    private final String currentPath; // The provided path to constructor.
    private final String name; // The section name

    protected MemorySection(Section parent, String path) {
        Validator.isNull(parent, "Cannot construct a root MemorySection when parent is null");
        Validator.isNull(path, "Cannot construct a root MemorySection when path is null");
        this.currentPath = path;
        this.parent = parent;
        this.fullPath = createPath(parent, path);
        this.root = parent.getRoot();
        this.name = currentPath.substring(currentPath.lastIndexOf(root.options().getPathSeparator()) + 1);
    }


    protected MemorySection() {
        Validator.checkIf(this instanceof Configuration, "Cannot construct a root MemorySection when not a Configuration");
        this.currentPath = "";
        this.name = "";
        this.fullPath = "";
        this.parent = null;
        this.root = (Configuration) this;
    }

    /**
     * Retrieve the current path of the Section.
     *
     * <p>
     * This corresponds to the path provided at initialization.
     *
     * @return The current path of this Section.
     */
    @Override
    public String getCurrentPath() {
        return currentPath;
    }

    /**
     * Retrieve the name of the Section.
     *
     * <p>
     * This corresponds to the name of the Section
     * or to the last part of the current path.
     *
     * @return The name of this Section.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retrieve the root of the Section.
     *
     * <p>
     * This corresponds to the root or
     * to the Configuration that contains
     * the Section.
     *
     * @return The root of this Section.
     */
    @Override
    public Configuration getRoot() {
        return root;
    }

    /**
     * Retrieve the parent of the Section.
     *
     * <p>
     * This corresponds to the parent or
     * to the section on top of the current
     * Section.
     *
     * @return The parent of this Section.
     */
    @Override
    public Section getParent() {
        return parent;
    }

    /**
     * Retrieve the full path of the Section.
     *
     * <p>
     * This corresponds to the full path or
     * to the path from the root to the current
     * Section.
     *
     * @return The full path of this Section.
     */
    @Override
    public String getFullPath() {
        return fullPath;
    }

    @Override
    public List<String> getKeys(boolean recursive) {
        List<String> output = new ArrayList<>();
        for (String key : map.keySet()) {
            output.add(key);
            Object keyValue = map.get(key);
            if (keyValue instanceof Section section && recursive) {
                output.addAll(section.getKeys(true));
            }
        }
        return output;
    }

    @Override
    public Map<String, Object> getValues(boolean recursive) {
        Map<String, Object> output = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof Section subSection && recursive) {
                output.put(key, convertSectionToMap(subSection));
            } else {
                output.put(key, value);
            }
        }
        return output;
    }

    private Map<String, Object> convertSectionToMap(Section section) {
        Map<String, Object> output = new LinkedHashMap<>();
        for (String key : section.getKeys(false)) {
            Object value = section.get(key);
            if (value instanceof Section subSection) {
                output.put(key, convertSectionToMap(subSection));
            } else {
                output.put(key, value);
            }
        }
        return output;
    }


    @Override
    public boolean contains(String path) {
        return get(path) != null;
    }

    @Override
    public boolean isSet(String path) {
        return get(path, null) != null;
    }

    @Override
    public Object get(String path) {
        if (path.isEmpty()) {
            return this;
        }
        Configuration root = getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot access section without a root");
        }
        final char separator = root.options().getPathSeparator();
        /*
         * after is the leading (higher) index.
         * before is the trailing (lower) index.
         *
         * In path: 'service.api.enable':
         *
         *   -> In cycle 1: the before = 7 and the after = 0: key = 'service'
         *   -> In cycle 2: the before = 11 and the after = 8: key = 'api'
         *   -> In cycle 3: the before = 17 and the after = 12: key = 'enable'
         */
        int before = -1, after;
        Section section = this;
        while ((before = path.indexOf(separator, after = before + 1)) != -1) {
            section = section.getSection(path.substring(after, before));
            if (section == null) {
                return null;
            }
        }
        String key = path.substring(after);
        if (section == this) {
            return map.get(key);
        }
        return section.get(key);
    }

    @Override
    public Object get(String path, Object def) {
        Object val = get(path);
        return val == null ? def : val;
    }

    @Override
    public void set(String path, Object value) {
        Configuration root = getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot set value without a root");
        }
        final char separator = root.options().getPathSeparator();
        int before = -1, after;
        Section section = this;
        while ((before = path.indexOf(separator, after = before + 1)) != -1) {
            String node = path.substring(after, before);
            Section subSection = section.getSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            } else {
                section = subSection;
            }
        }
        String key = path.substring(after);
        if (section == this) {
            if (value == null) {
                map.remove(key);
            } else {
                map.put(key, value);
            }
        } else {
            section.set(key, value);
        }
    }

    @Override
    public @Nullable Section getSection(String path) {
        Object val = get(path);
        return val instanceof Section ? (Section) val : null;
    }

    @Override
    public boolean isSection(String path) {
        Object val = get(path);
        return val instanceof Section;
    }

    @Override
    public Section createSection(String path, Map<String, Object> map) {
        Section result = createSection(path);
        for (String key : map.keySet()) {
            if (result == null)
                return null;
            result.set(key, map.get(key));
        }
        return result;
    }

    @Override
    public Section createSection(String path) {
        Configuration root = getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot create section without a root");
        }
        final char separator = root.options().getPathSeparator();
        Section section = this;
        int before = -1, after;
        while ((before = path.indexOf(separator, after = before + 1)) != -1) {
            String node = path.substring(after, before);
            Section subSection = section.getSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            } else {
                section = subSection;
            }
        }

        String key = path.substring(after);
        if (section == this) {
            Section result = new MemorySection(this, key);
            map.put(key, result);
            return result;
        }
        return section.createSection(key);
    }

    @Override
    public @Nullable String getString(String path) {
        Object val = get(path);
        return val instanceof String ? (String) val : null;
    }

    @Override
    public String getString(String path, String def) {
        String val = getString(path);
        return val == null ? def : val;
    }

    @Override
    public boolean isString(String path) {
        return getString(path) != null;
    }

    @Override
    public char getChar(String path) {
        Object val = get(path);
        return (val instanceof String defStr && defStr.length() == 1) ? defStr.charAt(0) : (char) -1;
    }

    @Override
    public char getChar(String path, char def) {
        char val = getChar(path);
        return val == (char) -1 ? def : getChar(path);
    }

    @Override
    public boolean isChar(String path) {
        return getChar(path) != (char) -1;
    }

    @Override
    public int getInt(String path) {
        Object val = get(path);
        return val instanceof Number ? ((Number) val).intValue() : -1;
    }

    @Override
    public int getInt(String path, int def) {
        Object val = get(path);
        return val instanceof Number number ? number.intValue() : def;
    }

    @Override
    public boolean isInt(String path) {
        Object val = get(path);
        return val instanceof Integer;
    }

    @Override
    public Number getNumber(String path) {
        Object val = get(path);
        return val instanceof Number ? (Number) val : -1;
    }

    @Override
    public Number getNumber(String path, Number def) {
        Object val = get(path);
        return val instanceof Number number ? number : def;
    }

    @Override
    public boolean isNumber(String path) {
        Object val = get(path);
        return val instanceof Number;
    }

    @Override
    public boolean getBoolean(String path) {
        Object val = get(path);
        return val instanceof Boolean ? (Boolean) val : false;
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        Object val = get(path);
        return val instanceof Boolean ? (Boolean) val : def;
    }

    @Override
    public boolean isBoolean(String path) {
        Object val = get(path);
        return val instanceof Boolean;
    }

    @Override
    public double getDouble(String path) {
        Object val = get(path);
        return val instanceof Double ? (Double) val : -1;
    }

    @Override
    public double getDouble(String path, double def) {
        Object val = get(path);
        return val instanceof Double ? (Double) val : def;
    }

    @Override
    public boolean isDouble(String path) {
        Object val = get(path);
        return val instanceof Double;
    }

    @Override
    public float getFloat(String path) {
        Object val = get(path);
        return val instanceof Float ? (Float) val : -1;
    }

    @Override
    public float getFloat(String path, float def) {
        Object val = get(path);
        return val instanceof Float ? (Float) val : def;
    }

    @Override
    public boolean isFloat(String path) {
        Object val = get(path);
        return val instanceof Float;
    }

    @Override
    public long getLong(String path) {
        Object val = get(path);
        return val instanceof Long ? (Long) val : -1;
    }

    @Override
    public long getLong(String path, long def) {
        Object val = get(path);
        return val instanceof Long ? (Long) val : def;
    }

    @Override
    public boolean isLong(String path) {
        Object val = get(path);
        return val instanceof Long;
    }

    @Override
    public short getShort(String path) {
        Object val = get(path);
        return val instanceof Short ? (Short) val : -1;
    }

    @Override
    public short getShort(String path, short def) {
        Object val = get(path);
        return val instanceof Short ? (Short) val : def;
    }

    @Override
    public boolean isShort(String path) {
        Object val = get(path);
        return val instanceof Short;
    }

    @Override
    public BigInteger getBigInteger(String path) {
        Object val = get(path);
        return val instanceof BigInteger ? (BigInteger) val : null;
    }

    @Override
    public BigInteger getBigInteger(String path, BigInteger def) {
        Object val = get(path);
        return val instanceof BigInteger ? (BigInteger) val : def;
    }

    @Override
    public boolean isBigInteger(String path) {
        Object val = get(path);
        return val instanceof BigInteger;
    }

    @Override
    public BigDecimal getBigDecimal(String path) {
        Object val = get(path);
        return val instanceof BigDecimal ? (BigDecimal) val : null;
    }

    @Override
    public BigDecimal getBigDecimal(String path, BigDecimal def) {
        Object val = get(path);
        return val instanceof BigDecimal ? (BigDecimal) val : def;
    }

    @Override
    public boolean isBigDecimal(String path) {
        Object val = get(path);
        return val instanceof BigDecimal;
    }

    @Override
    public List<?> getList(String path) {
        Object val = get(path);
        return val instanceof List<?> ? (List<?>) val : null;
    }

    @Override
    public List<?> getList(String path, List<?> def) {
        List<?> val = getList(path);
        return val == null ? def : val;
    }

    @Override
    public boolean isList(String path) {
        List<?> val = getList(path);
        return val != null;
    }

    @Override
    public List<Section> getSectionList() {
        final List<Section> result = new ArrayList<>();
        for (String key : getKeys(false)) {
            Section section = getSection(key);
            if (section != null) {
                result.add(section);
            }
        }
        return result;
    }

    @Override
    public List<Section> getSectionList(String path) {
        List<Section> result = new ArrayList<>();
        Section val = getSection(path);
        if (val != null) {
            for (String key : val.getKeys(false)) {
                Section section = val.getSection(key);
                if (section != null) {
                    result.add(section);
                }
            }
        }
        return result;
    }

    @Override
    public List<Object> getObjectList(String path) {
        List<Object> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        result.addAll(val);
        return result;
    }

    @Override
    public List<String> getStringList(String path) {
        List<String> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof String || isPrimitive(o)) {
                result.add(String.valueOf(o));
            }
        }
        return result;
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        List<Integer> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add(number.intValue());
            } else if (o instanceof String string) {
                try {
                    result.add(Integer.parseInt(string));
                } catch (NumberFormatException ignore) {
                }
            } else if (o instanceof Character character) {
                try {
                    result.add((int) character);
                } catch (ClassCastException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public List<Number> getNumberList(String path) {
        List<Number> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add(number);
            }
        }
        return result;
    }

    @Override
    public List<Boolean> getBooleanList(String path) {
        List<Boolean> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Boolean) {
                result.add((Boolean) o);
            } else if (o instanceof String) {
                if (Boolean.TRUE.toString().equals(o)) {
                    result.add(true);
                } else if (Boolean.FALSE.toString().equals(o)) {
                    result.add(false);
                }
            }
        }
        return result;
    }

    @Override
    public List<Double> getDoubleList(String path) {
        List<Double> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add(number.doubleValue());
            } else if (o instanceof String string) {
                try {
                    result.add(Double.parseDouble(string));
                } catch (NumberFormatException ignore) {
                }
            } else if (o instanceof Character character) {
                try {
                    result.add((double) character);
                } catch (ClassCastException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public List<Float> getFloatList(String path) {
        List<Float> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add(number.floatValue());
            } else if (o instanceof String string) {
                try {
                    result.add(Float.parseFloat(string));
                } catch (NumberFormatException ignore) {
                }
            } else if (o instanceof Character character) {
                try {
                    result.add((float) character);
                } catch (ClassCastException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public List<Long> getLongList(String path) {
        List<Long> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add(number.longValue());
            } else if (o instanceof String string) {
                try {
                    result.add(Long.parseLong(string));
                } catch (NumberFormatException ignore) {
                }
            } else if (o instanceof Character character) {
                try {
                    result.add((long) character);
                } catch (ClassCastException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public List<Byte> getByteList(String path) {
        List<Byte> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add(number.byteValue());
            } else if (o instanceof String string) {
                try {
                    result.add(Byte.parseByte(string));
                } catch (NumberFormatException ignore) {
                }
            } else if (o instanceof Character character) {
                try {
                    result.add((byte) character.charValue());
                } catch (ClassCastException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public List<Character> getCharacterList(String path) {
        List<Character> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Character) {
                result.add((Character) o);
            } else if (o instanceof String string) {
                if (string.length() == 1)
                    result.add(string.charAt(0));
            } else if (o instanceof Number) {
                result.add((char) ((Number) o).intValue());
            }
        }
        return result;
    }

    @Override
    public List<Short> getShortList(String path) {
        List<Short> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add(number.shortValue());
            } else if (o instanceof String string) {
                try {
                    result.add(Short.parseShort(string));
                } catch (NumberFormatException ignore) {
                }
            } else if (o instanceof Character character) {
                try {
                    result.add((short) character.charValue());
                } catch (ClassCastException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public List<BigInteger> getBigIntegerList(String path) {
        List<BigInteger> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add((BigInteger) number);
            } else if (o instanceof String string) {
                try {
                    result.add(BigInteger.valueOf(Long.parseLong(string)));
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public List<BigDecimal> getBigDecimalList(String path) {
        List<BigDecimal> result = new ArrayList<>();
        List<?> val = getList(path);
        if (val == null)
            return result;
        for (Object o : val) {
            if (o instanceof Number number) {
                result.add((BigDecimal) number);
            } else if (o instanceof String string) {
                try {
                    result.add(BigDecimal.valueOf(Long.parseLong(string)));
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public Section[] getSectionsArray() {
        return getSectionList().toArray(new Section[]{});
    }

    @Override
    public Section[] getSectionArray(String path) {
        return getSectionList(path).toArray(new Section[]{});
    }

    @Override
    public Object[] getObjectArray(String path) {
        return getObjectList(path).toArray(new Object[]{});
    }

    @Override
    public String[] getStringArray(String path) {
        return getStringList(path).toArray(new String[]{});
    }

    @Override
    public Integer[] getIntegerArray(String path) {
        return getIntegerList(path).toArray(new Integer[]{});
    }

    @Override
    public Number[] getNumberArray(String path) {
        return getNumberList(path).toArray(new Number[]{});
    }

    @Override
    public Boolean[] getBooleanArray(String path) {
        return getBooleanList(path).toArray(new Boolean[]{});
    }

    @Override
    public Double[] getDoubleArray(String path) {
        return getDoubleList(path).toArray(new Double[]{});
    }

    @Override
    public Float[] getFloatArray(String path) {
        return getFloatList(path).toArray(new Float[]{});
    }

    @Override
    public Long[] getLongArray(String path) {
        return getLongList(path).toArray(new Long[]{});
    }

    @Override
    public Byte[] getByteArray(String path) {
        return getByteList(path).toArray(new Byte[]{});
    }

    @Override
    public Character[] getCharacterArray(String path) {
        return getCharacterList(path).toArray(new Character[]{});
    }

    @Override
    public Short[] getShortArray(String path) {
        return getShortList(path).toArray(new Short[]{});
    }

    @Override
    public BigInteger[] getBigIntegerArray(String path) {
        return getBigIntegerList(path).toArray(new BigInteger[]{});
    }

    @Override
    public BigDecimal[] getBigDecimalArray(String path) {
        return getBigDecimalList(path).toArray(new BigDecimal[]{});
    }

    @Override
    public int[] getPrimitiveIntegerArray(String path) {
        Object[] val = getObjectArray(path);
        Object[] def = val != null ? val : null instanceof Object[] ? (Object[]) null : null;
        if (def == null)
            return new int[]{};
        int[] result = new int[def.length];
        for (int i = 0; i < result.length; i++) {
            Object o = def[i];
            if (o instanceof Number) {
                result[i] = ((Number) o).intValue();
            } else if (o instanceof String string) {
                try {
                    result[i] = Integer.parseInt(string);
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public boolean[] getPrimitiveBooleanArray(String path) {
        Object[] val = getObjectArray(path);
        Object[] def = val != null ? val : null instanceof Object[] ? (Object[]) null : null;
        if (def == null)
            return new boolean[]{};
        boolean[] result = new boolean[def.length];
        for (int i = 0; i < result.length; i++) {
            Object o = def[i];
            if (o instanceof Boolean) {
                result[i] = (Boolean) o;
            } else if (o instanceof String string) {
                if (equalsBoolean(string)) {
                    result[i] = Boolean.parseBoolean(string);
                }
            }
        }
        return result;
    }

    @Override
    public double[] getPrimitiveDoubleArray(String path) {
        Object[] val = getObjectArray(path);
        Object[] def = val != null ? val : null instanceof Object[] ? (Object[]) null : null;
        if (def == null)
            return new double[]{};
        double[] result = new double[def.length];
        for (int i = 0; i < result.length; i++) {
            Object o = def[i];
            if (o instanceof Number) {
                result[i] = ((Number) o).doubleValue();
            } else if (o instanceof String string) {
                try {
                    result[i] = Double.parseDouble(string);
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public float[] getPrimitiveFloatArray(String path) {
        Object[] val = getObjectArray(path);
        Object[] def = val != null ? val : null instanceof Object[] ? (Object[]) null : null;
        if (def == null)
            return new float[]{};
        float[] result = new float[def.length];
        for (int i = 0; i < result.length; i++) {
            Object o = def[i];
            if (o instanceof Number) {
                result[i] = ((Number) o).floatValue();
            } else if (o instanceof String string) {
                try {
                    result[i] = Float.parseFloat(string);
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public long[] getPrimitiveLongArray(String path) {
        Object[] val = getObjectArray(path);
        Object[] def = val != null ? val : null instanceof Object[] ? (Object[]) null : null;
        if (def == null)
            return new long[]{};
        long[] result = new long[def.length];
        for (int i = 0; i < result.length; i++) {
            Object o = def[i];
            if (o instanceof Number) {
                result[i] = ((Number) o).longValue();
            } else if (o instanceof String string) {
                try {
                    result[i] = Long.parseLong(string);
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public byte[] getPrimitiveByteArray(String path) {
        Object[] val = getObjectArray(path);
        Object[] def = val != null ? val : null instanceof Object[] ? (Object[]) null : null;
        if (def == null)
            return new byte[]{};
        byte[] result = new byte[def.length];
        for (int i = 0; i < result.length; i++) {
            Object o = def[i];
            if (o instanceof Number) {
                result[i] = ((Number) o).byteValue();
            } else if (o instanceof String string) {
                try {
                    result[i] = Byte.parseByte(string);
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public char[] getPrimitiveCharacterArray(String path) {
        Object[] val = getObjectArray(path);
        Object[] def = val != null ? val : null instanceof Object[] ? (Object[]) null : null;
        if (def == null)
            return new char[]{};
        char[] result = new char[def.length];
        for (int i = 0; i < result.length; i++) {
            Object o = def[i];
            if (o instanceof Character) {
                result[i] = (Character) o;
            } else if (o instanceof String string) {
                if (string.length() == 1) {
                    result[i] = string.charAt(0);
                }
            }
        }
        return result;
    }

    @Override
    public short[] getPrimitiveShortArray(String path) {
        Object[] val = getObjectArray(path);
        Object[] def = val != null ? val : null instanceof Object[] ? (Object[]) null : null;
        if (def == null)
            return new short[]{};
        short[] result = new short[def.length];
        for (int i = 0; i < result.length; i++) {
            Object o = def[i];
            if (o instanceof Number) {
                result[i] = ((Number) o).shortValue();
            } else if (o instanceof String string) {
                try {
                    result[i] = Short.parseShort(string);
                } catch (NumberFormatException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public DataType getType(String path) {
        Object val = get(path);
        return DataType.getType(val.getClass());
    }

    /**
     * Creates a full path to the given {@link Section} from its
     * root {@link Configuration}.
     *
     * @param section Section to create a path for.
     * @param key     Name of the specified section.
     * @return Full path of the section from its root.
     */
    public static String createPath(Section section, String key) {
        return createRelativePath(section, key, section.getRoot());
    }

    /**
     * Creates a relative path to the given {@link Section} from
     * the given relative section.
     *
     * @param section    Section to create a path for.
     * @param key        Name of the specified section.
     * @param relativeTo Section to create the path relative to.
     * @return Full path of the section from its root.
     */
    public static String createRelativePath(Section section, @Nullable String key, Section relativeTo) {
        Configuration root = section.getRoot();
        char separator = root.options().getPathSeparator();
        StringBuilder builder = new StringBuilder();
        for (Section parent = section; (parent != null) && (parent != relativeTo); parent = parent.getParent()) {
            if (!builder.isEmpty()) {
                builder.insert(0, separator);
            }
            builder.insert(0, parent.getName());
        }
        if (!Validator.isNull(key, null)) {
            if (!builder.isEmpty()) {
                builder.append(separator);
            }
            builder.append(key);
        }
        return builder.toString();
    }

    protected boolean isPrimitive(Object input) {
        return input instanceof Integer || input instanceof Boolean ||
                input instanceof Character || input instanceof Byte ||
                input instanceof Short || input instanceof Double ||
                input instanceof Long || input instanceof Float;
    }

    protected boolean equalsBoolean(String input) {
        return input.equalsIgnoreCase("true")
                || input.equalsIgnoreCase("false");
    }


    @Override
    public String toString() {
        return map.toString();
    }

    @Override
    public Map<String, Object> asMap() {
        return map;
    }
}