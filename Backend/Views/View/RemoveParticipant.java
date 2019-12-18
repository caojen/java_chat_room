package Backend.Views.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import Backend.Views.Views;
import Backend.Models.User;
import Backend.Models.Users.Admin;
import Backend.Control.Control;
import Backend.Models.Room;

public class RemoveParticipant extends Views {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(!exchange.getRequestMethod().equals("POST")) {
            // exchange.sendResponseHeaders(403, 0);
            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(("status=403&message=" + URLEncoder.encode("Forbidden", "utf-8")).getBytes());
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
                Map<String, String> receive = Views.stringToMap(sb.toString());

                String username = receive.get("username");
                String token = receive.get("token");
                String roomid = receive.get("roomid");
                String target = receive.get("target");

                User user = User.get_user(username);
                User user_t = User.get_user(target);

                if(user == null || user_t == null) {
                returnCode = 403;
                returnMessage = "status=403&message=" + URLEncoder.encode("Authentication Failed", "utf-8");
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
                        if(room.getOwner().getUsername().equals(user_t.getUsername())){
                            returnCode = 405;
                            returnMessage = "status=405&message="+URLEncoder.encode("Owner_Cannot_be_Deleted", "utf-8");
                        } else if (room.getOwner().getUsername().equals(user.getUsername()) || Admin.isAdmin(user)) {
                            Control.delete_participant(roomid, user_t.getUsername());
                            returnCode = 200;
                            returnMessage = "status=200&message=" + URLEncoder.encode("Done", "utf-8");
                        } else {
                            returnCode = 403;
                            returnMessage = "status=403&message=" + URLEncoder.encode("Permission Defined", "utf-8");
                        }
                    }
                }
                
                }

            } catch (Exception e) {
                returnCode = 400;
                returnMessage = "status=400&message=" + URLEncoder.encode("Message_not_success", "utf-8");
            }
            this.Log(returnMessage);
            returnCode = 200;
            exchange.sendResponseHeaders(returnCode, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(returnMessage.getBytes("UTF-8"));
            os.close();

        }

    }
    
}