package Backend.Backend;

import java.io.IOException;

import Backend.Urls.Urls;

public class Backend {
  public static void start()
      throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
        
    System.out.println("Please wait, the backend is beginning to get ready...");

    Urls.start();
  }
}