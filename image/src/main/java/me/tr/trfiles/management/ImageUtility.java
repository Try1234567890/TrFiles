package me.tr.trfiles.management;

import me.tr.trfiles.Validator;
import me.tr.trfiles.implementations.ico.ICO;
import me.tr.trfiles.implementations.jpg.JPG;
import me.tr.trfiles.implementations.png.PNG;
import me.tr.trfiles.memory.implementations.MemoryICO;
import me.tr.trfiles.memory.implementations.MemoryJPG;
import me.tr.trfiles.memory.implementations.MemoryPNG;

import java.io.*;

public class ImageUtility {
    private ImageUtility() {
    }

    public static boolean isImage(InputStream is) {
        try {
            byte[] header = is.readNBytes(32);

            return //isGIF(header) ||
                    isPNG(header) ||
                    isJPG(header) ||
                    //isBMP(header) ||
                    //isTIFF(header) ||
                    //isWEBP(header) ||
                    isICO(header);
                    //isSVG(header) ||
                    //isEPS(header) ||
                    //isPDF(header);

        } catch (IOException e) {
            throw new RuntimeException("Error while reading stream.", e);
        }
    }

    public static boolean isPNG(byte[] bytes) {
        return FileUtility.matchesMagic(bytes, MemoryPNG.ENTRY.getSignature());
    }


    public static boolean isJPG(byte[] bytes) {
        return FileUtility.matchesMagic(bytes, MemoryJPG.ENTRY.getSignature());
    }


    public static boolean isICO(byte[] bytes) {
        return FileUtility.matchesMagic(bytes, MemoryICO.ENTRY.getSignature());
    }



    //public static boolean isGIF(byte[] bytes) {
    //    return FileUtility.matchesMagic(bytes, MemoryGIF.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isBMP(byte[] bytes) {
    //    return FileUtility.matchesMagic(bytes, MemoryBMP.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isTIFF(byte[] bytes) {
    //    return FileUtility.matchesMagic(bytes, MemoryTIFF.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isWEBP(byte[] bytes) {
    //    return FileUtility.matchesMagic(bytes, MemoryWEBP.ENTRY.getMagicNumbers());
    //}
//
    //public static boolean isSVG(byte[] bytes) {
    //    return FileUtility.matchesMagic(bytes, MemorySVG.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isEPS(byte[] bytes) {
    //    return FileUtility.matchesMagic(bytes, MemoryEPS.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isPDF(byte[] bytes) {
    //    return FileUtility.matchesMagic(bytes, MemoryPDF.ENTRY.getMagicNumbers());
    //}

    public static boolean isPNG(InputStream is) {
        return FileUtility.hasMagicNumber(is, MemoryPNG.ENTRY.getSignature());
    }


    public static boolean isJPG(InputStream is) {
        return FileUtility.hasMagicNumber(is, MemoryJPG.ENTRY.getSignature());
    }


    public static boolean isICO(InputStream is) {
        return FileUtility.hasMagicNumber(is, MemoryICO.ENTRY.getSignature());
    }



    //public static boolean isGIF(InputStream is) {
    //    return FileUtility.hasMagicNumber(is, MemoryGIF.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isBMP(InputStream is) {
    //    return FileUtility.hasMagicNumber(is, MemoryBMP.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isTIFF(InputStream is) {
    //    return FileUtility.hasMagicNumber(is, MemoryTIFF.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isWEBP(InputStream is) {
    //    return FileUtility.hasMagicNumber(is, MemoryWEBP.ENTRY.getMagicNumbers());
    //}
//
    //public static boolean isSVG(InputStream is) {
    //    return FileUtility.hasMagicNumber(is, MemorySVG.ENTRY.getMagicNumbers());
    //}
//
//
    //public static boolean isEPS(InputStream is) {
    //    return FileUtility.hasMagicNumber(is, MemoryEPS.ENTRY.getMagicNumbers());
    //}


    //public static boolean isPDF(InputStream is) {
    //    return FileUtility.hasMagicNumber(is, MemoryPDF.ENTRY.getMagicNumbers());
    //}

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
            return PNG.isValid(file) && isPNG(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isJPG(File file) {
        validate(file);
        try {
            return JPG.isValid(file) && isJPG(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    public static boolean isICO(File file) {
        validate(file);
        try {
            return ICO.isValid(file) && isICO(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
        }
    }

    //public static boolean isGIF(File file) {
    //    validate(file);
    //    try {
    //        return GIF.ENTRY.isValid(file) && isGIF(new FileInputStream(file));
    //    } catch (FileNotFoundException e) {
    //        throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
    //    }
    //}
//
    //public static boolean isBMP(File file) {
    //    validate(file);
    //    try {
    //        return BMP.ENTRY.isValid(file) && isBMP(new FileInputStream(file));
    //    } catch (FileNotFoundException e) {
    //        throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
    //    }
    //}
//
    //public static boolean isTIFF(File file) {
    //    validate(file);
    //    try {
    //        return TIFF.ENTRY.isValid(file) && isTIFF(new FileInputStream(file));
    //    } catch (FileNotFoundException e) {
    //        throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
    //    }
    //}
//
    //public static boolean isWEBP(File file) {
    //    validate(file);
    //    try {
    //        return WEBP.ENTRY.isValid(file) && isWEBP(new FileInputStream(file));
    //    } catch (FileNotFoundException e) {
    //        throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
    //    }
    //}
//
    //public static boolean isSVG(File file) {
    //    validate(file);
    //    try {
    //        return SVG.ENTRY.isValid(file) && isSVG(new FileInputStream(file));
    //    } catch (FileNotFoundException e) {
    //        throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
    //    }
    //}
//
    //public static boolean isEPS(File file) {
    //    validate(file);
    //    try {
    //        return EPS.ENTRY.isValid(file) && isEPS(new FileInputStream(file));
    //    } catch (FileNotFoundException e) {
    //        throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
    //    }
    //}
//
    //public static boolean isPDF(File file) {
    //    validate(file);
    //    try {
    //        return PDF.ENTRY.isValid(file) && isPDF(new FileInputStream(file));
    //    } catch (FileNotFoundException e) {
    //        throw new RuntimeException("An error occurs while opening an Input Stream for " + file.getPath(), e);
    //    }
    //}

    private static void validate(File file) {
        Validator.isNull(file, "File cannot be null");
        Validator.checkIf(file.exists(), "File not exists");
        Validator.checkIf(file.isFile(), file.getPath() + " is not a file.");
        Validator.checkIf(file.canRead(), "File is not readable");
    }

}
