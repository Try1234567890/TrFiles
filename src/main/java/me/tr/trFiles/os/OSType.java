package me.tr.trFiles.os;

import me.tr.trFiles.Validator;

public enum OSType {

    WINDOWS(new char[]{'\\', '/', ':', '*', '?', '"', '<', '>', '|'}),
    MAC(new char[]{'/'}),
    LINUX(new char[]{'/'});


    private final char[] illegalChars;

    OSType(char[] illegalChars) {
        this.illegalChars = illegalChars;
    }

    public char[] getIllegalChars() {
        return illegalChars;
    }

    public static OSType fromString(String str) {
        if (Validator.isNull(str, null)) return null;
        for (OSType type : values()) {
            String name = type.name().toLowerCase();
            if (str.toLowerCase().contains(name)) {
                return type;
            }
        }
        return null;
    }
}
