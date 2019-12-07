package Frontend.Frontend;

import java.net.URLDecoder;
import java.util.Map;

import Frontend.Configuation.Configuation;
import Frontend.Console.CMD;
import Frontend.Console.ReceiverConsole;
import Frontend.Console.SenderConsole;
import Frontend.Helper.Helper;

import Frontend.Main;

public class Frontend {
  // if the console is running...
  public static boolean running = false;

  public static void start() {
    System.out.println("Welcome to Chat-Room Client!");

    // login or register
    while(true) {
      if(switch_start_mode()) { // if login success, then break and continue...
        break;
      };
    }
    // room select:
    while(true) {
      System.out.println("");
      if(room_select()) { // if room_select success, break;
        break;
      }
    }

    // after enter room, sender console and receive console should begin...
    Frontend.running = true;
    new SenderConsole().run();
    new ReceiverConsole().run();
  }

  /**
   * Choose to login or register
   * @return true if login success
   */
  private static boolean switch_start_mode() {
    System.out.println("[choose mode] 1->login; 2->register; Ctrl+C->quit");
    
    String input = Main.scan.nextLine();
    
    if(input.equals("1")) {
      if(login_require() == true) {
        return true;    
      } else {
        return false;
      }
    } else if(input.equals("2")) {
      register_user();
      return false;
    } else {
      return false;
    }
  }


  /**
   * login mode
   * @return true if login success
   */
  private static boolean login_require() {
    System.out.print("[login required] Please type your username(q to back): ");

    String username = Main.scan.nextLine();
    if(username.equals("q")) {
      return false;
    }

    System.out.print("[password required] Please type your password(q to back): ");
    String password = Main.scan.nextLine();
    if(password.equals("q")) {
      return false;
    }

    Map<String, String> login_result = Helper.login(username, password);
    if(!login_result.get("status").equals("200")) {
      System.out.println("[login failed] username not exists or password error!");
      return false;
    }

    System.out.println("[login success]");
    return true;
  }

  private static void register_user() {
    System.out.println("[register] Type q anytime to quit!");
    System.out.print("[register username] Please type your new username: ");
    String username = Main.scan.nextLine();
    if(username.equals("q")) {
      return;
    }

    System.out.print("[register password] Please type your password: ");
    String password = Main.scan.nextLine();
    if(password.equals("q")) {
      return;
    }

    System.out.print("[register email] Please type your Email address: ");
    String email = Main.scan.nextLine();
    if(email.equals("q")) {
      return;
    }

    System.out.print("[register phone] Please type your phone number: ");
    String phone = Main.scan.nextLine();
    if(phone.equals("q")) {
      return;
    }

    Map<String, String> register_result = Helper.register(username, password, email, phone);

    if(!register_result.get("status").equals("200")) {
      try {
        System.out.println("[register failed] " + URLDecoder.decode(register_result.get("message"), "utf-8"));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("[register success]");
    }
  }

  /**
   * Select a chat room
   * @return true if select success
   */
  private static boolean room_select() {
    // first: asking for room list, show it and its owner, type the room_id and enter the room:
    Map<String, String> roomList = Helper.getRoomList();
    
    System.out.println("[room list]");
    for(String roomid: roomList.keySet()) {
      System.out.println("[id] " + roomid + " [owner] " + roomList.get(roomid));
    }
    System.out.println("[room select] Please type room id to enter the room:");

    String roomid = Main.scan.nextLine();

    while(roomid.equals("")) {
      roomid = Main.scan.nextLine();
    }

    if(roomid.charAt(0) == '#') {
      try{
        CMD.execute(roomid);
      } catch(Exception e) {
        e.printStackTrace();
      }
      return false;
    }

    Map<String, String> enterResult = Helper.enterRoom(roomid);
    if(!enterResult.get("status").equals("200")) {
      System.out.println("[room select failed] " + enterResult.get("message"));
      return false;
    } else {
      System.out.println("[room select succeed]");
      Configuation.set_room_id(roomid);
      return true;
    }
  }
}