package fr.umlv.javanotebook.watcher;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;


public class Watcher {
	private WatchService watcher;
	private Path dir;

	
	/**
	 * Creates a watcher on the folder giving by path
	 * 
	 * @param path : path of the folder that is watched
	 *
	 * 
	 */
	
	public Watcher(String path) {
		try {
			watcher = FileSystems.getDefault().newWatchService();
			dir = Paths.get(path);
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * This function tests if the watcher finds a modified file in the folder attached
	 * 
	 * @return if file in folder was modified return true, else return false.
	 * 
	 */

	public boolean action() {
			WatchKey key;
			key = watcher.poll();
			if(key!=null){
				return doEvents(key);
			}
			return false;
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

	
