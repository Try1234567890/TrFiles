package me.tr.trfiles.management.archives.zip;

import me.tr.trfiles.management.archives.zip.unzipper.UnZippers;
import me.tr.trfiles.management.archives.zip.zipper.Zippers;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zips {
    private Zips() {
    }


    public static void zipFileOrThrown(ZipOutputStream zip, File value) throws IOException {
        Zippers.zipOrThrown(zip, value);
    }

    public static void zipFile(ZipOutputStream zip, File value) {
        Zippers.zip(zip, value);
    }

    public static void zipFolderOrThrown(ZipOutputStream zip, File value) throws IOException {
        Zippers.zipOrThrown(zip, value);
    }

    public static void zipFolder(ZipOutputStream zip, File value) {
        Zippers.zip(zip, value);
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
        Zippers.zipOrThrown(zip, value);
    }

    public static void zipFile(File zip, File value) {
        Zippers.zip(zip, value);
    }

    public static void zipFolderOrThrown(File zip, File value) throws IOException {
        Zippers.zipOrThrown(zip, value);
    }

    public static void zipFolder(File zip, File value) {
        Zippers.zip(zip, value);
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
        Zippers.zipOrThrown(zip, value);
    }

    public static void zipParallel(File zip, File value) {
        Zippers.zip(zip, value);
    }


    public static File unzipOrThrown(ZipInputStream zis, File file) throws IOException {
        return UnZippers.unzipOrThrown(zis, file);
    }

    public static File unzip(ZipInputStream zis, File file) {
        return UnZippers.unzip(zis, file);
    }

    public static File unzipOrThrown(ZipFile zip, File file) throws IOException {
        return UnZippers.unzipOrThrown(zip, file);
    }

    public static File unzip(ZipFile zip, File file) {
        return UnZippers.unzip(zip, file);
    }

    public static File unzipParallelOrThrown(ZipFile zip, File file) throws IOException {
        return UnZippers.unzipOrThrown(zip, file);
    }

    public static File unzipParallel(ZipFile zip, File file) {
        return UnZippers.unzip(zip, file);
    }
}
