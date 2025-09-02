package me.tr.trFiles.configuration.management;

import me.tr.trFiles.Validator;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileManager {
    private FileManager() {
    }

    public static void write(String content, File to) {
        Validator.isNull(content, "Content cannot be null");
        write(new ByteArrayInputStream(content.getBytes()), to);
    }


    public static void write(InputStream is, File to) {
        Validator.isNull(is, "Input stream cannot be null");
        Validator.isNull(to, "File cannot be null");
        Validator.checkIf(to.canRead(), "Cannot read file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write file at " + to.getPath());
        Validator.checkIf(to.isFile(), () -> create(to));

        try {
            write(is, new FileOutputStream(to));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while writing contents to " + to.getPath(), e);
        }
    }

    public static void write(InputStream is, OutputStream to) throws IOException {
        Validator.isNull(is, "Input stream cannot be null");
        Validator.isNull(to, "File cannot be null");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(to))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            writer.flush();
        }
    }


    public static String read(File file) {
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file");
        try {
            return read(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while reading " + file.getPath(), e);
        }
    }


    public static String read(InputStream is) throws IOException {
        Validator.isNull(is, "InputStream cannot be null");
        return read(new InputStreamReader(is));
    }


    public static String read(Reader reader) throws IOException {
        Validator.isNull(reader, "Reader cannot be null");
        try (BufferedReader br = reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader)) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();
        }
    }

    public static void zip(File zip, File file) {
        Validator.isNull(zip, "Zip file cannot be null.");
        Validator.checkIf(file.isFile(), "The object found at " + file.getPath() + " is not a file.");
        Validator.checkIf(FileUtility.isZip(zip), "The file found at " + zip.getPath() + " is not a zip archive.");
        Validator.checkIf(file.canRead(), "The file found at " + file.getPath() + " is not readable.");
        Validator.checkIf(zip.canWrite(), "The file found at " + zip.getPath() + " is not writable.");

        try (OutputStream os = new FileOutputStream(zip)) {
            zip(os, file);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while zipping " + file.getPath(), e);
        }
    }

    public static void zip(OutputStream os, File file) {
        Validator.isNull(os, "OutputStream cannot be null");
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file.");

        try (ZipOutputStream zos = new ZipOutputStream(os)) {
            zip(zos, file);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while zipping " + file.getPath(), e);
        }
    }

    public static void zip(ZipOutputStream zos, File file) {
        Validator.isNull(zos, "ZipOutputStream cannot be null");
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        Validator.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file.");

        try (zos) {
            ZipEntry entry = new ZipEntry(FileUtility.getStringPathFromFile(file));
            zos.putNextEntry(entry);
            byte[] buffer = new byte[1024];
            int length;
            try (InputStream is = new FileInputStream(file)) {
                while ((length = is.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while zipping " + file.getPath(), e);
        }
    }


    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean create(File file) {
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.canRead(), "Cannot read file at " + file.getPath());
        if (file.exists()) {
            if (file.isFile()) return true;
            delete(file);
        }
        Validator.checkIf(file.canWrite(), "Cannot write file at " + file.getPath());
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

    public static boolean delete(File file) {
        if (!file.exists()) return true;
        return file.delete();
    }

    public static boolean create(String path) {
        Validator.isNull(path, "String path cannot be null");
        return create(FileUtility.getFileFromString(path));
    }
}