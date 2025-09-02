package me.tr.trFiles.images;

import me.tr.trFiles.images.helper.Direction;
import me.tr.trFiles.images.helper.Format;
import me.tr.trFiles.images.helper.colors.Color;
import me.tr.trFiles.images.helper.shapes.Shape;

public interface Image {

    void resize(int width, int height);

    void removeBG();

    void rotate(int degrees);

    void gaussianBlur(Shape shape, double sigma);

    void toGrayScale();

    void replace(Color before, Color after);

    void drawShape(Shape shape);

    void addText(int x, int y, String text);

    void convert(Format format);

    void flip(Direction direction);

    void crop(int width, int height);

    int[][] getPixels();

    int getPixel(int x, int y);

    void setPixel(int x, int y, Color pixel);

    Color getColor(int x, int y);

    ImageOptions options();
}

