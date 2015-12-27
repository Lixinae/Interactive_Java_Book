package fr.umlv.javanotebook.exercice;

import org.parboiled.common.FileUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

class Exercise {

    private final String respons;
    private final String numero;

    Exercise(String numero, String respons) {
        this.numero = Objects.requireNonNull(numero);
        this.respons = Objects.requireNonNull(respons);
    }

    private static String generateHtml(char[] markdown) {
        PegDownProcessor processor = new PegDownProcessor(Extensions.ALL);
        return processor.markdownToHtml(markdown);
    }

    /**
     * Parses the markdown file and returns the html code generated by the
     * PegDown processor
     *
     * @return returns the html code generated by the PegDown processor
     */
    String toWeb() {
        String fichier = "./exercice/" + numero + ".MARKDOWN";
        InputStream input;
        try {
            input = new FileInputStream(fichier);
        } catch (FileNotFoundException e) {
            return "<p>There is no Exercice " + numero + "</p>";
        }
        return generateHtml(FileUtils.readAllChars(input));
    }

    String getRespons() {
        return respons;
    }

    String getNumero() {
        return numero;
    }
}
