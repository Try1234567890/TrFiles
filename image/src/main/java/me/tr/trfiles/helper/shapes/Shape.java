package me.tr.trfiles.helper.shapes;

public abstract class Shape {
    private int[][] pixels;

    private Shape() {
    }


    public int[][] getPixels() {
        return pixels;
    }

    public void setPixels(int[][] pixels) {
        this.pixels = pixels;
    }
}
