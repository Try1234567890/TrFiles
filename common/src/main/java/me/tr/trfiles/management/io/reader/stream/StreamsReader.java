package me.tr.trfiles.management.io.reader.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class StreamsReader {
    public static final BytesStreamReader BYTES_READER = new BytesStreamReader();
    public static final CharsStreamReader CHARS_READER = new CharsStreamReader();
    public static final StringStreamReader STRING_READER = new StringStreamReader();
    public static final StringListStreamReader STRING_LIST_READER = new StringListStreamReader();

    private StreamsReader() {
    }

    // --- BYTES ---

    public static byte[] readAsBytesOrThrown(InputStream is, long from, long to) throws IOException {
        return BYTES_READER.readOrThrown(is, from, to);
    }

    public static byte[] readAsBytesOrThrown(InputStream is, long from) throws IOException {
        return BYTES_READER.readOrThrown(is, from);
    }

    public static byte[] readAsBytesOrThrown(InputStream is) throws IOException {
        return BYTES_READER.readOrThrown(is);
    }

    public static Optional<byte[]> readAsBytes(InputStream is, long from, long to) {
        return BYTES_READER.read(is, from, to);
    }

    public static Optional<byte[]> readAsBytes(InputStream is, long from) {
        return BYTES_READER.read(is, from);
    }

    public static Optional<byte[]> readAsBytes(InputStream is) {
        return BYTES_READER.read(is);
    }

    public static byte[] readAsBytesOrNull(InputStream is, long from, long to) {
        return BYTES_READER.readOrNull(is, from, to);
    }

    public static byte[] readAsBytesOrNull(InputStream is, long from) {
        return BYTES_READER.readOrNull(is, from);
    }

    public static byte[] readAsBytesOrNull(InputStream is) {
        return BYTES_READER.readOrNull(is);
    }

    public static byte[] readAsBytesOrDefault(InputStream is, long from, long to, byte[] def) {
        return BYTES_READER.readOrDefault(is, from, to, def);
    }

    public static byte[] readAsBytesOrDefault(InputStream is, long from, byte[] def) {
        return BYTES_READER.readOrDefault(is, from, def);
    }

    public static byte[] readAsBytesOrDefault(InputStream is, byte[] def) {
        return BYTES_READER.readOrDefault(is, def);
    }

    // --- CHARS ---

    public static char[] readAsCharsOrThrown(InputStream is, long from, long to) throws IOException {
        return CHARS_READER.readOrThrown(is, from, to);
    }

    public static char[] readAsCharsOrThrown(InputStream is, long from) throws IOException {
        return CHARS_READER.readOrThrown(is, from);
    }

    public static char[] readAsCharsOrThrown(InputStream is) throws IOException {
        return CHARS_READER.readOrThrown(is);
    }

    public static Optional<char[]> readAsChars(InputStream is, long from, long to) {
        return CHARS_READER.read(is, from, to);
    }

    public static Optional<char[]> readAsChars(InputStream is, long from) {
        return CHARS_READER.read(is, from);
    }

    public static Optional<char[]> readAsChars(InputStream is) {
        return CHARS_READER.read(is);
    }

    public static char[] readAsCharsOrNull(InputStream is, long from, long to) {
        return CHARS_READER.readOrNull(is, from, to);
    }

    public static char[] readAsCharsOrNull(InputStream is, long from) {
        return CHARS_READER.readOrNull(is, from);
    }

    public static char[] readAsCharsOrNull(InputStream is) {
        return CHARS_READER.readOrNull(is);
    }

    public static char[] readAsCharsOrDefault(InputStream is, long from, long to, char[] def) {
        return CHARS_READER.readOrDefault(is, from, to, def);
    }

    public static char[] readAsCharsOrDefault(InputStream is, long from, char[] def) {
        return CHARS_READER.readOrDefault(is, from, def);
    }

    public static char[] readAsCharsOrDefault(InputStream is, char[] def) {
        return CHARS_READER.readOrDefault(is, def);
    }

    // --- STRING ---

    public static String readAsStringOrThrow(InputStream is, long from, long to) throws IOException {
        return STRING_READER.readOrThrown(is, from, to);
    }

    public static String readAsStringOrThrown(InputStream is, long from) throws IOException {
        return STRING_READER.readOrThrown(is, from);
    }

    public static String readAsStringOrThrown(InputStream is) throws IOException {
        return STRING_READER.readOrThrown(is);
    }

    public static Optional<String> readAsString(InputStream is, long from, long to) {
        return STRING_READER.read(is, from, to);
    }

    public static Optional<String> readAsString(InputStream is, long from) {
        return STRING_READER.read(is, from);
    }

    public static Optional<String> readAsString(InputStream is) {
        return STRING_READER.read(is);
    }

    public static String readAsStringOrNull(InputStream is, long from, long to) {
        return STRING_READER.readOrNull(is, from, to);
    }

    public static String readAsStringOrNull(InputStream is, long from) {
        return STRING_READER.readOrNull(is, from);
    }

    public static String readAsStringOrNull(InputStream is) {
        return STRING_READER.readOrNull(is);
    }

    public static String readAsStringOrDefault(InputStream is, long from, long to, String def) {
        return STRING_READER.readOrDefault(is, from, to, def);
    }

    public static String readAsStringOrDefault(InputStream is, long from, String def) {
        return STRING_READER.readOrDefault(is, from, def);
    }

    public static String readAsStringOrDefault(InputStream is, String def) {
        return STRING_READER.readOrDefault(is, def);
    }

    // --- STRING LIST ---

    public static List<String> readAsStringListOrThrown(InputStream is, long from, long to) throws IOException {
        return STRING_LIST_READER.readOrThrown(is, from, to);
    }

    public static List<String> readAsStringListOrThrown(InputStream is, long from) throws IOException {
        return STRING_LIST_READER.readOrThrown(is, from);
    }

    public static List<String> readAsStringListOrThrown(InputStream is) throws IOException {
        return STRING_LIST_READER.readOrThrown(is);
    }

    public static Optional<List<String>> readAsStringList(InputStream is, long from, long to) {
        return STRING_LIST_READER.read(is, from, to);
    }

    public static Optional<List<String>> readAsStringList(InputStream is, long from) {
        return STRING_LIST_READER.read(is, from);
    }

    public static Optional<List<String>> readAsStringList(InputStream is) {
        return STRING_LIST_READER.read(is);
    }

    public static List<String> readAsStringListOrNull(InputStream is, long from, long to) {
        return STRING_LIST_READER.readOrNull(is, from, to);
    }

    public static List<String> readAsStringListOrNull(InputStream is, long from) {
        return STRING_LIST_READER.readOrNull(is, from);
    }

    public static List<String> readAsStringListOrNull(InputStream is) {
        return STRING_LIST_READER.readOrNull(is);
    }

    public static List<String> readAsStringListOrDefault(InputStream is, long from, long to, List<String> def) {
        return STRING_LIST_READER.readOrDefault(is, from, to, def);
    }

    public static List<String> readAsStringListOrDefault(InputStream is, long from, List<String> def) {
        return STRING_LIST_READER.readOrDefault(is, from, def);
    }

    public static List<String> readAsStringListOrDefault(InputStream is, List<String> def) {
        return STRING_LIST_READER.readOrDefault(is, def);
    }
}
