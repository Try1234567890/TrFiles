package me.tr.trfiles.management.connection.downloader.file;

import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileDownloader implements IFileDownloader {

    @Override
    public File downloadOrThrown(URL url, File destination) throws IOException {
        try (InputStream in = url.openStream()) {
            Streamings.writeOrThrown(in, destination);
        }
        return destination;
    }

}
