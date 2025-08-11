package me.tr.trFiles.general.managers;

import me.tr.trFiles.general.utility.FileUtility;
import me.tr.trFiles.general.utility.Validate;
import me.tr.trFiles.general.utility.os.OSUtility;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager {

    /**
     * Get an instance of {@link File} from a String correctly
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
     * Get a formatted file path as String.
     *
     * @param file File to get the path from.
     * @return the formatted file path.
     */
    public String getStringPathFromFile(File file) {
        return file.toString().replace('\\', '/');
    }

    /**
     * Get a formatted file path as String.
     *
     * @param path Path to get the path from.
     * @return the formatted file path.
     */
    public String getStringPathFromPath(Path path) {
        return getStringPathFromFile(path.toFile());
    }


    public void write(InputStream is, File to) throws IOException {
        Validate.notNull(is != null, "InputStream cannot be null");
        Validate.notNull(to != null, "File cannot be null");
        Validate.checkIf(to.isFile(), () -> createFile(to));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }


    /**
     * Inserts a file into a zip archive.
     *
     * @param zip   The zip archive.
     * @param file  The file to insert.
     * @param force if true and the zip file already exists, it will be overwritten, otherwise it will be created.
     * @throws NullPointerException if force is true and zip and file are null.
     * @throws RuntimeException     if an error occurs while creating the file.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void zipFile(File zip, File file, boolean force) {
        if (force) {
            if (zip == null || !zip.isFile()) {
                if (file == null) {
                    throw new NullPointerException("File cannot be null.");
                }
                zip = new File(file.getPath() + ".zip");
            }
            try {
                zip.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Error while creating file.", e);
            }
        }
        zipFile(zip, file);
    }

    /**
     * Inserts a file into a zip archive.
     *
     * @param zip  The zip archive.
     * @param file The file to insert.
     * @throws RuntimeException if an error occurs while zipping the file.
     */
    public void zipFile(File zip, File file) {
        Validate.notNull(zip != null, "Zip file cannot be null.");
        Validate.checkIf(file.isFile(), "The object found at " + file.getPath() + " is not a file.");
        Validate.checkIf(FileUtility.isZip(zip), "The file found at " + zip.getPath() + " is not a zip archive.");
        Validate.checkIf(file.canRead(), "The file found at " + file.getPath() + " is not readable.");
        Validate.checkIf(zip.canWrite(), "The file found at " + zip.getPath() + " is not writable.");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            ZipEntry entry = new ZipEntry(file.getName());
            zos.putNextEntry(entry);
            byte[] buffer = new byte[1024];
            int length;
            try (InputStream is = new FileInputStream(file)) {
                while ((length = is.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while zipping file " + file.getPath(), e);
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