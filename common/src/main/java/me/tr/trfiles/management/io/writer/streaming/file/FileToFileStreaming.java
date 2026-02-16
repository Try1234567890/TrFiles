package me.tr.trfiles.management.io.writer.streaming.file;

import java.io.*;

public class FileToFileStreaming implements FileStreaming<File> {

    @Override
    public void writeOrThrown(File from, File destination) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(from));
             OutputStream os = new BufferedOutputStream(new FileOutputStream(destination))) {
            byte[] buffer = new byte[16384]; // 16KB
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
