package me.tr.trfiles.management.connection.uploader.file;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

public class ParallelFileUploader implements IFileUploader {
    private static final FileUploader DEFAULT_UPLOADER = new FileUploader();
    private static final long MIN_PARALLEL_SIZE = 1024 * 1024;
    private final int threadCount;

    public ParallelFileUploader(int threadCount) {
        this.threadCount = threadCount;
    }

    public ParallelFileUploader() {
        this(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void uploadOrThrown(File source, URL url) throws IOException {
        long fileSize = source.length();

        if (fileSize <= MIN_PARALLEL_SIZE || threadCount <= 1) {
            DEFAULT_UPLOADER.uploadOrThrown(source, url);
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        try {
            long chunkSize = (fileSize + threadCount - 1) / threadCount;
            CompletableFuture<?>[] futures = new CompletableFuture[threadCount];

            for (int i = 0; i < threadCount; i++) {
                final int index = i;
                long start = i * chunkSize;
                if (start >= fileSize) break;

                long end = Math.min(start + chunkSize - 1, fileSize - 1);

                futures[i] = CompletableFuture.runAsync(() -> {
                    try {
                        uploadPart(source, url, start, end, index);
                    } catch (IOException e) {
                        throw new CompletionException(e);
                    }
                }, executor);
            }

            CompletableFuture.allOf(futures).join();
        } catch (CompletionException e) {
            throw new IOException("An error occurs while uploading a file part", e.getCause());
        } finally {
            executor.shutdown();
        }
    }

    private void uploadPart(File source, URL url, long start, long end, int index) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");

        long length = end - start + 1;
        conn.setRequestProperty("Content-Range", "bytes " + start + "-" + end + "/" + source.length());
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setFixedLengthStreamingMode(length);
        conn.setConnectTimeout(10000);

        try (RandomAccessFile raf = new RandomAccessFile(source, "r");
             OutputStream out = conn.getOutputStream()) {

            raf.seek(start);
            byte[] buffer = new byte[8192];
            long remaining = length;

            while (remaining > 0) {
                int toRead = (int) Math.min(buffer.length, remaining);
                int read = raf.read(buffer, 0, toRead);
                if (read == -1) break;
                out.write(buffer, 0, read);
                remaining -= read;
            }
        }

        if (conn.getResponseCode() >= 400) {
            throw new IOException("Part " + index + " failed: " + conn.getResponseCode());
        }
    }
}