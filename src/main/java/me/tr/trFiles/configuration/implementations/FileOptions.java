package me.tr.trFiles.configuration.implementations;

/**
 * This class contains the FileConfiguration options.
 */
public class FileOptions {
    private FileConfiguration configuration;
    private String commentPrefix;
    private String commentSuffix;
    private String header;
    private String footer;

    protected FileOptions(FileConfiguration configuration, String commentPrefix, String commentSuffix) {
        setConfiguration(configuration);
        setCommentPrefix(commentPrefix);
        setCommentSuffix(commentSuffix);
        setHeader("");
        setFooter("");
    }

    protected FileOptions(FileConfiguration configuration) {
        setConfiguration(configuration);
        setCommentPrefix("");
        setCommentSuffix("");
        setHeader("");
        setFooter("");
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(FileConfiguration configuration) {
        this.configuration = configuration;
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

    @Override
    public String toString() {
        return "FileOptions{" +
                "commentPrefix='" + commentPrefix + '\'' +
                ", commentSuffix='" + commentSuffix + '\'' +
                ", header='" + header + '\'' +
                ", footer='" + footer + '\'' +
                '}';
    }
}
