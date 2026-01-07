package me.tr.trfiles.images.helper.colors;

import me.tr.trfiles.Validator;

public class ColorUtility {

    public static int toHexadecimalFromARGB(int[] argb) {
        Validator.isNull(argb, "Provided ARGB is null");
        Validator.checkIf(argb.length == 4, "Provided ARGB array has wrong length.");
        int a = argb[0];
        int r = argb[1];
        int g = argb[2];
        int b = argb[3];
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int toHexadecimalFromHSL(double[] hsl) {
        Validator.isNull(hsl, "Provided HSL is null");
        Validator.checkIf(hsl.length == 3, "Provided HSL array has wrong length.");
        int[] argb = toARGBFromHSL(hsl);
        return toHexadecimalFromARGB(argb);
    }

    public static int toHexadecimalFromHex(String hex) {
        Validator.isNull(hex, "Provided hex is null");
        int[] argb = toARGBFromHex(hex);
        return toHexadecimalFromARGB(argb);
    }

    public static String toHexFromARGB(int[] argb) {
        Validator.isNull(argb, "Provided ARGB is null");
        Validator.checkIf(argb.length == 4, "Provided ARGB array has wrong length.");
        int a = argb[0];
        int r = argb[1];
        int g = argb[2];
        int b = argb[3];
        return String.format("#%02X%02X%02X%02X", a, r, g, b);
    }

    public static String toHexFromHSL(double[] hsl) {
        int[] argb = toARGBFromHSL(hsl);
        return toHexFromARGB(argb);
    }

    public static String toHexFromHexadecimal(int hex) {
        int[] argb = toARGBFromHexadecimal(hex);
        return toHexFromARGB(argb);
    }

    public static double[] toHSLFromARGB(int[] argb) {
        Validator.isNull(argb, "Provided ARGB is null");
        Validator.checkIf(argb.length == 4, "Provided ARGB array has wrong length.");

        double r = argb[1] / 255.0;
        double g = argb[2] / 255.0;
        double b = argb[3] / 255.0;

        double max = Math.max(r, Math.max(g, b));
        double min = Math.min(r, Math.min(g, b));
        double delta = max - min;

        double h = 0;
        double s;
        double l = (max + min) / 2.0;

        if (delta == 0) {
            h = 0;
            s = 0;
        } else {
            s = delta / (1 - Math.abs(2 * l - 1));
            if (max == r) {
                h = 60 * (((g - b) / delta) % 6);
            } else if (max == g) {
                h = 60 * (((b - r) / delta) + 2);
            } else {
                h = 60 * (((r - g) / delta) + 4);
            }
        }

        if (h < 0) h += 360;

        return new double[]{h, s, l};
    }

    public static double[] toHSLFromHex(String hex) {
        int[] argb = toARGBFromHex(hex);
        return toHSLFromARGB(argb);
    }

    public static double[] toHSLFromHexadecimal(int hex) {
        int[] argb = toARGBFromHexadecimal(hex);
        return toHSLFromARGB(argb);
    }

    public static int[] toARGBFromHex(String hex) {
        Validator.isNull(hex, "Provided hex is null");
        hex = hex.replace("#", "").trim();

        int a = 255, r, g, b;

        if (hex.length() == 6) {
            r = Integer.parseInt(hex.substring(0, 2), 16);
            g = Integer.parseInt(hex.substring(2, 4), 16);
            b = Integer.parseInt(hex.substring(4, 6), 16);
        } else if (hex.length() == 8) {
            a = Integer.parseInt(hex.substring(0, 2), 16);
            r = Integer.parseInt(hex.substring(2, 4), 16);
            g = Integer.parseInt(hex.substring(4, 6), 16);
            b = Integer.parseInt(hex.substring(6, 8), 16);
        } else {
            throw new IllegalArgumentException("Hex must have 6 or 8 characters");
        }
        return new int[]{a, r, g, b};
    }

    public static int[] toARGBFromHSL(double[] hsl) {
        Validator.isNull(hsl, "Provided HSL is null");
        Validator.checkIf(hsl.length == 3, "Provided HSL array has wrong length.");
        double h = hsl[0];
        double s = hsl[1];
        double l = hsl[2];

        double c = (1 - Math.abs(2 * l - 1)) * s;
        double x = c * (1 - Math.abs((h / 60.0) % 2 - 1));
        double m = l - c / 2.0;

        double r = 0, g = 0, b = 0;
        if (h < 60) {
            r = c;
            g = x;
            b = 0;
        } else if (h < 120) {
            r = x;
            g = c;
            b = 0;
        } else if (h < 180) {
            r = 0;
            g = c;
            b = x;
        } else if (h < 240) {
            r = 0;
            g = x;
            b = c;
        } else if (h < 300) {
            r = x;
            g = 0;
            b = c;
        } else {
            r = c;
            g = 0;
            b = x;
        }

        int R = (int) Math.round((r + m) * 255);
        int G = (int) Math.round((g + m) * 255);
        int B = (int) Math.round((b + m) * 255);

        return new int[]{255, R, G, B};
    }

    public static int[] toARGBFromHexadecimal(int hex) {
        int a = (hex >> 24) & 0xFF;
        int r = (hex >> 16) & 0xFF;
        int g = (hex >> 8) & 0xFF;
        int b = hex & 0xFF;
        return new int[]{a, r, g, b};
    }
}
