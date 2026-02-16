package me.tr.trfiles.management.archives.zip.unzipper.zis;

import me.tr.trfiles.management.FileManager;
import me.tr.trfiles.management.io.writer.streaming.Streamings;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZISFileUnZipper implements ZISUnZipper {


    @Override
    public File unzipOrThrown(ZipInputStream zip, File to) throws IOException {
        if (!to.isDirectory()) {
            throw new IllegalArgumentException("The destination " + to.getPath() + " is not a directory");
        }

        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            String entryName = entry.getName();
            checkForZipSlip(to, new File(entryName));
            File out = new File(to, entryName);

            if (entry.isDirectory()) {
                FileManager.createAsFolder(out);
            } else {
                FileManager.createAsFile(out);
                Streamings.writeOrThrown(zip, out);
            }
        }

        return to;
    }
}
