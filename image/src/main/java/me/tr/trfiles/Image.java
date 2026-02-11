package me.tr.trfiles;

import me.tr.trfiles.helper.Direction;
import me.tr.trfiles.helper.Format;
import me.tr.trfiles.helper.colors.Color;
import me.tr.trfiles.helper.shapes.Shape;

public interface Image {

    void resize(int width, int height);

    void removeBG();

    void rotate(int degrees);

    void gaussianBlur(int sigma);

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

    int getWidth();

    int getHeight();

    ImageOptions options();
}

