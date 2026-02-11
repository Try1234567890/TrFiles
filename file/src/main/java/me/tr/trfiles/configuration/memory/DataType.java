package me.tr.trfiles.configuration.memory;

import me.tr.trfiles.configuration.Section;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

public enum DataType {

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

    ARRAY((Class<?>) null),
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

    COLLECTION((DataType) null),
    SECTION_COLLECTION(DataType.SECTION),
    STRING_COLLECTION(DataType.STRING),
    INTEGER_COLLECTION(DataType.NUMBER),
    NUMBER_COLLECTION(DataType.BIG_INTEGER),
    BOOLEAN_COLLECTION(DataType.BIG_DECIMAL),
    DOUBLE_COLLECTION(DataType.INTEGER),
    FLOAT_COLLECTION(DataType.BOOLEAN),
    LONG_COLLECTION(DataType.DOUBLE),
    BYTE_COLLECTION(DataType.FLOAT),
    CHAR_COLLECTION(DataType.LONG),
    SHORT_COLLECTION(DataType.BYTE),
    BIG_INTEGER_COLLECTION(DataType.CHAR),
    BIG_DECIMAL_COLLECTION(DataType.SHORT),


    NULL((Class<?>) null),
    UNRECOGNIZED((Class<?>) null);

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

    DataType(DataType type) {
        this.reference = Collection.class;
        this.type = type;
    }

    /**
     * Get the DataType for the provided class.
     *
     * @param clazz The class to get the DataType for.
     * @return The DataType for the provided class.
     */
    public static DataType getType(Object obj) {
        for (DataType dataType : DataType.values()) {
            if (dataType.is(obj))
                return dataType;
        }
        return UNRECOGNIZED;
    }

    /**
     * Check if the provided class is of this DataType.
     *
     * @param obj The object to check.
     * @return {@code true} if the class is of this DataType, otherwise {@code false}.
     */
    public boolean is(Object obj) {
        if (obj == null) return this == NULL;

        if (obj instanceof Collection<?> coll) {
            Object elem = coll.isEmpty() ? null : coll.iterator().next();
            if (elem == null)
                return this == COLLECTION;
            else return this.type.is(elem);
        }

        if (obj.getClass().isArray()) {
            Object elem = Array.getLength(obj) > 0 ? Array.get(obj, 0) : null;
            if (elem == null)
                return this == ARRAY;
            else return this.reference.isAssignableFrom(elem.getClass());
        }

        if (obj.getClass().isPrimitive()) {
            return this.primitive.isAssignableFrom(obj.getClass());
        }

        return this == UNRECOGNIZED;
    }
}














