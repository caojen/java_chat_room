package Frontend.Console;

import java.util.Map;
import java.util.TreeMap;

import Frontend.Helper.Helper;
import Frontend.Frontend.Frontend;

import java.util.LinkedHashMap;

public class ReceiverConsole implements Consoler {

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

  @Override
  public void run() {
    ReceiverRunningClass.start();
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

 class ReceiverRunningClass implements Runnable {
  LoopForReceive timer = new LoopForReceive();
  
  public static void start() {
    ReceiverRunningClass receiverRunning = new ReceiverRunningClass();
    Thread t = new Thread(receiverRunning);
    t.start();
  }

  @Override
  public void run() {
    timer.start();
  }
}

class LoopForReceive {
  public void start() {
    while(Frontend.running == true) {
      // loop:
      //    ask for new message from server
      //    get the last key of this 'showText', if showText is null, last_key wiil be null.
      try {
        String last_key = "";
        if(ReceiverConsole.showText.isEmpty() == true) {
          last_key = null;
        } else {
          for(String key: ReceiverConsole.showText.keySet()) {
            last_key = key;
          }
        }
        if(Frontend.running == false) {
          break;
        }
        Map<String, String> result = Helper.getMessage(last_key);

        // sort by the key
        result = sort(result);

        if(result != null) {
          System.out.println("\n[new message]");
          for(String key: result.keySet()) {
            ReceiverConsole.showText.put(key, result.get(key));
            AfterReceive(key, result.get(key));
          }
        }
        Thread.sleep(500);
      } catch (Exception e) {
        Frontend.running = false;
        e.printStackTrace();
      }
    }
  }

  /**
   * to deal with the key-value that have received.
   * @param key
   * @param value
   */
  private void AfterReceive(String key, String value) {
    System.out.println("[" + key + "] " + value);
  }


  private Map<String, String> sort(Map<String, String> map) {
    if(map == null) {
      return null;
    }
    return new TreeMap<String, String>(map);
  }
}
