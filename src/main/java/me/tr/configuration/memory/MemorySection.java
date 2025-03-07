package me.tr.configuration.memory;

import me.tr.configuration.Configuration;
import me.tr.configuration.Section;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class MemorySection implements Section {
    protected final Map<String, Object> map = new LinkedHashMap<>();
    private final Configuration root;
    private final Section parent;
    private final String currentPath;
    public final String name;

    protected MemorySection(Section parent, String path) {
        if (parent == null || path == null) {
            throw new IllegalArgumentException("Cannot construct a root MemorySection when parent or path is null");
        }
        this.currentPath = path;
        this.parent = parent;
        this.name = createPath(parent, path);
        this.root = parent.getRoot();
    }


    protected MemorySection() {
        if (!(this instanceof Configuration)) {
            throw new IllegalStateException("Cannot construct a root MemorySection when not a Configuration");
        }
        this.currentPath = "";
        this.name = "";
        this.parent = null;
        this.root = (Configuration) this;
    }


    @Override
    public String getCurrentPath() {
        return currentPath;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Configuration getRoot() {
        return root;
    }

    @Override
    public Section getParent() {
        return parent;
    }


    @Override
    public List<String> getKeys(boolean recursive) {
        List<String> output = new ArrayList<>();
        System.out.println(map);
        for (String key : map.keySet()) {
            output.add(key);
            Object keyValue = map.get(key);
            if (keyValue instanceof Section section && recursive) {
                output.addAll(section.getKeys(true));
            }
        }
        System.out.println(output);
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
        return get(path) != null;
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
        final char separator = root.options().pathSeparator();
        int separatorIndex = -1, separatorLength;
        Section section = this;
        while ((separatorIndex = path.indexOf(separator, separatorLength = separatorIndex + 1)) != -1) {
            section = section.getSection(path.substring(separatorLength, separatorIndex));
            if (section == null) {
                return null;
            }
        }
        String key = path.substring(separatorLength);
        if (section == this) {
            return map.get(key);
        }
        return section.get(key);
    }

    @Override
    public @Nullable Section getSection(String path) {
        Object val = get(path);
        if (val instanceof Section) {
            return (Section) val;
        }
        return null;
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
        final char separator = root.options().pathSeparator();
        int separatorIndex = -1, separatorLength;
        Section section = this;
        while ((separatorIndex = path.indexOf(separator, separatorLength = separatorIndex + 1)) != -1) {
            section = section.getSection(path.substring(separatorLength, separatorIndex));
            if (section == null) {
                return;
            }
        }
        String key = path.substring(separatorLength);
        if (section == this) {
            map.put(key, value);
        } else {
            section.set(key, value);
        }
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
        final char separator = root.options().pathSeparator();
        Section section = this;
        int separatorIndex = -1, lengthBeforeSeparator;
        while ((separatorIndex = path.indexOf(separator, lengthBeforeSeparator = separatorIndex + 1)) != -1) {
            String node = path.substring(lengthBeforeSeparator, separatorIndex);
            Section subSection = section.getSection(node);
            if (subSection == null) {
                section = section.createSection(node);
            } else {
                section = subSection;
            }
        }

        String key = path.substring(lengthBeforeSeparator);
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
        return val instanceof String ? ((String) val) : null;
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
    public int getInt(String path) {
        Object val = get(path);
        return val instanceof Number number ? number.intValue() : -1;
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
    public List<String> getStringList(String path) {
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<String>();
        for (Object o : val) {
            if (o instanceof String || isPrimitiveWrapper(o)) {
                result.add(String.valueOf(o));
            }
        }
        return result;
    }

    @Override
    public List<Integer> getIntegerList(String path) {
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
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
    public List<Boolean> getBooleanList(String path) {
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<Boolean> result = new ArrayList<>();
        for (Object o : val) {
            if (o instanceof Boolean) {
                result.add((Boolean) o);
            } else if (o instanceof String string) {
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
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<Double> result = new ArrayList<>();
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
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<Float> result = new ArrayList<>();
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
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<Long> result = new ArrayList<>();
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
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<Byte> result = new ArrayList<>();
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
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<Character> result = new ArrayList<>();
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
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<Short> result = new ArrayList<>();
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
                    result.add((short) ((Character) o).charValue());
                } catch (ClassCastException ignore) {
                }
            }
        }
        return result;
    }

    @Override
    public List<BigInteger> getBigIntegerList(String path) {
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<BigInteger> result = new ArrayList<>();
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
        List<?> val = getList(path);
        if (val == null) {
            return new ArrayList<>();
        }
        List<BigDecimal> result = new ArrayList<>();
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
    public boolean isSection(String path) {
        Object val = get(path);
        return val instanceof Section;
    }

    @Override
    public void addDefault(String path, Object value) {
        Configuration root = getRoot();
        if (root == null) {
            throw new IllegalStateException("Cannot add default without root");
        }
        root.addDefault(createPath(this, path), value);
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
    public static String createRelativePath(Section section, String key, Section relativeTo) {
        Configuration root = section.getRoot();
        char separator = root.options().pathSeparator();
        StringBuilder builder = new StringBuilder();
        for (Section parent = section; (parent != null) && (parent != relativeTo); parent = parent.getParent()) {
            if (!builder.isEmpty()) {
                builder.insert(0, separator);
            }
            builder.insert(0, parent.getName());
        }
        if (key != null && !key.isEmpty()) {
            if (!builder.isEmpty()) {
                builder.append(separator);
            }
            builder.append(key);
        }
        return builder.toString();
    }

    protected boolean isPrimitiveWrapper(Object input) {
        return input instanceof Integer || input instanceof Boolean ||
                input instanceof Character || input instanceof Byte ||
                input instanceof Short || input instanceof Double ||
                input instanceof Long || input instanceof Float;
    }


    @Override
    public String toString() {
        return map.toString();
    }
}