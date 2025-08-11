package me.tr.trFiles.configuration;

/**
 * Various settings for controlling the input and output of a {@link
 * Configuration}
 */
public class Options {
    private char pathSeparator = '.';
    private final Configuration configuration;

    protected Options(Configuration configuration) {
        this.configuration = configuration;
    }

    public char getPathSeparator() {
        return pathSeparator;
    }

    public void setPathSeparator(char pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
