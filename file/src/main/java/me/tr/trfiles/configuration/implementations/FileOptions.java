package me.tr.trfiles.configuration.implementations;

import me.tr.trfiles.configuration.memory.MemoryOptions;

/**
 * This class contains the FileConfiguration options.
 */
public class FileOptions extends MemoryOptions {
    private String commentPrefix;
    private String commentSuffix;
    private String header;
    private String footer;

    protected FileOptions(FileConfiguration configuration, String commentPrefix, String commentSuffix) {
        super(configuration.getMemory());
        setCommentPrefix(commentPrefix);
        setCommentSuffix(commentSuffix);
        setHeader("");
        setFooter("");
    }

    protected FileOptions(FileConfiguration configuration) {
        super(configuration.getMemory());
        setCommentPrefix("");
        setCommentSuffix("");
        setHeader("");
        setFooter("");
    }

    public String getCommentPrefix() {
        return commentPrefix;
    }

    public void setCommentPrefix(String commentPrefix) {
        this.commentPrefix = commentPrefix;
    }

    public String getCommentSuffix() {
        return commentSuffix;
    }

    public void setCommentSuffix(String commentSuffix) {
        this.commentSuffix = commentSuffix;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }
}
