package me.tr.trFiles.general.utility.os;

public class OSUtility {
    public static final String OS_NAME = System.getProperty("os.name");
    public static final OSType OS = OSType.fromString(OS_NAME);

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
        if (OS == null) {
            return from;
        }
        from = from.replace('\\', '/');
        int currIndex = 2 /* Remove the disk letter that contains ':'. */, nextIndex;
        StringBuilder builder = new StringBuilder();
        while ((currIndex = from.indexOf('/', nextIndex = currIndex + 1)) != -1) {
            String folder = from.substring(nextIndex, currIndex);
            System.out.println(folder);
            for (char c : OS.getIllegalChars()) {
                folder = folder.replace(Character.toString(c), "");
            }
            builder.append(folder).append('/');
        }
        String file = from.substring(nextIndex);
        for (char c : OS.getIllegalChars()) {
            file = file.replace(String.valueOf(c), "");
        }
        builder.append(file);
        return from.substring(0, 3) + builder;
    }
}
