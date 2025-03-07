package me.tr.general.utilities;

public class Validate {

    public static boolean isNull(String val) {
        return val == null || val.isEmpty();
    }

    public static boolean checkIf(boolean condition, String mess) {
        if (!condition) {
            throw new IllegalArgumentException(mess);
        }
        return true;
    }
}
