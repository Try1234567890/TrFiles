package me.tr.trFiles.general.utility;

/**
 * This class contains methods to check some conditions
 */
public class Validate {

    /**
     * Check if string param is null or empty.
     *
     * @param val String to check.
     * @return {@code true} if string is null or empty, otherwise false.
     */
    public static boolean isNull(String val) {
        return val == null || val.isEmpty();
    }

    /**
     * Check if the condition is respected,
     * if not throw new exception with the message provided.
     *
     * @param condition The condition to check for.
     * @param mess      Message to log if the exception is thrown
     * @return {@code true} If the result of condition is {@code true}, otherwise throw exception.
     * @throws IllegalArgumentException If result of condition is {@code false}.
     */
    public static boolean checkIf(boolean condition, String mess) {
        if (!condition) {
            throw new IllegalArgumentException(mess);
        }
        return true;
    }

    /**
     * Check if the condition is respected,
     * if not run the provided Runnable.
     *
     * @param condition The condition to check for.
     * @param run       Runnable to execute if condition is false.
     */
    public static void checkIf(boolean condition, Runnable run) {
        if (!condition) {
            run.run();
        }
    }

    /**
     * Check if the condition is respected,
     * if not throw new exception with the message provided.
     *
     * @param condition The condition to check for.
     * @param mess      Message to log if the exception is thrown
     * @return {@code true} If the result of condition is {@code true}, otherwise throw exception.
     * @throws NullPointerException If result of condition is {@code false}.
     */
    public static boolean notNull(boolean condition, String mess) {
        if (!condition) {
            throw new NullPointerException(mess);
        }
        return true;
    }
}
