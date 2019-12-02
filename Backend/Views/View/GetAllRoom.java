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

public class GetAllRoom extends Views {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if(exchange.getRequestMethod() != "GET") {
            exchange.sendResponseHeaders(403, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(("status=403&message="+URLEncoder.encode("Forbidden", "utf-8")).getBytes("utf-8"));
            os.close();
        } else {
            Map<String, String> rooms = Room.all();
            String str = "";

            for(String key: room.keySet()) {
                if(!str.equals("")) {
                    str = str + "&"; 
                }
                str = str + key + "=" + room.get(key);
            }

            String returnMessage = "status=200&data="+ URLEncoder.encode(str);

            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(returnMessage.getByte("UTF-8"));
            os.close();
        }
    }
}