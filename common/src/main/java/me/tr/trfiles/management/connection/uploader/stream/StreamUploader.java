package me.tr.trfiles.management.connection.uploader.stream;

import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StreamUploader implements IStreamUploader {

    @Override
    public void uploadOrThrown(InputStream source, URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setChunkedStreamingMode(16384);

        try (OutputStream out = conn.getOutputStream()) {
            Streamings.writeOrThrown(source, out);
        }

        if (conn.getResponseCode() >= 400) {
            throw new IOException("HTTP upload failed");
        }
    }
}