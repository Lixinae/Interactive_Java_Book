package fr.umlv.javanotebook.configuration;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;


/**
 * this class extends to AbstractVerticle, it is a Server for connecting
 * on local host with port 8989.
 * he wait an enter on local host for execute his request.
 * example:
 */
public class Server extends AbstractVerticle {

    private final int port; // port du server
    private final String adress; // nom du serveur , localhost because we work on local only
    private final String exerciseFolder;

    /**
     * Initialize the name, port
     * @param exerciseFolder Folder where the exercises are
     */

    public Server(String exerciseFolder) {
        this.adress = "localhost";
        this.port = 8989;
        this.exerciseFolder = exerciseFolder;
    }

    /**
     * Starts the vert-x server
     *
     * Also starts all the handlers for the JQuery requests
     */

    @Override
    public void start() {
        Router router = Router.router(vertx);
        Requests requests = new RequestsImpl(exerciseFolder);
        requests.listOfRequest(router);
        router.route().handler(StaticHandler.create());
        vertx.createHttpServer().requestHandler(router::accept).listen(port);
    }

    /**
     * Prints the adress of the server on the terminal
     */
    
    public void print_url() {
        System.out.println("Copy to browser to start");
        System.out.println(this.adress + ":" + this.port);
    }
}