package fr.umlv.javanotebook.validation;


/**
 * Project :Interactive_Java_Book
 * Created by Narex on 08/01/2016.
 */
public interface Validation {

	/**
	 * valid an answer for java code,
	 * @param input The answer of the user to the given exercise
	 * @param testInput code that tests what the user entered
	 * @return Return String explain the error of answer,
	 * the method test failed, or "good answer" if method doesn't
	 * failed and Java code haven't error.
	 */
	String valid(String input, String testInput);
}
