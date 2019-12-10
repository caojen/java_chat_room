package Frontend;

import java.util.Scanner;

import Frontend.Configuation.Configuation;
import Frontend.Frontend.Frontend;

public class Main {

  public static Scanner scan = new Scanner(System.in);
  
  /**
   * init the Frontend control will pass to class Frontend
   * 
   * @param args no-used
   * @throws InterruptedException
   */
  public static void main(String args[]) throws InterruptedException {
    try {
      Configuation.load_from_file();
      Frontend.start();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("[STOP] The Client Stop because of Error!");
      System.out.println("[STOP] Trying To Restart Client.");
      Thread.sleep(3000);
      Main.main(args);
    }
  }
}