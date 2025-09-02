package me.tr.trFiles.configuration.management;

import me.tr.trFiles.os.OSUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Arrays;

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


    /**
     * Get an instance of {@link File} from a String correctly
     * respecting directories and file name.
     *
     * @param path Path to file.
     * @return A new instance of {@link File}.
     * @see #getPathFromString(String)
     */
    public static File getFileFromString(String path) {
        return new File(OSUtility.removeIllegalChars(path));
    }

    /**
     * Got an instance of {@link File} from a String correctly
     * respecting directories and file name by delegating to {@link #getFileFromString(String)}
     * and calling {@link File#toPath()} method on the result.
     *
     * @param path Path to file.
     * @return A new instance of {@link File}.
     * @see #getPathFromString(String)
     */
    public static Path getPathFromString(String path) {
        return getFileFromString(path).toPath();
    }

    /**
     * Get a formatted file path as String.
     *
     * @param file File to get the path from.
     * @return the formatted file path.
     */
    public static String getStringPathFromFile(File file) {
        return OSUtility.removeIllegalChars(file.toString()).replace('\\', '/');
    }

    /**
     * Get a formatted file path as String.
     *
     * @param path Path to get the path from.
     * @return the formatted file path.
     */
    public static String getStringPathFromPath(Path path) {
        return getStringPathFromFile(path.toFile());
    }

    public static boolean isJar(File file) {
        return file.isFile() && getExtension(file).equalsIgnoreCase("jar")
                && (hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x03, 0x04})
                || hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x05, 0x06})
                || hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x07, 0x08}));
    }

    public static boolean isZip(File file) {
        return file.isFile() && getExtension(file).equalsIgnoreCase("zip")
                && (hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x03, 0x04})
                || hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x05, 0x06})
                || hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x07, 0x08}));
    }

    public static boolean hasMagicNumber(File file, byte[] magicNumbers) {
        try (InputStream is = new FileInputStream(file)) {
            return hasMagicNumber(is, magicNumbers);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading file at " + file.getPath(), e);
        }
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean hasMagicNumber(InputStream is, byte[] magicNumbers) {
        byte[] header = new byte[magicNumbers.length];
        try (is) {
            is.read(header);
            return Arrays.equals(header, magicNumbers);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading provided input stream.", e);
        }
    }
}
