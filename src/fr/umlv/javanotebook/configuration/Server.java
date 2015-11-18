package fr.umlv.javanotebook.configuration;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class Server extends AbstractVerticle{

	final int port; // port du server
	final String adress; // nom du serveur , localhost car on ne travail que en local 
	
	public Server(){
		this.adress="localhost";
		this.port=8989;
	}
	
	@Override
	public void start() {
		Router router = Router.router(vertx);
		// route to JSON REST APIs 
		//router.get("/exercise/:id").handler(this::getAnExercise);
		// otherwise serve static pages
		router.route().handler(StaticHandler.create());

		vertx.createHttpServer().requestHandler(router::accept).listen(port);
	}

//	private void getAnExercise(RoutingContext routingContext) {
//		String id = routingContext.request().getParam("id");
//		System.out.println("ask for an exercise by id " + id);
//		routingContext.response()
//		.putHeader("content-type", "application/json")
//		.end(exercise.toJSON());
//	}
	
	// Affiche l'url du serveur sur le terminal
	public void print_url(){
		System.out.println("Copy to browser to start");
		System.out.println(this.adress+":"+this.port);
	}
	
//	public boolean verify(){
//		return true;
//	}
	
	


}