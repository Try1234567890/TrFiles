package me.tr.trfiles.management.io.writer.file;

import me.tr.trfiles.Validator;
import me.tr.trfiles.management.FileManager;

import java.io.File;
import java.io.IOException;

public class BytesFileWriter implements FileWriter<byte[]> {

    @Override
    public void writeOrThrown(File file, byte[] bytes) throws IOException {
        Validator.checkIf(file.exists(), () -> FileManager.createAsFile(file));
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.canWrite(), "not writable");

        try (java.io.FileOutputStream fis = new java.io.FileOutputStream(file)) {
            fis.write(bytes);
        }
    }
}
