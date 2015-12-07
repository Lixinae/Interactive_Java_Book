package fr.umlv.javanotebook.watcher;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;


// La classe Watcher ne sera implementer qu'au dernier moment , une fois que le reste
// fonctionne , elle n'est pas indispensable !!!
public class Watcher {
	private WatchService watcher;
	private Path dir;
	private boolean isWorking;
	
	
	
	
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
	
	// Can throw an InterruptedException since it can be interrupted
	// If its throw an Exception
	
	public void actions() throws InterruptedException{
		while (isWorking) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException ex) {
				throw new InterruptedException();
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
			@SuppressWarnings("unchecked")
			WatchEvent<Path> ev = (WatchEvent<Path>) event;
			Path fileName = ev.context();
			// Peut ajouter des test a ce moment pour voir ce que l'on fait en cas de modification
			System.out.println(kind.name() + ": " + fileName);

			
			
			if (kind == OVERFLOW) {
				continue;
			} else if (kind == ENTRY_CREATE) {
				// Eventuellement ajouter dans liste sinon rien
			} else if (kind == ENTRY_DELETE) {

			} else if (kind == ENTRY_MODIFY) {
				// Update de l'exercice modifié si c'est exercice courant
				// Si autre exo -> rien faire , chargement effectuer uniquement
				// lors de la demande utilisateur
			}

//			if (kind == ENTRY_MODIFY &&
//					fileName.toString().equals("WatchTest.java")) {
//				System.out.println("My source file has changed!!!");
//			}
		}
	}
}

	
