package me.tr.trFiles.general.managers;

import me.tr.trFiles.general.utility.FileUtility;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;


public class ImageManager extends FileManager {

    public void saveImageFromJar(String jar, String path, String to) {
        saveImageFromJar(getFileFromString(jar), getFileFromString(path), getFileFromString(to));
    }

    public void saveImageFromJar(String jar, String path) {
        saveImageFromJar(getFileFromString(jar), getFileFromString(path), getFileFromString(path));
    }

    public void saveImageFromJar(File jar, String path) {
        saveImageFromJar(jar, getFileFromString(path));
    }

    public void saveImageFromJar(File jar, String path, String to) {
        saveImageFromJar(jar, getFileFromString(path), getFileFromString(to));
    }

    public void saveImageFromJar(File jar, File file) {
        try (JarFile jarFile = new JarFile(jar)) {
            saveImageFromJar(jarFile, file, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImageFromJar(File jar, File file, File to) {
        try (JarFile jarFile = new JarFile(jar)) {
            saveImageFromJar(jarFile, file, to);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveImageFromJar(JarFile jarFile, String path, String to) {
        saveImageFromJar(jarFile, getFileFromString(path), getFileFromString(to));
    }

    public void saveImageFromJar(JarFile jarFile, String path) {
        saveImageFromJar(jarFile, getFileFromString(path));
    }

    // todo comments
    public void saveImageFromJar(JarFile jarFile, File file) {
        saveImageFromJar(jarFile, file, file);
    }


    /**
     * Save {@link BufferedImage} from provided {@link JarFile} if possible.
     *
     * @param jarFile {@link JarFile} to get image from.
     * @param file    {@link File} representing the image into jar.
     * @param to      {@link File} representing the file to save image to.
     */
    public void saveImageFromJar(JarFile jarFile, File file, File to) {
        BufferedImage image = getImageFromJar(jarFile, file);
        if (image == null) return;
        try {
            ImageIO.write(image, FileUtility.getExtension(file), to);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving image " + getStringPathFromFile(file), e);
        }
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link File} if possible.
     *
     * @param jar  Path to file representing jar to get image from.
     * @param path Path to file representing the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(String jar, String path) {
        return getImageFromJar(getFileFromString(jar), getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link File} if possible.
     *
     * @param jar  {@link File} representing jar to get image from.
     * @param file {@link File} representing the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(File jar, File file) {
        try (JarFile jarFile = new JarFile(jar)) {
            return getImageFromJar(jarFile, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link File} if possible.
     *
     * @param jar  {@link File} representing jar to get image from.
     * @param path Path to file representing the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(File jar, String path) {
        return getImageFromJar(jar, getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} if possible.
     *
     * @param jarFile {@link JarFile} to get image from.
     * @param path    Path to file representing the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(JarFile jarFile, String path) {
        return getImageFromJar(jarFile, getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} if possible.
     *
     * @param jarFile {@link JarFile} to get image from.
     * @param file    {@link File} representing the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(JarFile jarFile, File file) {
        ZipEntry entry = jarFile.getEntry(getStringPathFromFile(file));
        if (entry == null) {
            return null;
        }
        try {
            InputStream is = jarFile.getInputStream(entry);
            return getImage(is, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieve {@link BufferedImage} from the provided path to file if possible.
     *
     * @param path Path to file to get image from.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImage(String path) {
        return getImage(getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link File} if possible.
     *
     * @param file {@link File} to get image from.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImage(File file) {
        try (InputStream stream = new FileInputStream(file)) {
            return getImage(stream);
        } catch (IOException e) {
            throw new RuntimeException("Error while creating new InputString for file " + getStringPathFromFile(file), e);
        }
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link InputStream} if possible.
     *
     * @param stream {@link InputStream} to get image from.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImage(InputStream stream) {
        return getImage(stream, true);
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link InputStream} if possible.
     *
     * @param stream      {@link InputStream} to get image from.
     * @param closeStream Should close stream after finish operations.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImage(InputStream stream, boolean closeStream) {
        try {
            BufferedImage image = ImageIO.read(stream);
            if (closeStream)
                stream.close();
            return image;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading image", e);
        }
    }

}
