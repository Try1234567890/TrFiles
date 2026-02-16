package me.tr.trfiles.memory.implementations;

import me.tr.trfiles.memory.MemoryImageEntry;
import me.tr.trfiles.memory.MemoryImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class MemoryICO extends MemoryImage {
    public static final MemoryImageEntry ENTRY = new MemoryImageEntry() {
        @Override
        public byte[] getSignature() {
            return new byte[]{0x00, 0x00, 0x01, 0x00};
        }

        @Override
        public MemoryImage newInstance(BufferedImage image) {
            return new MemoryICO(image);
        }
    };

    /**
     * Constructs a new {@code MemoryImage} with the specified pixel matrix.
     * <p>The primary array is cloned to prevent external reference modification.
     * Since the organization is {@code [x][y]}, the first dimension represents
     * the width and the second represents the height.</p>
     *
     * @param pixels The pixel matrix organized as {@code [width][height]}.
     * @throws NullPointerException if the {@code pixels} array is null.
     */
    protected MemoryICO(int[][] pixels) {
        super(ENTRY, pixels);
    }

    /**
     * Constructs a {@code MemoryImage} by extracting pixel data from a
     * {@link InputStream}.
     *
     * @param is The source {@code InputStream}.
     * @throws NullPointerException if the {@code image} is null.
     */
    public MemoryICO(InputStream is) throws IOException {
        super(ENTRY, is);
    }

    /**
     * Constructs a {@code MemoryImage} by reading an image from a {@link File}.
     *
     * @param file The file containing the image data.
     * @throws IOException              if an error occurs during reading or the file is invalid.
     * @throws NullPointerException     if the {@code file} object is null.
     * @throws IllegalArgumentException if the {@code file} is not a valid image.
     */
    public MemoryICO(File file) throws IOException {
        super(ENTRY, file);
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
    public MemoryICO(String path) throws IOException {
        super(ENTRY, path);
    }

    /**
     * Constructs a {@code MemoryImage} by extracting pixel data from a
     * {@link BufferedImage}.
     *
     * @param image The source {@code BufferedImage}.
     * @throws NullPointerException if the {@code image} is null.
     */
    public MemoryICO(BufferedImage image) {
        super(ENTRY, image);
    }


    public static MemoryICO ofOrThrown(String path) throws IOException {
        return ofOrThrown(path, MemoryICO.class);
    }

    public static MemoryICO ofOrThrown(File file) throws IOException {
        return ofOrThrown(file, MemoryICO.class);
    }

    public static MemoryICO ofOrThrown(InputStream is) throws IOException {
        return ofOrThrown(is, MemoryICO.class);
    }

    public static MemoryICO of(BufferedImage image) {
        return of(image, MemoryICO.class);
    }

    public static Optional<MemoryICO> of(String path) {
        return of(path, MemoryICO.class);
    }

    public static Optional<MemoryICO> of(File file) {
        return of(file, MemoryICO.class);
    }

    public static Optional<MemoryICO> of(InputStream is) {
        return of(is, MemoryICO.class);
    }

    public static MemoryICO ofOrNull(String path) {
        return ofOrNull(path, MemoryICO.class);
    }

    public static MemoryICO ofOrNull(File file) {
        return ofOrNull(file, MemoryICO.class);
    }

    public static MemoryICO ofOrNull(InputStream is) {
        return ofOrNull(is, MemoryICO.class);
    }
}
