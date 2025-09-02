package me.tr.trFiles.images.management;

import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.management.FileUtility;
import me.tr.trFiles.images.helper.Format;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageUtility {
    private ImageUtility() {
    }

    public static boolean isImage(InputStream is) {
        return isPNG(is) ||
                isJPG(is) ||
                isGIF(is) ||
                isBMP(is) ||
                isTIFF(is) ||
                isWEBP(is) ||
                isICO(is) ||
                isSVG(is) ||
                isEPS(is) ||
                isPDF(is);
    }

    public static boolean isPNG(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.PNG.getMagicNumbers());
    }


    public static boolean isJPG(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.JPG.getMagicNumbers());
    }


    public static boolean isGIF(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.GIF.getMagicNumbers());
    }


    public static boolean isBMP(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.BMP.getMagicNumbers());
    }


    public static boolean isTIFF(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.TIFF.getMagicNumbers());
    }


    public static boolean isWEBP(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.WEBP.getMagicNumbers());
    }


    public static boolean isICO(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.ICO.getMagicNumbers());
    }


    public static boolean isSVG(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.SVG.getMagicNumbers());
    }


    public static boolean isEPS(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.EPS.getMagicNumbers());
    }


    public static boolean isPDF(InputStream is) {
        return FileUtility.hasMagicNumber(is, Format.PDF.getMagicNumbers());
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
