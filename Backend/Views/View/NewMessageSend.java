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

public class NewMessageSend extends Views {

    @Override
    public void handle(HttpExchange exchange) throws IOExcpetion {
        if(exchange.getRequestMethod() != "POST") {
            exchange.sendResponseHeader(403, 0);
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
                    returnMessage = "Authentication Failed";
                } else {
                    boolean isAnthenticate = user.anthenticate(token);

                    if(!isAnthenticate) {
                        returnCode = 403;
                        returnMessage = "Anthentication Failed";
                    } else {
                        Room room = Room.LoadRoom(roomid);
                        if(room == null) {
                            returnCode = 402;
                            returnMessage = "No Such Room";
                        } else {
                            String last_key = receive.get("last_key");
                            Map<String, String> m = room.get_message(last_key);
                            returnMessage = Urls.mapToString(m);
                            returnCode =  200;
                        }
                    }
                } 
            } catch (Exception e) {
                returnCode = 400; 
                returnMessage = "Message_not_success";     
            }

            exchange.sendResponseHeader(returnCode, 0);
            OutputStrem os = exchange.getResponseBody();
            os.write(returnMessage.getBytes("UTF-8"));
            os.close();
        }
    }
}