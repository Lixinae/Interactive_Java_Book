package fr.umlv.javanotebook.configuration;

import fr.umlv.javanotebook.exercice.Exercises;
import fr.umlv.javanotebook.validation.MyValidation;
import fr.umlv.javanotebook.watcher.Watcher;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class Server extends AbstractVerticle {

	private final int port; // port du server
	private final String adress; // nom du serveur , localhost because we work on local only
	private final Watcher watcher;
	private final Exercises exs;
	private final MyValidation valid,valid2,valid3;
	private int countValid=0;

	/**
	 * Initialize the name and port of the server And create a new watcher on
	 * the folder "exercice"
	 */

	public Server() {
		this.adress = "localhost";
		this.port = 8989;
		watcher = new Watcher("./exercice");
		exs = new Exercises();
		valid = new MyValidation();
		valid2 = new MyValidation();
		valid3 = new MyValidation();
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
		String exToWeb = exs.getToWebFromKey(id);
		routingContext.response().end(exToWeb);
	}

	private void getNumberOfFiles(RoutingContext routingContext) {
		routingContext.request();
		System.out.println("Asking for number of exercise in folder");
		routingContext.response().end(exs.countFiles());
	}

	private void updateFile(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		System.out.println("Was exercise " + id + " modified ?");
		if (watcher.action()) {
			System.out.println("Exercise " + id + " modified");
			routingContext.response().end(exs.getToWebFromKey(id));
		} else {
			System.out.println("Exercise " + id + " wasn\'t modified ");
			routingContext.response().end();
		}
	}

	private void validateExercice(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		String input = routingContext.request().getParam("input");
		System.out.println("Asking to validate exercise " + id);
		
		// must clean the string of stupid web characters
		input = cleanWebChars(input);
		System.out.println(input);
		switch(countValid){
		case 0:
			countValid++;
			validateExerciceAnnexe(routingContext, id,valid,input);
			break;
		case 1:
			countValid++;
			validateExerciceAnnexe(routingContext, id,valid2,input);
			break;
		case 2:
			countValid++;
			validateExerciceAnnexe(routingContext, id,valid3,input);
			break;
		default:
			validateExerciceAnnexe(routingContext, id,valid,input);
		}
	}
	private String cleanWebChars(String input) {
		String[] chaineatrad = {"%20","%5B","%5D","%7B","%7D","%22","%5C","%27","%5E","%C3%A8","%C3%A7","%C2%B0"};
		String[] chainetrad = {" ","[","]","{","}","\"","\\\\","\'","^","é","ç","°"};
		if(chainetrad.length != chaineatrad.length){
			throw new IllegalStateException("error traductor WebChars");
		}
		for(int i=0;i<chaineatrad.length;i++){
			input=input.replaceAll(chaineatrad[i],chainetrad[i]);
		}
		return input;
	}

	private void validateExerciceAnnexe(RoutingContext routingContext, String id,MyValidation val,String input) {
		val.addInQueue(input);
		if (!val.accept()) {
			routingContext.response().end(val.status());
		} else if (val.validate().compareTo(exs.getAnswerFromKey(id)) == 0) {
			routingContext.response().end("Congratulations");
		} else {
			routingContext.response().end("Wrong answer");
		}
		val.reset();
		countValid--;
	}

	/*
	 * private void showJUnitTest(RoutingContext routingContext){ String id =
	 * routingContext.request().getParam("id");
	 * System.out.println("Asking to see JUnit test for exercise "+id);
	 * routingContext.response().end(); }
	 */

	/**
	 * Prints the adress of the server on the terminal
	 */
	public void print_url() {
		System.out.println("Copy to browser to start");
		System.out.println(this.adress + ":" + this.port);
	}
}