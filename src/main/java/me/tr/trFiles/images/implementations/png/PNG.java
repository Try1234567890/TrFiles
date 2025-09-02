package me.tr.trFiles.images.implementations.png;

import me.tr.trFiles.images.helper.TrImageInputStream;
import me.tr.trFiles.images.implementations.TrImage;
import me.tr.trFiles.images.memory.MemoryImage;

import java.awt.image.BufferedImage;
import java.io.File;

public class PNG extends TrImage {
    private PNGOptions options;

    public PNG(File imageFile) {
        super(imageFile);
    }

    public PNG(TrImageInputStream is) {
        super(is);
    }

    @Override
    protected PNG toPng(int[][] pixels) {
        return this;
    }

    @Override
    protected PNG fromPng(int[][] pixels) {
        return this;
    }

    @Override
    public PNGOptions options() {
        if (options == null) {
            options = new PNGOptions(this);
        }
        return options;
    }
}
