package me.tr.trfiles.management.io.reader;


import me.tr.trfiles.management.io.reader.file.FilesReader;
import me.tr.trfiles.management.io.reader.stream.StreamsReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class Readers {
    private Readers() {
    }


    // --- BYTES ---

    public static byte[] readAsBytesOrThrown(InputStream is, long from, long to) throws IOException {
        return StreamsReader.readAsBytesOrThrown(is, from, to);
    }

    public static byte[] readAsBytesOrThrown(InputStream is, long from) throws IOException {
        return StreamsReader.readAsBytesOrThrown(is, from);
    }

    public static byte[] readAsBytesOrThrown(InputStream is) throws IOException {
        return StreamsReader.readAsBytesOrThrown(is);
    }

    public static Optional<byte[]> readAsBytes(InputStream is, long from, long to) {
        return StreamsReader.readAsBytes(is, from, to);
    }

    public static Optional<byte[]> readAsBytes(InputStream is, long from) {
        return StreamsReader.readAsBytes(is, from);
    }

    public static Optional<byte[]> readAsBytes(InputStream is) {
        return StreamsReader.readAsBytes(is);
    }

    public static byte[] readAsBytesOrNull(InputStream is, long from, long to) {
        return StreamsReader.readAsBytesOrNull(is, from, to);
    }

    public static byte[] readAsBytesOrNull(InputStream is, long from) {
        return StreamsReader.readAsBytesOrNull(is, from);
    }

    public static byte[] readAsBytesOrNull(InputStream is) {
        return StreamsReader.readAsBytesOrNull(is);
    }

    public static byte[] readAsBytesOrDefault(InputStream is, long from, long to, byte[] def) {
        return StreamsReader.readAsBytesOrDefault(is, from, to, def);
    }

    public static byte[] readAsBytesOrDefault(InputStream is, long from, byte[] def) {
        return StreamsReader.readAsBytesOrDefault(is, from, def);
    }

    public static byte[] readAsBytesOrDefault(InputStream is, byte[] def) {
        return StreamsReader.readAsBytesOrDefault(is, def);
    }

    // --- CHARS ---

    public static char[] readAsCharsOrThrown(InputStream is, long from, long to) throws IOException {
        return StreamsReader.readAsCharsOrThrown(is, from, to);
    }

    public static char[] readAsCharsOrThrown(InputStream is, long from) throws IOException {
        return StreamsReader.readAsCharsOrThrown(is, from);
    }

    public static char[] readAsCharsOrThrown(InputStream is) throws IOException {
        return StreamsReader.readAsCharsOrThrown(is);
    }

    public static Optional<char[]> readAsChars(InputStream is, long from, long to) {
        return StreamsReader.readAsChars(is, from, to);
    }

    public static Optional<char[]> readAsChars(InputStream is, long from) {
        return StreamsReader.readAsChars(is, from);
    }

    public static Optional<char[]> readAsChars(InputStream is) {
        return StreamsReader.readAsChars(is);
    }

    public static char[] readAsCharsOrNull(InputStream is, long from, long to) {
        return StreamsReader.readAsCharsOrNull(is, from, to);
    }

    public static char[] readAsCharsOrNull(InputStream is, long from) {
        return StreamsReader.readAsCharsOrNull(is, from);
    }

    public static char[] readAsCharsOrNull(InputStream is) {
        return StreamsReader.readAsCharsOrNull(is);
    }

    public static char[] readAsCharsOrDefault(InputStream is, long from, long to, char[] def) {
        return StreamsReader.readAsCharsOrDefault(is, from, to, def);
    }

    public static char[] readAsCharsOrDefault(InputStream is, long from, char[] def) {
        return StreamsReader.readAsCharsOrDefault(is, from, def);
    }

    public static char[] readAsCharsOrDefault(InputStream is, char[] def) {
        return StreamsReader.readAsCharsOrDefault(is, def);
    }

    // --- STRING ---

    public static String readAsStringOrThrown(InputStream is, long from, long to) throws IOException {
        return StreamsReader.readAsStringOrThrow(is, from, to);
    }

    public static String readAsStringOrThrown(InputStream is, long from) throws IOException {
        return StreamsReader.readAsStringOrThrown(is, from);
    }

    public static String readAsStringOrThrown(InputStream is) throws IOException {
        return StreamsReader.readAsStringOrThrown(is);
    }

    public static Optional<String> readAsString(InputStream is, long from, long to) {
        return StreamsReader.readAsString(is, from, to);
    }

    public static Optional<String> readAsString(InputStream is, long from) {
        return StreamsReader.readAsString(is, from);
    }

    public static Optional<String> readAsString(InputStream is) {
        return StreamsReader.readAsString(is);
    }

    public static String readAsStringOrNull(InputStream is, long from, long to) {
        return StreamsReader.readAsStringOrNull(is, from, to);
    }

    public static String readAsStringOrNull(InputStream is, long from) {
        return StreamsReader.readAsStringOrNull(is, from);
    }

    public static String readAsStringOrNull(InputStream is) {
        return StreamsReader.readAsStringOrNull(is);
    }

    public static String readAsStringOrDefault(InputStream is, long from, long to, String def) {
        return StreamsReader.readAsStringOrDefault(is, from, to, def);
    }

    public static String readAsStringOrDefault(InputStream is, long from, String def) {
        return StreamsReader.readAsStringOrDefault(is, from, def);
    }

    public static String readAsStringOrDefault(InputStream is, String def) {
        return StreamsReader.readAsStringOrDefault(is, def);
    }

    // --- STRING LIST ---

    public static List<String> readAsStringListOrThrown(InputStream is, long from, long to) throws IOException {
        return StreamsReader.readAsStringListOrThrown(is, from, to);
    }

    public static List<String> readAsStringListOrThrown(InputStream is, long from) throws IOException {
        return StreamsReader.readAsStringListOrThrown(is, from);
    }

    public static List<String> readAsStringListOrThrown(InputStream is) throws IOException {
        return StreamsReader.readAsStringListOrThrown(is);
    }

    public static Optional<List<String>> readAsStringList(InputStream is, long from, long to) {
        return StreamsReader.readAsStringList(is, from, to);
    }

    public static Optional<List<String>> readAsStringList(InputStream is, long from) {
        return StreamsReader.readAsStringList(is, from);
    }

    public static Optional<List<String>> readAsStringList(InputStream is) {
        return StreamsReader.readAsStringList(is);
    }

    public static List<String> readAsStringListOrNull(InputStream is, long from, long to) {
        return StreamsReader.readAsStringListOrNull(is, from, to);
    }

    public static List<String> readAsStringListOrNull(InputStream is, long from) {
        return StreamsReader.readAsStringListOrNull(is, from);
    }

    public static List<String> readAsStringListOrNull(InputStream is) {
        return StreamsReader.readAsStringListOrNull(is);
    }

    public static List<String> readAsStringListOrDefault(InputStream is, long from, long to, List<String> def) {
        return StreamsReader.readAsStringListOrDefault(is, from, to, def);
    }

    public static List<String> readAsStringListOrDefault(InputStream is, long from, List<String> def) {
        return StreamsReader.readAsStringListOrDefault(is, from, def);
    }

    public static List<String> readAsStringListOrDefault(InputStream is, List<String> def) {
        return StreamsReader.readAsStringListOrDefault(is, def);
    }



    public static byte[] readAsBytesOrThrown(File file, long from, long to) throws IOException {
        return FilesReader.readAsBytesOrThrown(file, from, to);
    }

    public static byte[] readAsBytesOrThrown(File file, long from) throws IOException {
        return FilesReader.readAsBytesOrThrown(file, from);
    }

    public static byte[] readAsBytesOrThrown(File file) throws IOException {
        return FilesReader.readAsBytesOrThrown(file);
    }

    public static Optional<byte[]> readAsBytes(File file, long from, long to) {
        return FilesReader.readAsBytes(file, from, to);
    }

    public static Optional<byte[]> readAsBytes(File file, long from) {
        return FilesReader.readAsBytes(file, from);
    }

    public static Optional<byte[]> readAsBytes(File file) {
        return FilesReader.readAsBytes(file);
    }

    public static byte[] readAsBytesOrNull(File file, long from, long to) {
        return FilesReader.readAsBytesOrNull(file, from, to);
    }

    public static byte[] readAsBytesOrNull(File file, long from) {
        return FilesReader.readAsBytesOrNull(file, from);
    }

    public static byte[] readAsBytesOrNull(File file) {
        return FilesReader.readAsBytesOrNull(file);
    }

    public static byte[] readAsBytesOrDefault(File file, long from, long to, byte[] def) {
        return FilesReader.readAsBytesOrDefault(file, from, to, def);
    }

    public static byte[] readAsBytesOrDefault(File file, long from, byte[] def) {
        return FilesReader.readAsBytesOrDefault(file, from, def);
    }

    public static byte[] readAsBytesOrDefault(File file, byte[] def) {
        return FilesReader.readAsBytesOrDefault(file, def);
    }

    // --- CHARS ---

    public static char[] readAsCharsOrThrown(File file, long from, long to) throws IOException {
        return FilesReader.readAsCharsOrThrown(file, from, to);
    }

    public static char[] readAsCharsOrThrown(File file, long from) throws IOException {
        return FilesReader.readAsCharsOrThrown(file, from);
    }

    public static char[] readAsCharsOrThrown(File file) throws IOException {
        return FilesReader.readAsCharsOrThrown(file);
    }

    public static Optional<char[]> readAsChars(File file, long from, long to) {
        return FilesReader.readAsChars(file, from, to);
    }

    public static Optional<char[]> readAsChars(File file, long from) {
        return FilesReader.readAsChars(file, from);
    }

    public static Optional<char[]> readAsChars(File file) {
        return FilesReader.readAsChars(file);
    }

    public static char[] readAsCharsOrNull(File file, long from, long to) {
        return FilesReader.readAsCharsOrNull(file, from, to);
    }

    public static char[] readAsCharsOrNull(File file, long from) {
        return FilesReader.readAsCharsOrNull(file, from);
    }

    public static char[] readAsCharsOrNull(File file) {
        return FilesReader.readAsCharsOrNull(file);
    }

    public static char[] readAsCharsOrDefault(File file, long from, long to, char[] def) {
        return FilesReader.readAsCharsOrDefault(file, from, to, def);
    }

    public static char[] readAsCharsOrDefault(File file, long from, char[] def) {
        return FilesReader.readAsCharsOrDefault(file, from, def);
    }

    public static char[] readAsCharsOrDefault(File file, char[] def) {
        return FilesReader.readAsCharsOrDefault(file, def);
    }

    // --- STRING ---

    public static String readAsStringOrThrown(File file, long from, long to) throws IOException {
        return FilesReader.readAsStringOrThrown(file, from, to);
    }

    public static String readAsStringOrThrown(File file, long from) throws IOException {
        return FilesReader.readAsStringOrThrown(file, from);
    }

    public static String readAsStringOrThrown(File file) throws IOException {
        return FilesReader.readAsStringOrThrown(file);
    }

    public static Optional<String> readAsString(File file, long from, long to) {
        return FilesReader.readAsString(file, from, to);
    }

    public static Optional<String> readAsString(File file, long from) {
        return FilesReader.readAsString(file, from);
    }

    public static Optional<String> readAsString(File file) {
        return FilesReader.readAsString(file);
    }

    public static String readAsStringOrNull(File file, long from, long to) {
        return FilesReader.readAsStringOrNull(file, from, to);
    }

    public static String readAsStringOrNull(File file, long from) {
        return FilesReader.readAsStringOrNull(file, from);
    }

    public static String readAsStringOrNull(File file) {
        return FilesReader.readAsStringOrNull(file);
    }

    public static String readAsStringOrDefault(File file, long from, long to, String def) {
        return FilesReader.readAsStringOrDefault(file, from, to, def);
    }

    public static String readAsStringOrDefault(File file, long from, String def) {
        return FilesReader.readAsStringOrDefault(file, from, def);
    }

    public static String readAsStringOrDefault(File file, String def) {
        return FilesReader.readAsStringOrDefault(file, def);
    }

    // --- STRING LIST ---

    public static List<String> readAsStringListOrThrown(File file, long from, long to) throws IOException {
        return FilesReader.readAsStringListOrThrown(file, from, to);
    }

    public static List<String> readAsStringListOrThrown(File file, long from) throws IOException {
        return FilesReader.readAsStringListOrThrown(file, from);
    }

    public static List<String> readAsStringListOrThrown(File file) throws IOException {
        return FilesReader.readAsStringListOrThrown(file);
    }

    public static Optional<List<String>> readAsStringList(File file, long from, long to) {
        return FilesReader.readAsStringList(file, from, to);
    }

    public static Optional<List<String>> readAsStringList(File file, long from) {
        return FilesReader.readAsStringList(file, from);
    }

    public static Optional<List<String>> readAsStringList(File file) {
        return FilesReader.readAsStringList(file);
    }

    public static List<String> readAsStringListOrNull(File file, long from, long to) {
        return FilesReader.readAsStringListOrNull(file, from, to);
    }

    public static List<String> readAsStringListOrNull(File file, long from) {
        return FilesReader.readAsStringListOrNull(file, from);
    }

    public static List<String> readAsStringListOrNull(File file) {
        return FilesReader.readAsStringListOrNull(file);
    }

    public static List<String> readAsStringListOrDefault(File file, long from, long to, List<String> def) {
        return FilesReader.readAsStringListOrDefault(file, from, to, def);
    }

    public static List<String> readAsStringListOrDefault(File file, long from, List<String> def) {
        return FilesReader.readAsStringListOrDefault(file, from, def);
    }

    public static List<String> readAsStringListOrDefault(File file, List<String> def) {
        return FilesReader.readAsStringListOrDefault(file, def);
    }
}
