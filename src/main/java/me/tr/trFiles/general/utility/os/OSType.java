package me.tr.trFiles.general.utility.os;

public enum OSType {

    WINDOWS(new char[]{'\\', '/', ':', '*', '?', '"', '<', '>', '|'}),
    MAC(new char[]{'/'}),
    LINUX(new char[]{'/'});


    private final char[] illegalChars;

    OSType(char[] illegalChars) {
        this.illegalChars = illegalChars;
    }

    public char[] illegalChars() {
        return illegalChars;
    }

    public static OSType fromString(String str) {
        if (str == null || str.isEmpty()) return null;
        for (OSType type : values()) {
            String name = type.name().toLowerCase();
            if (str.toLowerCase().contains(name)
                    || str.toLowerCase().contains(name.substring(0, 3))) {
                return type;
            }
        }
        return null;
    }
}
