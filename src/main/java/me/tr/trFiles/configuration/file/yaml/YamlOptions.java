package me.tr.trFiles.configuration.file.yaml;

import me.tr.trFiles.configuration.file.FileOptions;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import org.yaml.snakeyaml.DumperOptions;

public class YamlOptions extends FileOptions {
    private final String commentPrefix = "# ";
    private int intent = 2;
    private DumperOptions.FlowStyle flowStyle = DumperOptions.FlowStyle.BLOCK;
    private boolean copyHeader = false;
    private boolean copyFooter = false;
    private String header = "";
    private String footer = "";

    protected YamlOptions(MemoryConfiguration configuration) {
        super(configuration);
        super.commentPrefix(commentPrefix);
    }

    public int indent() {
        return intent;
    }

    public YamlOptions indent(int intent) {
        if (intent < 2 || intent > 9) {
            throw new IllegalArgumentException("Indentation must be between 2 and 9");
        }
        this.intent = intent;
        return this;
    }


    public DumperOptions.FlowStyle flowStyle() {
        return flowStyle;
    }

    public YamlOptions flowStyle(DumperOptions.FlowStyle flowStyle) {
        this.flowStyle = flowStyle;
        return this;
    }

    public YamlOptions header(String header) {
        this.header = header;
        return this;
    }

    public String header() {
        return header;
    }

    public YamlOptions footer(String footer) {
        this.footer = footer;
        return this;
    }

    public String footer() {
        return footer;
    }

    public boolean copyHeader() {
        return copyHeader;
    }

    public YamlOptions copyHeader(boolean copyHeader) {
        this.copyHeader = copyHeader;
        return this;
    }

    public boolean copyFooter() {
        return copyFooter;
    }

    public YamlOptions copyFooter(boolean copyFooter) {
        this.copyFooter = copyFooter;
        return this;
    }

    public String commentPrefix() {
        return commentPrefix;
    }
}
