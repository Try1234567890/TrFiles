package me.tr.trFiles.configuration.memory;

import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.Configuration;
import me.tr.trFiles.configuration.Section;
import me.tr.trFiles.configuration.management.FileManager;
import me.tr.trFiles.configuration.management.FileUtility;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class MemoryConfiguration extends MemorySection implements Configuration {
    private Configuration defaults;
    private MemoryOptions options;

    @Override
    public void addDefault(String path, Object value) {
        getDefaults().set(path, value);
    }

    @Override
    public void addDefaults(Map<String, Object> defaults) {
        convertMapsToSections(defaults, getDefaults());
    }

    @Override
    public void addDefaults(Configuration defaults) {
        this.defaults = defaults;

    }

    @Override
    public void setDefaults(Configuration defaults) {
        this.defaults = defaults;
    }

    @Override
    public Configuration getDefaults() {
        if (this.defaults == null) {
            this.defaults = from();
        }
        return this.defaults;
    }


    /**
     * Load the configuration from the provided String.
     *
     * @param contents The file contents to load the configuration from.
     */
    public abstract void loadFromString(String contents);

    /**
     * Save the current configuration as a String.
     *
     * @return the configuration to string.
     */
    public abstract String saveToString();

    public String getEmptyConfig() {
        return "{}\n";
    }


    protected void convertMapsToSections(Map<?, ?> input, Section section) {
        Validator.isNull(input, "Input map cannot be null.");
        Validator.isNull(section, "Section cannot be null.");
        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();
            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.createSection(key));
            } else {
                section.set(key, value);
            }
        }
    }

    public MemoryConfiguration() {
    }

    public MemoryConfiguration(Reader reader) {
        from(reader);
    }

    public MemoryConfiguration(InputStream is) {
        from(is);
    }

    public MemoryConfiguration(Map<String, Object> map) {
        from(map);
    }

    public MemoryConfiguration from() {
        loadFromString(getEmptyConfig());
        return this;
    }

    public MemoryConfiguration from(Reader reader) {
        Validator.isNull(reader, "The provided Reader is null");
        try {
            System.out.println("MemoryConfiguration#from() " + getClass().getName() + "@" + Integer.toHexString(hashCode()));
            loadFromString(FileManager.read(reader));
            return this;
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided reader.", e);
        }
    }

    public MemoryConfiguration from(InputStream is) {
        Validator.isNull(is, "The provided InputStream is null.");
        try (is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return from(reader);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided input stream.", e);
        }
    }

    public MemoryConfiguration from(Map<String, Object> map) {
        Validator.isNull(map, "The provided Map is null");
        convertMapsToSections(map, this);
        return this;
    }

    public MemoryConfiguration from(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.canRead(), "Cannot read the provided file at " + file.getPath());
        Validator.checkIf(file.canWrite(), "Cannot write the provided file at " + file.getPath());
        Validator.checkIf(file.isFile(), "The saving file at " + file.getPath() + " not exists or is not a file.");

        try (InputStream is = new FileInputStream(file)) {
            return from(is);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided file: " + file.getPath(), e);
        }
    }

    public MemoryConfiguration from(Path path) {
        Validator.isNull(path, "The provided path is null.");
        return from(path.toFile());
    }

    public MemoryConfiguration from(String path) {
        Validator.isNull(path, "The path provided is null.");
        return from(FileUtility.getFileFromString(path));
    }

    public MemoryConfiguration from(ZipFile archive, File inside, File to) {
        Validator.isNull(archive, "The archive file cannot be null.");
        Validator.isNull(inside, "The inside archive file cannot be null.");
        Validator.isNull(to, "The saving file cannot be null.");

        Validator.checkIf(to.canRead(), "Cannot read the saving file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write the saving file at " + to.getPath());
        Validator.checkIf(to.isFile(), "The saving file at " + to.getPath() + " not exists or is not a file.");

        Validator.checkIf(FileUtility.getExtension(inside).equalsIgnoreCase(FileUtility.getExtension(to)), "Inside archive file and saving file implementations not corresponds.");
        try (archive) {
            ZipEntry entry = archive.getEntry(FileUtility.getStringPathFromFile(inside));
            if (entry == null) {
                throw new RuntimeException("The entry " + FileUtility.getStringPathFromFile(inside) + " does not exist inside the provided archive.");
            }
            return from(archive.getInputStream(entry));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided archive", e);
        }
    }

    public void save(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.canRead(), "Cannot read the file at " + file.getPath());
        Validator.checkIf(file.canWrite(), "Cannot write the file at " + file.getPath());
        Validator.checkIf(file.exists(), () -> FileManager.create(file));

        FileManager.write(saveToString(), file);
    }

    public void reload(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.canRead(), "Cannot read the file at " + file.getPath());
        Validator.checkIf(file.canWrite(), "Cannot write the file at " + file.getPath());
        Validator.checkIf(file.exists(), () -> FileManager.create(file));

        loadFromString(FileManager.read(file));
    }

    public void move(File file, File to) {
        copy(file, to);
        FileManager.delete(file);
    }

    public void copy(File file, File to) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.canRead(), "Cannot read the file at " + file.getPath());
        Validator.checkIf(file.canWrite(), "Cannot write the file at " + file.getPath());
        Validator.checkIf(file.exists(), "Source file does not exist.");

        Validator.isNull(to, "The provided file is null.");
        Validator.checkIf(to.canRead(), "Cannot read the file at " + to.getPath());
        Validator.checkIf(to.canWrite(), "Cannot write the file at " + to.getPath());
        Validator.checkIf(to.exists(), () -> FileManager.create(file));
        FileManager.write(FileManager.read(file), to);
    }

    public void delete(File file) {
        FileManager.delete(file);
    }

    public void zip(File zip, File file) {
        Validator.isNull(zip, "The provided zip file is null.");
        Validator.checkIf(zip.canRead(), "Cannot read the provided zip file.");
        Validator.checkIf(zip.canWrite(), "Cannot read the write zip file.");
        Validator.checkIf(zip.isFile(), "The provided zip file at " + zip.getPath() + " does not exist or is not a file.");


        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.canRead(), "Cannot read the file at " + file.getPath());
        Validator.checkIf(file.canWrite(), "Cannot write the file at " + file.getPath());
        Validator.checkIf(file.isFile(), "The provided file at " + file.getPath() + " does not exist or is not a file.");

        FileManager.zip(zip, file);
    }

    public MemoryConfiguration convert(File file, MemoryConfiguration to) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.canRead(), "Cannot read the provided file.");
        Validator.checkIf(file.canWrite(), "Cannot write the file at " + file.getPath());
        Validator.checkIf(file.isDirectory(), "The provided file at " + file.getPath() + " is not a file or not exist.");

        Validator.isNull(to, "The provided implementation is null.");

        MemoryConfiguration configuration = to.from(file);
        FileManager.delete(file);
        configuration.save(file);

        return configuration;
    }

    @Override
    public MemoryOptions options() {
        if (options == null) {
            options = new MemoryOptions(this);
        }
        return options;
    }
}
