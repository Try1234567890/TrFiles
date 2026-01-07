package me.tr.trfiles.os;

import me.tr.trfiles.Validator;

public enum OSType {

    WINDOWS(new Character[]{':', '*', '?', '"', '<', '>', '|'}),
    MAC(new Character[]{}),
    LINUX(new Character[]{});


    private final Character[] illegalChars;

    OSType(Character[] illegalChars) {
        this.illegalChars = illegalChars;
    }

    /**
     * Retrieve the illegal chars that folder/file name
     * cannot contain based on current OS.
     *
     * @return the OS illegal chars in folder/file name.
     */
    public Character[] getIllegalChars() {
        return illegalChars;
    }

    /**
     * Retrieve the {@link OSType} from a string that contains it.
     * <p>
     * If the string param is {@code null}, the method will return {@code null}.
     *
     * @param str The string to parse the {@link OSType} from.
     * @return The {@link OSType} parsed from the string if found, otherwise {@code null}.
     */
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
