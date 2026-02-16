package me.tr.trfiles.management.io.writer.streaming.file;

import java.io.*;

public class FileToStreamStreaming implements FileStreaming<OutputStream> {

    @Override
    public void writeOrThrown(File from, OutputStream destination) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(from))) {
            byte[] buffer = new byte[16384]; // 16KB
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                destination.write(buffer, 0, bytesRead);
            }
        }
    }
}
