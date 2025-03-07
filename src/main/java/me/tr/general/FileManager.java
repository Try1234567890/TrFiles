package me.tr.general;

import me.tr.general.utilities.FileUtilities;
import me.tr.general.utilities.Validate;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileManager {

    public @Nullable InputStream getFileInJar(String jar, String pathIntoJar) {
        return getFileInJar(new File(jar), new File(pathIntoJar));
    }

    public @Nullable InputStream getFileInJar(File jar, File pathIntoJar) {
        Validate.checkIf(jar.exists(), "Jar file at path " + jar + " doesn't exist");
        Validate.checkIf(FileUtilities.isJar(jar), "File found at path " + jar + " is not a .jar file.");
        try {
            JarFile jarFile = new JarFile(jar);
            JarEntry jarEntry = jarFile.getJarEntry(pathIntoJar.getPath());
            Validate.checkIf(jarEntry != null, "File into jar at path " + pathIntoJar.getPath() + " not found");
            return jarFile.getInputStream(jarEntry);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nullable String saveToString(String file) {
        return saveToString(new File(file));
    }

    public @Nullable String saveToString(File file) {
        Validate.checkIf(file.exists(), "File at path " + file.getPath() + " doesn't exists.");
        Validate.checkIf(file.isFile(), "Object at path " + file.getPath() + " is not a file.");
        try {
            return saveToString(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

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


    public @Nullable File saveFile(InputStream file, String pathToSave, boolean override) {
        File fileToSave = new File(pathToSave);
        if (override)
            createFile(fileToSave);
        Validate.checkIf(fileToSave.exists(), "Cannot find file at " + pathToSave);
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


    public @Nullable File saveFile(InputStream file, String pathToSave) {
        return saveFile(file, pathToSave, false);
    }


    public @Nullable File saveFile(File file, String pathToSave, boolean override) {
        try {
            InputStream is = new FileInputStream(file);
            return saveFile(is, pathToSave, override);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nullable File saveFile(File file, String pathToSave) {
        return saveFile(file, pathToSave, false);
    }


    public @Nullable File saveFile(String file, String pathToSave, boolean override) {
        File fileToSave = new File(pathToSave);
        return saveFile(fileToSave, pathToSave, override);
    }

    public File saveFile(String file, String pathToSave) {
        return saveFile(file, pathToSave, false);
    }

    public @Nullable File createFile(File file) {
        if (file.exists()) return file;
        try {
            if (file.createNewFile()) return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    public @Nullable File createFile(String file) {
        return createFile(new File(file));
    }
}
