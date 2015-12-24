package fr.umlv.javanotebook.exercice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Exercises {

	private final List<Exercise> exercices = new ArrayList<>();

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
		return Integer.toString(exercices.size());
	}

	private void getAllExercicesAndAnswers() {
		Path path = Paths.get("./exercice/answers.rep");
		try {
			Files.lines(path).forEach(
					l -> exercices.add(new Exercise(l.split(" : ")[0], l
							.split(" : ")[1])));
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	public String getAnswerFromKey(String key) {
		for (Exercise ex : exercices) {
			if (ex.getNumero().compareTo(key.substring(0, 1)) == 0) {
				return ex.getRespons();
			}
		}
		throw new IllegalStateException("Answer to exercice " + key
				+ " doesn't exist");
	}

	public String getToWebFromKey(String key) {
		for (Exercise ex : exercices) {
			if (ex.getNumero().compareTo(key.substring(0, 1)) == 0) {
				return ex.toWeb();
			}
		}
		throw new IllegalStateException("Exercice doesnt exist");
	}

}