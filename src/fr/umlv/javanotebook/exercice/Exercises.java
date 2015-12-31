package fr.umlv.javanotebook.exercice;

import fr.umlv.javanotebook.test.Test_Exo;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * this class implement a list of Exercise.
 */
public class Exercises {

	private final List<Exercise> exercises = new ArrayList<>();
	private final String folder;

	/**
	 *
	 * @param folder Folder where the exercises are
	 */
	public Exercises(String folder) {
		this.folder = Objects.requireNonNull(folder);
		getAllExercicesAndAnswers();
	}

	/**
	 * @return returns the number of exercises in the exercice folder
	 */
	public String countFiles() {
		return "" + numberOfFiles();
	}

	private int numberOfFiles() {
		Path path = Paths.get(folder);
		if (Files.exists(path)) {
			try {
				Long temp = Files.list(path).count();
				return temp.intValue();
			} catch (IOException e) {
				throw new IllegalArgumentException("There is no folder " + folder);
			}
		}
		return 0;
	}

	private void getAllExercicesAndAnswers() {
		for (int i = 0; i < numberOfFiles(); i++) {
			exercises.add(Exercise.create_Exercise(Integer.toString(i), Test_Exo.class, folder));
		}
	}

	/**
	 * get the answer for the exercice with number key.
	 *
	 * @param key is the number of the exercice
	 * @return the good answer for the exercice
	 */
	public List<Method> getAnswerFromKey(String key) {
		for (Exercise ex : exercises) {
			if (ex.getNumero().compareTo(key) == 0) {
				return ex.getRespons();
			}
		}
		throw new IllegalArgumentException("Answer to exercice " + key + " doesn't exist");
	}

	/**
	 * get the web format for the exercice with the number key
	 *
	 * @param key is the number of the exercice
	 * @return the web format for the exercice
	 */
	public String getToWebFromKey(String key) {
		for (Exercise ex : exercises) {
			if (ex.getNumero().compareTo(key) == 0) {
				return ex.toWeb();
			}
		}
		throw new IllegalStateException("Exercice doesnt exist");
	}
}
