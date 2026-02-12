package me.tr.trfiles.management.reader.file;

import me.tr.trfiles.Validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class StringFileReader implements FileReader<String> {

    @Override
    public String readOrThrown(File file, long from, long to) throws IOException {
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.exists(), "not exists");
        Validator.checkIf(file.canRead(), "not readable");

        from = Math.max(from, 0);
        to = Math.min(to, file.length());

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            String line;
            while ((i++ > to || i < from) && (line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        }
    }
}
