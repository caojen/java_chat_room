package Backend.Views.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;

import Backend.Views.Views;
import Backend.Models.User;
import Backend.Models.Room;

public class NewMessageReceive extends Views {

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    if(exchange.getRequestMethod() != "POST") {
      exchange.sendResponseHeaders(403, 0);
      OutputStream os = exchange.getResponseBody();
      os.write(("status=403&message="+URLEncoder.encode("Forbidden", "utf-8")).getBytes("utf-8"));
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

      int returnCode = 0;
      String returnMessage = "";

      try {
        Map<String, String> receive = Urls.stringToMap(sb.toString());

        String username = receive.get("username");
        String token = receive.get("token");
        String roomid = receive.get("roomid");

        User user = User.get_user(username);

        if(user == null) {
          returnCode = 403;
          returnMessage = "status=403&message=" + URLEncoder.encode("Authentication Failed");
        } else {
          boolean isAnthenticate = user.anthenticate(token);

          if(!isAnthenticate) {
            returnCode = 403;
            returnMessage = "status=403&message=" + URLEncoder.encode("Authentication Failed", "utf-8");
          } else {
            Room room = Room.LoadRoom(roomid);
            if(room == null) {
              returnCode = 402;
              returnMessage = "status=402&message=" + URLEncoder.encode("No Such Room", "utf-8");
            } else {
              String message = receive.get("message");
              if(!message.equals("")) {
                room.appendMessage(message);
                room.save();
              }
              returnCode = 200;
              returnMessage = "status=200&message=" + URLEncoder.encode("Successfully Sent", "utf-8");
            }
          }
          
        }

      } catch (Exception e) {
        returnCode = 400;
        returnMessage = "status=400&message=" + URLEncoder.encode("Message_not_success", "utf-8");
      }
      
      exchange.sendResponseHeaders(returnCode, 0);
      OutputStream os = exchange.getResponseBody();
      os.write(returnMessage.getBytes("UTF-8"));
      os.close();
    }
  }
  
}