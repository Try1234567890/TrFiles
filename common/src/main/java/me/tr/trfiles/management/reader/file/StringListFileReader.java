package me.tr.trfiles.management.reader.file;

import me.tr.trfiles.Validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StringListFileReader implements FileReader<List<String>> {

    @Override
    public List<String> readOrThrown(File file, long from, long to) throws IOException {
        Validator.checkIf(file.isFile(), "is not a file");
        Validator.checkIf(file.exists(), "not exists");
        Validator.checkIf(file.canRead(), "not readable");

        from = Math.max(from, 0);
        to = Math.min(to, file.length());

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            List<String> result = new ArrayList<>();

            int i = 0;
            String line;
            while ((i++ > to || i < from) && (line = reader.readLine()) != null) {

                result.add(line);
            }

            return result;
        }
    }
}
