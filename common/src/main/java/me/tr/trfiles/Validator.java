package me.tr.trfiles;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * This class contains methods to check some conditions
 */
public class Validator {
    private Validator() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class.");
    }

    /**
     * Check if the object is null.
     * <p>
     * If the object is {@code null} and the msg is {@code not null} a new {@link NullPointerException}
     * will be thrown; If the msg is null, no exception will be thrown and object null-state will be return.
     * <p>
     * If the object is an instance of {@link String} and the trimmed String is {@code empty},
     * the object will be considered {@code null}.
     * <p>
     * If the object is an instance of {@link Number} and the double value is minus {@code 0.0},
     * the object will be considered {@code null}.
     * <p>
     * If the object is an instance of {@link Collection} and the collections is {@code empty},
     * the object will be considered {@code null}.
     * <p>
     * If the object is an instance of {@link Array} and the array is {@code empty},
     * the object will be considered {@code null}.
     * <p>
     * If the object is {@code not null} the method simply return {@code false}.
     *
     * @param object The object to check.
     * @param msg    The msg to thrown with the {@link NullPointerException}; If msg is null no exception will be thrown.
     * @return {@code true} if the object is null, otherwise {@code false};
     * If object is {@code null} and the msg is {@code not null} the exception will be thrown.
     */
    public static boolean isNull(Object object, String msg) {
        boolean isNull = object == null
                || (object instanceof String str && str.trim().isEmpty())
                || (object instanceof Number num && num.doubleValue() < 0.0)
                || (object instanceof Collection<?> coll && coll.isEmpty())
                || (object instanceof Map<?, ?> map && map.isEmpty())
                || (object.getClass().isArray() && Array.getLength(object) < 0);
        if (isNull && msg != null) {
            throw new NullPointerException(msg);
        }
        return isNull;
    }

    /**
     * Check if the object is null.
     * <p>
     * This method is equivalent to call {@link #isNull(Object, String)}
     * with msg set to {@code null}.
     *
     * @param object The object to check.
     * @return {@code true} if the object is null, otherwise {@code false}.
     */
    public static boolean isNull(Object object) {
        return isNull(object, null);
    }

    /**
     * Check if the condition is {@code false} and thrown a new {@link IllegalArgumentException}.
     * <p>
     * If the condition is {@code false} and the msg is {@code not null} a new {@link IllegalArgumentException}
     * will be thrown;
     * <p>
     * If the msg is {@code null} or the condition is {@code true} the method simply return the condition result.
     *
     * @param condition The condition to eval.
     * @param msg       The msg to thrown with the {@link NullPointerException}; If msg is null no exception will be thrown.
     * @return {@code true} if the condition result is {@code true}, otherwise {@code false};
     * If the condition result is {@code false} and msg is {@code not null} the exception will be thrown.
     */
    public static boolean checkIf(boolean condition, String msg) {
        if (!condition && msg != null) {
            throw new IllegalArgumentException(msg);
        }
        return condition;
    }

    /**
     * Check if the condition is {@code false} and run the provided Runnable.
     * <p>
     * If the conditions {@code false} and the runnable is {@code not null}, the provided runnable
     * will be executed;
     * <p>
     * If the runnable is {@code null} or condition is {@code true}, method simply returns the condition result.
     *
     * @param condition The condition to eval.
     * @param run       The runnable to execute; If run is null the condition will be returned.
     * @return {@code true} if the condition result is {@code true}, otherwise {@code false}.
     */
    public static boolean checkIf(boolean condition, Runnable run) {
        if (!condition && run != null) {
            run.run();
        }
        return condition;
    }
}
