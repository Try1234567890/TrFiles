package me.tr.trfiles.management.io.writer.streaming;

import me.tr.trfiles.management.io.writer.streaming.file.FileToFileStreaming;
import me.tr.trfiles.management.io.writer.streaming.file.FileToStreamStreaming;
import me.tr.trfiles.management.io.writer.streaming.stream.StreamToFileStreaming;
import me.tr.trfiles.management.io.writer.streaming.stream.StreamToStreamStreaming;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Streamings {
    private static final FileToFileStreaming FILE_TO_FILE = new FileToFileStreaming();
    private static final FileToStreamStreaming FILE_TO_STREAM = new FileToStreamStreaming();

    private static final StreamToFileStreaming STREAM_TO_FILE = new StreamToFileStreaming();
    private static final StreamToStreamStreaming STREAM_TO_STREAM = new StreamToStreamStreaming();


    private Streamings() {
    }

    public static void writeOrThrown(InputStream from, OutputStream destination) throws IOException {
        STREAM_TO_STREAM.writeOrThrown(from, destination);
    }

    public static void writeOrThrown(File from, OutputStream destination) throws IOException {
        FILE_TO_STREAM.writeOrThrown(from, destination);
    }

    public static void writeOrThrown(File from, File destination) throws IOException {
        FILE_TO_FILE.writeOrThrown(from, destination);
    }

    public static void writeOrThrown(InputStream from, File destination) throws IOException {
        STREAM_TO_FILE.writeOrThrown(from, destination);
    }


    public static void write(InputStream from, OutputStream destination) {
        STREAM_TO_STREAM.write(from, destination);
    }

    public static void write(File from, OutputStream destination) {
        FILE_TO_STREAM.write(from, destination);
    }

    public static void write(File from, File destination) {
        FILE_TO_FILE.write(from, destination);

    }

    public static void write(InputStream from, File destination) {
        STREAM_TO_FILE.write(from, destination);
    }
}
