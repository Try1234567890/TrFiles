package me.tr.trfiles.file.management.reader.file;

import me.tr.trfiles.Validator;

import java.io.File;
import java.io.IOException;

public class BytesFileReader implements FileReader<byte[]> {

    @Override
    public byte[] readOrThrown(File file) throws IOException {
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.exists(), "not exists");
        Validator.checkIf(file.canRead(), "not readable");

        try (java.io.FileInputStream fis = new java.io.FileInputStream(file)) {
            byte[] data = new byte[(int) file.length()];

            int bytesRead = fis.read(data);

            if (bytesRead != data.length) {
                throw new java.io.IOException("Cannot read all content of " + file.getPath());
            }

            return data;
        }
    }
}
