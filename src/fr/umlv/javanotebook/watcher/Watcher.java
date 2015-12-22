package fr.umlv.javanotebook.watcher;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

import static java.nio.file.StandardWatchEventKinds.*;


/**
 * Project :Interactive_Java_Book
 * Created by Narex on 02/12/2015.
 */
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
	// TODO Add documentation
	
	/**
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
			Kind<?> kind = event.kind();
			if (kind == OVERFLOW) {
				continue;
			} 
			else if (kind == ENTRY_MODIFY) {
				key.reset();
				return true;
			}
		}
		key.reset();
		return false;
	}
}

	
