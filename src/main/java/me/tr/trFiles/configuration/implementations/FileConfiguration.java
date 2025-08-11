package me.tr.trFiles.configuration.implementations;

import me.tr.trFiles.TrFiles;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;
import me.tr.trFiles.general.utility.FileUtility;
import me.tr.trFiles.general.utility.Validate;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public abstract class FileConfiguration extends MemoryConfiguration {
    private FileOptions options;
    private Implementations implementation;

    /**
     * Load the configuration from the provided String.
     *
     * @param contents The file contents to load the configuration from.
     * @return the loaded configuration.
     */
    protected abstract FileConfiguration loadFromString(String contents);

    /**
     * Save the current configuration as a String.
     *
     * @return the configuration to string.
     */
    protected abstract String saveToString();

    /**
     * Create a new instance of the current FileConfiguration instance
     *
     * @return The new file configuration instance.
     */
    protected abstract FileConfiguration newInstance();

    /**
     * Load the provided file into a FileConfiguration
     *
     * @param file The file to load.
     * @return The loaded configuration
     */
    protected FileConfiguration load(File file) {
        Validate.notNull(file != null, "File cannot be null.");
        Validate.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file.");
        Validate.checkIf(file.canRead(), "File " + file.getPath() + " is not readable.");
        String contents;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            contents = builder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error while loading configuration. ", e);
        }
        setFileConfiguration(this);
        return loadFromString(contents);
    }

    /**
     * Save the current configuration to the provided file.
     *
     * @param file The file to save configuration into.
     */
    protected void save(File file) {
        Validate.notNull(file != null, "File cannot be null.");
        Validate.checkIf(file.isFile(), "Object at " + file.getPath() + " is not a file.");
        Validate.checkIf(file.canWrite(), "File " + file.getPath() + " is not writable.");
        String contents = saveToString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(contents);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving configuration. ", e);
        }
    }


    public FileConfiguration(MemoryConfiguration configuration) {
        super(configuration);
    }

    public FileConfiguration() {
    }


    /**
     * Save the current configuration to the same file used
     * while loading it.
     */
    public void save() {
        save(getFile());
    }

    /**
     * Reload the current configuration from the same file used
     * while loading it.
     */
    public void reload() {
        asMap().clear();
        load(getFile());
    }


    /**
     * Move the current configuration to the provided file and delete the original file.
     *
     * @param file The file to move the current configuration to.
     */
    public void move(File file) {
        copy(file);
        delete();
    }

    /**
     * Copy the current configuration to the provided file.
     *
     * @param file File to copy into the contents of the current configuration.
     */
    public void copy(File file) {
        Validate.notNull(file != null, "File cannot be null.");
        Validate.checkIf(file.exists(), () -> TrFiles.getFileManager().createFile(file));
        save(file);
        reload();
    }

    /**
     * Delete and clear the current configuration.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void delete() {
        asMap().clear();
        getFile().delete();
    }

    /**
     * Zip the current configuration into the provided zip file.
     *
     * @param zip Zip file to zip into.
     */
    public void zip(File zip) {
        // All checks are made in zipFile method.
        TrFiles.getFileManager().zipFile(zip, getFile());
    }

    /**
     * Zip the current configuration into an archive with the same
     * path and name of this configuration file.
     */
    public void zip() {
        File zip = new File(getFile().getParent(), getFile().getName() + ".zip");
        Validate.checkIf(zip.exists(), () -> TrFiles.getFileManager().createFile(zip));
        zip(zip);
    }


    /**
     * Convert the current configuration (changing file too) to the provided one.
     *
     * @param implementation The implementation instance to convert into the current.
     * @return the converted configuration instance.
     */
    public FileConfiguration convert(Implementations implementation) {
        Validate.notNull(implementation != null, "Implementation cannot be null.");
        FileConfiguration to = fromMap(asMap(), implementation);
        if (to.getFile() == null) {
            to.setFile(new File(getFile().getParent(),
                    FileUtility.getFileNameWithoutExtension(getFile().getName())
                            + to.getImplementation().getExtensions()[0]));
            delete();
        }
        Validate.checkIf(to.getFile().exists(), () -> TrFiles.getFileManager().createFile(to.getFile()));
        to.save(to.getFile());
        return to;
    }

    public static FileConfiguration fromJar(File jar, File inside, @Nullable File to) throws IOException {
        Validate.notNull(jar != null, "Jar cannot be null");
        Validate.checkIf(jar.isFile(), "Object at " + jar.getPath() + " is not a file.");
        Validate.checkIf(FileUtility.isJar(jar), "Object at " + jar.getPath() + " is not a jar.");
        Validate.notNull(inside != null, "File inside jar path cannot be null");

        if (to == null) {
            to = inside;
        }
        if (!to.isFile()) {
            TrFiles.getFileManager().createFile(to);
        }
        try (JarFile jarFile = new JarFile(jar)) {
            ZipEntry ze = jarFile.getEntry(TrFiles.getFileManager().getStringPathFromFile(inside));
            if (ze == null) {
                throw new FileNotFoundException("File inside " + inside + " jar " + jar.getPath() + " not found.");
            }
            InputStream is = jarFile.getInputStream(ze);
            TrFiles.getFileManager().write(is, to);
        }
        return from(to);
    }

    public static FileConfiguration from(File file) {
        Validate.notNull(file != null, "Provided path cannot be null.");
        Implementations implementation = Implementations.fromExtension(FileUtility.getExtension(file));
        if (implementation == null) {
            throw new IllegalArgumentException("File " + file + " doesn't have a valid implementation. Make sure it is one of " + Implementations.listToString());
        }
        FileConfiguration configuration = implementation.getReference().newInstance();
        configuration.setImplementation(implementation);
        configuration.setFile(file);
        return configuration.load(file);
    }

    /**
     * Create a new FileConfiguration from a map.
     *
     * @param map            The map contains the data.
     * @param implementation The implementations to create
     * @return The chosen FileConfiguration filled with provided data.
     */
    protected static FileConfiguration fromMap(Map<String, Object> map, Implementations implementation) {
        Validate.notNull(map != null, "Map cannot be null.");
        Validate.notNull(implementation != null, "Implementation cannot be null.");
        FileConfiguration newConfig = implementation.getReference().newInstance();
        if (map.isEmpty()) return newConfig;
        newConfig.asMap().putAll(map);
        return newConfig;
    }

    /**
     * Build the header of the current configuration file
     *
     * @return the header built ready for dumping.
     */
    protected String buildHeader() {
        return buildComment(options().getHeader());
    }

    /**
     * Build the footer of the current configuration file
     *
     * @return the footer built ready for dumping.
     */
    protected String buildFooter() {
        return buildComment(options().getFooter());
    }

    /**
     * Build a comment by adding start and end chars.
     *
     * @param comments The comments to build, divided in lines by '\n'
     * @return A formatted String like a comment.
     */
    protected String buildComment(String comments) {
        if (Validate.isNull(comments)) return "";
        StringBuilder builder = new StringBuilder();
        for (String comment : comments.split("\n")) {
            builder.append(options().getCommentPrefix())
                    .append(comment)
                    .append(options().getCommentSuffix())
                    .append("\n");
        }
        return builder.toString();
    }

    /**
     * Get the current configuration options.
     *
     * @return The current configuration options.
     */
    @Override
    public FileOptions options() {
        if (options == null) {
            options = new FileOptions(this);
        }
        return options;
    }

    /**
     * Get the current implementation
     *
     * @return the current implementation
     */
    public Implementations getImplementation() {
        return implementation;
    }

    /**
     * Set the current implementation
     *
     * @param implementation The implementation to set for this configuration
     */
    protected void setImplementation(Implementations implementation) {
        this.implementation = implementation;
    }
}
