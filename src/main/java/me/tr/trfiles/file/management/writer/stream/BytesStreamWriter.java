package me.tr.trfiles.file.management.writer.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BytesStreamWriter implements OSWriter<byte[]> {

    @Override
    public void writeOrThrown(OutputStream out, byte[] bytes) throws IOException {
        out.write(bytes);
    }
}
