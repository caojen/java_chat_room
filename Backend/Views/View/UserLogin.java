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

public class UserLogin extends Views {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod() != "POST") {
            exchange.sendResponseHeaders(403, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(("status=403&message=" + URLEncoder.encode("Forbidden", "utf-8")).getBytes("utf-8"));
            os.close();
        } else{
            InputStream is = exchange.getResponseBody();
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
                String password = receive.get("password");
                User user = User.get_user(username);
                user.setPassword(password);
                String token = user.anthenticate();
                if(token == null) {
                    returnCode = 403;
                    returnMessage = "status=403&message=" + URLEncoder.encode("Anthenticate Failed", "utf-8");
                } else {
                    returnCode = 200;
                    returnMessage = "status=200&token=" + URLEncoder.encode(token, "utf-8");
                    // TODO: returnMessage should add username and usertype;
                }
            } catch (Exception e) {
                returnCode = 400;
                returnMessage = "status=400&message=" + URLEncoder.encode("Message_not_success", "utf-8");
            }

            exchange.sendResponseHeaders(returnCode, 0);
            OutputStream os = exchagne.getResponseBody();
            os.write(returnMessage.getBytes("UTF-8"));
            os.close();
        }
    }
}