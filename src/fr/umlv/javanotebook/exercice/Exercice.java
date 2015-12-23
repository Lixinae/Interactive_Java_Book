package fr.umlv.javanotebook.exercice;

import org.parboiled.common.FileUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Project :Interactive_Java_Book
 * Created by Narex on 21/12/2015.
 */
public class Exercice {

    private final String respons;
    private final String name;

    public Exercice(String name, String respons) {
        this.name = name;
        this.respons = respons;
    	//recupere sa reponse en fonction du num exo
    }

    private static String generateHtml(char[] markdown) {
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);
        return processor.markdownToHtml(markdown);
    }
    //get reponse
    /**
     * Parses the markdown file and returns the html code generated by the PegDown processor
     *
     * @return returns the html code generated by the PegDown processor
     */
    public String toWeb() {
        Objects.requireNonNull(name);
        String fichier = "./exercice/" + name;
        InputStream input;
        try {
            input = new FileInputStream(fichier);
        } catch (FileNotFoundException e) {
            return "<p>There is no Exercice " + name + "</p>";
        }
        char[] markdown = FileUtils.readAllChars(input);
        return generateHtml(markdown);
    }

    public String getRespons(){
        return respons;
    }
}