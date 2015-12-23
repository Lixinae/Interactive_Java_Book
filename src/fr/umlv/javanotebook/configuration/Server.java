package fr.umlv.javanotebook.configuration;

import fr.umlv.javanotebook.exercice.Exercice;
import fr.umlv.javanotebook.exercice.Exercices;
import fr.umlv.javanotebook.validation.MyValidation;
import fr.umlv.javanotebook.watcher.Watcher;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;


public class Server extends AbstractVerticle{

	private final int port; // port du server
	private final String adress; // nom du serveur , localhost because we work on local only
	private final Watcher watcher;
	private final Exercices exs;
	//champ hashmap exerciceAndAnswers
	
	/**
	 * Initialize the name and port of the server
	 * And create a new watcher on the folder "exercice"
	 */

	public Server(){
		this.adress="localhost";
		this.port=8989;
		watcher = new Watcher("./exercice");
		exs = new Exercices();
		//recuperer les exerciceAndAnswers
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
		router.get("/watcherModify/:id").handler(this::updateFile);
		router.post("/validateExercice/:id/:input").handler(this::validateExercice);
	}
	


	
	
	private void getExercise(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		System.out.println("Asking for exercise " + id);
		String answer = exs.getAnswerFromKey(id);
		Exercice ex = new Exercice(id,answer);
		routingContext.response().end(ex.toWeb());
	}
	
	private void getNumberOfFiles(RoutingContext routingContext){
		routingContext.request();
		System.out.println("Asking for number of file in folder");
		routingContext.response().end(exs.countFiles());
	}


	private void updateFile(RoutingContext routingContext){
		String id = routingContext.request().getParam("id");
		System.out.println("Asking for exercise modify " + id);
		if(watcher.action()){
			System.out.println("Asking for exercise "+id);
			String answer = exs.getAnswerFromKey(id);
			Exercice ex = new Exercice(id,answer);
			routingContext.response().end(ex.toWeb());
		}
		else{
			System.out.println("Didn\'t update exercice");
			routingContext.response().end();
		}
	}
	private void validateExercice(RoutingContext routingContext){
		String id = routingContext.request().getParam("id");
		String input = routingContext.request().getParam("input");
		MyValidation valid = new MyValidation();
		valid.addInQueue(input);
		System.out.println("Asking to validate exercice " + id);
		if (!valid.accept()){
			routingContext.response().end(valid.status());
//			valid.reset();
		}
		else if (valid.validate().compareTo(exs.getAnswerFromKey(id))==0){
			routingContext.response().end("Congratulations");
//			valid.reset();
		}
		else{
			routingContext.response().end("Wrong answer");
//			valid.reset();
		}
		valid.reset();
	}



	private void showJUnitTest(RoutingContext routingContext){
		String id = routingContext.request().getParam("id");
		System.out.println("Asking to see JUnit test for exercise "+id);
		routingContext.response().end();
	}
	
	
	
	/**
	 * Prints the adress of the server on the terminal
	 */
	public void print_url(){
		System.out.println("Copy to browser to start");
		System.out.println(this.adress+":"+this.port);
	}
}