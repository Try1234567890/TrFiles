package me.tr.trFiles.images.helper;

public enum Direction {

    RIGHT(90),
    DOWN(180),
    LEFT(270),
    UP(360),
    ;

    // Degrees start from top.
    private final int degrees;

    Direction(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }
}
