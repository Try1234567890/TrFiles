package me.tr.trFiles.general.utility;

import java.io.File;

public class ImageUtility extends FileUtility {


    public static boolean isImage(String file) {
        return isJpeg(file) || isPng(file) || isBmp(file) || isWbmp(file) || isGif(file) || isTiff(file);
    }

    public static boolean isJpeg(String file) {
        return getExtension(file).equalsIgnoreCase("jpg")
                || getExtension(file).equalsIgnoreCase("jpeg");
    }

    public static boolean isPng(String file) {
        return getExtension(file).equalsIgnoreCase("png");
    }

    public static boolean isBmp(String file) {
        return getExtension(file).equalsIgnoreCase("bmp");
    }


    public static boolean isWbmp(String file) {
        return getExtension(file).equalsIgnoreCase("wbmp");
    }

    public static boolean isGif(String file) {
        return getExtension(file).equalsIgnoreCase("gif");
    }

    public static boolean isTiff(String file) {
        return getExtension(file).equalsIgnoreCase("tiff")
                || getExtension(file).equalsIgnoreCase("tif");
    }

    public static boolean isImage(File file) {
        return isImage(file.getName());
    }

    public static boolean isJpeg(File file) {
        return isJpeg(file.getName());
    }

    public static boolean isPng(File file) {
        return isPng(file.getName());
    }

    public static boolean isBmp(File file) {
        return isBmp(file.getName());
    }


    public static boolean isWbmp(File file) {
        return isWbmp(file.getName());
    }

    public static boolean isGif(File file) {
        return isGif(file.getName());
    }

    public static boolean isTiff(File file) {
        return isTiff(file.getName());
    }

}
