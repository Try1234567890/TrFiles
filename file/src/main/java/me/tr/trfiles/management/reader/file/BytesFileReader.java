package me.tr.trfiles.management.reader.file;

import me.tr.trfiles.Validator;

import java.io.File;
import java.io.IOException;

public class BytesFileReader implements FileReader<byte[]> {

    @Override
    public byte[] readOrThrown(File file, long from, long to) throws IOException {
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.exists(), "not exists");
        Validator.checkIf(file.canRead(), "not readable");


        from = Math.max(from, 0);
        to = Math.min(to, file.length());

        try (java.io.FileInputStream fis = new java.io.FileInputStream(file)) {
            int len = Math.toIntExact(to - from);

            fis.skip(from);

            byte[] data = new byte[len];
            int bytesRead = fis.read(data, 0, len);

            if (bytesRead != data.length) {
                throw new java.io.IOException("Cannot read all content of " + file.getPath());
            }

            return data;
        }
    }
}
