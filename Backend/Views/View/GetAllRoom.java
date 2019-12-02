package Backend.Views.View;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import Backend.Views.Views;
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

            for(String key: rooms.keySet()) {
                if(!str.equals("")) {
                    str = str + "&"; 
                }
                str = str + key + "=" + rooms.get(key);
            }

            String returnMessage = "status=200&data="+ URLEncoder.encode(str, "utf-8");

            exchange.sendResponseHeaders(200, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(returnMessage.getBytes("UTF-8"));
            os.close();
        }
    }
}