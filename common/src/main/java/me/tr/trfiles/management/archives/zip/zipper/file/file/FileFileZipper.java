package me.tr.trfiles.management.archives.zip.zipper.file.file;

import me.tr.trfiles.management.archives.zip.zipper.file.FileZipper;
import me.tr.trfiles.management.archives.zip.zipper.zos.file.ZOSFileZipper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class FileFileZipper implements FileZipper<File> {
    private static final ZOSFileZipper ZOS_ZIPPER = new ZOSFileZipper();

    @Override
    public void zipOrThrown(File zip, File value) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            ZOS_ZIPPER.zipOrThrown(zos, value);
        }
    }
}
