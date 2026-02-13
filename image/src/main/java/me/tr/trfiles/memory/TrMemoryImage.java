package me.tr.trfiles.memory;

import me.tr.trfiles.Image;
import me.tr.trfiles.ImageEntry;
import me.tr.trfiles.Validator;
import me.tr.trfiles.exceptions.UnknownImplementationException;
import me.tr.trfiles.properties.Direction;
import me.tr.trfiles.properties.colors.Color;
import me.tr.trfiles.properties.colors.ColorUtility;
import me.tr.trfiles.properties.shapes.Shape;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class MemoryImage implements Image {
    private int[][] pixels;
    private MemoryImageOptions options;
    private final ImageEntry entry;

    protected MemoryImage(ImageEntry entry, int[][] pixels, MemoryImageOptions options) {
        this.entry = entry;
        this.pixels = pixels;
        this.options = options;
    }

    public MemoryImage(InputStream is, MemoryImageOptions options) {
        ImageEntry entry = ImageEntry.of(is).orElseThrow(() -> new UnknownImplementationException("The implementation of provided image is not recognized or not supported."));
        int[][] pixels = readPixels(is).orElseThrow(() -> new RuntimeException("An error occurs while reading the provided image."));
        this(entry, pixels, options);
    }

    public MemoryImage(InputStream is) {
        this(is, null);
    }

    @Override
    public int[][] getPixels() {
        return pixels;
    }

    public void setPixels(int[][] pixels) {
        this.pixels = pixels;
    }

    @Override
    public void setPixel(int x, int y, Color pixel) {
        try {
            this.pixels[x][y] = pixel.hexadecimal();
        } catch (ArrayIndexOutOfBoundsException ignore) {
        }
    }

    @Override
    public int getPixel(int x, int y) {
        try {
            return this.pixels[x][y];
        } catch (ArrayIndexOutOfBoundsException ignore) {
            return -1;
        }
    }

    @Override
    public Color getColor(int x, int y) {
        return Color.fromHexadecimal(getPixel(x, y));
    }

    @Override
    public int getWidth() {
        return getPixels().length;
    }

    @Override
    public int getHeight() {
        return getPixels().length > 1 ? getPixels()[0].length : 0;
    }

    @Override
    public ImageEntry getEntry() {
        return entry;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void removeBG() {

    }

    @Override
    public void rotate(int degrees) {
        int[][] pixels = getPixels();
        double angle = Math.toRadians(degrees);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        int width = pixels.length;
        int height = pixels[0].length;

        int centreX = width / 2;
        int centreY = height / 2;

        int newWidth = (int) Math.round(Math.abs(width * cos) + Math.abs(height * sin));
        int newHeight = (int) Math.round(Math.abs(height * cos) + Math.abs(width * sin));

        int newCentreX = newWidth / 2;
        int newCentreY = newHeight / 2;

        int[][] rotated = new int[newWidth][newHeight];

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                double dx = x - newCentreX;
                double dy = y - newCentreY;

                double srcX = dx * cos + dy * sin + centreX;
                double srcY = -dx * sin + dy * cos + centreY;

                if (srcX >= 0 && srcX < width && srcY >= 0 && srcY < height) {
                    rotated[x][y] = pixels[(int) srcX][(int) srcY];
                }
            }
        }

        this.pixels = rotated;
    }

    @Override
    public void gaussianBlur(int sigma) {
    }

    @Override
    public void toGrayScale() {
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                int pixel = pixels[x][y];
                int[] argb = ColorUtility.toARGBFromHexadecimal(pixel);

                int r = argb[0];
                int g = argb[1];
                int b = argb[2];
                int a = argb[3];

                int gray = (int) Math.round((0.299 * r) + (0.587 * g) + (0.114 * b));

                int newPixel = (gray << 24) | (gray << 16) | (gray << 8) | a;

                pixels[x][y] = newPixel;
            }
        }
    }


    @Override
    public void replace(Color before, Color after) {
        Validator.isNull(before, "Color to replace cannot be null");
        int beforeHex = before.hexadecimal();
        int afterHex = (after != null) ? after.hexadecimal() : 0x00000000;

        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                if (pixels[x][y] == beforeHex) {
                    pixels[x][y] = afterHex;
                }
            }
        }
    }

    @Override
    public void drawShape(Shape shape) {

    }

    @Override
    public void addText(int x, int y, String text) {

    }

    @Override
    public void crop(int width, int height) {

    }

    @Override
    public void convert(Class<? extends Image> to) {

    }

    @Override
    public void flip(Direction direction) {
        int degrees = (360 - options().getDirection().getDegrees()) + direction.getDegrees();
        rotate(degrees);
    }

    @Override
    public MemoryImageOptions options() {
        if (options == null) {
            options = new MemoryImageOptions(this);
        }
        return options;
    }

    private static Optional<int[][]> readPixels(InputStream is) {
        try {
            BufferedImage image = ImageIO.read(is);

            int width = image.getWidth();
            int height = image.getHeight();
            int[][] pixels = new int[width][height];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    int pixel = image.getRGB(x, y);
                    pixels[x][y] = pixel;
                }
            }

            return Optional.of(pixels);
        } catch (IOException ignored) {
        }
        return Optional.empty();
    }
}
