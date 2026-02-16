package me.tr.trfiles.management.archives.zip.zipper;

import me.tr.trfiles.management.archives.zip.zipper.file.file.FileFileZipper;
import me.tr.trfiles.management.archives.zip.zipper.file.file.FileFolderZipper;
import me.tr.trfiles.management.archives.zip.zipper.file.parallel.ParallelFileFolderZipper;
import me.tr.trfiles.management.archives.zip.zipper.zos.file.ZOSFileZipper;
import me.tr.trfiles.management.archives.zip.zipper.zos.file.ZOSFolderZipper;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

public class Zippers {
    private static final ZOSFileZipper ZOS_FILE_ZIPPER = new ZOSFileZipper();
    private static final ZOSFolderZipper ZOS_FOLDER_ZIPPER = new ZOSFolderZipper();
    private static final FileFileZipper FILE_FILE_ZIPPER = new FileFileZipper();
    private static final FileFolderZipper FILE_FOLDER_ZIPPER = new FileFolderZipper();
    private static final ParallelFileFolderZipper PARALLEL_FILE_ZIPPER = new ParallelFileFolderZipper();

    private Zippers() {
    }

    public static void zipFileOrThrown(ZipOutputStream zip, File value) throws IOException {
        ZOS_FILE_ZIPPER.zipOrThrown(zip, value);
    }

    public static void zipFile(ZipOutputStream zip, File value) {
        ZOS_FILE_ZIPPER.zip(zip, value);
    }

    public static void zipFolderOrThrown(ZipOutputStream zip, File value) throws IOException {
        ZOS_FOLDER_ZIPPER.zipOrThrown(zip, value);
    }

    public static void zipFolder(ZipOutputStream zip, File value) {
        ZOS_FOLDER_ZIPPER.zip(zip, value);
    }

    public static void zipOrThrown(ZipOutputStream zip, File value) throws IOException {
        if (value.isFile())
            zipFileOrThrown(zip, value);
        else zipFolderOrThrown(zip, value);
    }

    public static void zip(ZipOutputStream zip, File value) {
        if (value.isFile())
            zipFile(zip, value);
        else zipFolder(zip, value);
    }


    public static void zipFileOrThrown(File zip, File value) throws IOException {
        FILE_FILE_ZIPPER.zipOrThrown(zip, value);
    }

    public static void zipFile(File zip, File value) {
        FILE_FILE_ZIPPER.zip(zip, value);
    }

    public static void zipFolderOrThrown(File zip, File value) throws IOException {
        FILE_FOLDER_ZIPPER.zipOrThrown(zip, value);
    }

    public static void zipFolder(File zip, File value) {
        FILE_FOLDER_ZIPPER.zip(zip, value);
    }

    public static void zipOrThrown(File zip, File value) throws IOException {
        if (value.isFile())
            zipFileOrThrown(zip, value);
        else zipFolderOrThrown(zip, value);
    }

    public static void zip(File zip, File value) {
        if (value.isFile())
            zipFile(zip, value);
        else zipFolder(zip, value);
    }

    public static void zipParallelOrThrown(File zip, File value) throws IOException {
        PARALLEL_FILE_ZIPPER.zipOrThrown(zip, value);
    }

    public static void zipParallel(File zip, File value) {
        PARALLEL_FILE_ZIPPER.zip(zip, value);
    }

}















