package me.tr.trfiles.management.connection.downloader;

import me.tr.trfiles.management.connection.downloader.file.FileDownloader;
import me.tr.trfiles.management.connection.downloader.file.ParallelFileDownloader;
import me.tr.trfiles.management.connection.downloader.stream.ParallelStreamDownloader;
import me.tr.trfiles.management.connection.downloader.stream.StreamDownloader;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class Downloaders {
    private static final FileDownloader FILE_DONWLOADER = new FileDownloader();
    private static final ParallelFileDownloader PARALLEL_FILE_DOWNLOADER = new ParallelFileDownloader();
    private static final StreamDownloader STREAM_DONWLOADER = new StreamDownloader();
    private static final ParallelStreamDownloader PARALLEL_STREAM_DOWNLOADER = new ParallelStreamDownloader();

    private Downloaders() {}

    public static File downloadOrThrown(URL url, File destination) throws IOException {
        return FILE_DONWLOADER.downloadOrThrown(url, destination);
    }

    public static File download(URL url, File destination) {
        return FILE_DONWLOADER.download(url, destination);
    }

    public static File downloadParallelOrThrown(URL url, File destination) throws IOException {
        return PARALLEL_FILE_DOWNLOADER.downloadOrThrown(url, destination);
    }

    public static File downloadParallel(URL url, File destination) {
        return PARALLEL_FILE_DOWNLOADER.download(url, destination);
    }

    public static OutputStream downloadOrThrown(URL url, OutputStream destination) throws IOException {
        return STREAM_DONWLOADER.downloadOrThrown(url, destination);
    }

    public static OutputStream download(URL url, OutputStream destination) {
        return STREAM_DONWLOADER.download(url, destination);
    }

    public static OutputStream downloadParallelOrThrown(URL url, OutputStream destination) throws IOException {
        return PARALLEL_STREAM_DOWNLOADER.downloadOrThrown(url, destination);
    }

    public static OutputStream downloadParallel(URL url, OutputStream destination) {
        return PARALLEL_STREAM_DOWNLOADER.download(url, destination);
    }
}
