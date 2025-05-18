package me.tr.trFiles.configuration.file.json;

import me.tr.trFiles.configuration.file.FileOptions;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

public class JsonOptions extends FileOptions {
    private boolean prettyPrinting = true;
    private String dateFormat = "yyyy-MM-dd | HH:mm:ss ";
    private int version = 1;

    protected JsonOptions(MemoryConfiguration configuration) {
        super(configuration);
    }

    public boolean prettyPrinting() {
        return prettyPrinting;
    }

    public void prettyPrinting(boolean prettyPrinting) {
        this.prettyPrinting = prettyPrinting;
    }

    public String dateFormat() {
        return dateFormat;
    }

    public void dateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int version() {
        return version;
    }

    public void version(int version) {
        this.version = version;
    }
}
