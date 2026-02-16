package me.tr.trfiles.memory.implementations;

import me.tr.trfiles.memory.MemoryImage;
import me.tr.trfiles.memory.MemoryImageEntry;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class MemoryJPG extends MemoryImage {
    public static final MemoryImageEntry ENTRY = new MemoryImageEntry() {
        @Override
        public byte[] getSignature() {
            return new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
        }

        @Override
        public MemoryImage newInstance(BufferedImage image) {
            return new MemoryJPG(image);
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
    protected MemoryJPG(int[][] pixels) {
        super(ENTRY, pixels);
    }

    /**
     * Constructs a {@code MemoryImage} by extracting pixel data from a
     * {@link InputStream}.
     *
     * @param is The source {@code InputStream}.
     * @throws NullPointerException if the {@code image} is null.
     */
    public MemoryJPG(InputStream is) throws IOException {
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
    public MemoryJPG(File file) throws IOException {
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
    public MemoryJPG(String path) throws IOException {
        super(ENTRY, path);
    }

    /**
     * Constructs a {@code MemoryImage} by extracting pixel data from a
     * {@link BufferedImage}.
     *
     * @param image The source {@code BufferedImage}.
     * @throws NullPointerException if the {@code image} is null.
     */
    public MemoryJPG(BufferedImage image) {
        super(ENTRY, image);
    }


    public static MemoryJPG ofOrThrown(String path) throws IOException {
        return ofOrThrown(path, MemoryJPG.class);
    }

    public static MemoryJPG ofOrThrown(File file) throws IOException {
        return ofOrThrown(file, MemoryJPG.class);
    }

    public static MemoryJPG ofOrThrown(InputStream is) throws IOException {
        return ofOrThrown(is, MemoryJPG.class);
    }

    public static MemoryJPG of(BufferedImage image) {
        return of(image, MemoryJPG.class);
    }

    public static Optional<MemoryJPG> of(String path) {
        return of(path, MemoryJPG.class);
    }

    public static Optional<MemoryJPG> of(File file) {
        return of(file, MemoryJPG.class);
    }

    public static Optional<MemoryJPG> of(InputStream is) {
        return of(is, MemoryJPG.class);
    }

    public static MemoryJPG ofOrNull(String path) {
        return ofOrNull(path, MemoryJPG.class);
    }

    public static MemoryJPG ofOrNull(File file) {
        return ofOrNull(file, MemoryJPG.class);
    }

    public static MemoryJPG ofOrNull(InputStream is) {
        return ofOrNull(is, MemoryJPG.class);
    }
}
