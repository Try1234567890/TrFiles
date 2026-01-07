package me.tr.trfiles.file.management.writer.file;

import me.tr.trfiles.Validator;
import me.tr.trfiles.file.management.FileManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class StringListFileWriter implements FileWriter<List<String>> {

    @Override
    public void writeOrThrown(File file, List<String> string) throws IOException {
        Validator.checkIf(file.exists(), () -> FileManager.createAsFile(file));
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.canWrite(), "not writable");

        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(file))) {

            for (String s : string) {
                writer.write(s);
                writer.newLine();
            }

            writer.flush();
        }
    }
}
