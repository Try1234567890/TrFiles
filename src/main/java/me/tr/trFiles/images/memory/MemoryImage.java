package me.tr.trFiles.images.memory;

import me.tr.trFiles.Validator;
import me.tr.trFiles.images.Image;
import me.tr.trFiles.images.helper.Direction;
import me.tr.trFiles.images.helper.Format;
import me.tr.trFiles.images.helper.colors.Color;
import me.tr.trFiles.images.helper.colors.ColorUtility;
import me.tr.trFiles.images.helper.shapes.Shape;
import org.jetbrains.annotations.Nullable;

public class MemoryImage implements Image {
    private int[][] pixels;
    private MemoryOptions options;

    protected MemoryImage() {
    }

    private MemoryImage(int[][] pixels, MemoryOptions options) {
        this.pixels = pixels;
        this.options = options;
    }

    private MemoryImage(int[][] pixels) {
        this.pixels = pixels;
    }

    public static MemoryImage of(int[][] pixels, MemoryOptions options) {
        return new MemoryImage(pixels, options);
    }

    public static MemoryImage of(int[][] pixels) {
        return new MemoryImage(pixels);
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
    public void gaussianBlur(Shape shape, double sigma) {

    }

    @Override
    public void toGrayScale() {
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                int pixel = (int) pixels[x][y];
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
    public void replace(Color before, @Nullable Color after) {
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
    public void convert(Format format) {

    }

    @Override
    public void flip(Direction direction) {
        int degrees = (360 - options().getDirection().getDegrees()) + direction.getDegrees();
        rotate(degrees);
    }

    @Override
    public MemoryOptions options() {
        if (options == null) {
            options = new MemoryOptions(this);
        }
        return options;
    }
}
