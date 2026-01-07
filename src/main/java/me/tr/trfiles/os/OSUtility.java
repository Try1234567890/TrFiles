package me.tr.trfiles.os;

import me.tr.trfiles.Validator;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class OSUtility {
    public static final String OS_NAME = System.getProperty("os.name");
    public static final OSType OS = OSType.fromString(OS_NAME);

    /**
     * Check if the current OS (Operative System) is {@code Windows}.
     * <p>
     * This method simply checks if {@link #OS_NAME} contains {@code Win}
     * in it.
     *
     * @return true if {@link #OS_NAME} contains {@code Win}, otherwise false.
     * @throws NullPointerException if {@link #OS_NAME} is null or empty.
     * @see #isMac()
     * @see #isLinux()
     */
    public static boolean isWin() {
        Validator.isNull(OS_NAME, "The OS Name property is null.");
        return OS_NAME.contains("Win");
    }

    /**
     * Check if the current OS (Operative System) is {@code MacOS}.
     * <p>
     * This method simply checks if {@link #OS_NAME} contains {@code Mac}
     * in it.
     *
     * @return true if {@link #OS_NAME} contains {@code Mac}, otherwise false.
     * @throws NullPointerException if {@link #OS_NAME} is null or empty.
     * @see #isWin()
     * @see #isLinux()
     */
    public static boolean isMac() {
        Validator.isNull(OS_NAME, "The OS Name property is null.");
        return OS_NAME.contains("Mac");
    }

    /**
     * Check if the current OS (Operative System) is {@code Linux}.
     * <p>
     * This method simply checks if {@link #OS_NAME} contains {@code Linux}
     * in it.
     *
     * @return true if {@link #OS_NAME} contains {@code Linux}, otherwise false.
     * @throws NullPointerException if {@link #OS_NAME} is null or empty.
     * @see #isMac()
     * @see #isWin()
     */
    public static boolean isLinux() {
        Validator.isNull(OS_NAME, "The OS Name property is null.");
        return OS_NAME.contains("Linux");
    }

    /**
     * Replace all backslash ("\\") to slash ("/")
     * <p>
     * This method simply call the {@link String#replace(char, char)}
     * from the provided path.
     *
     * @param path The file path to replace backslash from.
     * @return A new String instance with backslash ("\\") replace to slash ("/").
     * @throws NullPointerException if the provided path is null or empty.
     */
    public static String toSlash(String path) {
        Validator.isNull(path, "The path property is null.");
        return path.replace('\\', '/');
    }

    /**
     * Replace all slash ("/") to backslash ("\\")
     * <p>
     * This method simply call the {@link String#replace(char, char)}
     * from the provided path.
     *
     * @param path The file path to replace slash from.
     * @return A new String instance with slash ("/") replace to slash ("\\").
     * @throws NullPointerException if the provided path is null or empty.
     */
    public static String toBackSlash(String path) {
        Validator.isNull(path, "The path property is null.");
        return path.replace('/', '\\');
    }


    /**
     * Validates and normalizes a given file path according to the current operating system.
     * <p>
     * The method performs the following operations:
     * <ul>
     *     <li>Checks if the input path is null and throws an exception if it is.</li>
     *     <li>Converts all slashes in the path to backslashes.</li>
     *     <li>Removes or processes illegal characters based on the operating system.</li>
     *     <li>Handles special cases for URLs (e.g., "file:" prefix) and UNC paths.</li>
     *     <li>Converts backslashes to forward slashes for URLs on non-Windows systems.</li>
     * </ul>
     *
     * @param path the file path to validate and normalize; must not be null
     * @return a normalized and validated path string suitable for the current operating system
     * @throws NullPointerException if the path is null
     */
    public static String validatePath(String path) {
        Validator.isNull(path, "The path property is null.");
        if (OS == null) return path;

        path = toBackSlash(path);
        Set<Character> illegalChars = Arrays.stream(OS.getIllegalChars()).collect(Collectors.toSet());

        StringBuilder builder = new StringBuilder();
        boolean isURL = false;
        int len = path.length();
        int diskLetterIndex = 0;

        for (int i = 0; i < len; i++) {
            char ch = path.charAt(i);

            if (illegalChars.contains(ch)) {
                if (ch == ':' && !builder.isEmpty()) {
                    if (i == diskLetterIndex + 1) {
                        builder.append(':');
                    } else if (i == 4 && builder.substring(0, 4).equalsIgnoreCase("file")) {
                        builder.append(':');
                        diskLetterIndex = 8;
                        isURL = true;
                    }
                } else if (i == 2 && builder.substring(0, 2).equals("\\\\") && ch == '?') {
                    if (isWin()) {
                        builder.append('?');
                        diskLetterIndex = 4;
                    } else if (i + 1 < len && path.charAt(i + 1) == '\\') {
                        builder.setLength(0);
                    }
                }
                continue;
            }

            if (ch == '\\' && isURL) {
                builder.append('/');
                continue;
            }

            builder.append(ch);
        }

        return isWin() ? builder.toString() : toSlash(builder.toString());
    }

}
