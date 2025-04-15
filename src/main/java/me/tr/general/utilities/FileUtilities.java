package me.tr.general.utilities;

import java.io.File;

/**
 * Utility file class
 */
public class FileUtilities {

    /**
     * Get the extension file by splitting the provided
     * fileName by {@code .} and getting last part.
     *
     * @param fileName File name to get extension.
     * @return Extension got (without {@code .} at start)
     */
    public static String getExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        return parts[parts.length - 1];
    }

    /**
     * Get the extension file by getting file name
     * e delegate to {@link #getExtension(String)}
     *
     * @param file File to get extension.
     * @return Extension got (without {@code .} at start)
     */
    public static String getExtension(File file) {
        return getExtension(file.getName());
    }

    /**
     * Check if the file is a YAML by getting extension with {@link #getExtension(String)}
     * and check if it equals to {@code yaml} or {@code yml}.
     *
     * @param fileName File name to check.
     * @return {@code true} if the file is a YAML file, otherwise {@code false}.
     */
    public static boolean isYaml(String fileName) {
        return getExtension(fileName).equalsIgnoreCase("yaml")
                || getExtension(fileName).equalsIgnoreCase("yml");
    }

    /**
     * Check if the file is a YAML by getting file name
     * and delegate to {@link #isYaml(String)}
     *
     * @param file File to check.
     * @return {@code true} if the file is a YAML file, otherwise {@code false}.
     */
    public static boolean isYaml(File file) {
        return isYaml(file.getName());
    }

    /**
     * Check if the file is a JSON by getting extension with {@link #getExtension(String)}
     * and check if it equals to {@code json}.
     *
     * @param fileName File name to check.
     * @return {@code true} if the file is a JSON file, otherwise {@code false}.
     */
    public static boolean isJson(String fileName) {
        return getExtension(fileName).equalsIgnoreCase("json");
    }

    /**
     * Check if the file is a JSON by getting file name
     * and delegate to {@link #isJson(String)}
     *
     * @param file File to check.
     * @return {@code true} if the file is a JSON file, otherwise {@code false}.
     */
    public static boolean isJson(File file) {
        return isJson(file.getName());
    }

    /**
     * Check if the file is a JAR by getting extension with {@link #getExtension(String)}
     * and check if it equals to {@code jar}.
     *
     * @param fileName File name to check.
     * @return {@code true} if the file is a JAR file, otherwise {@code false}.
     */
    public static boolean isJar(String fileName) {
        return getExtension(fileName).equalsIgnoreCase("jar");
    }

    /**
     * Check if the file is a JAR by getting file name
     * and delegate to {@link #isJar(String)}.
     *
     * @param file File to check.
     * @return {@code true} if the file is a JAR file, otherwise {@code false}.
     */
    public static boolean isJar(File file) {
        return isJar(file.getName());
    }

}
