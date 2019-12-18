package Backend.Views.View;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

import Backend.Views.Views;

public class Verify extends Views {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    int returnCode = 200;
    String message = "status=200&message=OK";
    exchange.sendResponseHeaders(returnCode, 0);
    OutputStream os = exchange.getResponseBody();
    os.write(message.getBytes("UTF-8"));
    os.close();
  }

}