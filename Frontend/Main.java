package Frontend;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Main {
  static String uri = "http://localhost:8080";
  public static void main(String args[]) {
    HttpRequest hr = new HttpRequestSender();
    Map<String, String> params = new HashMap<String, String>();
    params.put("status", "true");
    params.put("text", "test");
    try {
      Map<String, String> result = hr.get(uri, params);
      for(String key: result.keySet()) {
        System.out.println(key + "=" + result.get(key));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}