package me.tr.trfiles.file.management.reader.file;

import javax.swing.plaf.PanelUI;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FilesReader {
    public static final BytesFileReader BYTES_READER = new BytesFileReader();
    public static final CharsFileReader CHARS_READER = new CharsFileReader();
    public static final StringFileReader STRING_READER = new StringFileReader();
    public static final StringListFileReader STRING_LIST_READER = new StringListFileReader();


    private FilesReader() {
    }

    public static byte[] readAsBytesOrThrown(File file) throws IOException {
        return BYTES_READER.readOrThrown(file);
    }

    public static Optional<byte[]> readAsBytes(File file) {
        return BYTES_READER.read(file);
    }

    public static byte[] readAsBytesOrNull(File file) {
        return BYTES_READER.readOrNull(file);
    }

    public static byte[] readAsBytesOrDefault(File file, byte[] str) {
        return BYTES_READER.readOrDefault(file, str);
    }

    public static char[] readAsCharsOrThrown(File file) throws IOException {
        return CHARS_READER.readOrThrown(file);
    }

    public static Optional<char[]> readAsChars(File file) {
        return CHARS_READER.read(file);
    }

    public static char[] readAsCharsOrNull(File file) {
        return CHARS_READER.readOrNull(file);
    }

    public static char[] readAsCharsOrDefault(File file, char[] str) {
        return CHARS_READER.readOrDefault(file, str);
    }

    public static String readAsStringOrThrown(File file) throws IOException {
        return STRING_READER.readOrThrown(file);
    }

    public static Optional<String> readAsString(File file) {
        return STRING_READER.read(file);
    }

    public static String readAsStringOrNull(File file) {
        return STRING_READER.readOrNull(file);
    }

    public static String readAsStringOrDefault(File file, String str) {
        return STRING_READER.readOrDefault(file, str);
    }

    public static List<String> readAsStringListOrThrown(File file) throws IOException {
        return STRING_LIST_READER.readOrThrown(file);
    }

    public static Optional<List<String>> readAsStringList(File file) {
        return STRING_LIST_READER.read(file);
    }

    public static List<String> readAsStringListOrNull(File file) {
        return STRING_LIST_READER.readOrNull(file);
    }

    public static List<String> readAsStringListOrDefault(File file, List<String> str) {
        return STRING_LIST_READER.readOrDefault(file, str);
    }

}
