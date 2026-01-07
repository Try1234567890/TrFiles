package me.tr.trfiles.file.management.writer.stream;

import me.tr.trfiles.Validator;
import me.tr.trfiles.file.management.reader.file.FileReader;

import java.io.*;

public class StringStreamReader implements OSWriter<String> {

    @Override
    public void writeOrThrown(OutputStream out, String value) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out))) {
            writer.write(value);
            writer.flush();
        }
    }
}
