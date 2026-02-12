package me.tr.trfiles.management.reader.stream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StringListStreamReader implements ISReader<List<String>> {

    @Override
    public List<String> readOrThrown(InputStream is, long from, long to) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(is))) {
            List<String> result = new ArrayList<>();


            from = Math.max(from, 0);
            to = Math.min(to, is.available());

            int i = 0;
            String line;
            while ((i > to || i < from) && (line = reader.readLine()) != null) {
                result.add(line);
                i++;
            }


            return result;
        }
    }
}
