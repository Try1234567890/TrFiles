package me.tr.trfiles.memory;

import me.tr.trfiles.Image;
import me.tr.trfiles.Validator;
import me.tr.trfiles.exceptions.UnknownImplementationException;
import me.tr.trfiles.management.ImageManager;
import me.tr.trfiles.registries.MemoryImagesRegistry;
import me.tr.trformatter.color.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Optional;

/**
 * An abstract representation of an image stored in a memory-based integer matrix.
 * <p>This class provides a base implementation for pixel manipulation using a
 * two-dimensional array where access is performed via (x, y) coordinates.
 * The internal structure follows the {@code pixels[x][y]} convention,
 * which maps directly to {@code pixels[column][row]}.</p>
 *
 * @author Try___
 * @version 1.1
 */
public abstract class MemoryImage implements Image {
    private final int[][] pixels;
    private final int height;
    private final int width;
    private final MemoryImageEntry entry;

    /**
     * Constructs a new {@code MemoryImage} with the specified pixel matrix.
     * <p>The primary array is cloned to prevent external reference modification.
     * Since the organization is {@code [x][y]}, the first dimension represents
     * the width and the second represents the height.</p>
     *
     * @param pixels The pixel matrix organized as {@code [width][height]}.
     * @throws NullPointerException if the {@code pixels} array is null.
     */
    protected MemoryImage(MemoryImageEntry entry, int[][] pixels) {
        Validator.isNull(entry, "entry cannot be null.");
        Validator.isNull(pixels, "pixels are null.");
        this.entry = entry;
        this.pixels = pixels.clone();
        this.width = pixels.length;
        this.height = (pixels.length > 0) ? pixels[0].length : 0;
    }

    /**
     * Constructs a {@code MemoryImage} by extracting pixel data from a
     * {@link InputStream}.
     *
     * @param is The source {@code InputStream}.
     * @throws NullPointerException if the {@code image} is null.
     */
    protected MemoryImage(MemoryImageEntry entry, InputStream is) throws IOException {
        this(entry, ImageManager.readPixels(is));
    }

    /**
     * Constructs a {@code MemoryImage} by reading an image from a {@link File}.
     *
     * @param file The file containing the image data.
     * @throws IOException              if an error occurs during reading or the file is invalid.
     * @throws NullPointerException     if the {@code file} object is null.
     * @throws IllegalArgumentException if the {@code file} is not a valid image.
     */
    protected MemoryImage(MemoryImageEntry entry, File file) throws IOException {
        this(entry, ImageManager.readPixels(file));
    }

    /**
     * Constructs a {@code MemoryImage} by reading an image from the specified
     * file system path.
     *
     * @param path The string path to the image file.
     * @throws IOException              if the file cannot be found or read.
     * @throws NullPointerException     if the {@code path} is null.
     * @throws IllegalArgumentException if the {@code path} does not point to a valid image.
     */
    protected MemoryImage(MemoryImageEntry entry, String path) throws IOException {
        this(entry, ImageManager.readPixels(path));
    }

    /**
     * Constructs a {@code MemoryImage} by extracting pixel data from a
     * {@link BufferedImage}.
     *
     * @param image The source {@code BufferedImage}.
     * @throws NullPointerException if the {@code image} is null.
     */
    protected MemoryImage(MemoryImageEntry entry, BufferedImage image) {
        this(entry, ImageManager.readPixels(image));
    }

    public static <T extends MemoryImage> T ofOrThrown(String path, Class<T> reference) throws IOException {
        Validator.isNull(path, "path is null");
        return ofOrThrown(new File(path), reference);
    }

    public static <T extends MemoryImage> T ofOrThrown(File file, Class<T> reference) throws IOException {
        Validator.isNull(file, "file is null");
        return ofOrThrown(new FileInputStream(file), reference);
    }

    public static <T extends MemoryImage> T ofOrThrown(InputStream is, Class<T> reference) throws IOException {
        Validator.isNull(is, "is is null");
        return of(ImageIO.read(is), reference);
    }

    @SuppressWarnings("unchecked")
    public static <T extends MemoryImage> T of(BufferedImage image, Class<T> reference) {
        Validator.isNull(image, "image is null");
        Validator.isNull(reference, "reference class is null");
        MemoryImageEntry entry = MemoryImagesRegistry.getImage(reference).orElseThrow(() -> new UnknownImplementationException("The implementation of " + reference + " is not in registry."));
        return (T) entry.newInstance(image);
    }

    public static <T extends MemoryImage> Optional<T> of(String path, Class<T> reference) {
        try {
            return Optional.ofNullable(ofOrThrown(path, reference));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static <T extends MemoryImage> Optional<T> of(File file, Class<T> reference) {
        try {
            return Optional.ofNullable(ofOrThrown(file, reference));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static <T extends MemoryImage> Optional<T> of(InputStream is, Class<T> reference) {
        try {
            return Optional.ofNullable(ofOrThrown(is, reference));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static <T extends MemoryImage> T ofOrNull(String path, Class<T> reference) {
        return of(path, reference).orElse(null);
    }

    public static <T extends MemoryImage> T ofOrNull(File file, Class<T> reference) {
        return of(file, reference).orElse(null);
    }

    public static <T extends MemoryImage> T ofOrNull(InputStream is, Class<T> reference) {
        return of(is, reference).orElse(null);
    }


    /**
     * {@inheritDoc}
     *
     * @param x The horizontal coordinate.
     * @param y The vertical coordinate.
     * @return The integer decimal value of the pixel.
     * @throws ArrayIndexOutOfBoundsException if coordinates are outside the image bounds.
     */
    @Override
    public int getPixel(int x, int y) {
        checkBounds(x, y);
        return pixels[x][y];
    }

    /**
     * {@inheritDoc}
     *
     * @param x     The horizontal coordinate.
     * @param y     The vertical coordinate.
     * @param pixel The {@link Color} object containing the new pixel data.
     * @throws ArrayIndexOutOfBoundsException if coordinates are outside the image bounds.
     * @throws NullPointerException           if the provided {@code pixel} is null.
     */
    @Override
    public void setPixel(int x, int y, Color pixel) {
        checkBounds(x, y);
        pixels[x][y] = pixel.getDecimal();
    }

    /**
     * Retrieves the color of the pixel at the specified coordinates as a {@link Color} object.
     *
     * @param x The horizontal coordinate.
     * @param y The vertical coordinate.
     * @return A {@link Color} instance representing the pixel.
     * @throws ArrayIndexOutOfBoundsException if coordinates are outside the image bounds.
     */
    @Override
    public Color getColor(int x, int y) {
        checkBounds(x, y);
        return Color.ofDecimal(pixels[x][y]);
    }

    /**
     * Validates that the provided coordinates are within the matrix boundaries.
     *
     * @param x The X coordinate to validate.
     * @param y The Y coordinate to validate.
     * @throws ArrayIndexOutOfBoundsException if {@code x} or {@code y} are invalid.
     */
    private void checkBounds(int x, int y) {
        if (x < 0 || x >= width) {
            throw new ArrayIndexOutOfBoundsException("X coordinate out of bounds: " + x + " (width: " + width + ")");
        }
        if (y < 0 || y >= height) {
            throw new ArrayIndexOutOfBoundsException("Y coordinate out of bounds: " + y + " (height: " + height + ")");
        }
    }

    @Override
    public void save(File to) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, getPixel(x, y));
            }
        }

        ImageIO.write(image, "png", to);
    }

    @Override
    public void reload(BufferedImage image) {
        int[][] newPixels = ImageManager.readPixels(image);

        for (int x = 0; x < width; x++) {
            if (height >= 0)
                System.arraycopy(newPixels[x], 0, pixels[x], 0, height);
        }
    }


    /**
     * Returns a copy of the internal pixel matrix.
     *
     * @return A two-dimensional {@code int} array representing the image pixels in {@code [x][y]} format.
     */
    @Override
    public int[][] getPixels() {
        int[][] newPixels = new int[width][height];

        for (int x = 0; x < width; x++) {
            System.arraycopy(pixels[x], 0, newPixels[x], 0, height);
        }

        return newPixels;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }
}