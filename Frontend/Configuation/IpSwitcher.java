package Frontend.Configuation;

import Frontend.Main;

public class IpSwitcher {
  public static void run() {
    System.out.println("[Connection Confirm] This Client will direct to the following url");
    System.out.println("\n\t" + Configuation.ApiPrifix);
    System.out.println("\n[Connection Confirm]");
    System.out.println("\n\tPress [Enter] to continue.");
    System.out.println("\n\tType new url to change the configuation.");

    String input = Main.scan.nextLine();
    if(input.equals("")) {
      System.out.println("[Connection Confirm] OK");
    } else {
      Configuation.ApiPrifix = input;
      System.out.println("[Warning] The url is redirected to " + input);
    }

  }
}