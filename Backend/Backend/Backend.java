package Backend.Backend;

import Backend.Urls.Urls;

public class Backend {
  public static void start() throws InterruptedException {
        
    System.out.println("Please wait, the backend is beginning to get ready...");
    try {
      Urls.start();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("The server is closed because of error above.");
      System.out.println("Trying to restart...");
      Thread.sleep(2000);
      Backend.start();
    }
  }
}