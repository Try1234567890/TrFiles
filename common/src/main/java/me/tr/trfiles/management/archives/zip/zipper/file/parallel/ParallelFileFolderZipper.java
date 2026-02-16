package me.tr.trfiles.management.archives.zip.zipper.file.parallel;

import me.tr.trfiles.management.archives.zip.zipper.file.FileZipper;
import me.tr.trfiles.management.archives.zip.zipper.file.file.FileFileZipper;
import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ParallelFileFolderZipper implements FileZipper<File> {
    private static final FileFileZipper FILE_ZIPPER = new FileFileZipper();

    @Override
    public void zipOrThrown(File zip, File value) throws IOException {
        if (zip.isFile()) {
            FILE_ZIPPER.zipOrThrown(zip, value);
            return;
        }
        File[] files = value.listFiles();

        if (files == null || files.length == 0) return;


        if (files.length == 1) {
            FILE_ZIPPER.zipOrThrown(zip, files[0]);
            return;
        }

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        AtomicReference<Throwable> t = new AtomicReference<>(null);

        try (FileSystem zipfs = FileSystems.newFileSystem(zip.toPath(), env)) {
            Arrays.asList(files).parallelStream().forEach(path -> {
                try {
                    File fileInZip = zipfs.getPath("/" + path.getPath()).toFile();
                    Streamings.writeOrThrown(path, fileInZip);
                } catch (Exception e) {
                    t.set(e);
                }
            });
        }

        Throwable throwable = t.get();
        if (throwable != null)
            throw new IOException("An error occurred while zipping " + value.getPath() + " inside " + zip.getPath(), throwable);
    }
}
