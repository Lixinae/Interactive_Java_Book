package fr.umlv.javanotebook.watcher;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * this class implement a Watcher on the folder dir.
 */
public class Watcher {
    private WatchService watcher;

    /**
     * Creates a watcher on the folder giving by path
     *
     * @param path : path of the folder that is watched
     */

    public Watcher(String path) {
        try {
            watcher = FileSystems.getDefault().newWatchService();
            Paths.get(path).register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        } catch (IOException e) {
            throw new IllegalArgumentException("The folder " + path + " doesn't exist");
        }
    }

    /**
     * This function tests if the watcher finds a modified file in the folder
     * attached
     *
     * @return if file in folder was modified return true, else return false.
     */

    public boolean testModify() {
        WatchKey key;
        key = watcher.poll();
        return key != null && doEvents(key);
    }

    private boolean doEvents(WatchKey key) {
        for (WatchEvent<?> event : key.pollEvents()) {
            if (event.kind() == ENTRY_MODIFY) {
                key.reset();
                return true;
            }
        }
        key.reset();
        return false;
    }
}
