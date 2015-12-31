package fr.umlv.javanotebook.main;


import fr.umlv.javanotebook.configuration.Server;
import io.vertx.core.Vertx;

/**
 * Main class for the program
 */
public class InterJavaBook {

    public static void main(String[] args) {
        String folder = "./exercice/";
        Server s = new Server(folder);
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(s);
        s.print_url();
    }
}
