package me.tr.trFiles.configuration.implementations;

import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.configuration.memory.MemoryOptions;

public class FileOptions extends MemoryOptions {
    private String commentPrefix;
    private String commentSuffix;
    private String header;
    private String footer;

    protected FileOptions(FileConfiguration configuration, String commentPrefix, String commentSuffix) {
        super(configuration);
        setCommentPrefix(commentPrefix);
        setCommentSuffix(commentSuffix);
        setHeader("");
        setFooter("");
    }

    protected FileOptions(FileConfiguration configuration) {
        super(configuration);
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
