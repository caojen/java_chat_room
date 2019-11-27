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
  public static Map<String, String> sendMessage(Map<String, String> message) {
    return null;
  }
}