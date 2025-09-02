package me.tr.trFiles.os;

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

    public static String toUnixPath(String path) {
        return path.replace('\\', '/');
    }

    public static String removeIllegalChars(String path) {
        path = toUnixPath(path);
        if (OS == null) {
            return path;
        }
        String[] folders = path.split("/");
        StringBuilder newPath = new StringBuilder(folders[0] + '/');
        for (int i = 1 /* Skip Disk Letter (Es -> C:/) */; i < folders.length; i++) {
            String folder = folders[i];
            for (char illegalChar : OS.getIllegalChars()) {
                folder = folder.replace(illegalChar + "", "");
            }
            newPath.append(folder).append('/');
        }
        return newPath.toString();
    }
}
