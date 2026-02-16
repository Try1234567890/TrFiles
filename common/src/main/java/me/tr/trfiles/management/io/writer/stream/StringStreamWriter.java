package me.tr.trfiles.management.io.writer.stream;

import java.io.*;

public class StringStreamWriter implements OSWriter<String> {

    @Override
    public void writeOrThrown(OutputStream out, String value) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            writer.write(value);
            writer.flush();
        }
    }
}
