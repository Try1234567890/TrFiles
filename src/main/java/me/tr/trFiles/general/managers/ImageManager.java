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
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;


public class ImageManager extends FileManager {

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} by creating
     * new instance of {@link Path} from String parameter and delegating to
     * {@link #getImageFromJar(JarFile, Path)}.
     *
     * @param jarFile {@link File} to get image from.
     * @param path    String path to the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(File jarFile, String path) {
        return getImageFromJar(jarFile, getFileFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} by creating
     * new instance of {@link Path} from String parameter and delegating to
     * {@link #getImageFromJar(JarFile, Path)}.
     *
     * @param jarFile {@link File} to get image from.
     * @param file    File to the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(File jarFile, File file) {
        return getImageFromJar(jarFile, file.toPath());
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} if possible.
     *
     * @param jarFile {@link File} to get image from.
     * @param path    Path to the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(File jarFile, Path path) {
        Validate.notNull(jarFile != null, "Jar File cannot be null");
        Validate.checkIf(FileUtility.isJar(jarFile), "File at " + jarFile + " is not a jar file.");
        Validate.notNull(path != null, "Path cannot be null");
        try (JarFile jar = new JarFile(jarFile)) {
            return getImageFromJar(jar, path);
        } catch (IOException e) {
            throw new RuntimeException("Error while opening jar file at " + getStringPathFromFile(jarFile), e);
        }
    }


    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} by creating
     * new instance of {@link Path} from String parameter and delegating to
     * {@link #getImageFromJar(JarFile, Path)}.
     *
     * @param jarFile {@link JarFile} to get image from.
     * @param path    Path to the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(JarFile jarFile, String path) {
        return getImageFromJar(jarFile, getPathFromString(path));
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} by creating
     * new instance of {@link Path} from String parameter and delegating to
     * {@link #getImageFromJar(JarFile, Path)}.
     *
     * @param jarFile {@link JarFile} to get image from.
     * @param file    File to the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(JarFile jarFile, File file) {
        return getImageFromJar(jarFile, file.toPath());
    }

    /**
     * Retrieve {@link BufferedImage} from provided {@link JarFile} if possible.
     *
     * @param jarFile {@link JarFile} to get image from.
     * @param path    Path to the image into jar.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImageFromJar(JarFile jarFile, Path path) {
        Validate.notNull(jarFile != null, "Jar file cannot be null");
        String pathStr = getStringPathFromPath(path);
        Validate.checkIf(ImageUtility.isImage(pathStr), "File at " + pathStr + " is not an image");
        ZipEntry entry = jarFile.getEntry(pathStr);
        if (entry == null) {
            return null;
        }
        try (InputStream is = jarFile.getInputStream(entry)) {
            return getImage(is);
        } catch (IOException e) {
            throw new RuntimeException("Error while opening InputStream for " + path, e);
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
     * Retrieve {@link BufferedImage} from provided {@link InputStream} if possible.
     *
     * @param stream {@link InputStream} to get image from.
     * @return Instance of {@link BufferedImage} if no error occurs, otherwise null.
     */
    public @Nullable BufferedImage getImage(InputStream stream) {
        try {
            return ImageIO.read(stream);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading image", e);
        }
    }

}
