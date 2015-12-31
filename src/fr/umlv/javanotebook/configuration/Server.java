package fr.umlv.javanotebook.configuration;

import fr.umlv.javanotebook.exercice.Exercises;
import fr.umlv.javanotebook.validation.Validation;
import fr.umlv.javanotebook.watcher.Watcher;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;


/**
 * this class extends to AbstractVerticle, it is a Server for connecting
 * on local host with port 8989.
 * he wait an enter on local host for execute his request.
 * example:
 * Server s = new Server();
 * Vertx vertx = Vertx.vertx();
 * vertx.deployVerticle(s);
 * s.print_url();
 */
public class Server extends AbstractVerticle {

    private final int port; // port du server
    private final String adress; // nom du serveur , localhost because we work on local only
    private final Watcher watcher; // watcher for the main folder
    private final Exercises exs; // list of exercise

    /**
     * Initialize the name, port , exercise of the server
     * And create a new watcher on the folder "exercice"
     */

    public Server(String folder) {
        this.adress = "localhost";
        this.port = 8989;
        watcher = new Watcher(folder);
        exs = new Exercises(folder);
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
        input = cleanWebChars(input);
        validateExerciceAnnexe(routingContext, id, input);
    }

    private String cleanWebChars(String input) {
        String[] chaineatrad = {"%20", "%5B", "%5D", "%7B", "%7D", "%22", "%5C", "%27", "%5E", "%C3%A8", "%C3%A7",
                "%C3%A9", "%C2%B0", "%3C", "%3E"};
        String[] chainetrad = {" ", "[", "]", "{", "}", "\"", "\\\\", "\'", "^", "è", "ç", "é", "°", "<", ">"};
        if (chainetrad.length != chaineatrad.length) {
            throw new IllegalStateException("Strings to translate have different size");
        }
        for (int i = 0; i < chaineatrad.length; i++) {
            input = input.replaceAll(chaineatrad[i], chainetrad[i]);
        }
        return input;
    }

    private void validateExerciceAnnexe(RoutingContext routingContext, String id, String input) {
        Validation val = new Validation();
        String respons = val.valid(input, exs.getAnswerFromKey(id));
        routingContext.response().end(respons);
    }


    /*a quoi elle sert? je supprime?*/
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