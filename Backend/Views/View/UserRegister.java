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
import Backend.Models.Users.Participant;

public class UserRegister extends Views {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod().equals("POST")) {
            exchange.sendResponseHeaders(403, 0);
            OutputStream os = exchange.getResponseBody();
            os.write((("status=403&message=" + URLEncoder.encode("Forbidden", "utf-8"))).getBytes("utf-8"));
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
                String password = receive.get("password");
                String email = receive.get("email");
                String phone = receive.get("phone");

                if(Participant.create(username, password, email, phone) != null) {
                    returnCode = 200;
                    returnMessage = "status=200&message=" + URLEncoder.encode("User_Register_Success", "utf-8");    
                } else {
                    returnCode = 403;
                    returnMessage = "status=403&message=" + URLEncoder.encode("Username_Duplicated_Exception", "utf-8");
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