package me.tr.trfiles.management.connection.uploader.stream;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelStreamUploader implements IStreamUploader {
    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB
    private final int threadCount;

    public ParallelStreamUploader(int threadCount) {
        this.threadCount = threadCount;
    }

    public ParallelStreamUploader() {
        this(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public void uploadOrThrown(InputStream source, URL url) throws IOException {
        Semaphore semaphore = new Semaphore(threadCount * 2);
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        try (source) {
            byte[] buffer = new byte[CHUNK_SIZE];
            int bytesRead;
            int chunkIndex = 0;

            while ((bytesRead = source.read(buffer)) != -1) {
                semaphore.acquire();

                final byte[] chunkData = new byte[bytesRead];
                System.arraycopy(buffer, 0, chunkData, 0, bytesRead);
                final int index = chunkIndex++;

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    try {
                        uploadChunk(chunkData, url, index);
                    } catch (IOException e) {
                        throw new CompletionException(e);
                    } finally {
                        semaphore.release();
                    }
                }, executor);

                futures.add(future);
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Upload interrupted", e);
        } catch (CompletionException e) {
            throw new IOException("An error occurs while uploading: " + e.getCause().getMessage(), e.getCause());
        } finally {
            executor.shutdown();
        }
    }

    private void uploadChunk(byte[] data, URL url, int index) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestProperty("X-Chunk-Index", String.valueOf(index));
        conn.setFixedLengthStreamingMode(data.length);
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(30000);

        try (OutputStream out = conn.getOutputStream()) {
            out.write(data);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode >= 400) {
            throw new IOException("Upload of chunk " + index + " failed with code: " + responseCode);
        }
    }
}