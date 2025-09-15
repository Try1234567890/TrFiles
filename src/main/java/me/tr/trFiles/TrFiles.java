package me.tr.trFiles;

import me.tr.trFiles.configuration.ConfigRegistry;

public class TrFiles {

    /**
     * This method must be called on your program init.
     */
    public static void init() {
        ConfigRegistry.registerAll("me.tr.trFiles.configuration.implementations");
    }

}
