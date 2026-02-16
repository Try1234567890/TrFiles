package me.tr.trfiles.management.connection.uploader.file;

import me.tr.trfiles.management.io.writer.streaming.Streamings;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUploader implements IFileUploader {

    @Override
    public void uploadOrThrown(File source, URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setFixedLengthStreamingMode(source.length());

        try (OutputStream out = conn.getOutputStream()) {
            Streamings.writeOrThrown(source, out);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode >= 400) {
            throw new IOException("HTTP upload failed with code: " + responseCode);
        }
    }
}