package me.tr.trFiles.general.managers;

import me.tr.trFiles.general.utility.FileUtility;
import me.tr.trFiles.general.utility.Validate;
import me.tr.trFiles.general.utility.os.OSUtility;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileManager {
    private JarFile jarToClose;

    public void closeJar() {
        if (jarToClose != null) {
            try {
                jarToClose.close();
            } catch (IOException e) {
                throw new RuntimeException("Error while closing file.", e);
            }
        }
    }

    /**
     * Got an instance of {@link File} from a String correctly
     * respecting directories and file name.
     *
     * @param path Path to file.
     * @return A new instance of {@link File}.
     * @see #getPathFromString(String)
     */
    public File getFileFromString(String path) {
        path = path.replace('\\', '/');
        int lastSlashIndex = path.lastIndexOf('/') + 1;
        lastSlashIndex = lastSlashIndex <= 0 ? path.length() : lastSlashIndex;
        return new File(path.substring(0, lastSlashIndex), OSUtility.removeIllegalChars(path.substring(lastSlashIndex)));
    }


    public String getStringPathFromFile(File file) {
        return file.getPath().replace('\\', '/');
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
    public Path getPathFromString(String path) {
        return getFileFromString(path).toPath();
    }

    /**
     * Retrieves an {@link InputStream} for a file located inside a JAR file.
     *
     * @param jar         The path to the JAR file.
     * @param pathIntoJar The internal path of the file within the JAR.
     * @return An {@link InputStream} if the file is found inside the JAR, otherwise {@code null}.
     * @throws IllegalArgumentException If the given JAR path is not a file or not a valid JAR.
     * @throws NullPointerException     If the specified JAR file does not exist.
     * @see #getFileInJar(File, File)
     */
    public @Nullable InputStream getFileInJar(String jar, String pathIntoJar) {
        return getFileInJar(getFileFromString(jar), getFileFromString(pathIntoJar));
    }


    /**
     * Retrieves an {@link InputStream} for a file located inside a JAR file.
     *
     * @param jar         The JAR file to open.
     * @param fileIntoJar The file into the JAR.
     * @return An {@link InputStream}   if the file is found inside the JAR, otherwise {@code null}.
     * @throws IllegalArgumentException If the given file is not a valid JAR.
     * @throws NullPointerException     If the specified JAR file does not exist.
     * @throws RuntimeException         If an error occurs while getting input stream.
     * @see #getFileInJar(String, String)
     */
    public @Nullable InputStream getFileInJar(File jar, File fileIntoJar) {
        Validate.notNull(jar.exists(), "Jar file at path " + jar + " doesn't exist");
        Validate.checkIf(jar.isFile(), "Object found at path " + jar + " is not a file.");
        Validate.checkIf(FileUtility.isJar(jar), "File found at path " + jar + " is not a .jar file.");
        try {
            JarFile jarFile = new JarFile(jar);
            JarEntry jarEntry = jarFile.getJarEntry(getStringPathFromFile(fileIntoJar));
            Validate.notNull(jarEntry != null, "File into jar " + getStringPathFromFile(jar) + " at path " + getStringPathFromFile(fileIntoJar) + " not found");
            jarToClose = jarFile;
            return jarFile.getInputStream(jarEntry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves an {@link InputStream} for a file located inside a JAR file.
     *
     * @param jarFile     The JAR file to search in.
     * @param fileIntoJar The file into the JAR.
     * @return An {@link InputStream}   if the file is found inside the JAR, otherwise {@code null}.
     * @throws IllegalArgumentException If the given file is not a valid JAR.
     * @throws NullPointerException     If the specified JAR file does not exist.
     * @throws RuntimeException         If an error occurs while getting input stream.
     * @see #getFileInJar(String, String)
     */
    public @Nullable InputStream getFileInJar(JarFile jarFile, File fileIntoJar) {
        try {
            JarEntry jarEntry = jarFile.getJarEntry(getStringPathFromFile(fileIntoJar));
            Validate.notNull(jarEntry != null, "File into jar at path " + getStringPathFromFile(fileIntoJar) + " not found");
            jarToClose = jarFile;
            return jarFile.getInputStream(jarEntry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create a new file and all its parent directories at the specified path.
     * If a file already exists, no new file will be created.
     *
     * @param file Path redirecting to file working on.
     * @return true if the file has been created, otherwise false.
     * @throws RuntimeException If an error occurs while creating a new file.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public boolean createFile(File file) {
        if (file.exists())
            return true;
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists())
                parent.mkdirs();
            if (file.createNewFile())
                return true;
        } catch (IOException e) {
            throw new RuntimeException("Error while creating " + getStringPathFromFile(file), e);
        }
        return false;
    }

    /**
     * Create a new file by string path param and delegating to {@link #createFile(File)}
     *
     * @param file Path redirecting to file working on.
     * @return true if the file has been created, otherwise false.
     * @throws RuntimeException If an error occurs while creating a new file.
     */
    public boolean createFile(String file) {
        return createFile(getFileFromString(file));
    }
}