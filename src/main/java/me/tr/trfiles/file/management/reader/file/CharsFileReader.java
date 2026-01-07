package me.tr.trfiles.file.management.reader.file;

import me.tr.trfiles.Validator;

import java.io.File;
import java.io.IOException;

public class CharsFileReader implements FileReader<char[]> {

    @Override
    public char[] readOrThrown(File file) throws IOException {
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.exists(), "not exists");
        Validator.checkIf(file.canRead(), "not readable");

        try (java.io.FileReader fis = new java.io.FileReader(file)) {
            char[] data = new char[(int) file.length()];

            int bytesRead = fis.read(data);

            if (bytesRead != data.length) {
                throw new IOException("Cannot read all content of " + file.getPath());
            }

            return data;
        }
    }
}
