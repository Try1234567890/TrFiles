package me.tr.trfiles.file.management.reader.stream;

import me.tr.trfiles.Validator;
import me.tr.trfiles.file.management.reader.file.FileReader;

import java.io.*;
import java.util.List;

public class StringListStreamReader implements ISReader<List<String>> {

    @Override
    public List<String> readOrThrown(InputStream is) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(is))) {
            return reader.lines().toList();
        }
    }
}
