package me.tr.trfiles.management.io.writer;

import me.tr.trfiles.management.io.writer.file.FilesWriter;
import me.tr.trfiles.management.io.writer.stream.OSsWriter;
import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Writers {

    private Writers() {
    }

    public static void writeOrThrown(InputStream from, OutputStream destination) throws IOException {
        Streamings.writeOrThrown(from, destination);
    }

    public static void writeOrThrown(File from, OutputStream destination) throws IOException {
        Streamings.writeOrThrown(from, destination);
    }

    public static void writeOrThrown(File from, File destination) throws IOException {
        Streamings.writeOrThrown(from, destination);
    }

    public static void writeOrThrown(InputStream from, File destination) throws IOException {
        Streamings.writeOrThrown(from, destination);
    }


    public static void write(InputStream from, OutputStream destination) {
        Streamings.write(from, destination);
    }

    public static void write(File from, OutputStream destination) {
        Streamings.write(from, destination);
    }

    public static void write(File from, File destination) {
        Streamings.write(from, destination);

    }

    public static void write(InputStream from, File destination) {
        Streamings.write(from, destination);
    }

    public static void writerBytesOrThrown(File file, byte[] bytes) throws IOException {
        FilesWriter.writerBytesOrThrown(file, bytes);
    }

    public static void writerBytes(File file, byte[] bytes) {
        FilesWriter.writerBytes(file, bytes);
    }

    public static void writerCharsOrThrown(File file, char[] chars) throws IOException {
        FilesWriter.writerCharsOrThrown(file, chars);
    }

    public static void writerChars(File file, char[] chars) {
        FilesWriter.writerChars(file, chars);
    }

    public static void writerStringOrThrown(File file, String string) throws IOException {
        FilesWriter.writerStringOrThrown(file, string);
    }

    public static void writerString(File file, String string) {
        FilesWriter.writerString(file, string);
    }

    public static void writerStringListOrThrown(File file, List<String> strings) throws IOException {
        FilesWriter.writerStringListOrThrown(file, strings);
    }

    public static void writerStringList(File file, List<String> strings) {
        FilesWriter.writerStringList(file, strings);
    }


    public static void writerBytesOrThrown(OutputStream os, byte[] bytes) throws IOException {
        OSsWriter.writerBytesOrThrown(os, bytes);
    }

    public static void writerBytes(OutputStream os, byte[] bytes) {
        OSsWriter.writerBytes(os, bytes);
    }

    public static void writerCharsOrThrown(OutputStream os, char[] chars) throws IOException {
        OSsWriter.writerCharsOrThrown(os, chars);
    }

    public static void writerChars(OutputStream os, char[] chars) {
        OSsWriter.writerChars(os, chars);
    }

    public static void writerStringOrThrown(OutputStream os, String string) throws IOException {
        OSsWriter.writerStringOrThrown(os, string);
    }

    public static void writerString(OutputStream os, String string) {
        OSsWriter.writerString(os, string);
    }

    public static void writerStringListOrThrown(OutputStream os, List<String> strings) throws IOException {
        OSsWriter.writerStringListOrThrown(os, strings);
    }

    public static void writerStringList(OutputStream os, List<String> strings) {
        OSsWriter.writerStringList(os, strings);
    }
}
