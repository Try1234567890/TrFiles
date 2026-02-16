package me.tr.trfiles.memory;

import java.awt.image.BufferedImage;

public interface MemoryImageEntry {

    byte[] getSignature();

    MemoryImage newInstance(BufferedImage image);
}
