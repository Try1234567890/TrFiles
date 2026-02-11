package me.tr.trfiles.management.reader.stream;

import me.tr.trfiles.Validator;

import java.io.IOException;
import java.io.InputStream;

public class BytesStreamReader implements ISReader<byte[]> {

    @Override
    public byte[] readOrThrown(InputStream is, long from, long to) throws IOException {
        int len = (int) (to - from);
        is.skip(from);

        byte[] buf = new byte[len];
        int bytesRead = is.read(buf, 0, len);

        if (bytesRead != len) {
            throw new java.io.IOException("Cannot read all content of provided input stream.");
        }

        return buf;
    }
}
