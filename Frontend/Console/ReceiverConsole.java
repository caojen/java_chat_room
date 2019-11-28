package Frontend.Console;

import java.util.Map;

import Frontend.Helper.Helper;

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
    while(true) {
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
        Map<String, String> result = Helper.getMessage(last_key);
        for(String key: result.keySet()) {
          ReceiverConsole.showText.put(key, result.get(key));
        }
        
        Thread.sleep(500);
      } catch (Exception e) {
        e.printStackTrace();
      }
      
    }
  }
}
