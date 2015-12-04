package fr.umlv.javanotebook.exercice;

import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.parboiled.common.FileUtils;
import org.parboiled.common.Preconditions;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Main_test_Parser {

	// TODO
	// Ajouter l'id de l'exercice en parametre
	// Code totalement degueulasse a changer
	public static String toWeb(String id) {
		String fichier = "./exercice/"+id;
		InputStream input = null;
		try {
			input = new FileInputStream(fichier);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
		char[] markdown = FileUtils.readAllChars(input);
		Preconditions.checkNotNull(markdown, "The specified file isn't found - "+fichier);
		PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);

		// convertie le fichier markdown au format html
		// Creer page html basique avec fichier javascript dans le header
		// <head> <script src="truc.js"> </script> </head>
		// Ajouter le code generer dans une div dans la page par defaut
		String html_code=processor.markdownToHtml(markdown);
		return html_code;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		

		
		//System.out.println(html_code);
	}
}
