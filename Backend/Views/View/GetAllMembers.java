package Backend.Views.View;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;

import Backend.Views.Views;
import Backend.Control.Control;
import Backend.Models.Users.Participant;

public class GetAllMembers extends Views {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            exchange.sendResponseHeaders(403, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(("status=403&message=" + URLEncoder.encode("Forbidden", "utf-8")).getBytes("utf-8"));
            os.close();
        } else {
            String uri = exchange.getRequestURI().toString();

            int returnCode = 0;
            String returnMessage = "";
            try {
                if(uri.split("?").length != 2) {
                    returnCode = 400;
                    returnMessage = "status=400&message="+URLEncoder.encode("get_param_error!", "utf-8");
                } else {
                    Map<String, String> map = Views.stringToMap(uri.split("?")[1]);
                    String roomid = map.get("roomid");
                    List<Participant> ps = Control.get_all_participant(roomid);
                    if(ps != null) {
                        StringBuffer sb = new StringBuffer();
                        int count = 0;
                        for(Participant p: ps) {
                            if(count != 0) {
                                sb.append("&");
                            }
                            sb.append(String.valueOf(count) + "=" + p.getUsername());
                        }
                        returnCode = 200;
                        returnMessage = "status=200&message=" + URLEncoder.encode(sb.toString(), "utf-8");
                    } else {
                        returnCode = 403;
                        returnMessage = "status=403&message=" + URLEncoder.encode("Query_Error", "utf-8");
                    }
                }
            } catch (Exception e) {
                returnCode = 402;
                returnMessage = "status=402&message=" + URLEncoder.encode("Message_not_success", "utf-8");
            }
            this.Log(returnMessage);
            exchange.sendResponseHeaders(returnCode, 0);
            OutputStream os = exchange.getResponseBody();
            os.write(returnMessage.getBytes("utf-8"));
            os.close();
        }
    }
}