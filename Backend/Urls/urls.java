package Backend.Urls;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Urls {
  private static HttpServer httpServer = null;

  private static Map<String, String> urlReflect = null;

  private static void addUrlReflect() {
    if (urlReflect == null) {
      urlReflect = new HashMap<>();
    }

    urlReflect.put("/send", "NewMessageReceive");
    urlReflect.put("/get", "NewMessageSend");
    urlReflect.put("/login", "UserLogin");
    urlReflect.put("/register", "UserRegister");
    urlReflect.put("/room/all", "GetAllRoom");
    urlReflect.put("/room/enter", "EnterRoom");
    urlReflect.put("/room/quit", "QuitRoom");
    urlReflect.put("/room/remove", "RemoveRoom");
    urlReflect.put("/room/delete", "DeleteRoom");
    urlReflect.put("/room/change", "RoomOwnerChange");
    urlReflect.put("/room/members", "GetAllMembers");
  }

  public static void start() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
    Urls.addUrlReflect();

    Urls.httpServer = HttpServer.create(new InetSocketAddress(8888), 0);

    for (String s : Urls.urlReflect.keySet()) {
      Urls.httpServer.createContext(s, (HttpHandler) Class.forName(Urls.urlReflect.get(s)).newInstance());
    }

    Urls.httpServer.start();
  }
}