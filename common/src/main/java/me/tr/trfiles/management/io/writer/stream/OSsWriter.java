package me.tr.trfiles.management.io.writer.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class OSsWriter {
    public static final BytesStreamWriter BYTES_WRITER = new BytesStreamWriter();
    public static final CharsStreamWriter CHARS_WRITER = new CharsStreamWriter();
    public static final StringStreamWriter STRING_WRITER = new StringStreamWriter();
    public static final StringListStreamWriter STRING_LIST_WRITER = new StringListStreamWriter();


    private OSsWriter() {
    }

    public static void writerBytesOrThrown(OutputStream os, byte[] bytes) throws IOException {
        BYTES_WRITER.writeOrThrown(os, bytes);
    }

    public static void writerBytes(OutputStream os, byte[] bytes) {
        BYTES_WRITER.write(os, bytes);
    }

    public static void writerCharsOrThrown(OutputStream os, char[] chars) throws IOException {
        CHARS_WRITER.writeOrThrown(os, chars);
    }

    public static void writerChars(OutputStream os, char[] chars) {
        CHARS_WRITER.write(os, chars);
    }

    public static void writerStringOrThrown(OutputStream os, String string) throws IOException {
        STRING_WRITER.writeOrThrown(os, string);
    }

    public static void writerString(OutputStream os, String string) {
        STRING_WRITER.write(os, string);
    }

    public static void writerStringListOrThrown(OutputStream os, List<String> strings) throws IOException {
        STRING_LIST_WRITER.writeOrThrown(os, strings);
    }

    public static void writerStringList(OutputStream os, List<String> strings) {
        STRING_LIST_WRITER.write(os, strings);
    }
}
