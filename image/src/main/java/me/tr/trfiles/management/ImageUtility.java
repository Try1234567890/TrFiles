package me.tr.trfiles.management;

import me.tr.trfiles.Validator;
import me.tr.trfiles.file.management.FileUtility;
import me.tr.trfiles.helper.Format;

import java.io.*;

public class ImageUtility {
    private ImageUtility() {
    }

    public static boolean isImage(InputStream is) {
        try (BufferedInputStream bis = is instanceof BufferedInputStream ? (BufferedInputStream) is : new BufferedInputStream(is)) {
            bis.mark(Integer.MAX_VALUE);
            return isGIF(bis, false) ||
                    isPNG(bis, false) ||
                    isJPG(bis, false) ||
                    isBMP(bis, false) ||
                    isTIFF(bis, false) ||
                    isWEBP(bis, false) ||
                    isICO(bis, false) ||
                    isSVG(bis, false) ||
                    isEPS(bis, false) ||
                    isPDF(bis, false);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while closing the input stream.", e);
        }
    }

    public static boolean isPNG(InputStream is) {
        return isPNG(is, true);
    }


    public static boolean isJPG(InputStream is) {
        return isJPG(is, true);
    }


    public static boolean isGIF(InputStream is) {
        return isGIF(is, true);
    }


    public static boolean isBMP(InputStream is) {
        return isBMP(is, true);
    }


    public static boolean isTIFF(InputStream is) {
        return isTIFF(is, true);
    }


    public static boolean isWEBP(InputStream is) {
        return isWEBP(is, true);
    }


    public static boolean isICO(InputStream is) {
        return isICO(is, true);
    }


    public static boolean isSVG(InputStream is) {
        return isSVG(is, true);
    }


    public static boolean isEPS(InputStream is) {
        return isEPS(is, true);
    }


    public static boolean isPDF(InputStream is) {
        return isPDF(is, true);
    }

    public static boolean isPNG(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.PNG.getMagicNumbers(), close);
    }


    public static boolean isJPG(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.JPG.getMagicNumbers(), close);
    }


    public static boolean isGIF(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.GIF.getMagicNumbers(), close);
    }


    public static boolean isBMP(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.BMP.getMagicNumbers(), close);
    }


    public static boolean isTIFF(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.TIFF.getMagicNumbers(), close);
    }


    public static boolean isWEBP(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.WEBP.getMagicNumbers(), close);
    }


    public static boolean isICO(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.ICO.getMagicNumbers(), close);
    }


    public static boolean isSVG(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.SVG.getMagicNumbers(), close);
    }


    public static boolean isEPS(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.EPS.getMagicNumbers(), close);
    }


    public static boolean isPDF(InputStream is, boolean close) {
        return FileUtility.hasMagicNumber(is, Format.PDF.getMagicNumbers(), close);
    }

    public static boolean isImage(File file) {
        validate(file);
        try {
            return isImage(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isPNG(File file) {
        validate(file);
        try {
            return Format.PNG.isValid(file) && isPNG(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isJPG(File file) {
        validate(file);
        try {
            return Format.JPG.isValid(file) && isJPG(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isGIF(File file) {
        validate(file);
        try {
            return Format.GIF.isValid(file) && isGIF(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isBMP(File file) {
        validate(file);
        try {
            return Format.BMP.isValid(file) && isBMP(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isTIFF(File file) {
        validate(file);
        try {
            return Format.TIFF.isValid(file) && isTIFF(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isWEBP(File file) {
        validate(file);
        try {
            return Format.WEBP.isValid(file) && isWEBP(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isICO(File file) {
        validate(file);
        try {
            return Format.ICO.isValid(file) && isICO(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isSVG(File file) {
        validate(file);
        try {
            return Format.SVG.isValid(file) && isSVG(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isEPS(File file) {
        validate(file);
        try {
            return Format.EPS.isValid(file) && isEPS(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isPDF(File file) {
        validate(file);
        try {
            return Format.PDF.isValid(file) && isPDF(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    private static void validate(File file) {
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.exists(), "File not exists");
        Validator.checkIf(!file.isDirectory(), "Object at " + file.getPath() + " is a folder.");
    }

}
