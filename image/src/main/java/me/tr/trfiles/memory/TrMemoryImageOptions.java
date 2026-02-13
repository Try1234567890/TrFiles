package me.tr.trfiles.memory;

import me.tr.trfiles.Image;
import me.tr.trfiles.ImageOptions;

public class MemoryImageOptions extends ImageOptions {


    public MemoryImageOptions(MemoryImage image) {
        super(image);
    }


    @Override
    public MemoryImage getImage() {
        return (MemoryImage) super.getImage();
    }
}
