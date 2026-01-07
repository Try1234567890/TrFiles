package me.tr.trfiles.file.management;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private FileManager() {
    }

    public static CreatingResult createAsFileOrThrown(File file) throws IOException {
        if (file.exists()) return CreatingResult.ALREADY_EXISTS;
        if (file.isDirectory()) return CreatingResult.DIFFERENT_TYPE;

        CreatingResult result = createAsFolder(file.getParentFile());

        if (!result.equals(CreatingResult.SUCCESS))
            return result;

        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();

        return CreatingResult.SUCCESS;
    }

    public static CreatingResult createAsFileOrThrown(String file) throws IOException {
        return createAsFileOrThrown(new File(file));
    }

    public static CreatingResult createAsFile(File file) {
        try {
            return createAsFileOrThrown(file);
        } catch (IOException ignored) {
            return CreatingResult.FAILED;
        }
    }

    public static CreatingResult createAsFile(String file) {
        return createAsFile(new File(file));
    }

    public static CreatingResult createAsFolder(File file) {
        if (file.exists()) return CreatingResult.ALREADY_EXISTS;
        if (file.isFile()) return CreatingResult.DIFFERENT_TYPE;

        while (file != null) {
            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.mkdir();
            }
            file = file.getParentFile();
        }

        return CreatingResult.SUCCESS;
    }

    public static CreatingResult createAsFolder(String file) {
        return createAsFolder(new File(file));
    }

    public static boolean delete(File file) {
        return file.delete();
    }

    public static boolean delete(String file) {
        return delete(new File(file));
    }

    public enum CreatingResult {

        ALREADY_EXISTS,
        DIFFERENT_TYPE,
        ERROR_OCCURS,
        FAILED,
        SUCCESS

    }

}
