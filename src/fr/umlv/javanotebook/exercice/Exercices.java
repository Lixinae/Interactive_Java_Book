package fr.umlv.javanotebook.exercice;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

public class Exercices {

//	private final int id;
//	private String respons;
	
	private final HashMap<Integer,Object> exercices = new HashMap<>();
	
	/**
	 * Fetches all the exercise
	 * Associating the id of the exercise with the awaited respons
	 */
	public void getAllExercices(){
		// for
		
		//exercices.put(key, value);
	}

	/**
	 * Changes the answer of an exercise
	 * 
	 * @param id : id of the exercice
	 * @param newAnswer : new Answer
	 */
	public void updateAnswer(int id,Object newAnswer){
		Objects.requireNonNull(newAnswer);
		exercices.put(id, newAnswer);
	}
	
	/**
	 * 
	 * @return returns the number of exercises in the exercice folder
	 */
	public static String countFiles(){
		return ""+new File("./exercice").listFiles().length;
	}
	
}
