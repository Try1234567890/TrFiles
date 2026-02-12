package me.tr.trfiles.management.writer.stream;

import java.io.IOException;
import java.io.OutputStream;

public class CharsStreamWriter implements OSWriter<char[]> {

    @Override
    public void writeOrThrown(OutputStream os, char[] chars) throws IOException {
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }
        os.write(bytes);
    }
}
