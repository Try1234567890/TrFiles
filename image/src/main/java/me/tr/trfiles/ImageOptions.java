package me.tr.trfiles;

import me.tr.trfiles.helper.Direction;
import me.tr.trfiles.helper.Format;
import me.tr.trfiles.helper.Flags;

public class ImageOptions {
    private final Image image;
    private Flags[] flags;
    private Format format;
    private Direction direction;


    public ImageOptions(Image image) {
        this.image = image;
        this.flags = new Flags[0];
        this.format = Format.PNG;
        this.direction = Direction.UP;
    }

    public Image getImage() {
        return image;
    }

    public Flags[] getFlags() {
        return flags;
    }

    public void setFlags(Flags[] flags) {
        this.flags = flags;
    }


    public void addFlags(Flags... flags) {
        if (flags == null || flags.length == 0) return;

        Flags[] currentFlags = this.flags;
        if (currentFlags == null) {
            this.flags = flags;
            return;
        }

        Flags[] newFlags = new Flags[currentFlags.length + flags.length];
        System.arraycopy(currentFlags, 0, newFlags, 0, currentFlags.length);
        System.arraycopy(flags, 0, newFlags, currentFlags.length, flags.length);

        this.flags = newFlags;
    }


    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
