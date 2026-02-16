package me.tr.trfiles.implementations;

import me.tr.trfiles.Image;
import me.tr.trfiles.Validator;
import me.tr.trfiles.exceptions.UnknownImplementationException;
import me.tr.trfiles.memory.MemoryImage;
import me.tr.trfiles.registries.ImagesRegistry;
import me.tr.trformatter.color.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class TrImage implements Image {
    private final File file;
    private final MemoryImage memory;
    private final TrImageEntry entry;

    protected TrImage(TrImageEntry entry, File file, MemoryImage memory) {
        Validator.isNull(memory, "Memory cannot be null.");
        this.file = file;
        this.memory = memory;
        this.entry = entry;
    }

    public static TrImage fromOrThrown(File file) throws IOException {
        TrImageEntry entry = ImagesRegistry.getImage(file).orElseThrow(() -> new UnknownImplementationException(file));
        return entry.newInstance(file);
    }

    public static TrImage fromOrThrown(String file) throws IOException {
        return fromOrThrown(new File(file));
    }

    public static Optional<? extends TrImage> from(File file) {
        try {
            return Optional.of(fromOrThrown(file));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<? extends TrImage> from(String file) {
        return from(new File(file));
    }

    public static TrImage fromOrNull(File file) {
        return from(file).orElse(null);
    }

    public static TrImage fromOrNull(String file) {
        return from(file).orElse(null);
    }

    public File getFile() {
        return file;
    }

    public MemoryImage getMemory() {
        return memory;
    }

    /**
     * Retrieve the 2D array of image pixels.
     *
     * @return the image pixels.
     */
    @Override
    public int[][] getPixels() {
        return getMemory().getPixels();
    }

    /**
     * Get the pixel at the provided coordinate.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return the pixel at the provided coordinate.
     * @throws ArrayIndexOutOfBoundsException if one of the coordinate is bigger than image size.
     */
    @Override
    public int getPixel(int x, int y) {
        return getMemory().getPixel(x, y);
    }

    /**
     * Set the pixel color at the provided coordinate.
     *
     * @param x     the x coordinate.
     * @param y     the y coordinate.
     * @param color the new pixel color.
     */
    @Override
    public void setPixel(int x, int y, Color color) {
        getMemory().setPixel(x, y, color);
    }

    /**
     * Set the pixel color at the provided coordinate.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return the pixel color at the provided coordinate.
     * @throws ArrayIndexOutOfBoundsException if one of the coordinate is bigger than image size.
     */
    @Override
    public Color getColor(int x, int y) {
        return getMemory().getColor(x, y);
    }

    /**
     * @return the image width. (or {@code getPixels().length}).
     */
    @Override
    public int getWidth() {
        return getMemory().getWidth();
    }

    /**
     * @return the image height. (or {@code getPixels()[0].length})
     */
    @Override
    public int getHeight() {
        return getMemory().getHeight();
    }

    /**
     * Write the current image to file.
     *
     * @param file the file to write to.
     */
    @Override
    public void save(File file) throws IOException {
        getMemory().save(file);
    }

    /**
     * Reload the image from image.
     *
     * @param image the image to reload from.
     */
    @Override
    public void reload(BufferedImage image) {
        getMemory().reload(image);
    }

    /**
     * Write to disk the current image and ignore any errors.
     */
    public void saveOrIgnore() {
        try {
            save(getFile());
        } catch (IOException ignore) {
        }
    }

    /**
     * Reload the image from disk and ignore any errors.
     */
    public void reloadOrIgnore() {
        try {
            reload(getFile());
        } catch (IOException ignore) {
        }
    }

    /**
     * Write to disk the current image.
     */
    public void save() throws IOException {
        save(getFile());
    }

    /**
     * Reload the image from disk.
     */
    public void reload() throws IOException {
        reload(getFile());
    }

    public TrImageEntry getEntry() {
        return entry;
    }
}
