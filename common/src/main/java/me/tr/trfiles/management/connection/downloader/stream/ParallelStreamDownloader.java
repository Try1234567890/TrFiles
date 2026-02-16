package me.tr.trfiles.management.connection.downloader.stream;

import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.TreeMap;
import java.util.concurrent.*;

public class ParallelStreamDownloader implements IStreamDownloader {
    private static final StreamDownloader DOWNLOADER = new StreamDownloader();
    private final int threadCount;

    public ParallelStreamDownloader(int threadCount) {
        this.threadCount = threadCount;
    }

    public ParallelStreamDownloader() {
        this(Runtime.getRuntime().availableProcessors());
    }

    @Override
    public OutputStream downloadOrThrown(URL url, OutputStream destination) throws IOException {
        long fileSize = getFileSize(url);

        if (fileSize <= 0 || threadCount <= 1) {
            return DOWNLOADER.downloadOrThrown(url, destination);
        }

        long chunkSize = fileSize / threadCount;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        CompletionService<DownloadedPart> completionService = new ExecutorCompletionService<>(executor);

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            long start = i * chunkSize;
            long end = (i == threadCount - 1) ? fileSize - 1 : (start + chunkSize - 1);

            completionService.submit(() -> {
                byte[] data = downloadPartToMemory(url, start, end);
                return new DownloadedPart(index, data);
            });
        }

        TreeMap<Integer, byte[]> results = new TreeMap<>();
        int nextToRead = 0;

        try {
            for (int i = 0; i < threadCount; i++) {
                DownloadedPart part = completionService.take().get();
                results.put(part.index, part.data);

                while (results.containsKey(nextToRead)) {
                    destination.write(results.remove(nextToRead));
                    nextToRead++;
                }
            }
            destination.flush();
        } catch (InterruptedException | ExecutionException e) {
            throw new IOException("Errore durante il download parallelo", e);
        } finally {
            executor.shutdown();
        }

        return destination;
    }

    private byte[] downloadPartToMemory(URL url, long start, long end) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Range", "bytes=" + start + "-" + end);

        try (InputStream in = conn.getInputStream();
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Streamings.writeOrThrown(in, bos);
            return bos.toByteArray();
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

    private static record DownloadedPart(int index, byte[] data) {
    }
}
