package me.tr.trfiles.management.io.writer.streaming.stream;

import java.io.*;

public class StreamToFileStreaming implements StreamStreaming<File> {

    @Override
    public void writeOrThrown(InputStream from, File destination) throws IOException {
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(destination))) {
            byte[] buffer = new byte[16384]; // 16KB
            int bytesRead;
            while ((bytesRead = from.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
