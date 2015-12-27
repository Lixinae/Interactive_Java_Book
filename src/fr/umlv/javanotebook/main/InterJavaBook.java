package fr.umlv.javanotebook.main;


import fr.umlv.javanotebook.configuration.Server;
import io.vertx.core.Vertx;

public class InterJavaBook {

    public static void main(String[] args) {
        Server s = new Server();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(s);
        s.print_url();
    }
}
