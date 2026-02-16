package me.tr.trfiles.memory.implementations;

import me.tr.trfiles.memory.MemoryImage;
import me.tr.trfiles.memory.MemoryImageEntry;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class MemoryPNG extends MemoryImage {
    public static final MemoryImageEntry ENTRY = new MemoryImageEntry() {
        @Override
        public byte[] getSignature() {
            return new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        }

        @Override
        public MemoryImage newInstance(BufferedImage image) {
            return new MemoryPNG(image);
        }
    };

    /**
     * Constructs a new {@code MemoryPNG} with the specified pixel matrix.
     * <p>The primary array is cloned to prevent external reference modification.
     * Since the organization is {@code [x][y]}, the first dimension represents
     * the width and the second represents the height.</p>
     *
     * @param pixels The pixel matrix organized as {@code [width][height]}.
     * @throws NullPointerException if the {@code pixels} array is null.
     */
    protected MemoryPNG(int[][] pixels) {
        super(ENTRY, pixels);
    }

    /**
     * Constructs a {@code MemoryPNG} by extracting pixel data from a
     * {@link InputStream}.
     *
     * @param is The source {@code InputStream}.
     * @throws NullPointerException if the {@code image} is null.
     */
    public MemoryPNG(InputStream is) throws IOException {
        super(ENTRY, is);
    }

    /**
     * Constructs a {@code MemoryPNG} by reading an image from a {@link File}.
     *
     * @param file The file containing the image data.
     * @throws IOException              if an error occurs during reading or the file is invalid.
     * @throws NullPointerException     if the {@code file} object is null.
     * @throws IllegalArgumentException if the {@code file} is not a valid image.
     */
    public MemoryPNG(File file) throws IOException {
        super(ENTRY, file);
    }

    /**
     * Constructs a {@code MemoryPNG} by reading an image from the specified
     * file system path.
     *
     * @param path The string path to the image file.
     * @throws IOException              if the file cannot be found or read.
     * @throws NullPointerException     if the {@code path} is null.
     * @throws IllegalArgumentException if the {@code path} does not point to a valid image.
     */
    public MemoryPNG(String path) throws IOException {
        super(ENTRY, path);
    }

    /**
     * Constructs a {@code MemoryPNG} by extracting pixel data from a
     * {@link BufferedImage}.
     *
     * @param image The source {@code BufferedImage}.
     * @throws NullPointerException if the {@code image} is null.
     */
    public MemoryPNG(BufferedImage image) {
        super(ENTRY, image);
    }

    public static MemoryPNG ofOrThrown(String path) throws IOException {
        return ofOrThrown(path, MemoryPNG.class);
    }

    public static MemoryPNG ofOrThrown(File file) throws IOException {
        return ofOrThrown(file, MemoryPNG.class);
    }

    public static MemoryPNG ofOrThrown(InputStream is) throws IOException {
        return ofOrThrown(is, MemoryPNG.class);
    }

    public static MemoryPNG of(BufferedImage image) {
        return of(image, MemoryPNG.class);
    }

    public static Optional<MemoryPNG> of(String path) {
        return of(path, MemoryPNG.class);
    }

    public static Optional<MemoryPNG> of(File file) {
        return of(file, MemoryPNG.class);
    }

    public static Optional<MemoryPNG> of(InputStream is) {
        return of(is, MemoryPNG.class);
    }

    public static MemoryPNG ofOrNull(String path) {
        return ofOrNull(path, MemoryPNG.class);
    }

    public static MemoryPNG ofOrNull(File file) {
        return ofOrNull(file, MemoryPNG.class);
    }

    public static MemoryPNG ofOrNull(InputStream is) {
        return ofOrNull(is, MemoryPNG.class);
    }
}
