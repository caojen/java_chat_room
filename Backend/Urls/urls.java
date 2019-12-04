package Backend.Urls;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import Backend.Views.View.DeleteRoom;
import Backend.Views.View.EnterRoom;
import Backend.Views.View.GetAllMembers;
import Backend.Views.View.GetAllRoom;
import Backend.Views.View.NewMessageReceive;
import Backend.Views.View.NewMessageSend;
import Backend.Views.View.QuitRoom;
import Backend.Views.View.RemoveParticipant;
import Backend.Views.View.RoomOwnerChange;
import Backend.Views.View.UserLogin;
import Backend.Views.View.UserRegister;


public class Urls {
  private static HttpServer httpServer = null;

  public static void start() throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {

    Urls.httpServer = HttpServer.create(new InetSocketAddress(8888), 0);

    Urls.httpServer.createContext("/send", new NewMessageReceive());
    Urls.httpServer.createContext("/get", new NewMessageSend());
    Urls.httpServer.createContext("/login", new UserLogin());
    Urls.httpServer.createContext("/register", new UserRegister());
    Urls.httpServer.createContext("/room/all", new GetAllRoom());
    Urls.httpServer.createContext("/room/enter", new EnterRoom());
    Urls.httpServer.createContext("/room/quit", new QuitRoom());
    Urls.httpServer.createContext("/room/remove", new RemoveParticipant());
    Urls.httpServer.createContext("/room/delete", new DeleteRoom());
    Urls.httpServer.createContext("/room/change", new RoomOwnerChange());
    Urls.httpServer.createContext("/room/members", new GetAllMembers());

    Urls.httpServer.start();

    System.out.println("Urls is listening at 8888");
  }
}