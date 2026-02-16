package me.tr.trfiles.management.archives.zip.zipper.zos.file;

import me.tr.trfiles.management.archives.zip.zipper.zos.ZOSZipper;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZOSFolderZipper implements ZOSZipper<File> {
    private static final ZOSFileZipper FILE_ZIPPER = new ZOSFileZipper();

    @Override
    public void zipOrThrown(ZipOutputStream zip, File value) throws IOException {
        if (value.isFile()) {
            FILE_ZIPPER.zipOrThrown(zip, value);
            return;
        }
        File[] files = value.listFiles();

        if (files == null || files.length == 0) return;


        if (files.length == 1) {
            FILE_ZIPPER.zipOrThrown(zip, files[0]);
            return;
        }

        for (File file : files) {
            String name = file.getPath();

            if (file.isDirectory()) {
                zip.putNextEntry(new ZipEntry(name + "/"));
                zip.closeEntry();
            } else {
                FILE_ZIPPER.zipEntry(zip, file);
            }
        }
    }
}
