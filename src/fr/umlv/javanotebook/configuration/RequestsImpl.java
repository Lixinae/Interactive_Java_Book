package fr.umlv.javanotebook.configuration;

import fr.umlv.javanotebook.exercice.Exercises;
import fr.umlv.javanotebook.validation.Validation;
import fr.umlv.javanotebook.validation.ValidationImpl;
import fr.umlv.javanotebook.watcher.Watcher;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 * Project :Interactive_Java_Book
 * Created by Narex on 08/01/2016.
 */
public class RequestsImpl implements Requests {

    private final Watcher watcher; // watcher for the main folder
    private final Exercises exs; // list of exercise


    public RequestsImpl(String folder) {
        this.watcher = new Watcher(folder);
        this.exs = new Exercises(folder);
    }

    /**
     * Set all the request you need to use with the vertx server
     *
     * @param router the router of the vertx server
     */
    public void listOfRequest(Router router) {
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
        System.out.println("Asking for number of exercise in folder");
        routingContext.response().end(exs.countFiles());
    }

    private void updateFile(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        System.out.println("Was exercise " + id + " modified ?");
        if (watcher.testModify()) {
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
        String[] webChars = {"%20", "%5B", "%5D", "%7B", "%7D", "%22", "%5C", "%27", "%5E", "%C3%A8", "%C3%A7",
                "%C3%A9", "%C2%B0", "%3C", "%3E"};
        String[] usableChars = {" ", "[", "]", "{", "}", "\"", "\\\\", "\'", "^", "è", "ç", "é", "°", "<", ">"};
        if (usableChars.length != webChars.length) {
            throw new IllegalStateException("Strings to translate have different size");
        }
        for (int i = 0; i < webChars.length; i++) {
            input = input.replaceAll(webChars[i], usableChars[i]);
        }
        return input;
    }

    private void validateExerciceAnnexe(RoutingContext routingContext, String id, String input) {
        Validation val = new ValidationImpl();
        String respons = val.valid(input, exs.getTestCodeFromKey(id));
        routingContext.response().end(respons);
    }


}
