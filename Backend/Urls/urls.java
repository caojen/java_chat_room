package Backend.Urls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;

public class Urls {
  private static int port = 8080;
  private static BufferedReader reader;
  private static PrintWriter writer;
  private static ServerSocket server;
  private static Socket socket;

  public static void listen() throws IOException {
    server = new ServerSocket(port);
    System.out.println("[Server create success]");
    while(true) {
      System.out.println("[Listening]");
      socket = server.accept();
      reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      writer = new PrintWriter(socket.getOutputStream(), true);
      System.out.println("[Message receive]");
      getClientMessage();
    }
  }

  private static void getClientMessage() throws IOException {
    String message = reader.readLine();
    
  }
}