package me.tr.trfiles.size;

import java.util.Arrays;

public enum SizeUnit {
    /*
     * Unit of Measure	Bytes
     *     Kilobyte (kB)	1,000¹ = 1,000
     *     Megabyte (MB)	1,000² = 1,000,000
     *     Gigabyte (GB)	1,000³ = 1,000,000,000
     *     Terabyte (TB)	1,000⁴ = 1,000,000,000,000
     *     Petabyte (PB)	1,000⁵ = 1,000,000,000,000,000
     * Unit of Measure	Bytes
     *     Kibibyte (KiB)	1,024¹ = 1,024
     *     Mebibyte (MiB)	1,024² = 1,048,576
     *     Gibibyte (GiB)	1,024³ = 1,073,741,824
     *     Tebibyte (TiB)	1,024⁴ = 1,099,511,627,776
     *     Pebibyte (PiB)	1,024⁵ = 1,125,899,906,842,620
     */

    BYTE(1, "B"),

    /**
     * Decimal Units
     */
    KILOBYTE(1000, "KB"),
    MEGABYTE(Math.pow(1000, 2), "MB"),
    GIGABYTE(Math.pow(1000, 3), "GB"),
    TERABYTE(Math.pow(1000, 4), "TB"),
    PETABYTE(Math.pow(1000, 5), "PB"),

    /**
     * Binary Units
     */
    KIBIBYTE(1024, "KiB"),
    MEBIBYTE(Math.pow(1024, 2), "MiB"),
    GIBIBYTE(Math.pow(1024, 3), "GiB"),
    TEBIBYTE(Math.pow(1024, 4), "TiB"),
    PEBIBYTE(Math.pow(1024, 5), "PiB");


    private final double bytes;
    private final String tag;

    SizeUnit(double bytes, String tag) {
        this.bytes = bytes;
        this.tag = tag;
    }

    /**
     * Parses a string into a SizeUnit.
     *
     * @param str The string to parse.
     * @return The parsed SizeUnit, or null if not found.
     */
    public static SizeUnit parse(String str) {
        try {
            return SizeUnit.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            return Arrays.stream(SizeUnit.values()).filter(
                    unit -> unit.getTag().equalsIgnoreCase(str)
            ).findFirst().orElse(null);
        }
    }

    /**
     * Gets the number of bytes in this unit.
     *
     * @return The number of bytes.
     */
    public double getBytes() {
        return bytes;
    }

    /**
     * Gets the tag of this unit.
     *
     * @return The tag.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Converts this unit to another unit.
     *
     * @param unit The unit to convert to.
     * @return The converted value.
     */
    public double to(SizeUnit unit) {
        return this.getBytes() / unit.getBytes();
    }

    /**
     * Converts an amount of this unit to bytes.
     *
     * @param amount The amount to convert.
     * @return The amount in bytes.
     */
    public double toBytes(double amount) {
        return amount * this.getBytes();
    }

    /**
     * Converts an amount in bytes to this unit.
     *
     * @param bytes The amount in bytes.
     * @return The amount in this unit.
     */
    public double fromBytes(double bytes) {
        return bytes / this.getBytes();
    }
}
