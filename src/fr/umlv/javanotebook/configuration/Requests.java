package fr.umlv.javanotebook.configuration;

import io.vertx.ext.web.Router;

/**
 * Project :Interactive_Java_Book
 * Created by Narex on 08/01/2016.
 */
public interface Requests {

    /**
     * Set all the request you need to use with the vertx server
     *
     * @param router the router of the vertx server
     */
    void listOfRequest(Router router);
}
