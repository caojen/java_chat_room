package Backend.Views;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public abstract class Views implements HttpHandler {
  @Override
  public abstract void handle(HttpExchange exchange) throws IOException;
}