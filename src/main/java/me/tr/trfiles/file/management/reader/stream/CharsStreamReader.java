package me.tr.trfiles.file.management.reader.stream;

import me.tr.trfiles.Validator;
import me.tr.trfiles.file.management.reader.file.FileReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CharsStreamReader implements ISReader<char[]> {

    @Override
    public char[] readOrThrown(InputStream is) throws IOException {
        byte[] bytes = is.readAllBytes();
        int len = bytes.length;

        char[] chars = new char[len];

        for (int i = 0; i < len; ++i) {
            chars[i] = (char) bytes[i];
        }

        return chars;
    }
}
