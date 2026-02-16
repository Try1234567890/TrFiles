package me.tr.trfiles.management.connection.uploader;

import me.tr.trfiles.management.connection.uploader.file.FileUploader;
import me.tr.trfiles.management.connection.uploader.file.ParallelFileUploader;
import me.tr.trfiles.management.connection.uploader.stream.ParallelStreamUploader;
import me.tr.trfiles.management.connection.uploader.stream.StreamUploader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Uploaders {
    private static final FileUploader FILE_UPLOADER = new FileUploader();
    private static final ParallelFileUploader PARALLEL_FILE_UPLOADER = new ParallelFileUploader();
    private static final StreamUploader STREAM_UPLOADER = new StreamUploader();
    private static final ParallelStreamUploader PARALLEL_STREAM_UPLOADER = new ParallelStreamUploader();

    private Uploaders() {
    }

    public static void uploadOrThrown(File source, URL destination) throws IOException {
        FILE_UPLOADER.uploadOrThrown(source, destination);
    }

    public static void upload(File source, URL destination) {
        FILE_UPLOADER.upload(source, destination);
    }

    public static void uploadParallelOrThrown(File source, URL destination) throws IOException {
        PARALLEL_FILE_UPLOADER.uploadOrThrown(source, destination);
    }

    public static void uploadParallel(File source, URL destination) {
        PARALLEL_FILE_UPLOADER.upload(source, destination);
    }

    public static void uploadOrThrown(InputStream source, URL destination) throws IOException {
        STREAM_UPLOADER.uploadOrThrown(source, destination);
    }

    public static void upload(InputStream source, URL destination) {
        STREAM_UPLOADER.upload(source, destination);
    }

    public static void uploadParallelOrThrown(InputStream source, URL destination) throws IOException {
        PARALLEL_STREAM_UPLOADER.uploadOrThrown(source, destination);
    }

    public static void uploadParallel(InputStream source, URL destination) {
        PARALLEL_STREAM_UPLOADER.upload(source, destination);
    }
}
