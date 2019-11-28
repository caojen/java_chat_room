package Frontend.Console;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import Frontend.Helper.Helper;

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
    while(true) {
      // loop:
      //   get input(one line) and sent to backend
      try {
        System.out.print("[input] New Message:>>>");

        Scanner input = new Scanner(System.in);
        String message = input.nextLine();

        // format message:
        Map<String, String> m = new HashMap<String, String>();
        m.put("message", message);

        // send it:
        Map<String, String> result = Helper.sendMessage(m);

        if(result.get("status") != "200") {
          input.close();
          throw new Exception("[Failed] Send Message Error With Status = " + result.get("status") + "\n" + "beacuse of " + result.get("message"));
        }
        input.close();
        
        System.out.println("[Succeed] Message Send Success!");

        // wait for 200ms to avoid memory overflow
        Thread.sleep(200);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
