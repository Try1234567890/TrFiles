package me.tr.trfiles.file.configuration.size;

import me.tr.trfiles.Utility;
import me.tr.trfiles.Validator;

import java.io.File;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Size {
    private static final Pattern REGEX_PATTERN = Pattern.compile("(\\d+\\s*)([a-zA-Z]{1,3})");
    private final double amount;
    private final SizeUnit unit;

    public Size(double amount, SizeUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    /**
     * Create a Size object from a File's length in bytes.
     *
     * @param file The file to get the size from.
     * @return A Size object representing the file's size in bytes.
     */
    public static Size file(File file) {
        double size = file.length();

        return new Size(size, SizeUnit.BYTE);
    }

    /**
     * Parse a Size from a string.
     *
     * @param str The string to parse.
     * @return A Size object or null if the string is invalid.
     */
    public static Size parse(String str) {
        if (Validator.isNull(str))
            return null;

        Matcher matcher = REGEX_PATTERN.matcher(str);

        if (matcher.matches()) {
            String amt = matcher.group(1);
            String unitStr = matcher.group(2);

            if (Validator.isNull(unitStr) || Validator.isNull(amt))
                return null;


            long amount = Utility.parseLong(amt.trim());
            SizeUnit unit = SizeUnit.parse(unitStr.trim());

            if (unit == null || amount < 0)
                return null;


            return new Size(amount, unit);
        }

        return null;
    }

    /**
     * Get the amount of the size.
     *
     * @return The amount of the size.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Get the unit of the size.
     *
     * @return The unit of the size.
     */
    public SizeUnit getUnit() {
        return unit;
    }

    /**
     * Convert the size to the specified unit.
     *
     * @param unit The unit to convert to.
     * @return The size in the specified unit.
     */
    public double to(SizeUnit unit) {
        return amount * getUnit().to(unit);
    }

    /**
     * Convert the size to bytes.
     *
     * @return The size in bytes.
     */
    public double toByte() {
        return to(SizeUnit.BYTE);
    }

    /**
     * Convert the size to kilobytes.
     *
     * @return The size in kilobytes.
     */
    public double toKilo() {
        return to(SizeUnit.KILOBYTE);
    }

    /**
     * Convert the size to megabytes.
     *
     * @return The size in megabytes.
     */
    public double toMega() {
        return to(SizeUnit.MEGABYTE);
    }

    /**
     * Convert the size to gigabytes.
     *
     * @return The size in gigabytes.
     */
    public double toGiga() {
        return to(SizeUnit.GIGABYTE);
    }

    /**
     * Convert the size to terabytes.
     *
     * @return The size in terabytes.
     */
    public double toTera() {
        return to(SizeUnit.TERABYTE);
    }

    /**
     * Convert the size to petabytes.
     *
     * @return The size in petabytes.
     */
    public double toPeta() {
        return to(SizeUnit.PETABYTE);
    }

    /**
     * Convert the size to kibibytes.
     *
     * @return The size in kibibytes.
     */
    public double toKibi() {
        return to(SizeUnit.KIBIBYTE);
    }

    /**
     * Convert the size to mebibytes.
     *
     * @return The size in mebibytes.
     */
    public double toMebi() {
        return to(SizeUnit.MEBIBYTE);
    }

    /**
     * Convert the size to gibibytes.
     *
     * @return The size in gibibytes.
     */
    public double toGibi() {
        return to(SizeUnit.GIBIBYTE);
    }

    /**
     * Convert the size to tebibytes.
     *
     * @return The size in tebibytes.
     */
    public double toTebi() {
        return to(SizeUnit.TEBIBYTE);
    }

    /**
     * Convert the size to pebibytes.
     *
     * @return The size in pebibytes.
     */
    public double toPebi() {
        return to(SizeUnit.PEBIBYTE);
    }

    @Override
    public String toString() {
        return "TrSize{" + amount + " " + unit + '}';
    }

    public boolean isMajorOrEquals(Size size) {
        return isMajor(size) || isEquals(size);
    }

    public boolean isMajor(Size size) {
        return Double.compare(to(SizeUnit.BYTE), size.to(SizeUnit.BYTE)) == 1;
    }

    public boolean isEquals(Size size) {
        return Double.compare(to(SizeUnit.BYTE), size.to(SizeUnit.BYTE)) == 0;
    }

    public boolean isMinorOrEquals(Size size) {
        return isMinor(size) || isEquals(size);
    }

    public boolean isMinor(Size size) {
        return Double.compare(to(SizeUnit.BYTE), size.to(SizeUnit.BYTE)) == -1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount(), getUnit());
    }
}
