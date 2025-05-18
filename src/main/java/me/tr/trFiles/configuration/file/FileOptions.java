package me.tr.trFiles.configuration.file;

import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.configuration.memory.MemoryOptions;

public class FileOptions extends MemoryOptions {
    private String commentPrefix = "";
    private String commentSuffix = "";

    protected FileOptions(MemoryConfiguration configuration) {
        super(configuration);
    }


    public String commentPrefix() {
        return commentPrefix;
    }

    protected void commentPrefix(String commentPrefix) {
        this.commentPrefix = commentPrefix;
    }

    public String commentSuffix() {
        return commentSuffix;
    }

    protected void commentSuffix(String commentSuffix) {
        this.commentSuffix = commentSuffix;
    }
}
