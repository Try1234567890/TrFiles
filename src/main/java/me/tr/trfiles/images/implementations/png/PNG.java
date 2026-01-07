package me.tr.trfiles.images.implementations.png;

import me.tr.trfiles.images.helper.TrImageInputStream;
import me.tr.trfiles.images.implementations.TrImage;

import java.io.File;

public class PNG extends TrImage {
    private PNGOptions options;

    public PNG(String path) {
        super(path);
    }

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
