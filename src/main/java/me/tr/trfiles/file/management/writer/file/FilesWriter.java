package me.tr.trfiles.file.management.writer.file;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FilesWriter {
    public static final BytesFileWriter BYTES_WRITER = new BytesFileWriter();
    public static final CharsFileWriter CHARS_WRITER = new CharsFileWriter();
    public static final StringFileWriter STRING_WRITER = new StringFileWriter();
    public static final StringListFileWriter STRING_LIST_WRITER = new StringListFileWriter();


    private FilesWriter() {
    }

    public static void writerBytesOrThrown(File file, byte[] bytes) throws IOException {
        BYTES_WRITER.writeOrThrown(file, bytes);
    }

    public static void writerBytes(File file, byte[] bytes) {
        BYTES_WRITER.write(file, bytes);
    }

    public static void writerCharsOrThrown(File file, char[] chars) throws IOException {
        CHARS_WRITER.writeOrThrown(file, chars);
    }

    public static void writerChars(File file, char[] chars) {
        CHARS_WRITER.write(file, chars);
    }

    public static void writerStringOrThrown(File file, String string) throws IOException {
        STRING_WRITER.writeOrThrown(file, string);
    }

    public static void writerString(File file, String string) {
        STRING_WRITER.write(file, string);
    }

    public static void writerStringListOrThrown(File file, List<String> strings) throws IOException {
        STRING_LIST_WRITER.writeOrThrown(file, strings);
    }

    public static void writerStringList(File file, List<String> strings) {
        STRING_LIST_WRITER.write(file, strings);
    }
}