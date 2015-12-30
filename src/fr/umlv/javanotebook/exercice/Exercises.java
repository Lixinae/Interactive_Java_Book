package fr.umlv.javanotebook.exercice;

import fr.umlv.javanotebook.test.Test_Exo;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * this class implement a list of Exercise.
 */
public class Exercises {

	private final List<Exercise> exercises = new ArrayList<>();

	/**
	 * Fetches all the exercise Associating the id of the exercise with the
	 * awaited respons
	 */
	public Exercises() {
		getAllExercicesAndAnswers();
	}

	/**
	 * @return returns the number of exercises in the exercice folder
	 */
	public String countFiles() {
		return "" + numberOfFiles();
	}

	private int numberOfFiles() {
		File f = new File("./exercice");
		return f.listFiles().length;
	}

	private void getAllExercicesAndAnswers() {
		for (int i = 0; i < numberOfFiles(); i++) {
			exercises.add(Exercise.create_Exercise(Integer.toString(i),
					Test_Exo.class));
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
		throw new IllegalArgumentException("Answer to exercice " + key
				+ " doesn't exist");
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
