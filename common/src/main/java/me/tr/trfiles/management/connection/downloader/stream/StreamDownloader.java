package me.tr.trfiles.management.connection.downloader.stream;

import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class StreamDownloader implements IStreamDownloader {

    @Override
    public OutputStream downloadOrThrown(URL url, OutputStream destination) throws IOException {
        try (InputStream in = url.openStream()) {
            Streamings.writeOrThrown(in, destination);
        }
        return destination;
    }

}
