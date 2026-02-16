package me.tr.trfiles.management.io.writer.file;

import me.tr.trfiles.Validator;
import me.tr.trfiles.management.FileManager;

import java.io.File;
import java.io.IOException;

public class CharsFileWriter implements FileWriter<char[]> {

    @Override
    public void writeOrThrown(File file, char[] chars) throws IOException {
        Validator.checkIf(file.exists(), () -> FileManager.createAsFile(file));
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.canWrite(), "not writable");

        try (java.io.FileWriter fis = new java.io.FileWriter(file)) {
            fis.write(chars);
        }
    }
}
