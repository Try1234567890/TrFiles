package me.tr.trfiles.management.io.reader.stream;

import java.io.IOException;
import java.io.InputStream;

public class CharsStreamReader implements ISReader<char[]> {

    @Override
    public char[] readOrThrown(InputStream is, long from, long to) throws IOException {
        int len = (int) (to - from);
        is.skip(from);

        byte[] buf = new byte[len];

        int bytesRead = is.read(buf, 0, len);

        if (bytesRead != len) {
            throw new java.io.IOException("Cannot read all content of provided input stream.");
        }

        char[] chars = new char[len];

        for (int i = 0; i < len; i++) {
            chars[i] = (char) (buf[i] & 0xFF);
        }

        return chars;
    }
}
