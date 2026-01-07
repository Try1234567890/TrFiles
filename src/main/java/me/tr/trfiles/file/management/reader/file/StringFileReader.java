package me.tr.trfiles.file.management.reader.file;

import me.tr.trfiles.Validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class StringFileReader implements FileReader<String> {

    @Override
    public String readOrThrown(File file) throws IOException {
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.exists(), "not exists");
        Validator.checkIf(file.canRead(), "not readable");

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            return String.join("\n", reader.lines().toList());
        }
    }
}
