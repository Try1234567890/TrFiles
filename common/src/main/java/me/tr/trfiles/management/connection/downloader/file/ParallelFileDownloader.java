package me.tr.trfiles.management.connection.downloader.file;

import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelFileDownloader implements IFileDownloader {
    private static final FileDownloader DOWNLOADER = new FileDownloader();
    private final int threadCount;

    public ParallelFileDownloader(int threadCount) {
        this.threadCount = threadCount;
    }

    public ParallelFileDownloader() {
        this(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public File downloadOrThrown(URL url, File destination) throws IOException {
        long fileSize = getFileSize(url);

        if (fileSize <= 0 || threadCount <= 1) {
            return DOWNLOADER.downloadOrThrown(url, destination);
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        long chunkSize = fileSize / threadCount;
        CompletableFuture<?>[] futures = new CompletableFuture[threadCount];

        for (int i = 0; i < threadCount; i++) {
            long start = i * chunkSize;
            long end = (i == threadCount - 1) ? fileSize - 1 : (start + chunkSize - 1);

            File partFile = new File(destination.getPath() + ".part" + i);
            futures[i] = CompletableFuture.runAsync(() -> {
                try {
                    downloadPart(url, partFile, start, end);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }, executor);
        }

        CompletableFuture.allOf(futures).join();
        executor.shutdown();

        mergeFiles(destination, threadCount);
        return destination;
    }

    private void downloadPart(URL url, File partFile, long start, long end) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Range", "bytes=" + start + "-" + end);

        try (InputStream in = conn.getInputStream()) {
            Streamings.writeOrThrown(in, partFile);
        }
    }

    private void mergeFiles(File destination, int partCount) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            for (int i = 0; i < partCount; i++) {
                File partFile = new File(destination.getPath() + ".part" + i);
                try (FileInputStream fis = new FileInputStream(partFile)) {
                    Streamings.writeOrThrown(fis, fos);
                }
                partFile.delete();
            }
        }
    }

    private long getFileSize(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLengthLong();
        } catch (IOException e) {
            return -1;
        }
    }
}