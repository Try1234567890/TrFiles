package me.tr.trFiles.images.helper;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TrImageInputStream extends FileInputStream {
    private File file;
    private boolean closed;

    public TrImageInputStream(@NotNull File file) throws FileNotFoundException {
        super(file);
        closed = false;
    }


    @Override
    public int read() throws IOException {
        if (closed) throw new IOException("Stream closed");
        return super.read();
    }

    @Override
    public int read(byte @NotNull [] b, int off, int len) throws IOException {
        if (closed) throw new IOException("Stream closed");
        return super.read(b, off, len);
    }

    @Override
    public int read(byte @NotNull [] b) throws IOException {
        if (closed) throw new IOException("Stream closed");
        return super.read(b);
    }

    @Override
    public long skip(long n) throws IOException {
        if (closed) throw new IOException("Stream closed");
        return super.skip(n);
    }

    @Override
    public int available() throws IOException {
        if (closed) throw new IOException("Stream closed");
        return super.available();
    }

    @Override
    public void close() throws IOException {
        closed = true;
        super.close();
    }

    public File getFile() {
        return file;
    }

    public boolean isClosed() {
        return closed;
    }
}
