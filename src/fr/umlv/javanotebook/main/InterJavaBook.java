package fr.umlv.javanotebook.main;

import fr.umlv.javanotebook.configuration.Server;
import fr.umlv.javanotebook.exercice.Exercice;
import fr.umlv.javanotebook.exercice.Exercices;
import io.vertx.core.Vertx;

public class InterJavaBook {

	public static void main(String[] args) {
		Exercices exs = new Exercices();
		exs.getAllExercicesAndAnswers();
		Server s = new Server();
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(s);
		s.print_url();
	}
}
