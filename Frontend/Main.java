package Frontend;

import java.util.Scanner;

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
      Frontend.start();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("The Client Stop because of Error!");
      Thread.sleep(1000);
      Main.main(args);
    }
  }
}