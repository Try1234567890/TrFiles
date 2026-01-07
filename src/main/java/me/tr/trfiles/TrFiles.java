package me.tr.trfiles;

public class TrFiles {

    public static void init() {

    }

    public static void initAsync() {
        Thread thread = new Thread(TrFiles::init);
        thread.setName("tr-files-init");
        thread.setDaemon(true);
        thread.start();
    }

}
