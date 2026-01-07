package me.tr.trfiles.file.configuration.implementations.toon;

import dev.toonformat.jtoon.Delimiter;
import dev.toonformat.jtoon.PathExpansion;
import me.tr.trfiles.file.configuration.implementations.FileOptions;

public class ToonOptions extends FileOptions {
    private int indent;
    private Delimiter delimiter;
    private boolean strict;
    private PathExpansion expandPaths;
    boolean lengthMarker;

    protected ToonOptions(ToonConfiguration configuration) {
        super(configuration, "//", "");
        this.indent = 2;
        this.delimiter = Delimiter.COMMA;
        this.strict = true;
        this.expandPaths = PathExpansion.OFF;
        this.lengthMarker = false;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public Delimiter getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(Delimiter delimiter) {
        this.delimiter = delimiter;
    }

    public boolean isStrict() {
        return strict;
    }

    public void setStrict(boolean strict) {
        this.strict = strict;
    }

    public PathExpansion getExpandPaths() {
        return expandPaths;
    }

    public void setExpandPaths(PathExpansion expandPaths) {
        this.expandPaths = expandPaths;
    }

    public boolean isLengthMarker() {
        return lengthMarker;
    }

    public void setLengthMarker(boolean lengthMarker) {
        this.lengthMarker = lengthMarker;
    }
}
