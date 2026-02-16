package me.tr.trfiles.management.io.reader.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class StringStreamReader implements ISReader<String> {

    @Override
    public String readOrThrown(InputStream is, long from, long to) throws IOException {

        if (from > to) {
            throw new IOException("from is major than to");
        }

        from = Math.max(from, 0);
        to = Math.max(to, is.available());

        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            String line;
            while ((i > to || i < from) && (line = reader.readLine()) != null) {
                sb.append(line).append("\n");
                i++;
            }
            return sb.toString();
        }
    }
}
