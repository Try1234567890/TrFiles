package me.tr.trfiles.management.archives.zip.unzipper;

import java.io.File;
import java.io.IOException;

public interface UnZipper<T> {

    File unzipOrThrown(T zip, File to) throws IOException;

    default File unzip(T zip, File to) {
        try {
            return unzipOrThrown(zip, to);
        } catch (IOException ignore) {
            return to;
        }
    }

    default void checkForZipSlip(File targetDir, File zipEntryFile) throws IOException {
        String canonicalTargetDirPath = targetDir.getCanonicalPath();
        String canonicalZipEntryPath = zipEntryFile.getCanonicalPath();

        if (!canonicalZipEntryPath.startsWith(canonicalTargetDirPath + File.separator)) {
            throw new SecurityException("Zip Slip detected! Entry " + zipEntryFile.getName() + " point outside destination folder.");
        }
    }
}
