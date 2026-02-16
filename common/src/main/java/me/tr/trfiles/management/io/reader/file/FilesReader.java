package me.tr.trfiles.management.io.reader.file;

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

    // --- BYTES ---

    public static byte[] readAsBytesOrThrown(File file, long from, long to) throws IOException {
        return BYTES_READER.readOrThrown(file, from, to);
    }

    public static byte[] readAsBytesOrThrown(File file, long from) throws IOException {
        return BYTES_READER.readOrThrown(file, from);
    }

    public static byte[] readAsBytesOrThrown(File file) throws IOException {
        return BYTES_READER.readOrThrown(file);
    }

    public static Optional<byte[]> readAsBytes(File file, long from, long to) {
        return BYTES_READER.read(file, from, to);
    }

    public static Optional<byte[]> readAsBytes(File file, long from) {
        return BYTES_READER.read(file, from);
    }

    public static Optional<byte[]> readAsBytes(File file) {
        return BYTES_READER.read(file);
    }

    public static byte[] readAsBytesOrNull(File file, long from, long to) {
        return BYTES_READER.readOrNull(file, from, to);
    }

    public static byte[] readAsBytesOrNull(File file, long from) {
        return BYTES_READER.readOrNull(file, from);
    }

    public static byte[] readAsBytesOrNull(File file) {
        return BYTES_READER.readOrNull(file);
    }

    public static byte[] readAsBytesOrDefault(File file, long from, long to, byte[] def) {
        return BYTES_READER.readOrDefault(file, from, to, def);
    }

    public static byte[] readAsBytesOrDefault(File file, long from, byte[] def) {
        return BYTES_READER.readOrDefault(file, from, def);
    }

    public static byte[] readAsBytesOrDefault(File file, byte[] def) {
        return BYTES_READER.readOrDefault(file, def);
    }

    // --- CHARS ---

    public static char[] readAsCharsOrThrown(File file, long from, long to) throws IOException {
        return CHARS_READER.readOrThrown(file, from, to);
    }

    public static char[] readAsCharsOrThrown(File file, long from) throws IOException {
        return CHARS_READER.readOrThrown(file, from);
    }

    public static char[] readAsCharsOrThrown(File file) throws IOException {
        return CHARS_READER.readOrThrown(file);
    }

    public static Optional<char[]> readAsChars(File file, long from, long to) {
        return CHARS_READER.read(file, from, to);
    }

    public static Optional<char[]> readAsChars(File file, long from) {
        return CHARS_READER.read(file, from);
    }

    public static Optional<char[]> readAsChars(File file) {
        return CHARS_READER.read(file);
    }

    public static char[] readAsCharsOrNull(File file, long from, long to) {
        return CHARS_READER.readOrNull(file, from, to);
    }

    public static char[] readAsCharsOrNull(File file, long from) {
        return CHARS_READER.readOrNull(file, from);
    }

    public static char[] readAsCharsOrNull(File file) {
        return CHARS_READER.readOrNull(file);
    }

    public static char[] readAsCharsOrDefault(File file, long from, long to, char[] def) {
        return CHARS_READER.readOrDefault(file, from, to, def);
    }

    public static char[] readAsCharsOrDefault(File file, long from, char[] def) {
        return CHARS_READER.readOrDefault(file, from, def);
    }

    public static char[] readAsCharsOrDefault(File file, char[] def) {
        return CHARS_READER.readOrDefault(file, def);
    }

    // --- STRING ---

    public static String readAsStringOrThrown(File file, long from, long to) throws IOException {
        return STRING_READER.readOrThrown(file, from, to);
    }

    public static String readAsStringOrThrown(File file, long from) throws IOException {
        return STRING_READER.readOrThrown(file, from);
    }

    public static String readAsStringOrThrown(File file) throws IOException {
        return STRING_READER.readOrThrown(file);
    }

    public static Optional<String> readAsString(File file, long from, long to) {
        return STRING_READER.read(file, from, to);
    }

    public static Optional<String> readAsString(File file, long from) {
        return STRING_READER.read(file, from);
    }

    public static Optional<String> readAsString(File file) {
        return STRING_READER.read(file);
    }

    public static String readAsStringOrNull(File file, long from, long to) {
        return STRING_READER.readOrNull(file, from, to);
    }

    public static String readAsStringOrNull(File file, long from) {
        return STRING_READER.readOrNull(file, from);
    }

    public static String readAsStringOrNull(File file) {
        return STRING_READER.readOrNull(file);
    }

    public static String readAsStringOrDefault(File file, long from, long to, String def) {
        return STRING_READER.readOrDefault(file, from, to, def);
    }

    public static String readAsStringOrDefault(File file, long from, String def) {
        return STRING_READER.readOrDefault(file, from, def);
    }

    public static String readAsStringOrDefault(File file, String def) {
        return STRING_READER.readOrDefault(file, def);
    }

    // --- STRING LIST ---

    public static List<String> readAsStringListOrThrown(File file, long from, long to) throws IOException {
        return STRING_LIST_READER.readOrThrown(file, from, to);
    }

    public static List<String> readAsStringListOrThrown(File file, long from) throws IOException {
        return STRING_LIST_READER.readOrThrown(file, from);
    }

    public static List<String> readAsStringListOrThrown(File file) throws IOException {
        return STRING_LIST_READER.readOrThrown(file);
    }

    public static Optional<List<String>> readAsStringList(File file, long from, long to) {
        return STRING_LIST_READER.read(file, from, to);
    }

    public static Optional<List<String>> readAsStringList(File file, long from) {
        return STRING_LIST_READER.read(file, from);
    }

    public static Optional<List<String>> readAsStringList(File file) {
        return STRING_LIST_READER.read(file);
    }

    public static List<String> readAsStringListOrNull(File file, long from, long to) {
        return STRING_LIST_READER.readOrNull(file, from, to);
    }

    public static List<String> readAsStringListOrNull(File file, long from) {
        return STRING_LIST_READER.readOrNull(file, from);
    }

    public static List<String> readAsStringListOrNull(File file) {
        return STRING_LIST_READER.readOrNull(file);
    }

    public static List<String> readAsStringListOrDefault(File file, long from, long to, List<String> def) {
        return STRING_LIST_READER.readOrDefault(file, from, to, def);
    }

    public static List<String> readAsStringListOrDefault(File file, long from, List<String> def) {
        return STRING_LIST_READER.readOrDefault(file, from, def);
    }

    public static List<String> readAsStringListOrDefault(File file, List<String> def) {
        return STRING_LIST_READER.readOrDefault(file, def);
    }
}