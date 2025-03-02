package me.tr.configuration;

/**
 * Various settings for controlling the input and output of a {@link
 * Configuration}
 */
public class Options {
    private char pathSeparator = '.';
    private boolean copyDefault = false;
    private final Configuration configuration;

    protected Options(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration configuration() {
        return configuration;
    }

    public char pathSeparator() {
        return pathSeparator;
    }

    public Options pathSeparator(char pathSeparator) {
        this.pathSeparator = pathSeparator;
        return this;
    }

    public boolean copyDefault() {
        return copyDefault;
    }

    public Options copyDefault(boolean copyDefault) {
        this.copyDefault = copyDefault;
        return this;
    }
}
