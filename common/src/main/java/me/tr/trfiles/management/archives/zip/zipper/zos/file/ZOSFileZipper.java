package me.tr.trfiles.management.archives.zip.zipper.zos.file;

import me.tr.trfiles.management.archives.zip.zipper.zos.ZOSZipper;
import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZOSFileZipper implements ZOSZipper<File> {
    private static final ZOSFolderZipper FOLDER_ZIPPER = new ZOSFolderZipper();

    @Override
    public void zipOrThrown(ZipOutputStream zip, File value) throws IOException {
        if (value.isDirectory()) {
            FOLDER_ZIPPER.zipOrThrown(zip, value);
            return;
        }

        zipEntry(zip, value);
    }

    void zipEntry(ZipOutputStream zip, File value) throws IOException {
        ZipEntry entry = new ZipEntry(value.getPath());
        zip.putNextEntry(entry);
        Streamings.writeOrThrown(value, zip);
        zip.closeEntry();
    }
}
