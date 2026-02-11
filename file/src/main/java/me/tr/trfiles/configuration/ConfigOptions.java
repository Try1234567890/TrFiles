package me.tr.trfiles.configuration;

/**
 * Various settings for controlling the input and output of a {@link
 * Config}
 */
public class ConfigOptions {
    private char pathSeparator = '.';
    private final Config configuration;

    protected ConfigOptions(Config configuration) {
        this.configuration = configuration;
    }

    /**
     * Gets the character used to separate path
     * elements in a configuration path.
     *
     * @return the path separator character.
     */
    public char getPathSeparator() {
        return pathSeparator;
    }

    public void setPathSeparator(char pathSeparator) {
        this.pathSeparator = pathSeparator;
    }

    public Config getConfiguration() {
        return configuration;
    }
}
