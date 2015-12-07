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
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

// Exemple de code afin voir comment fonctionne la classe "watchservice"
public class WatchTest {

	
	public static void main(String[] args) {
		try {
			WatchService watcher = FileSystems.getDefault().newWatchService();
			Path dir = Paths.get("./Exercice/");
			dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

			System.out.println("Watch Service registered for dir: " + dir.getFileName());

			while (true) {
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException ex) {
					return;
				}

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

						// process create event

					} else if (kind == ENTRY_DELETE) {

						// process delete event

					} else if (kind == ENTRY_MODIFY) {

						// process modify event

					}

					if (kind == ENTRY_MODIFY &&
							fileName.toString().equals("WatchTest.java")) {
						System.out.println("My source file has changed!!!");
					}
				}

				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}

		} catch (IOException ex) {
			System.err.println(ex);
		}
	}
}