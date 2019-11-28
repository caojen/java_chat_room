package Frontend.Helper;

import java.util.Map;
import java.util.LinkedHashMap;

import Frontend.Configuation.Configuation;
import Frontend.Http.HttpRequestSender;
import Frontend.Http.HttpRequest;

public class Helper {
  static HttpRequest http = new HttpRequestSender();

  /**
   * send sender console message to server
   * @param message the message
   * @return http-response-map
   */
  public static Map<String, String> sendMessage(Map<String, String> message) throws Exception {
    try {
      message.put("username", Configuation.get_username());
      message.put("token", Configuation.get_token());
      message.put("room", Configuation.get_room_id());
      return Helper.http.post(Configuation.ApiPrifix + Configuation.sendMessage, message);
    } catch (Exception e) {
      throw e;
    }
  }


  /**
   * get the message from server based on the newest key.
   * @param last_key the last key of show_text. if is null, server will return the last three message.
   * @return a message map in order. (can be added directly to show_text)
   */
  public static Map<String, String> getMessage(String last_key) {
    try {
      message.put("username", Configuation.get_username());
      message.put("token", Configuation.get_token());
      message.put("room", Configuation.get_room_id());

      if(last_key == null) {
        message.put("last_key", "null");
      } else {
        message.put("last_key", last_key);
      }

      Map<String, String> result = Helper.http.post(Configuation.ApiPrifix + Configuation.getMessage, message);

      Map<String, String> ret = 

      
    }
  }

  /**
   * @param array a String with messages, e.g. key1=en(opopojo)&key2="asdf". the value should be encoded.
   */
  private static Map<String, String> toMap(String array) {
    
  }
}
