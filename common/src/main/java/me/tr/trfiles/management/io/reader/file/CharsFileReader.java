package me.tr.trfiles.management.io.reader.file;

import me.tr.trfiles.Validator;

import java.io.File;
import java.io.IOException;

public class CharsFileReader implements FileReader<char[]> {

    @Override
    public char[] readOrThrown(File file, long from, long to) throws IOException {
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.exists(), "not exists");
        Validator.checkIf(file.canRead(), "not readable");

        from = Math.max(from, 0);
        to = Math.max(to, file.length());

        try (java.io.FileReader fis = new java.io.FileReader(file)) {
            int len = Math.toIntExact(to - from);

            fis.skip(from);

            char[] data = new char[len];
            int bytesRead = fis.read(data, 0, len);

            if (bytesRead != data.length) {
                throw new IOException("Cannot read all content of " + file.getPath());
            }

            return data;
        }
    }
}
