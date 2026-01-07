package me.tr.trfiles;

public class Utility {
    private Utility() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate utility class.");
    }


    /**
     * Parse a float from a string.
     * <p>
     * If the parse fails, {@code -1} will be returned.
     *
     * @param str The string to parse float from.
     * @return A float if no error occurs, otherwise {@code -1}.
     */
    public static float parseFloat(String str) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

    /**
     * Parse a long from a string.
     * <p>
     * If the parse fails, {@code -1} will be returned.
     *
     * @param str The string to parse long from.
     * @return A long if no error occurs, otherwise {@code -1}.
     */
    public static long parseLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }
}
