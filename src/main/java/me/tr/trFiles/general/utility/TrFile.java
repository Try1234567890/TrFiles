package me.tr.trFiles.general.utility;

import java.io.*;
import java.nio.file.Path;

public class TrFile {
    private final String strPath;
    private final File file;
    private final Path path;
    private InputStream is;
    private OutputStream os;
    private BufferedWriter writer;
    private BufferedReader reader;

    public TrFile(String path) {
        Validate.checkIf(!Validate.isNull(path), "Path cannot be null or empty");
        this.strPath = path;
        this.file = new File(path);
        Validate.checkIf(file.isFile(), "File does not exist or is not a file");
        this.path = file.toPath();
    }


    public TrFile(File file) {
        Validate.notNull(file != null, "File cannot be null");
        Validate.checkIf(file.isFile(), "File does not exist or is not a file");
        this.file = file;
        this.path = file.toPath();
        this.strPath = path.toString();
    }

    public TrFile(Path path) {
        Validate.checkIf(path != null, "Path cannot be null");
        this.path = path;
        this.file = path.toFile();
        Validate.checkIf(file.isFile(), "File does not exist or is not a file");
        this.strPath = path.toString();
    }

    public String getStrPath() {
        return strPath;
    }

    public File getFile() {
        return file;
    }

    public Path getPath() {
        return path;
    }

    public InputStream getInputStream() {
        if (is != null) {
            return is;
        }
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error while creating a new InputStream for " + getStrPath(), e);
        }
    }


    public OutputStream getOutputStream() {
        if (os != null) {
            return os;
        }
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error while creating a new OutputStream for " + getStrPath(), e);
        }
    }

    public BufferedWriter getWriter() {
        if (writer != null) {
            return writer;
        }
        return new BufferedWriter(new OutputStreamWriter(getOutputStream()));
    }

    public BufferedReader getReader() {
        if (reader != null) {
            return reader;
        }
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public String toString() {
        return strPath;
    }
}
