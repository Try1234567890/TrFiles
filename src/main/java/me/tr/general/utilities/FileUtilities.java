package me.tr.general.utilities;

import java.io.File;

public class FileUtilities {

    public static String getExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        return parts[parts.length - 1];
    }

    public static String getExtension(File file) {
        return getExtension(file.getName());
    }


    public static boolean isYaml(String fileName) {
        return getExtension(fileName).equalsIgnoreCase("yaml") || getExtension(fileName).equalsIgnoreCase("yml");
    }

    public static boolean isYaml(File file) {
        return isYaml(file.getName());
    }

    public static boolean isJson(String fileName) {
        return getExtension(fileName).equalsIgnoreCase("json");
    }

    public static boolean isJson(File file) {
        return isJson(file.getName());
    }

    public static boolean isJar(String fileName) {
        return getExtension(fileName).equalsIgnoreCase("jar");
    }

    public static boolean isJar(File file) {
        return isJar(file.getName());
    }

}
