package Frontend;

import java.util.Scanner;

import Frontend.Frontend.Frontend;

public class Main {

  public static Scanner scan = new Scanner(System.in);
  /**
   * init the Frontend
   * control will pass to class Frontend
   * @param args no-used
   */
  public static void main(String args[]) {
    try {
      Frontend.start();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("The Client Stop because of Error!");
    }
  }
}