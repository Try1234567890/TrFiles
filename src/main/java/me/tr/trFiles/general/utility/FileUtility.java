package me.tr.trFiles.general.utility;

import me.tr.trFiles.configuration.file.json.JsonConfiguration;
import me.tr.trFiles.configuration.file.yaml.YamlConfiguration;

import java.io.File;
import java.util.Arrays;

/**
 * Utility file class
 */
public class FileUtility {
    /**
     * Check if the file name has an extension.
     * <p>
     * If the file name doesn't contain {@code .} return {@code false},
     * else if getting char at index of last {@code .} found in string + 1
     * doesn't throw the {@link IndexOutOfBoundsException} return {@code true}, otherwise {@code false}.
     *
     * @param name File name to check for extension from.
     * @return {@code true} if the file has an extension, otherwise {@code false}
     * @see #hasFileExtension(File)
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean hasFileExtension(String name) {
        int index = name.lastIndexOf(".");
        if (index != -1) {
            try {
                name.charAt(index + 1);
                return true;
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
        return false;
    }

    /**
     * Check if the file has an extension delegating to {@link #hasFileExtension(String)}
     * and using as String parameter the file name.
     * <p>
     *
     * @param file File to check for extension from.
     * @return {@code true} if the file has an extension, otherwise {@code false}
     * @see #hasFileExtension(String)
     */
    public static boolean hasFileExtension(File file) {
        return hasFileExtension(file.getName());
    }

    /**
     * Retrieve file name without extension by end reading name
     * until index calculated by this formula {@code (name length - extension length) - 1}
     * is reached.
     * <p>
     * <b>{@code If the file doesn't contain an extension, return an empty string.}</b>
     * </p>
     *
     * <blockquote><pre>
     * getFileNameWithoutExtension("config.yml") returns "config"
     * getFileNameWithoutExtension("messages.json") returns "messages"
     * getFileNameWithoutExtension("graphics.") returns ""
     * </pre></blockquote>
     *
     * @param name File name to get extension from.
     * @return File name without {@code .extension}
     * @see #getFileNameWithoutExtension(File)
     */
    public static String getFileNameWithoutExtension(String name) {
        if (!hasFileExtension(name))
            return "";
        return name.substring(0, (name.length() - getExtension(name).length()) - 1);
    }

    /**
     * Retrieve file name without extension delegating to {@link #getFileNameWithoutExtension(String)}
     * using file name as String parameter.
     *
     * @param file File to get name from.
     * @return File name without {@code .extension}
     * @see #getFileNameWithoutExtension(String)
     */
    public static String getFileNameWithoutExtension(File file) {
        return getFileNameWithoutExtension(file.getName());
    }

    /**
     * Retrieve the extension file by splitting the provided
     * fileName by {@code .} and getting last part.
     *
     * @param fileName File name to get extension.
     * @return If a supported extension is found, return extension (without {@code .} at start), otherwise an empty string.
     */
    public static String getExtension(String fileName) {
        if (!hasFileExtension(fileName))
            return "";
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index + 1);
    }

    /**
     * Retrieve the extension file by getting file name
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
        return Arrays.stream(YamlConfiguration.FILE_EXTENSIONS).anyMatch(getExtension(fileName)::equalsIgnoreCase);
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
        return Arrays.stream(JsonConfiguration.FILE_EXTENSIONS).anyMatch(getExtension(fileName)::equalsIgnoreCase);
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

    /**
     * Check if the file extension is supported by
     * using the file as parameter for {@link #isYaml(File)} &
     * {@link #isJson(File)} and at last one return true.
     *
     * @param file File to check if extension is supported for.
     * @return {@code true} if extension is supported, otherwise {@code false}.
     */
    public static boolean isSupportedExtension(File file) {
        return isYaml(file) || isJson(file);
    }

    /**
     * Check if the file name extension is supported by
     * using the file name as parameter for {@link #isYaml(String)} &
     * {@link #isJson(String)} and at last one return true.
     *
     * @param name File name to check if extension is supported for.
     * @return {@code true} if extension is supported, otherwise {@code false}.
     */
    public static boolean isSupportedExtension(String name) {
        return isYaml(name) || isJson(name);
    }

}
