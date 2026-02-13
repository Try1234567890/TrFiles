package me.tr.trfiles.properties;

import java.io.File;

public enum Format {
    // Raster
    PNG(Type.RASTER, new String[]{"png"}, new byte[]{(byte) 0x89, 0x50, 0x4E, 0x47}),
    JPG(Type.RASTER, new String[]{"jpg", "jpeg"}, new byte[]{(byte) 0xFF, (byte) 0xD8, (byte) 0xFF}),
    GIF(Type.RASTER, new String[]{"gif"}, new byte[]{0x47, 0x49, 0x46, 0x38}),
    BMP(Type.RASTER, new String[]{"bmp"}, new byte[]{0x42, 0x4D}),
    TIFF(Type.RASTER, new String[]{"tiff", "tif"}, new byte[]{0x49, 0x49, 0x2A, 0x00}),
    WEBP(Type.RASTER, new String[]{"webp"}, new byte[]{0x52, 0x49, 0x46, 0x46}),
    ICO(Type.RASTER, new String[]{"ico"}, new byte[]{0x00, 0x00, 0x01, 0x00}),

    // Vectorial
    SVG(Type.VECTORIAL, new String[]{"svg"}, new byte[]{0x3C, 0x3F, 0x78, 0x6D, 0x6C}), // <?xml
    EPS(Type.VECTORIAL, new String[]{"eps"}, new byte[]{0x25, 0x21}), // %!
    PDF(Type.VECTORIAL, new String[]{"pdf"}, new byte[]{0x25, 0x50, 0x44, 0x46}), // %PDF


    ;

    private final Type type;
    private final String[] extensions;
    private final byte[] magicNumbers;

    Format(Type type, String[] extensions, byte[] magicNumbers) {
        this.type = type;
        this.extensions = extensions;
        this.magicNumbers = magicNumbers;
    }

    public Type getType() {
        return type;
    }

    public String[] getExtensions() {
        return extensions;
    }

    public byte[] getMagicNumbers() {
        return magicNumbers;
    }

    public boolean isValid(File file) {
        for (String extension : getExtensions()) {
            if (file.getName().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }


    public static Format fromExtension(String extension) {
        try {
            return valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            for (Format format : Format.values()) {
                for (String exte : format.getExtensions()) {
                    String exteWithOutDot = exte.substring(1);
                    if (exte.equalsIgnoreCase(extension)
                            || exteWithOutDot.equalsIgnoreCase(extension)) {
                        return format;
                    }
                }
            }
        }
        return null;
    }

    public enum Type {
        RASTER,
        VECTORIAL
    }
}
