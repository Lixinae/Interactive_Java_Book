package fr.umlv.javanotebook.exercice;

import java.io.File;

public class Exercice {

	//private static int numberOfExercices;
	
	/**
	 * 
	 * @return returns the number of exercises in the exercice folder
	 */
	public static String countFiles(){
		return ""+new File("./exercice").listFiles().length;
	}
	
}
