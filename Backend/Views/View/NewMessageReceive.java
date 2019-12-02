package Backend.Views.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

import Backend.Views.Views;

public class NewMessageReceive extends Views {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    if(exchange.getRequestMethod() != "POST") {
      exchange.sendResponseHeaders(403, 0);
      OutputStream os = exchange.getResponseBody();
      os.write("Forbidden".getBytes("utf-8"));
      os.close();
    } else {
      InputStream is = exchange.getRequestBody();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader bf = new BufferedReader(isr);

      StringBuffer sb = new StringBuffer();

      String s = "";

      while((s = bf.readLine()) != null) {
        sb.append(s);
      }



      String response = "main=111";
      exchange.sendResponseHeaders(200, 0);
      OutputStream os = exchange.getResponseBody();
      os.write(response.getBytes("UTF-8"));
      os.close();
    }
  }
  
}