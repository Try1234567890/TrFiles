package me.tr.trFiles.configuration.implementations;

import me.tr.trFiles.Validator;
import me.tr.trFiles.configuration.management.FileUtility;
import me.tr.trFiles.configuration.memory.MemoryConfiguration;

import java.io.*;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public abstract class FileConfiguration extends MemoryConfiguration {
    private File file;
    private FileOptions options;

    /*
      protected FileConfiguration() {
      }
    */

    public FileConfiguration(File file, Map<String, Object> map) {
        from(file, map);
    }

    public FileConfiguration(File file, Reader reader) {
        from(file, reader);
    }

    public FileConfiguration(File file, InputStream is) {
        from(file, is);
    }

    public FileConfiguration(File file) {
        from(file);
    }

    public FileConfiguration(Path path) {
        from(path);
    }

    public FileConfiguration(String path) {
        from(path);
    }

    public FileConfiguration(ZipFile archive, File inside, File to) {
        from(archive, inside, to);
    }

    public FileConfiguration(File archive, File inside, File to) {
        from(archive, inside, to);
    }

    public FileConfiguration from(File file, Map<String, Object> map) {
        super.from(map);
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file, Reader reader) {
        super.from(reader);   // popola direttamente this
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file, InputStream is) {
        super.from(is);
        setFile(file);
        return this;
    }

    public FileConfiguration from(File file) {
        Validator.isNull(file, "The provided file is null.");
        Validator.checkIf(file.isFile(), "The saving file at " + file.getPath() + " not exists or is not a file.");
        Validator.checkIf(file.canRead(), "Cannot read the provided file at " + file.getPath());
        Validator.checkIf(file.canWrite(), "Cannot write the provided file at " + file.getPath());

        try (InputStream is = new FileInputStream(file)) {
            return from(file, is);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided file: " + file.getPath(), e);
        }
    }

    public FileConfiguration from(Path path) {
        Validator.isNull(path, "The provided path is null.");
        return from(path.toFile());
    }

    public FileConfiguration from(String path) {
        Validator.isNull(path, "The path provided is null.");
        return from(FileUtility.getFileFromString(path));
    }

    public FileConfiguration from(ZipFile archive, File inside, File to) {
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
            return from(to, archive.getInputStream(entry));
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided archive", e);
        }
    }

    public FileConfiguration from(File archiveFile, File inside, File to) {
        Validator.isNull(archiveFile, "The archive cannot be null.");
        Validator.isNull(inside, "The inside archive file cannot be null.");
        Validator.isNull(to, "The saving file cannot be null.");

        Validator.checkIf(archiveFile.canRead(), "Cannot read the archive file.");
        Validator.checkIf(FileUtility.isZip(archiveFile), "The provided archive file at" + archiveFile.getPath() + " not exists or is not a archive.");

        try (ZipFile archive = new ZipFile(archiveFile)) {
            return from(archive, inside, to);
        } catch (IOException e) {
            throw new RuntimeException("An error occurs while loading a configuration from provided archive", e);
        }
    }

    public void save() {
        super.save(getFile());
    }

    public void reload() {
        super.reload(getFile());
    }

    public void move(File to) {
        super.move(getFile(), to);
    }

    public void copy(File to) {
        super.copy(getFile(), to);
    }

    public void delete() {
        super.delete(getFile());
    }

    public void zip(File zip) {
        super.zip(getFile(), zip);
    }

    public FileConfiguration convert(FileConfiguration to) {
        MemoryConfiguration config = super.convert(getFile(), to);
        to.setConfiguration(config);
        to.setFile(getFile());
        return to;
    }

    @Override
    public String saveToString() {
        return buildHeader() + getConfiguration().saveToString() + buildFooter();
    }

    public String buildHeader() {
        return buildComments(options().getHeader());
    }

    public String buildFooter() {
        return buildComments(options().getFooter());
    }

    public String buildComments(String comments) {
        String[] lines = comments.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines)
            sb.append(options().getCommentPrefix()).append(line).append(options().getCommentSuffix()).append("\n");
        return sb.toString();
    }

    @Override
    public FileOptions options() {
        if (options == null) {
            options = new FileOptions(this);
        }
        return options;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public abstract void setConfiguration(MemoryConfiguration configuration);

    public abstract MemoryConfiguration getConfiguration();

    public abstract String[] getExtensions();

    private boolean isValid(File file) {
        for (String extension : getExtensions()) {
            if (FileUtility.getExtension(file).equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
