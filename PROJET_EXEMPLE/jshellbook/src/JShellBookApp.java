import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

public class JShellBookApp extends AbstractVerticle {
  static class Exercise {
    private final ArrayList<Object> items = new ArrayList<>();
    
    public void addQuestion(String text) {
      Objects.requireNonNull(text);
      items.add(text);
    }
    
    public void addAnswer(int lines) {
      items.add(lines);
    }
    
    public String toJSON() {
      return items.stream().map(o -> {
        if (o instanceof String) {  // UGLY isn't it ?
            return "{ \"question\": \"" + o + "\" }";
        }
        return "{ \"answer\": " + o + " }";
      }).collect(Collectors.joining(", ", "[", "]"));
    }
  }

  private static Exercise createFakeExercise() {
    Exercise exercise = new Exercise();
    exercise.addQuestion("This a question");
    exercise.addAnswer(3 /*lines*/);
    return exercise;
  }
  
  private final Exercise exercise = createFakeExercise();
  
  @Override
  public void start() {
    Router router = Router.router(vertx);
    // route to JSON REST APIs 
    router.get("/exercise/:id").handler(this::getAnExercise);
    // otherwise serve static pages
    router.route().handler(StaticHandler.create());

    vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    System.out.println("listen on port 8080");
  }
  
  private void getAnExercise(RoutingContext routingContext) {
    String id = routingContext.request().getParam("id");
    System.out.println("ask for an exercise by id " + id);
    routingContext.response()
       .putHeader("content-type", "application/json")
       .end(exercise.toJSON());
  }
  
  public static void main(String[] args) {
    // development option, avoid caching to see changes of
    // static files without having to reload the application,
    // obviously, this line should be commented in production
    System.setProperty("vertx.disableFileCaching", "true");
    
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new JShellBookApp());
  }
}
