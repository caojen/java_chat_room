package Frontend.Helper;

import java.util.Map;

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
      return Helper.http.post(Configuation.ApiPrifix + Configuation.sendMessage, message);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}
