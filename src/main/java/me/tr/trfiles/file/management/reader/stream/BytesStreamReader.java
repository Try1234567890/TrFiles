package me.tr.trfiles.file.management.reader.stream;

import me.tr.trfiles.Validator;
import me.tr.trfiles.file.management.reader.file.FileReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class BytesStreamReader implements ISReader<byte[]> {

    @Override
    public byte[] readOrThrown(InputStream is) throws IOException {
        return is.readAllBytes();
    }
}
