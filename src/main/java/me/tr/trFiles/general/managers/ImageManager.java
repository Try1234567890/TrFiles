package me.tr.trFiles.general.managers;

import me.tr.trFiles.general.utility.FileUtility;
import me.tr.trFiles.general.utility.ImageUtility;
import me.tr.trFiles.general.utility.Validate;
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
        saveImageFromJar(jar, file, file);
    }

    public void saveImageFromJar(File jar, File file, File to) {
        Validate.notNull(jar != null, "Jar file cannot be null");
        Validate.checkIf(jar.isFile(), "Object at " + getStringPathFromFile(jar) + " is not a file");
        Validate.checkIf(FileUtility.isJar(jar), "File at " + getStringPathFromFile(jar) + " is not a jar file");
        try (JarFile jarFile = new JarFile(jar)) {
            saveImageFromJar(jarFile, file, to);
        } catch (IOException e) {
            throw new RuntimeException("Error while opening jar file at " + getStringPathFromFile(jar), e);
        }
    }
    // todo comments
    public void saveImageFromJar(JarFile jarFile, String path, String to) {
        saveImageFromJar(jarFile, getFileFromString(path), getFileFromString(to));
    }


    /**
     * Save image at the provided path into {@link JarFile} by delegating
     * to {@link #saveImageFromJar(JarFile, File)}
     *
     * @param jarFile Jar to get image from.
     * @param path    {@link File} into jar where found image
     */
    public void saveImageFromJar(JarFile jarFile, String path) {
        saveImageFromJar(jarFile, getFileFromString(path));
    }

    /**
     * Save image at {@link File} into {@link JarFile} by delegating
     * to {@link #saveImageFromJar(JarFile, File, File)} and using as
     * last parameter the same of the into jar file parameter.
     *
     * @param jarFile Jar to get image from.
     * @param file    {@link File} into jar where found image.
     */
    public void saveImageFromJar(JarFile jarFile, File file) {
        saveImageFromJar(jarFile, file, file);
    }

    /**
     * Save image from {@link JarFile} by retrieving image into jar at
     * the path of first {@link File} parameter and saving it at the
     * path of second {@link File} parameter.
     *
     * @param jarFile Jar to get image from.
     * @param file    {@link File} into jar where found image.
     * @param to      {@link File} to save image into.
     */
    public void saveImageFromJar(JarFile jarFile, File file, File to) {
        BufferedImage image = getImageFromJar(jarFile, file);
        if (image == null)
            return;
        try {
            ImageIO.write(image, FileUtility.getExtension(file), to);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving image " + getStringPathFromFile(file), e);
        }
    }

    /**
     * Retrieve {@link BufferedImage} from jar by creating new instances of {@link File}
     * from parameters and using them to delegate to {@link #getImageFromJar(File, File)}
     *
     * @param jar  Jar file to get image from.
     * @param path Path to image into jar.
     * @return Instance if {@link BufferedImage} found if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(String jar, String path) {
        return getImageFromJar(getFileFromString(jar), getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from jar by creating new instance of {@link JarFile}
     * from first file parameter and delegating to {@link #getImageFromJar(JarFile, File)}.
     *
     * @param jar  Jar file to get image from.
     * @param file File to image into jar.
     * @return Instance if {@link BufferedImage} found if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(File jar, File file) {
        try (JarFile jarFile = new JarFile(jar)) {
            return getImageFromJar(jarFile, file);
        } catch (IOException e) {
            throw new RuntimeException("Error while opening jar at " + jar.getPath(), e);
        }
    }

    /**
     * Retrieve {@link BufferedImage} from jar by creating new instance of {@link File}
     * from String parameter and delegating to {@link #getImageFromJar(File, File)}.
     *
     * @param jar  Jar file to get image from.
     * @param path Path to the image into jar.
     * @return Instance if {@link BufferedImage} found if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(File jar, String path) {
        return getImageFromJar(jar, getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} by creating
     * new instance of {@link File} from String parameter and delegating to
     * {@link #getImageFromJar(JarFile, File)}.
     *
     * @param jarFile {@link JarFile} to get image from.
     * @param path    Path to the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(JarFile jarFile, String path) {
        return getImageFromJar(jarFile, getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} if possible.
     *
     * @param jarFile {@link JarFile} to get image from.
     * @param file    File to the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(JarFile jarFile, File file) {
        Validate.notNull(jarFile != null, "Jar file cannot be null");
        Validate.checkIf(ImageUtility.isImage(file), "File at " + getStringPathFromFile(file) + " is not an image");
        ZipEntry entry = jarFile.getEntry(getStringPathFromFile(file));
        if (entry == null) {
            return null;
        }
        try (InputStream is = jarFile.getInputStream(entry)) {
            return getImage(is, true);
        } catch (IOException e) {
            throw new RuntimeException("Error while opening InputStream for " + getStringPathFromFile(file), e);
        }
    }

    /**
     * Retrieve {@link BufferedImage} from the provided path to file by creating
     * new instance of {@link File} from String parameter and delegating to
     * {@link #getImage(File)}.
     *
     * @param path Path to file to get image from.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImage(String path) {
        return getImage(getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link File} by creating
     * new instance of {@link InputStream} from {@link File} parameter and delegating to
     * {@link #getImage(InputStream)}.
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
     * Retrieve {@link BufferedImage} from provided {@link InputStream}
     * by delegating to {@link #getImage(InputStream, boolean)} use as boolean <code>false</code>.
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
