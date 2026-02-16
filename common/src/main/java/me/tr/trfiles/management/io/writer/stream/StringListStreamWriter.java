package me.tr.trfiles.management.io.writer.stream;

import java.io.*;
import java.util.List;

public class StringListStreamWriter implements OSWriter<List<String>> {

    @Override
    public void writeOrThrown(OutputStream out, List<String> value) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            for (String str : value) {
                writer.write(str);
                writer.newLine();
            }

            writer.flush();
        }
    }
}
