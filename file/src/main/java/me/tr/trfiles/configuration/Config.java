package me.tr.trfiles.configuration;

import java.io.File;

/**
 * Represents a source of configurable options and settings
 */
public interface Config extends Section {

    /**
     * Write the current configuration to file.
     *
     * @param file the file to write to.
     */
    void save(File file);

    /**
     * Reload the configuration from file.
     *
     * @param file the file to reload from.
     */
    void reload(File file);

    /**
     * Move a file to another location.
     *
     * @param file the file to move.
     * @param to   the destination file.
     */
    void move(File file, File to);

    /**
     * Copy a file to another location.
     *
     * @param file the file to copy.
     * @param to   the destination file.
     */
    void copy(File file, File to);

    /**
     * Delete a file.
     *
     * @param file the file to delete.
     */
    void delete(File file);

    /**
     * Zip a file.
     *
     * @param zip  the destination zip file
     * @param file the file to zip
     */
    //void zip(File zip, File file);

    /**
     * Convert the current configuration to another memory configuration.
     *
     * @param to the memory configuration to convert to
     * @return the converted memory configuration
     */
    //MemoryConfiguration convert(MemoryConfiguration to);

    /**
     * Get the configuration options.
     *
     * @return the configuration options
     */
    ConfigOptions getOptions();

}
