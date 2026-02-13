package me.tr.trfiles.helper;

public enum Direction {

    RIGHT(90),
    DOWN(180),
    LEFT(270),
    UP(360),
    ;

    // Degrees starts from top.
    private final int degrees;

    Direction(int degrees) {
        this.degrees = degrees;
    }

    public int getDegrees() {
        return degrees;
    }
}
