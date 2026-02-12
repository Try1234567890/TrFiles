package me.tr.trfiles.implementations;

import me.tr.trfiles.Validator;
import me.tr.trfiles.management.FileUtility;
import me.tr.trfiles.helper.TrImageInputStream;
import me.tr.trfiles.management.ImageUtility;
import me.tr.trfiles.memory.MemoryImage;
import me.tr.trfiles.memory.MemoryOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class TrImage extends MemoryImage {
    private final File file;
    private BufferedImage image;
    private TrImageOptions options;

    public TrImage(String path) {
        this(FileUtility.getFileFromString(path));
    }

    public TrImage(File file) {
        Validator.checkIf(file != null, "File cannot be null.");
        Validator.checkIf(ImageUtility.isImage(file), "File not exists.");
        try (InputStream is = new FileInputStream(file)) {
            Validator.checkIf(is.available() > 0, "InputStream cannot be empty.");
            this.file = file;
            this.image = ImageIO.read(is);
            int width = image.getWidth();
            int height = image.getHeight();

            int[][] pixels = new int[width][height];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixels[x][y] = image.getRGB(x, y);
                }
            }

            setPixels(pixels);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while creating a new TrImage", e);
        }
    }

    public TrImage(TrImageInputStream is) {
        Validator.checkIf(is != null, "File cannot be null.");
        Validator.checkIf(ImageUtility.isImage(is), "File not exists.");
        try (is) {
            Validator.checkIf(is.available() > 0, "InputStream cannot be empty.");
            this.file = is.getFile();
            this.image = ImageIO.read(is);
            int width = image.getWidth();
            int height = image.getHeight();

            int[][] pixels = new int[width][height];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixels[x][y] = image.getRGB(x, y);
                }
            }

            setPixels(pixels);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while creating a new TrImage", e);
        }
    }

    public void save() throws IOException {
        BufferedImage image = new BufferedImage(getPixels().length, getPixels()[0].length, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < getPixels().length; x++) {
            for (int y = 0; y < getPixels()[0].length; y++) {
                image.setRGB(x, y, getPixel(x, y));
            }
        }

        ImageIO.write(image, options().getFormat().getExtensions()[0], file);

    }

    public TrImageOptions getOptions() {
        return options;
    }

    public File getFile() {
        return file;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setOptions(TrImageOptions options) {
        this.options = options;
    }

    @Override
    public MemoryOptions options() {
        if (options == null) {
            options = new TrImageOptions(this);
        }
        return options;
    }

    protected abstract MemoryImage toPng(int[][] pixels);

    protected abstract MemoryImage fromPng(int[][] pixels);

}
