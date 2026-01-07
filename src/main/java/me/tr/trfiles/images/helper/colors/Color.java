package me.tr.trfiles.images.helper.colors;

import me.tr.trfiles.Validator;

public record Color(int[] argb, double[] hsl, int hexadecimal, String hex) {
    private Color() {
        this(new int[4], new double[3], 0x00000000, "");
    }

    public static Color fromARGB(int[] argb) {
        Validator.isNull(argb, "Provided rgb is null");
        double[] hsl = ColorUtility.toHSLFromARGB(argb);
        int hexadecimal = ColorUtility.toHexadecimalFromARGB(argb);
        String hex = ColorUtility.toHexFromARGB(argb);
        return new Color(argb, hsl, hexadecimal, hex);
    }

    public static Color fromHSL(double[] hsl) {
        Validator.isNull(hsl, "Provided hsl is null");
        int[] argb = ColorUtility.toARGBFromHSL(hsl);
        int hexadecimal = ColorUtility.toHexadecimalFromHSL(hsl);
        String hex = ColorUtility.toHexFromHSL(hsl);
        return new Color(argb, hsl, hexadecimal, hex);
    }

    public static Color fromHex(String hex) {
        Validator.isNull(hex, "Provided hex is null");
        double[] hsl = ColorUtility.toHSLFromHex(hex);
        int[] argb = ColorUtility.toARGBFromHex(hex);
        int hexadecimal = ColorUtility.toHexadecimalFromHex(hex);
        return new Color(argb, hsl, hexadecimal, hex);
    }

    public static Color fromHexadecimal(int hexadecimal) {
        Validator.isNull(hexadecimal, "Provided hexadecimal is null");
        int[] argb = ColorUtility.toARGBFromHexadecimal(hexadecimal);
        double[] hsl = ColorUtility.toHSLFromHexadecimal(hexadecimal);
        String hex = ColorUtility.toHexFromHexadecimal(hexadecimal);
        return new Color(argb, hsl, hexadecimal, hex);
    }
}
