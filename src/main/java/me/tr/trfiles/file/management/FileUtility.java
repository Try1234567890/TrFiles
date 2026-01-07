package me.tr.trfiles.file.management;

import me.tr.trfiles.file.management.reader.file.FilesReader;
import me.tr.trfiles.os.OSUtility;

import java.io.*;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Utility file class for files
 */
public class FileUtility {

    private FileUtility() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class.");
    }

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
     * getFileNameWithoutExtension("graphics.") returns "graphics"
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
        return getExtensionWithPoint(fileName).substring(1);
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
     * Retrieve the extension file by splitting the provided
     * fileName by {@code .} and getting last part.
     *
     * @param fileName File name to get extension.
     * @return If a supported extension is found, return extension (with {@code .} at start),
     * otherwise an empty string.
     */
    public static String getExtensionWithPoint(String fileName) {
        if (!hasFileExtension(fileName))
            return "";
        int index = fileName.lastIndexOf('.');
        return fileName.substring(index);
    }

    /**
     * Retrieve the extension file by splitting the provided
     * fileName by {@code .} and getting last part.
     *
     * @param fileName File name to get extension.
     * @return If a supported extension is found, return extension (with {@code .} at start),
     * otherwise an empty string.
     */
    public static String getExtensionWithPoint(File fileName) {
        return getExtensionWithPoint(fileName.getName());
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
        return new File(OSUtility.validatePath(path));
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
        return OSUtility.validatePath(file.toString()).replace('\\', '/');
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


    /**
     * Checks if the provided input stream is a jar file.
     *
     * @param is The input stream to check for.
     * @return {@code true} if the file is jar, otherwise {@code false}.
     */
    public static boolean isJar(InputStream is) {
        return (hasMagicNumber(is, new byte[]{0x50, 0x4B, 0x03, 0x04})
                || hasMagicNumber(is, new byte[]{0x50, 0x4B, 0x05, 0x06})
                || hasMagicNumber(is, new byte[]{0x50, 0x4B, 0x07, 0x08}));
    }

    /**
     * Checks if the provided file is a jar file.
     *
     * @param file The file to check for.
     * @return {@code true} if the file is jar, otherwise {@code false}.
     */
    public static boolean isJar(File file) {
        return file.isFile() && getExtension(file).equalsIgnoreCase("jar")
                && (hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x03, 0x04})
                || hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x05, 0x06})
                || hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x07, 0x08}));
    }

    /**
     * Checks if the provided input stream is a zip file.
     *
     * @param is The input stream to check for.
     * @return {@code true} if the file is zip, otherwise {@code false}.
     */
    public static boolean isZip(InputStream is) {
        return (hasMagicNumber(is, new byte[]{0x50, 0x4B, 0x03, 0x04})
                || hasMagicNumber(is, new byte[]{0x50, 0x4B, 0x05, 0x06})
                || hasMagicNumber(is, new byte[]{0x50, 0x4B, 0x07, 0x08}));
    }

    /**
     * Checks if the provided file is a zip file.
     *
     * @param file The file to check for.
     * @return {@code true} if the file is zip, otherwise {@code false}.
     */
    public static boolean isZip(File file) {
        return file.isFile() && getExtension(file).equalsIgnoreCase("zip")
                && (hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x03, 0x04})
                || hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x05, 0x06})
                || hasMagicNumber(file, new byte[]{0x50, 0x4B, 0x07, 0x08}));
    }

    /**
     * Checks if the header of the provided file equals to the provided magic numbers.
     *
     * @param file         The file to read the header from.
     * @param magicNumbers The bytes to compare with.
     * @return {@code true} if the header equals with the provided bytes.
     */
    public static boolean hasMagicNumber(File file, byte[] magicNumbers) {
        return hasMagicNumber(file, magicNumbers, true);
    }

    /**
     * Checks if the header of the provided input stream equals to the provided magic numbers.
     *
     * @param is           The input stream to read the header from.
     * @param magicNumbers The bytes to compare with.
     * @return {@code true} if the header equals with the provided bytes.
     */
    public static boolean hasMagicNumber(InputStream is, byte[] magicNumbers) {
        try {
            return hasMagicNumber(new ByteArrayInputStream(is.readAllBytes()), magicNumbers, true);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading provided input stream", e);
        }
    }

    /**
     * Checks if the header of the provided input stream equals to the provided magic numbers.
     *
     * @param bytes        The bytes to read.
     * @param magicNumbers The bytes to compare with.
     * @return {@code true} if the header equals with the provided bytes.
     */
    public static boolean hasMagicNumber(byte[] bytes, byte[] magicNumbers) {
        return Arrays.equals(bytes, magicNumbers);
    }

    /**
     * Checks if the header of the provided file equals to the provided magic numbers.
     *
     * @param file         The file to read the header from.
     * @param magicNumbers The bytes to compare with.
     * @param close        if {@code true} close the input stream, otherwise not close the new input stream created.
     * @return {@code true} if the header equals with the provided bytes.
     */
    public static boolean hasMagicNumber(File file, byte[] magicNumbers, boolean close) {
        try (InputStream is = new FileInputStream(file)) {
            return hasMagicNumber(is, magicNumbers, close);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading file at " + file.getPath(), e);
        }
    }

    /**
     * Checks if the header of the provided input stream equals to the provided magic numbers.
     *
     * @param is           The input stream to read the header from.
     * @param magicNumbers The bytes to compare with.
     * @param close        if {@code true} close the input stream, otherwise not close the new input stream created.
     * @return {@code true} if the header equals with the provided bytes.
     */
    public static boolean hasMagicNumber(InputStream is, byte[] magicNumbers, boolean close) {
        return false;//Arrays.equals(FilesReader.readHeader(is, magicNumbers.length, close), magicNumbers);
    }
}