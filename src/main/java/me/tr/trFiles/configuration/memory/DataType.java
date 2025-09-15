package me.tr.trFiles.configuration.memory;

import me.tr.trFiles.configuration.Section;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;

public enum DataType {

    SECTION_ARRAY(Section[].class),
    STRING_ARRAY(String[].class),
    INTEGER_ARRAY(Integer[].class),
    NUMBER_ARRAY(Number[].class),
    BOOLEAN_ARRAY(Boolean[].class),
    DOUBLE_ARRAY(Double[].class),
    FLOAT_ARRAY(Float[].class),
    LONG_ARRAY(Long[].class),
    BYTE_ARRAY(Byte[].class),
    CHAR_ARRAY(Character[].class),
    SHORT_ARRAY(Short[].class),
    BIG_INTEGER_ARRAY(BigInteger[].class),
    BIG_DECIMAL_ARRAY(BigDecimal[].class),

    SECTION(Section.class),
    STRING(String.class),
    NUMBER(Number.class),
    BIG_INTEGER(BigInteger.class),
    BIG_DECIMAL(BigDecimal.class),
    INTEGER(Integer.class, int.class),
    BOOLEAN(Boolean.class, boolean.class),
    DOUBLE(Double.class, double.class),
    FLOAT(Float.class, float.class),
    LONG(Long.class, long.class),
    BYTE(Byte.class, byte.class),
    CHAR(Character.class, char.class),
    SHORT(Short.class, short.class),

    SECTION_LIST(ArrayList.class, DataType.SECTION),
    STRING_LIST(ArrayList.class, DataType.STRING),
    INTEGER_LIST(ArrayList.class, DataType.NUMBER),
    NUMBER_LIST(ArrayList.class, DataType.BIG_INTEGER),
    BOOLEAN_LIST(ArrayList.class, DataType.BIG_DECIMAL),
    DOUBLE_LIST(ArrayList.class, DataType.INTEGER),
    FLOAT_LIST(ArrayList.class, DataType.BOOLEAN),
    LONG_LIST(ArrayList.class, DataType.DOUBLE),
    BYTE_LIST(ArrayList.class, DataType.FLOAT),
    CHAR_LIST(ArrayList.class, DataType.LONG),
    SHORT_LIST(ArrayList.class, DataType.BYTE),
    BIG_INTEGER_LIST(ArrayList.class, DataType.CHAR),
    BIG_DECIMAL_LIST(ArrayList.class, DataType.SHORT),

    NULL(null),
    UNRECOGNIZED(null);;

    private final Class<?> reference;
    private Class<?> primitive;
    private DataType type;

    DataType(Class<?> reference) {
        this.reference = reference;
    }

    DataType(Class<?> reference, Class<?> primitive) {
        this.reference = reference;
        this.primitive = primitive;
    }

    DataType(Class<?> reference, DataType type) {
        this.reference = reference;
        this.type = type;
    }

    public static DataType getType(Class<?> clazz) {
        for (DataType dataType : DataType.values()) {
            if (dataType.is(clazz)) return dataType;
        }
        return UNRECOGNIZED;
    }

    public boolean is(Class<?> clazz) {
        if (clazz == null) return this == NULL;
        else if (clazz.isArray()) {
            return clazz.isAssignableFrom(reference);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                Collection<?> instance = (Collection<?>) constructor.newInstance();
                int accepted = 0;
                for (Object o : instance) {
                    if (type.is(o.getClass())) accepted++;
                }
                return accepted == instance.size();
            } catch (NoSuchMethodException |
                     SecurityException |
                     InstantiationException |
                     IllegalAccessException |
                     IllegalArgumentException |
                     InvocationTargetException ignored) {
                return false;
            }
        } else return reference != null && clazz.isAssignableFrom(this.reference) || (this.primitive != null && this.primitive.isAssignableFrom(clazz));
    }
}
