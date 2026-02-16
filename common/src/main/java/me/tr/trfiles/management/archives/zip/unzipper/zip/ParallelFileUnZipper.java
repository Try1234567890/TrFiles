package me.tr.trfiles.management.archives.zip.unzipper.zip;

import me.tr.trfiles.management.FileManager;
import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ParallelFileUnZipper implements ZIPUnZipper {
    @Override
    public File unzipOrThrown(ZipFile zip, File to) throws IOException {
        if (!to.isDirectory()) {
            throw new IllegalArgumentException("The destination " + to.getPath() + " is not a directory");
        }

        List<? extends ZipEntry> entries = Collections.list(zip.entries());
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (ZipEntry entry : entries) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    processEntry(zip, entry, to);
                } catch (IOException e) {
                    throw new CompletionException(e);
                }
            }, executor);
            futures.add(future);
        }

        try {
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (CompletionException e) {
            if (e.getCause() instanceof IOException io) {
                throw io;
            }
            throw new IOException("Error during parallel extraction", e);
        } finally {
            executor.shutdown();
        }

        return to;
    }

    private void processEntry(ZipFile zip, ZipEntry entry, File to) throws IOException {
        String entryName = entry.getName();
        File out = new File(to, entryName);
        checkForZipSlip(to, out);

        if (entry.isDirectory()) {
            FileManager.createAsFolder(out);
        } else {
            FileManager.createAsFile(out);
            try (InputStream in = zip.getInputStream(entry)) {
                FileManager.createAsFile(out);
                Streamings.writeOrThrown(in, out);
            }
        }
    }
}
