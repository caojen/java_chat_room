package Frontend.Console;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import Frontend.Helper.Helper;
import Frontend.Frontend.Frontend;
import Frontend.Main;

public class SenderConsole implements Consoler {
  // sender showText will not be presented in console, just store it.
  public static Map<String, String> showText = new LinkedHashMap<String, String>();

  @Override
  public void append(String key, String data) throws Exception {
    if(showText.containsKey(key) == true) {
      throw new Exception("Console_Key_Duplicated");
    }
    showText.put(key, data);
  }

  @Override
  public void delete(String key) throws Exception {
    if(showText.containsKey(key) == false) {
      throw new Exception("Console_Key_Not_Exist");
    }
    showText.remove(key);
  }

  /**
   * call this function will create a Thread.
   */
  @Override
  public void run() {
    SenderRunningClass.start();
  }

  @Override
  public String get() {
    StringBuffer result = new StringBuffer();
    for(String key: showText.keySet()) {
      result.append(key + ":\n\t" + showText.get(key) + "\n");
    }
    return result.toString();
  }
}

class SenderRunningClass implements Runnable {
  LoopForInputAndSendMessage timer = new LoopForInputAndSendMessage();
  public static void start() {
    SenderRunningClass senderRunning = new SenderRunningClass();
    Thread t = new Thread(senderRunning);
    t.start();
  }

  @Override
  public void run() {
    timer.start();
  }
}

class LoopForInputAndSendMessage {
  public void start() {
    while(Frontend.running == true) {
      // loop:
      //   get input(one line) and sent to backend
      try {
        System.out.print("[input] New Message:>>>");

        String message = Main.scan.nextLine();

        if(Frontend.running == false) {
          break;
        }

        while(message.equals("")) {
          message = Main.scan.nextLine();
          if(Frontend.running == false) {
            break;
          }
        }
        
        if(this.valid(message) == false) {
          System.out.println("[input error] Do not input = or & or ' or \" or ? or +");
          continue;
        }

        if(message.charAt(0) == '#' && message.length() > 1 && message.charAt(1) != '#') {
          // The 'message' is a command, call to class.CMD
          try {
            boolean Continue = CMD.execute(message);
            if(Continue == false) {
              Frontend.running = false;
              break;
            } else {
              System.out.println("[Command Execute Result] Done");
            }
          } catch (Exception e) {
            System.out.print("[Command Execute Failed] ");
            System.out.println(e.getMessage());
          }

          Thread.sleep(200);
          continue;

        } else if(message.charAt(0) == '#') {
          StringBuffer tmp = new StringBuffer(message);
          tmp.delete(0,1);
          message = tmp.toString();
        }

        // format message:
        Map<String, String> m = new HashMap<String, String>();
        m.put("message", message);

        // send it:
        Map<String, String> result = Helper.sendMessage(m);

        if(!result.get("status").equals("200")) {
          throw new Exception("[Failed] Send Message Error With Status = " + result.get("status") + "\n\t" + "Beacuse of " + result.get("message"));
        }
        
        System.out.println("[Succeed] Message Send Success!");

        // wait for 200ms to avoid memory overflow
        Thread.sleep(200);
      } catch (Exception e) {
        // e.printStackTrace();
        System.out.println(e.getMessage());
      }
    }
  }

  /**
     * To judge message valid. 
     * @param message
     * @return
     */
    private boolean valid(String message) {
      if(message.contains("=") || message.contains("&") || message.contains("'") || message.contains("\"") || message.contains("?") || message.contains("+")) {
          return false;
      }
      return true;
  }
}
