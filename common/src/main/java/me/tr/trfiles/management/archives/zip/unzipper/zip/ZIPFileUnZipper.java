package me.tr.trfiles.management.archives.zip.unzipper.zip;

import me.tr.trfiles.management.FileManager;
import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZIPFileUnZipper implements ZIPUnZipper {


    @Override
    public File unzipOrThrown(ZipFile zip, File to) throws IOException {
        if (!to.isDirectory()) {
            throw new IllegalArgumentException("The destination " + to.getPath() + " is not a directory");
        }

        Enumeration<? extends ZipEntry> entries = zip.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            String entryName = entry.getName();
            checkForZipSlip(to, new File(entryName));

            File out = new File(to, entryName);

            if (entry.isDirectory()) {
                FileManager.createAsFolder(out);
            } else {
                InputStream in = zip.getInputStream(entry);
                FileManager.createAsFile(out);
                Streamings.writeOrThrown(in, out);
            }
        }

        return to;
    }
}
