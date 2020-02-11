package info.revenberg.song.service;

import java.io.File;
import java.util.logging.Logger;

public class FileCleaningThread implements Runnable {
    private static Logger log = Logger.getLogger(FileCleaningThread.class.getName());

    private final static int waittime = 30000;
    private Thread t;
    private String filename;

    public FileCleaningThread(String filename) {
        this.filename = filename;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        try {
            Thread.sleep(waittime);
        } catch (InterruptedException e) {

            log.warning("Interrupted; " + filename + " not deleted.");
        }
        log.info("Remove: " + filename);
        File file = new File(filename);
        file.delete();

    }
}
