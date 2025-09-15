package me.tr.trFiles.configuration.management;

import me.tr.trFiles.Validator;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Utility class for handling file operations such as reading, writing,
 * zipping and unzipping.
 */
public class FileManager {
    private FileManager() {
    }

    /**
     * Write the given string content to the specified file.
     *
     * @param content The string to write.
     * @param to      The file to write into.
     */
    public static void write(String content, File to) {
        write(content, to, true);
    }

    /**
     * Write the given input stream into the specified file.
     *
     * @param is The input stream to write from.
     * @param to The file to write into.
     */
    public static void write(InputStream is, File to) {
        write(is, to, true);
    }

    /**
     * Write the content of an input stream into an output stream.
     *
     * @param is The input stream to read from.
     * @param to The output stream to write to.
     * @throws IOException if an I/O error occurs.
     */
    public static void write(InputStream is, OutputStream to) throws IOException {
        write(is, to, true);
    }

    /**
     * Write string content to the specified file.
     *
     * @param content The string to write.
     * @param to      The file to write into.
     * @param close   Whether to close the streams afterwards.
     */
    public static void write(String content, File to, boolean close) {
        Validator.isNull(content, "Content cannot be null");
        write(new ByteArrayInputStream(content.getBytes()), to, close);
    }

    /**
     * Write the content of a file into another file.
     *
     * @param file  The file to read from.
     * @param to    The file to write into.
     * @param close Whether to close the streams afterwards.
     */
    public static void write(File file, File to, boolean close) {
        Validator.isNull(file, "File cannot be null");
        Validator.isNull(to, "File cannot be null");
        Validator.checkIf(to.canRead(), "Cannot read file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write file at " + to.getPath());
        Validator.checkIf(to.isFile(), () -> create(to));

        try {
            write(new FileInputStream(file), new FileOutputStream(to), close);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while writing contents to " + to.getPath(), e);
        }
    }

    /**
     * Write the contents of an input stream into a file.
     *
     * @param is    The input stream.
     * @param to    The file to write to.
     * @param close Whether to close the streams afterwards.
     */
    public static void write(InputStream is, File to, boolean close) {
        Validator.isNull(is, "Input stream cannot be null");
        Validator.isNull(to, "File cannot be null");
        Validator.checkIf(to.canRead(), "Cannot read file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write file at " + to.getPath());
        Validator.checkIf(to.isFile(), () -> create(to));

        try {
            write(is, new FileOutputStream(to), close);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while writing contents to " + to.getPath(), e);
        }
    }

    /**
     * Write the contents of an input stream into an output stream.
     *
     * @param is    The input stream to read from.
     * @param to    The output stream to write into.
     * @param close Whether to close the streams afterwards.
     * @throws IOException if an I/O error occurs.
     */
    public static void write(InputStream is, OutputStream to, boolean close) throws IOException {
        Validator.isNull(is, "Input stream cannot be null");
        Validator.isNull(to, "File cannot be null");

        int n;
        while ((n = is.read()) != -1) {
            to.write(n);
        }
        if (close) {
            is.close();
            to.close();
        }
    }

    /**
     * Write string content to a file line by line.
     *
     * @param content The content to write.
     * @param to      The file to write into.
     */
    public static void writeLines(String content, File to) {
        writeLines(content, to, true);
    }

    /**
     * Write the contents of an input stream into a file line by line.
     *
     * @param is The input stream.
     * @param to The file to write into.
     */
    public static void writeLines(InputStream is, File to) {
        writeLines(is, to, true);
    }

    /**
     * Write the contents of an input stream into an output stream line by line.
     *
     * @param is The input stream.
     * @param to The output stream.
     * @throws IOException if an I/O error occurs.
     */
    public static void writeLines(InputStream is, OutputStream to) throws IOException {
        writeLines(is, to, true);
    }

    /**
     * Write string content to a file line by line.
     *
     * @param content The string to write.
     * @param to      The file to write into.
     * @param close   Whether to close the streams afterwards.
     */
    public static void writeLines(String content, File to, boolean close) {
        Validator.isNull(content, "Content cannot be null");
        writeLines(new ByteArrayInputStream(content.getBytes()), to, close);
    }

    /**
     * Write the contents of a file into another file line by line.
     *
     * @param file  The file to read from.
     * @param to    The file to write into.
     * @param close Whether to close the streams afterwards.
     */
    public static void writeLines(File file, File to, boolean close) {
        Validator.isNull(file, "File cannot be null");
        Validator.isNull(to, "File cannot be null");
        Validator.checkIf(to.canRead(), "Cannot read file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write file at " + to.getPath());

        try {
            writeLines(new FileInputStream(file), new FileOutputStream(to), close);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while writing contents to " + to.getPath(), e);
        }
    }

    /**
     * Write the contents of an input stream into a file line by line.
     *
     * @param is    The input stream.
     * @param to    The file to write into.
     * @param close Whether to close the streams afterwards.
     */
    public static void writeLines(InputStream is, File to, boolean close) {
        Validator.isNull(is, "Input stream cannot be null");
        Validator.isNull(to, "File cannot be null");
        Validator.checkIf(to.isFile(), () -> create(to));
        Validator.checkIf(to.canRead(), "Cannot read file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write file at " + to.getPath());

        try {
            writeLines(is, new FileOutputStream(to), close);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while writing contents to " + to.getPath(), e);
        }
    }

    /**
     * Write the contents of an input stream into an output stream line by line.
     *
     * @param is    The input stream.
     * @param to    The output stream.
     * @param close Whether to close the streams afterwards.
     * @throws IOException if an I/O error occurs.
     */
    public static void writeLines(InputStream is, OutputStream to, boolean close) throws IOException {
        Validator.isNull(is, "Input stream cannot be null");
        Validator.isNull(to, "File cannot be null");

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(to));

        String line;
        while ((line = reader.readLine()) != null) {
            writer.write(line);
            writer.newLine();
        }
        writer.flush();
        if (close) {
            is.close();
            to.close();
            reader.close();
            writer.close();
        }
    }

    /**
     * Read the content of a file as a char array.
     *
     * @param file The file to read.
     * @return The contents of the file.
     */
    public static char[] read(File file) {
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file");
        try {
            return read(new FileInputStream(file), true);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading " + file.getPath(), e);
        }
    }

    /**
     * Read the content of an input stream as a char array.
     *
     * @param is The input stream to read from.
     * @return The contents as char array.
     * @throws IOException if an I/O error occurs.
     */
    public static char[] read(InputStream is) throws IOException {
        return read(new InputStreamReader(is), true);
    }

    /**
     * Read the content of a reader as a char array.
     *
     * @param reader The reader.
     * @return The contents as char array.
     * @throws IOException if an I/O error occurs.
     */
    public static char[] read(Reader reader) throws IOException {
        return read(reader, true);
    }

    /**
     * Read the content of a file as a char array.
     *
     * @param file  The file to read.
     * @param close Whether to close the streams afterwards.
     * @return The contents of the file.
     */
    public static char[] read(File file, boolean close) {
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file");
        try {
            return read(new FileInputStream(file), close);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading " + file.getPath(), e);
        }
    }

    /**
     * Read the content of an input stream as a char array.
     *
     * @param is    The input stream to read.
     * @param close Whether to close the stream afterwards.
     * @return The contents as char array.
     * @throws IOException if an I/O error occurs.
     */
    public static char[] read(InputStream is, boolean close) throws IOException {
        Validator.isNull(is, "InputStream cannot be null");
        return read(new InputStreamReader(is), close);
    }

    /**
     * Read the content of a reader as a char array.
     *
     * @param reader The reader.
     * @param close  Whether to close the reader afterwards.
     * @return The contents as char array.
     * @throws IOException if an I/O error occurs.
     */
    public static char[] read(Reader reader, boolean close) throws IOException {
        Validator.isNull(reader, "Reader cannot be null");
        CharArrayWriter writer = new CharArrayWriter();
        char[] buffer = new char[8192];
        int n;
        try {
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            if (close) {
                reader.close();
            }
        }
        return writer.toCharArray();
    }

    /**
     * Read the contents of a file as a string (line by line).
     *
     * @param file The file to read.
     * @return The contents of the file.
     */
    public static String readLines(File file) {
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file");
        try {
            return readLines(new FileInputStream(file), true);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading " + file.getPath(), e);
        }
    }

    /**
     * Read the contents of an input stream as a string (line by line).
     *
     * @param is The input stream.
     * @return The contents of the stream.
     * @throws IOException if an I/O error occurs.
     */
    public static String readLines(InputStream is) throws IOException {
        return readLines(new InputStreamReader(is), true);
    }

    /**
     * Read the contents of a reader as a string (line by line).
     *
     * @param reader The reader.
     * @return The contents.
     * @throws IOException if an I/O error occurs.
     */
    public static String readLines(Reader reader) throws IOException {
        return readLines(reader, true);
    }

    /**
     * Read the contents of a file as a string (line by line).
     *
     * @param file  The file to read.
     * @param close Whether to close the stream afterwards.
     * @return The contents of the file.
     */
    public static String readLines(File file, boolean close) {
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file");
        try {
            return readLines(new FileInputStream(file), close);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading " + file.getPath(), e);
        }
    }

    /**
     * Read the contents of an input stream as a string (line by line).
     *
     * @param is    The input stream.
     * @param close Whether to close the stream afterwards.
     * @return The contents of the stream.
     * @throws IOException if an I/O error occurs.
     */
    public static String readLines(InputStream is, boolean close) throws IOException {
        Validator.isNull(is, "InputStream cannot be null");
        return readLines(new InputStreamReader(is), close);
    }

    /**
     * Read the contents of a reader as a string (line by line).
     *
     * @param reader The reader.
     * @param close  Whether to close the reader afterwards.
     * @return The contents of the reader.
     * @throws IOException if an I/O error occurs.
     */
    public static String readLines(Reader reader, boolean close) throws IOException {
        Validator.isNull(reader, "Reader cannot be null");
        BufferedReader br = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line).append("\n");
        }
        if (close) {
            br.close();
        }
        return result.toString();
    }

    /**
     * Zip a file into a zip archive.
     *
     * @param zip  The target zip file.
     * @param file The file to compress.
     */
    public static void zip(File zip, File file) {
        zip(zip, file, true);
    }

    /**
     * Zip a file into an output stream.
     *
     * @param os   The output stream.
     * @param file The file to compress.
     */
    public static void zip(OutputStream os, File file) {
        zip(os, file, true);
    }

    /**
     * Zip a file into a {@link ZipOutputStream}.
     *
     * @param zos  The zip output stream.
     * @param file The file to compress.
     */
    public static void zip(ZipOutputStream zos, File file) {
        zip(zos, file, true);
    }

    /**
     * Zip a file into a zip archive.
     *
     * @param zip   The target zip file.
     * @param file  The file to compress.
     * @param close Whether to close the stream afterwards.
     */
    public static void zip(File zip, File file, boolean close) {
        Validator.isNull(zip, "Zip file cannot be null.");
        Validator.checkIf(file.isFile(), "The object found at " + file.getPath() + " is not a file.");
        Validator.checkIf(FileUtility.isZip(zip), "The file found at " + zip.getPath() + " is not a zip archive.");
        Validator.checkIf(file.canRead(), "The file found at " + file.getPath() + " is not readable.");
        Validator.checkIf(zip.canWrite(), "The file found at " + zip.getPath() + " is not writable.");

        try {
            OutputStream os = new FileOutputStream(zip);
            zip(os, file);
            if (close) {
                os.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while zipping " + file.getPath(), e);
        }
    }

    /**
     * Zip a file into an output stream.
     *
     * @param os    The output stream.
     * @param file  The file to compress.
     * @param close Whether to close the stream afterwards.
     */
    public static void zip(OutputStream os, File file, boolean close) {
        Validator.isNull(os, "OutputStream cannot be null");
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file.");

        try {
            ZipOutputStream zos = new ZipOutputStream(os);
            zip(zos, file);
            if (close) {
                zos.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while zipping " + file.getPath(), e);
        }
    }

    /**
     * Zip a file into a {@link ZipOutputStream}.
     *
     * @param zos   The zip output stream.
     * @param file  The file to compress.
     * @param close Whether to close the stream afterwards.
     */
    public static void zip(ZipOutputStream zos, File file, boolean close) {
        Validator.isNull(zos, "ZipOutputStream cannot be null");
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file.");

        try {
            ZipEntry entry = new ZipEntry(FileUtility.getStringPathFromFile(file));
            zos.putNextEntry(entry);
            InputStream is = new FileInputStream(file);
            write(is, zos);
            if (close) {
                zos.close();
                is.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while zipping " + file.getPath(), e);
        }
    }

    /**
     * Unzip a zip file into the specified directory.
     *
     * @param zip The zip file.
     * @param to  The destination directory.
     */
    public static void unzip(File zip, File to) {
        unzip(zip, to, true);
    }

    /**
     * Unzip a zip input stream into the specified directory.
     *
     * @param is The input stream.
     * @param to The destination directory.
     */
    public static void unzip(InputStream is, File to) {
        unzip(is, to, true);
    }

    /**
     * Unzip a {@link ZipInputStream} into the specified directory.
     *
     * @param zis The zip input stream.
     * @param to  The destination directory.
     */
    public static void unzip(ZipInputStream zis, File to) {
        unzip(zis, to, true);
    }

    /**
     * Unzip a zip file into the specified directory.
     *
     * @param zip   The zip file.
     * @param to    The destination directory.
     * @param close Whether to close the streams afterwards.
     */
    public static void unzip(File zip, File to, boolean close) {
        Validator.isNull(zip, "Zip file cannot be null.");
        Validator.checkIf(to.isDirectory(), "The object found at " + to.getPath() + " is not a directory.");
        Validator.checkIf(FileUtility.isZip(zip), "The file found at " + zip.getPath() + " is not a zip archive.");
        Validator.checkIf(to.canRead(), "The file found at " + to.getPath() + " is not readable.");
        Validator.checkIf(zip.canRead(), "The file found at " + zip.getPath() + " is not readable.");
        try {
            FileInputStream is = new FileInputStream(zip);
            ZipInputStream zis = new ZipInputStream(is);
            unzip(zis, to, close);
            if (close) {
                is.close();
                zis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while unzipping " + to.getPath(), e);
        }
    }

    /**
     * Unzip an input stream into the specified directory.
     *
     * @param is    The input stream.
     * @param to    The destination directory.
     * @param close Whether to close the streams afterwards.
     */
    public static void unzip(InputStream is, File to, boolean close) {
        Validator.isNull(is, "InputStream cannot be null");
        Validator.isNull(to, "File cannot be null");
        Validator.checkIf(to.canRead(), "Cannot read file at " + to.getPath());
        Validator.checkIf(to.isDirectory(), "Object at " + to.getPath() + " is not a directory.");
        try {
            ZipInputStream zis = new ZipInputStream(is);
            unzip(zis, to, close);
            if (close) {
                is.close();
                zis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while unzipping " + to.getPath(), e);
        }
    }


    /**
     * Unzip a {@link ZipInputStream} into the specified directory.
     *
     * @param zis   The zip input stream.
     * @param to    The destination directory.
     * @param close Whether to close the streams afterwards.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void unzip(ZipInputStream zis, File to, boolean close) {
        Validator.isNull(zis, "ZipInputStream cannot be null");
        Validator.isNull(to, "File cannot be null");
        Validator.checkIf(to.isDirectory(), "Object at " + to.getPath() + " is not a directory.");

        try {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(to, entry.getName());
                String destDirPath = to.getCanonicalPath();
                String destFilePath = file.getCanonicalPath();

                if (!destFilePath.startsWith(destDirPath + File.separator)) {
                    throw new SecurityException("Entry is outside of the target dir: " + entry.getName());
                }

                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    create(file);
                    write(zis, file, false);
                }
            }

            if (close) {
                zis.closeEntry();
                zis.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while unzipping " + to.getPath(), e);
        }
    }

    /**
     * Create the provided file.
     *
     * @param file The file to create.
     * @return {@code true} if and only if the file is successfully created, otherwise {@code false}.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean create(File file) {
        Validator.isNull(file, "File cannot be null");
        if (file.exists()) {
            if (file.isFile()) return false;
            delete(file);
        }
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            return file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while creating " + file.getPath(), e);
        }
    }

    /**
     * Create the provided file.
     *
     * @param path The string-path to the file to create.
     * @return {@code true} if and only if the file or directory is successfully create, otherwise {@code false}.
     */
    public static boolean create(String path) {
        Validator.isNull(path, "String path cannot be null");
        return create(FileUtility.getFileFromString(path));
    }

    /**
     * Delete the provided file.
     *
     * @param file The file to delete.
     * @return {@code true} if and only if the file or directory is successfully deleted, otherwise {@code false}.
     */
    public static boolean delete(File file) {
        if (!file.exists()) return false;
        return file.delete();
    }

}