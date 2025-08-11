package me.tr.trFiles.general.utility;

import java.io.File;
import java.nio.file.Path;

/**
 * Utility file class for files
 */
public class FileUtility {


    /**
     * Get only the file name from its String path.
     *
     * @param path The file path as String
     * @return Only the file name.
     */
    public static String getFileName(String path) {
        path = path.replace('\\', '/');
        int index = path.lastIndexOf('/');
        return path.substring(index != -1 ? (index + 1) : 0);
    }

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
     * Check if the file has an extension delegating to {@link #hasFileExtension(String)}
     * and using as String parameter the file name.
     * <p>
     *
     * @param file File to check for extension from.
     * @return {@code true} if the file has an extension, otherwise {@code false}
     * @see #hasFileExtension(String)
     */
    public static boolean hasFileExtension(Path file) {
        return hasFileExtension(file.toFile());
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
     * Retrieve file name without extension delegating to {@link #getFileNameWithoutExtension(String)}
     * using file name as String parameter.
     *
     * @param file File to get name from.
     * @return File name without {@code .extension}
     * @see #getFileNameWithoutExtension(String)
     */
    public static String getFileNameWithoutExtension(Path file) {
        return getFileNameWithoutExtension(file.toFile());
    }

    /**
     * Retrieve the extension file by splitting the provided
     * fileName by {@code .} and getting last part.
     *
     * @param fileName File name to get extension.
     * @return If a supported extension is found, return extension (without {@code .} at start),
     * otherwise an empty string.
     */
    public static String getExtension(String fileName) {
        if (!hasFileExtension(fileName))
            return "";
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index + 1);
    }

    /**
     * Retrieve the extension file by splitting the provided
     * fileName by {@code .} and getting last part.
     *
     * @param file TrFile instance to get extension.
     * @return If a supported extension is found, return extension (without {@code .} at start),
     * otherwise an empty string.
     */
    public static String getExtension(TrFile file) {
        return getExtension(file.getFile().getName());
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
     * Retrieve the extension file by getting file name
     * e delegate to {@link #getExtension(String)}
     *
     * @param file File to get extension.
     * @return Extension got (without {@code .} at start)
     */
    public static String getExtension(Path file) {
        return getExtension(file.toFile());
    }


    public static boolean isJar(String file) {
        return hasFileExtension(file) && getExtension(file).equalsIgnoreCase("jar");
    }


    public static boolean isJar(File file) {
        return file.isFile() && isJar(file.getName());
    }

    public static boolean isJar(Path path) {
        return isJar(path.toFile());
    }

    public static boolean isZip(String file) {
        return hasFileExtension(file) && getExtension(file).equalsIgnoreCase("zip");
    }


    public static boolean isZip(File file) {
        return file.isFile() && isZip(file.getName());
    }

    public static boolean isZip(Path path) {
        return isZip(path.toFile());
    }
}
