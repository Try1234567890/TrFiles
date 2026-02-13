package me.tr.trfiles.memory;

import me.tr.trfiles.properties.Direction;
import me.tr.trfiles.properties.Type;
import me.tr.trfiles.properties.colors.Color;
import me.tr.trfiles.properties.shapes.Shape;

import java.io.File;

public interface Image {

    void resize(int width, int height);

    void removeBG();

    void rotate(int degrees);

    void gaussianBlur(int sigma);

    void toGrayScale();

    void replace(Color before, Color after);

    void drawShape(Shape shape);

    void addText(int x, int y, String text);

    void convert(Class<? extends Image> to);

    void flip(Direction direction);

    void crop(int width, int height);

    int[][] getPixels();

    int getPixel(int x, int y);

    void setPixel(int x, int y, Color pixel);

    Color getColor(int x, int y);

    int getWidth();

    int getHeight();

    ImageEntry getEntry();

    ImageOptions options();

    default byte[] getMagicNumbers() {
        return getEntry().getMagicNumbers();
    }

    default String[] getExtensions() {
        return getEntry().getExtensions();
    }

    default Type getType() {
        return getEntry().getType();
    }

    default boolean isValid(File file) {
        return getEntry().isValid(file);
    }
}

