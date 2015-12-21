package fr.umlv.javanotebook.configuration;

import fr.umlv.javanotebook.exercice.Exercice;
import fr.umlv.javanotebook.exercice.Exercices;
import fr.umlv.javanotebook.validation.MyValidation;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class Server extends AbstractVerticle{

	private final int port; // port du server
	private final String adress; // nom du serveur , localhost car on ne travail que en local 
	
	/**
	 * Initialise the name and port of the server
	 * 
	 */
	
	public Server(){
		this.adress="localhost";
		this.port=8989;
	}
	
	
	/**
	 * Starts the vert-x server
	 * 
	 * Also starts all the handlers for the JQuery requests
	 */
	
	@Override
	public void start() {
		Router router = Router.router(vertx);	
		// Liste des requetes du javascript
		listOfRequest(router);
		// otherwise serve static pages
		router.route().handler(StaticHandler.create());
		vertx.createHttpServer().requestHandler(router::accept).listen(port);
	}


	private void listOfRequest(Router router) {
		router.get("/exercice/:id").handler(this::getExercise);
		router.get("/countfiles").handler(this::getNumberOfFiles);
		// Ajouter les nouvelles requetes a faire
		router.post("/validateExercice/:id/:input").handler(this::validateExercice);
		// par exemple
		// router.get("showJUnitTest").handler(this::showJUnitTest);
	}

	
	
	private void getExercise(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		System.out.println("Asking for exercise " + id);
		Exercice ex = new Exercice();
		routingContext.response().end(ex.toWeb(id));
		//.putHeader("content-type", "application/json")

	}
	
	// Sends the number of files to the client
	// & Generating all the buttons needed
	private void getNumberOfFiles(RoutingContext routingContext){
		routingContext.request();
		System.out.println("Asking for number of file in folder");
		routingContext.response().end(Exercices.countFiles());
	}
	

	private void validateExercice(RoutingContext routingContext){
		String id = routingContext.request().getParam("id");
		String input = routingContext.request().getParam("input");
		MyValidation valid = new MyValidation();
		// TODO Ajouter fonction d'ajouter dans la file d'attente
		// Changer le MyValidation.accept par autre fonction

		// exemple de fonction valid.AddQueue(input)
		System.out.println("Asking to validate exercice " + id);
		routingContext.response().end(valid.accept(id, input));
	}

	//TODO Ajouter fonction d'affichage des tests dans end
	private void showJUnitTest(RoutingContext routingContext){
		String id = routingContext.request().getParam("id");
		System.out.println("Asking to see JUnit test for exercise "+id);
		routingContext.response().end();
	}
	
	
	
	/**
	 * Prints the adress of the server on the terminal
	 */
	// Affiche l'url du serveur sur le terminal
	public void print_url(){
		System.out.println("Copy to browser to start");
		System.out.println(this.adress+":"+this.port);
	}
}