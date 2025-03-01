package me.tr.configuration;

/**
 * Various settings for controlling the input and output of a {@link
 * Configuration}
 */
public class Options {
    private char pathSeparator = '.';
    private boolean copyDefaults = false;
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

    public Options pathSeparator(char value) {
        this.pathSeparator = value;
        return this;
    }

    public boolean copyDefaults() {
        return copyDefaults;
    }

    public Options copyDefaults(boolean value) {
        this.copyDefaults = value;
        return this;
    }
}
