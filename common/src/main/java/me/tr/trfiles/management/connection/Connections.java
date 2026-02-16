package me.tr.trfiles.management.connection;

import me.tr.trfiles.management.connection.downloader.Downloaders;
import me.tr.trfiles.management.connection.uploader.Uploaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Connections {

    private Connections() {
    }

    public static File downloadOrThrown(URL url, File destination) throws IOException {
        return Downloaders.downloadOrThrown(url, destination);
    }

    public static File download(URL url, File destination) {
        return Downloaders.download(url, destination);
    }

    public static File downloadParallelOrThrown(URL url, File destination) throws IOException {
        return Downloaders.downloadParallelOrThrown(url, destination);
    }

    public static File downloadParallel(URL url, File destination) {
        return Downloaders.downloadParallel(url, destination);
    }

    public static OutputStream downloadOrThrown(URL url, OutputStream destination) throws IOException {
        return Downloaders.downloadOrThrown(url, destination);
    }

    public static OutputStream download(URL url, OutputStream destination) {
        return Downloaders.download(url, destination);
    }

    public static OutputStream downloadParallelOrThrown(URL url, OutputStream destination) throws IOException {
        return Downloaders.downloadParallelOrThrown(url, destination);
    }

    public static OutputStream downloadParallel(URL url, OutputStream destination) {
        return Downloaders.downloadParallel(url, destination);
    }

    public static void uploadOrThrown(File source, URL destination) throws IOException {
        Uploaders.uploadOrThrown(source, destination);
    }

    public static void upload(File source, URL destination) {
        Uploaders.upload(source, destination);
    }

    public static void uploadParallelOrThrown(File source, URL destination) throws IOException {
        Uploaders.uploadOrThrown(source, destination);
    }

    public static void uploadParallel(File source, URL destination) {
        Uploaders.upload(source, destination);
    }

    public static void uploadOrThrown(InputStream source, URL destination) throws IOException {
        Uploaders.uploadOrThrown(source, destination);
    }

    public static void upload(InputStream source, URL destination) {
        Uploaders.upload(source, destination);
    }

    public static void uploadParallelOrThrown(InputStream source, URL destination) throws IOException {
        Uploaders.uploadOrThrown(source, destination);
    }

    public static void uploadParallel(InputStream source, URL destination) {
        Uploaders.upload(source, destination);
    }
}
