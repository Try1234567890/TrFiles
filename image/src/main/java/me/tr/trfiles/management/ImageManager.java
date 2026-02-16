package me.tr.trfiles.management;

import me.tr.trfiles.Validator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageManager {

    private ImageManager() {
    }

    public static int[][] readPixels(String image) throws IOException {
        Validator.isNull(image, "provided image path is null or empty");
        return readPixels(new File(image));
    }

    public static int[][] readPixels(File image) throws IOException {
        Validator.isNull(image, "provided image file is null or empty");
        if (!ImageUtility.isImage(image)) {
            throw new IllegalArgumentException(image.getPath() + " is not a valid image");
        }
        return readPixels(ImageIO.read(image));
    }

    public static int[][] readPixels(InputStream image) throws IOException {
        Validator.isNull(image, "provided image input stream is null or empty");
        if (!ImageUtility.isImage(image)) {
            throw new IllegalArgumentException("input stream is not a valid image");
        }
        return readPixels(ImageIO.read(image));
    }

    public static int[][] readPixels(BufferedImage image) {
        Validator.isNull(image, "provided buffered image is null or empty");
        int[][] pixels = new int[image.getWidth()][image.getHeight()];


        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int pixel = image.getRGB(x, y);

                pixels[x][y] = pixel;
            }
        }

        return pixels;
    }

}
