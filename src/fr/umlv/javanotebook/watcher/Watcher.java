package fr.umlv.javanotebook.watcher;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

import static java.nio.file.StandardWatchEventKinds.*;


// Implementer a la fin
public class Watcher {
	private WatchService watcher;
	private Path dir;
	private boolean isWorking;
	
	
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
			isWorking = true;
		} catch (IOException e) {
			isWorking = false;
			System.err.println(e);
		}
	}
	
	// Not the final name

	// TODO Add documentation
	
	/**
	 * 
	 * 
	 * 
	 * 
	 */
	
	public void actions() {
		while (isWorking) {
			WatchKey key;
			try {
				key = watcher.take();
				// renvoie 2 exception
			} catch (InterruptedException ex) {
				isWorking = false;
				return;
			}
			doEvents(key);
			//boolean valid = key.reset();
			if (!key.reset()) {
				break;
			}
		}
		
	}

	private void doEvents(WatchKey key) {
		for (WatchEvent<?> event : key.pollEvents()) {
			Kind<?> kind = event.kind();
			// TODO
			// SuppressWarning -> 
			@SuppressWarnings("unchecked")
			WatchEvent<Path> ev = (WatchEvent<Path>) event;
			Path fileName = ev.context();
			
			//System.out.println(kind.name() + ": " + fileName);
			
			if (kind == OVERFLOW) {
				continue;
			} else if (kind == ENTRY_CREATE) {
				// Eventuellement ajouter dans liste sinon rien
			} else if (kind == ENTRY_DELETE) {

			} else if (kind == ENTRY_MODIFY) {
				/* TODO
				// Update de l'exercice modifie si c'est exercice courant
				// Si autre exo -> rien faire , chargement effectuer uniquement
				// lors de la demande utilisateur
				*/
			}
		}
	}
}

	
