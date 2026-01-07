package me.tr.trfiles.file.management.writer.file;

import me.tr.trfiles.Validator;
import me.tr.trfiles.file.management.FileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class StringFileWriter implements FileWriter<String> {

    @Override
    public void writeOrThrown(File file, String string) throws IOException {
        Validator.checkIf(file.exists(), () -> FileManager.createAsFile(file));
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.canWrite(), "not writable");

        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {
            writer.write(string);
            writer.flush();
        }
    }
}
