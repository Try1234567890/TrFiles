package me.tr.trFiles.general.utility.os;

public class OSUtility {
    private static final String OS_NAME = System.getProperty("os.name");

    public static boolean isWin() {
        return OS_NAME.contains("Win");
    }

    public static boolean isMac() {
        return OS_NAME.contains("Mac");
    }

    public static boolean isUnix() {
        return OS_NAME.contains("Unix");
    }

    public static String removeIllegalChars(String from) {
        OSType os = OSType.fromString(OS_NAME);
        if (os == null)
            return from;
        for (char c : os.illegalChars()) {
            from = from.replace(String.valueOf(c), "");
        }
        return from;
    }
}
