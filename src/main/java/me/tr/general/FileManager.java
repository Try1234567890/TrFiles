package me.tr.general;

import me.tr.configuration.file.FileConfiguration;
import me.tr.configuration.file.json.JsonConfiguration;
import me.tr.configuration.file.yaml.YamlConfiguration;
import me.tr.general.utilities.ComparingMode;
import me.tr.general.utilities.FileUtilities;
import me.tr.general.utilities.Validate;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class to manage file and integrate any utility method for {@link FileConfiguration}
 */
public class FileManager {

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
        return getFileInJar(new File(jar), new File(pathIntoJar));
    }


    /**
     * Retrieves an {@link InputStream} for a file located inside a JAR file.
     *
     * @param jar         The JAR file to open.
     * @param pathIntoJar The path to the desired file within the JAR.
     * @return An {@link InputStream} if the file is found inside the JAR, otherwise {@code null}.
     * @throws IllegalArgumentException If the given file is not a valid JAR.
     * @throws NullPointerException     If the specified JAR file does not exist.
     * @throws RuntimeException         If an error occurs while getting input stream.
     * @see #getFileInJar(String, String)
     */
    public @Nullable InputStream getFileInJar(File jar, File pathIntoJar) {
        Validate.notNull(jar.exists(), "Jar file at path " + jar + " doesn't exist");
        Validate.checkIf(jar.isFile(), "Object found at path " + jar + " is not a file.");
        Validate.checkIf(FileUtilities.isJar(jar), "File found at path " + jar + " is not a .jar file.");
        try {
            JarFile jarFile = new JarFile(jar);
            JarEntry jarEntry = jarFile.getJarEntry(pathIntoJar.getPath());
            Validate.notNull(jarEntry != null, "File into jar " + jar.getPath() + " at path " + pathIntoJar.getPath() + " not found");
            return jarFile.getInputStream(jarEntry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a file (given as a path string) to a {@link String},
     * by creating a {@link File} object and delegating to {@link #saveToString(File)}.
     * <p>
     * This method allows converting any file, without restrictions on file type.
     *
     * @param file The path to the file to read.
     * @return A {@link String} representing the contents of the file.
     * @throws RuntimeException         If an error occurs while reading the file.
     * @throws NullPointerException     If the specified file does not exist.
     * @throws IllegalArgumentException If the specified path refers to a non-file object.
     * @see #saveToString(Reader)
     * @see #saveToString(File)
     */
    public @Nullable String saveToString(String file) {
        return saveToString(new File(file));
    }

    /**
     * Converts a {@link File} to a {@link String},
     * by creating a {@link FileReader} and delegating to {@link #saveToString(Reader)}.
     * <p>
     * This method allows converting any file, without restrictions on file type.
     *
     * @param file The {@link File} to read.
     * @return A {@link String} representing the contents of the file.
     * @throws RuntimeException         If an error occurs while reading the file.
     * @throws NullPointerException     If the specified file does not exist.
     * @throws IllegalArgumentException If the specified path refers to a non-file object.
     * @see #saveToString(Reader)
     * @see #saveToString(String)
     */
    public @Nullable String saveToString(File file) {
        Validate.notNull(file.exists(), "File at path " + file.getPath() + " doesn't exists.");
        Validate.checkIf(file.isFile(), "Object at path " + file.getPath() + " is not a file.");
        try {
            return saveToString(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new NullPointerException("File at path " + file.getPath() + " not found.");
        }
    }

    /**
     * Converts a file to a {@link String} by reading its content using a {@link Reader},
     * splitting lines by {@code \n}.
     * <p>
     * This method allows converting any file, without restrictions on file type.
     *
     * @param reader The {@link Reader} used to read the file.
     * @return A {@link String} representing the contents of the file, or {@code null} if an error occurs.
     * @throws RuntimeException If an error occurs while reading the file.
     * @see #saveToString(File)
     * @see #saveToString(String)
     */
    public @Nullable String saveToString(Reader reader) {
        try (BufferedReader bufferedReader = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads a file from the specified JAR file by params
     * and delegating to {@link #loadFileFromJar(String, String, String)}.
     * <p>
     * <b>{@code This method search file into jar as the same path of the file to write contents.}</b>
     *
     * @param file      The file to write the contents into (if found in the jar).
     * @param pathToJar The JAR file to search inside.
     * @return The loaded {@link FileConfiguration}, or {@code null} if loading fails.
     * @throws NullPointerException If the specified JAR file is not found.
     * @throws RuntimeException     If an error occurs while saving the input stream to the file.
     * @see #loadFileFromJar(String, String, String)
     * @see #loadFileFromJar(File, File)
     * @see #loadFileFromJar(File, File, File)
     */
    public @Nullable FileConfiguration loadFileFromJar(String file, String pathToJar) {
        return loadFileFromJar(file, pathToJar, file);
    }

    /**
     * Loads a file from the specified JAR file from params
     * and delegating to {@link #loadFileFromJar(File, File, File)}.
     * <p>
     * <b>{@code This method search file into jar as the same path of the file to write contents.}</b>
     *
     * @param file      The file to write the contents into (if found in the jar).
     * @param pathToJar The JAR file to search inside.
     * @return The loaded {@link FileConfiguration}, or {@code null} if loading fails.
     * @throws NullPointerException If the specified JAR file is not found.
     * @throws RuntimeException     If an error occurs while saving the input stream to the file.
     * @see #loadFileFromJar(String, String)
     * @see #loadFileFromJar(String, String, String)
     * @see #loadFileFromJar(File, File, File)
     */
    public @Nullable FileConfiguration loadFileFromJar(File file, File pathToJar) {
        return loadFileFromJar(file, pathToJar, file);
    }

    /**
     * Loads a file from the specified JAR file by creating {@link File}s
     * from params and delegating to {@link #loadFileFromJar(File, File, File)}.
     *
     * @param file        The file to write the contents into (if found in the jar).
     * @param pathToJar   The JAR file to search inside.
     * @param pathIntoJar The internal path of the file inside the JAR.
     * @return The loaded {@link FileConfiguration}, or {@code null} if loading fails.
     * @throws NullPointerException If the specified JAR file is not found.
     * @throws RuntimeException     If an error occurs while saving the input stream to the file.
     * @see #loadFileFromJar(String, String)
     * @see #loadFileFromJar(File, File)
     * @see #loadFileFromJar(File, File, File)
     */
    public @Nullable FileConfiguration loadFileFromJar(String file, String pathToJar, String pathIntoJar) {
        return loadFileFromJar(new File(file), new File(pathToJar), new File(pathIntoJar));
    }

    /**
     * Loads a file from the specified JAR file.
     * <p>
     * If the {@code file} does not exist, the method will create its parent directories and the file itself.
     * If the {@code pathToJar} does not contain the file at {@code pathIntoJar},
     * this method creates an empty file without copying contents.
     * </p>
     *
     * @param file        The file to write the contents into (if found in the jar).
     * @param pathToJar   The JAR file to search inside.
     * @param pathIntoJar The internal path of the file inside the JAR.
     * @return The loaded {@link FileConfiguration}, or {@code null} if loading fails.
     * @throws NullPointerException If the specified JAR file is not found.
     * @throws RuntimeException     If an error occurs while saving the input stream to the file.
     * @see #loadFileFromJar(String, String)
     * @see #loadFileFromJar(File, File)
     * @see #loadFileFromJar(String, String, String)
     */
    public @Nullable FileConfiguration loadFileFromJar(File file, File pathToJar, File pathIntoJar) {
        if (!file.exists()) {
            createDirectory(file);
            createFile(file);
            File jar = getFile(pathToJar);
            if (jar == null) {
                throw new NullPointerException("Jar file not found. Make sure jar file contains \"" + pathToJar.getName() + "\" in its name.");
            }
            InputStream is = getFileInJar(jar, pathIntoJar);
            if (is != null) {
                saveFile(is, file.getPath());
            }
        }
        String extension = FileUtilities.getExtension(file);
        return switch (extension) {
            case "yml", "yaml" -> YamlConfiguration.loadConfiguration(file);
            case "json" -> JsonConfiguration.loadConfiguration(file);
            default ->
                    throw new IllegalArgumentException("File type non supported " + extension + ". Make sure file extension are: \".yml (.yaml)\" or \".json\".");
        };
    }

    /**
     * Retrieves a file from the specified directory.
     * Delegates to {@link #getFile(File, ComparingMode)}, by creating a new file with the
     * string path and using {@link ComparingMode#EQUALS} as the comparison mode.
     *
     * @param path The path to the directory to search the file in.
     * @return {@link File} If the file is found, else if directory not contains files or files is not found {@code null}
     * @throws NullPointerException     If the specified path doesn't exist.
     * @throws IllegalArgumentException If the object at specified path is not a directory.
     */
    public @Nullable File getFile(String path) {
        return getFile(new File(path), ComparingMode.EQUALS);
    }

    /**
     * Retrieves a file from the specified directory.
     * Delegates to {@link #getFile(File, ComparingMode)}, by creating a new file with the
     * string path.
     *
     * @param path The path to the directory to search the file in.
     * @return {@link File} If the file is found, else if directory not contains files or files is not found {@code null}
     * @throws NullPointerException     If the specified path doesn't exist.
     * @throws IllegalArgumentException If the object at specified path is not a directory.
     */
    public @Nullable File getFile(String path, ComparingMode comparingMode) {
        return getFile(new File(path), comparingMode);
    }

    /**
     * Retrieves a file from the specified directory.
     * Delegates to {@link #getFile(File, ComparingMode)}, using {@link ComparingMode#EQUALS} as the comparison mode.
     *
     * @param path The path to the directory to search the file in.
     * @return {@link File} If the file is found, else if directory not contains files or files is not found {@code null}
     * @throws NullPointerException     If the specified path doesn't exist.
     * @throws IllegalArgumentException If the object at specified path is not a directory.
     */
    public @Nullable File getFile(File path) {
        return getFile(path, ComparingMode.EQUALS);
    }

    /**
     * Retrieves a file from the specified directory.
     * This method cycle all file in the directory and check single file depending on {@link ComparingMode}
     *
     * @param path          The path to the directory to search the file in.
     * @param comparingMode Comparing mode used to compare each file name and searched file name.
     * @return {@link File} If the file is found, else if directory not contains files or files is not found {@code null}
     * @throws NullPointerException     If the specified path doesn't exist.
     * @throws IllegalArgumentException If the object at specified path is not a directory.
     */
    public @Nullable File getFile(File path, ComparingMode comparingMode) {
        Validate.notNull(path.exists(), "File at path " + path.getPath() + " doesn't exists.");
        Validate.checkIf(path.isDirectory(), "Object at path " + path.getPath() + " is not a directory.");
        File[] files = path.listFiles();
        if (files == null)
            return null;
        for (File file : files) {
            switch (comparingMode) {
                case EQUALS_IGNORE_CASE:
                    if (file.getName().equalsIgnoreCase(path.getName())) {
                        return file;
                    }
                    break;
                case CONTAINS:
                    if (file.getName().contains(path.getName())) {
                        return file;
                    }
                    break;
                default:
                    if (file.getName().equals(path.getName())) {
                        return file;
                    }
                    break;
            }
        }
        return null;
    }

    /**
     * Save the {@link InputStream} to specified path.
     * If override is {@code true} a new file will be created and eventually overriding an existing file.
     *
     * @param file       File to read contents from.
     * @param pathToSave File to write contents in.
     * @param override   Create a new file and eventually overriding an existing one.
     * @return The saved file if no errors occur, otherwise {@code null}.
     * @throws NullPointerException     If {@code InputStream} or {@code File} are {@code null}.
     * @throws RuntimeException         If an error occurs while reading or writing.
     * @throws IllegalArgumentException If the object at specified path is not a file.
     * @see #saveFile(InputStream, String)
     * @see #saveFile(File, String)
     * @see #saveFile(File, String, boolean)
     * @see #saveFile(String, String)
     * @see #saveFile(String, String, boolean)
     */
    public @Nullable File saveFile(InputStream file, String pathToSave, boolean override) {
        Validate.notNull(file != null, "InputStream cannot be null.");
        File fileToSave = new File(pathToSave);
        if (override)
            createFile(fileToSave);
        Validate.notNull(fileToSave.exists(), "Cannot find file at " + pathToSave);
        Validate.checkIf(fileToSave.isFile(), "Object found at " + pathToSave + " is not file.");
        try {
            OutputStream out = new FileOutputStream(fileToSave);
            byte[] buf = new byte[1024];
            int len;
            while ((len = file.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            file.close();
            return fileToSave;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Save the {@link InputStream} to specified path by delegating
     * to {@link #saveFile(InputStream, String, boolean)} using {@code false} as {@code override}
     *
     * @param file       File to read contents from.
     * @param pathToSave File to write contents in.
     * @return The saved file if no errors occur, otherwise {@code null}.
     * @throws NullPointerException     If {@code InputStream} or {@code File} are {@code null}.
     * @throws RuntimeException         If an error occurs while reading or writing.
     * @throws IllegalArgumentException If the object at specified path is not a file.
     * @see #saveFile(InputStream, String, boolean)
     * @see #saveFile(File, String)
     * @see #saveFile(File, String, boolean)
     * @see #saveFile(String, String)
     * @see #saveFile(String, String, boolean)
     */
    public @Nullable File saveFile(InputStream file, String pathToSave) {
        return saveFile(file, pathToSave, false);
    }

    /**
     * Save the {@link File} to specified path.
     * If override is {@code true} a new file will be created and
     * eventually overriding an existing file.
     * <p>
     * This method creates a new {@link InputStream} by file param and delegates to {@link #saveFile(InputStream, String, boolean)}
     *
     * @param file       File to read contents from.
     * @param pathToSave File to write contents in.
     * @param override   Create a new file and eventually overriding an existing one.
     * @return The saved file if no errors occur, otherwise {@code null}.
     * @throws NullPointerException     If {@code InputStream} or {@code File} are {@code null}.
     * @throws RuntimeException         If an error occurs while reading or writing.
     * @throws IllegalArgumentException If the object at specified path is not a file.
     * @see #saveFile(InputStream, String)
     * @see #saveFile(InputStream, String, boolean)
     * @see #saveFile(File, String)
     * @see #saveFile(String, String)
     * @see #saveFile(String, String, boolean)
     */
    public @Nullable File saveFile(File file, String pathToSave, boolean override) {
        try {
            InputStream is = new FileInputStream(file);
            return saveFile(is, pathToSave, override);
        } catch (FileNotFoundException e) {
            throw new NullPointerException("File at path " + file.getPath() + " doesn't exists.");
        }
    }

    /**
     * Save the {@link InputStream} to specified path by delegating
     * to {@link #saveFile(File, String, boolean)} using {@code false} as {@code override}
     *
     * @param file       File to read contents from.
     * @param pathToSave File to write contents in.
     * @return The saved file if no errors occur, otherwise {@code null}.
     * @throws NullPointerException     If {@code InputStream} or {@code File} are {@code null}.
     * @throws RuntimeException         If an error occurs while reading or writing.
     * @throws IllegalArgumentException If the object at specified path is not a file.
     * @see #saveFile(InputStream, String)
     * @see #saveFile(InputStream, String, boolean)
     * @see #saveFile(File, String, boolean)
     * @see #saveFile(String, String)
     * @see #saveFile(String, String, boolean)
     */
    public @Nullable File saveFile(File file, String pathToSave) {
        return saveFile(file, pathToSave, false);
    }

    /**
     * Save the {@link File} to specified path.
     * If override is {@code true} a new file will be created and
     * eventually overriding an existing file.
     * <p>
     * This method creates a new {@link File} by string file param and delegates to {@link #saveFile(File, String, boolean)}
     *
     * @param file       File to read contents from.
     * @param pathToSave File to write contents in.
     * @param override   Create a new file and eventually overriding an existing one.
     * @return The saved file if no errors occur, otherwise {@code null}.
     * @throws NullPointerException     If {@code InputStream} or {@code File} are {@code null}.
     * @throws RuntimeException         If an error occurs while reading or writing.
     * @throws IllegalArgumentException If the object at specified path is not a file.
     * @see #saveFile(InputStream, String)
     * @see #saveFile(InputStream, String, boolean)
     * @see #saveFile(File, String)
     * @see #saveFile(File, String, boolean)
     * @see #saveFile(String, String)
     */
    public @Nullable File saveFile(String file, String pathToSave, boolean override) {
        File fileToSave = new File(pathToSave);
        return saveFile(fileToSave, pathToSave, override);
    }

    /**
     * Save the {@link File} to specified path.
     * This method creates a new {@code File} by string file param
     * and delegating to {@link #saveFile(File, String, boolean)}
     * using {@code false} as {@code override}
     *
     * @param file       File to read contents from.
     * @param pathToSave File to write contents in.
     * @return The saved file if no errors occur, otherwise {@code null}.
     * @throws NullPointerException     If {@code InputStream} or {@code File} are {@code null}.
     * @throws RuntimeException         If an error occurs while reading or writing.
     * @throws IllegalArgumentException If the object at specified path is not a file.
     * @see #saveFile(InputStream, String, boolean)
     * @see #saveFile(InputStream, String)
     * @see #saveFile(File, String, boolean)
     * @see #saveFile(File, String)
     * @see #saveFile(String, String, boolean)
     */
    public File saveFile(String file, String pathToSave) {
        return saveFile(file, pathToSave, false);
    }

    /**
     * Create a new file and all its parent directories at the specified path.
     * If a file already exists, no new file will be created.
     *
     * @param file Path redirecting to file working on.
     * @return The new file if it has been created, otherwise return the specified file.
     * @throws RuntimeException If an error occurs while creating a new file.
     */
    public File createFile(File file) {
        if (file.exists())
            return file;
        try {
            createDirectory(file.getParent());
            if (file.createNewFile())
                return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    /**
     * Create a new file by string path param and delegating to {@link #createFile(File)}
     *
     * @param file Path redirecting to file working on.
     * @return The new file if it has been created, otherwise return the specified file.
     * @throws RuntimeException If an error occurs while creating a new file.
     */
    public @Nullable File createFile(String file) {
        return createFile(new File(file));
    }

    /**
     * Creates all directories to the specified file.
     * Split directory by {@link File#separator} in parts
     * then concatenate parts while there isn't any other {@link File#separator}
     * (without last that is the file name) creating the path.
     * With the path, a new file has been created and then if the file does
     * not exist, make dir.
     *
     * @param path Path to file to create directories for.
     * @see #createFile(File)
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void createDirectory(String path) {
        int separatorIndex = -1, lengthBeforeSeparator;
        String currentPath = "";
        while ((separatorIndex = path.indexOf(File.separator, lengthBeforeSeparator = separatorIndex + 1)) != -1) {
            String dir = path.substring(lengthBeforeSeparator, separatorIndex);
            currentPath += dir + File.separator;
            File file = new File(currentPath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    /**
     * Creates all directories to the specified file.
     * Get the file path and delegate to {@link #createFile(String)}
     *
     * @param path Path to file to create directories for.
     * @see #createFile(String)
     */
    public void createDirectory(File path) {
        createDirectory(path.getPath());
    }
}
