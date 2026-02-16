package me.tr.trfiles.management.io.writer.streaming.stream;

import java.io.*;

public class StreamToStreamStreaming implements StreamStreaming<OutputStream> {

    @Override
    public void writeOrThrown(InputStream from, OutputStream destination) throws IOException {
        byte[] buffer = new byte[16384]; // 16KB
        int bytesRead;
        while ((bytesRead = from.read(buffer)) != -1) {
            destination.write(buffer, 0, bytesRead);
        }
    }
}
