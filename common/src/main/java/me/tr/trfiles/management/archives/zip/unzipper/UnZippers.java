package me.tr.trfiles.management.archives.zip.unzipper;


import me.tr.trfiles.management.archives.zip.unzipper.zip.ParallelFileUnZipper;
import me.tr.trfiles.management.archives.zip.unzipper.zip.ZIPFileUnZipper;
import me.tr.trfiles.management.archives.zip.unzipper.zis.ZISFileUnZipper;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class UnZippers {
    private static final ZISFileUnZipper ZIS_FILE_UNZIPPER = new ZISFileUnZipper();
    private static final ZIPFileUnZipper ZIP_FILE_UNZIPPER = new ZIPFileUnZipper();
    private static final ParallelFileUnZipper PARALLEL_FILE_UNZIPPER = new ParallelFileUnZipper();

    private UnZippers() {
    }

    public static File unzipOrThrown(ZipInputStream zis, File file) throws IOException {
        return ZIS_FILE_UNZIPPER.unzipOrThrown(zis, file);
    }

    public static File unzip(ZipInputStream zis, File file) {
        return ZIS_FILE_UNZIPPER.unzip(zis, file);
    }

    public static File unzipOrThrown(ZipFile zip, File file) throws IOException {
        return ZIP_FILE_UNZIPPER.unzipOrThrown(zip, file);
    }

    public static File unzip(ZipFile zip, File file) {
        return ZIP_FILE_UNZIPPER.unzip(zip, file);
    }

    public static File unzipParallelOrThrown(ZipFile zip, File file) throws IOException {
        return PARALLEL_FILE_UNZIPPER.unzipOrThrown(zip, file);
    }

    public static File unzipParallel(ZipFile zip, File file) {
        return PARALLEL_FILE_UNZIPPER.unzip(zip, file);
    }

}















