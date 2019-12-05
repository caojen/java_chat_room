package Backend.Views;

import com.sun.net.httpserver.HttpHandler;

import Backend.Views.Time;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import java.net.URLDecoder;

import com.sun.net.httpserver.HttpExchange;

public abstract class Views implements HttpHandler {
  @Override
  public abstract void handle(HttpExchange exchange) throws IOException;

  private static String logPath = "Backend/Log/backend.log";

  protected void Log(Map<String, String> map) throws IOException {
    if (map == null) {
      map = new HashMap<>();
    }

    map.put("server.time", Time.getTime());

    String str = mapToString(map);

    writeToFile(str);
  }

  protected void Log(String str) {
    try {
      this.Log(Views.stringToMap(str));
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected static String mapToString(Map<String, String> map) {
    if(map == null) {
      return "";
    }
    
    StringBuffer sb = new StringBuffer();

    for(String s: map.keySet()) {
      sb.append("[" + s +"] " + map.get(s) + "\n");
    }

    return sb.toString();
  }

  // this function will change \n to &
  protected static String mapToString(Map<String, String> map, boolean flag) {
    if(map == null) {
      return "";
    }
    
    StringBuffer sb = new StringBuffer();

    for(String s: map.keySet()) {
      if(sb.length() != 0) {
        sb.append("&");
      }
      sb.append(s +"=" + map.get(s));
    }

    return sb.toString();
  }

  private static void writeToFile(String str) throws IOException {
    str = "[" + Time.getTime() + "]\n" + str + "\n\n";

    File logFile = new File(Views.logPath);
    FileOutputStream fos = null;
    if(logFile.exists() == false) {
      logFile.createNewFile();
      fos = new FileOutputStream(logFile);
    } else {
      fos = new FileOutputStream(logFile, true);
    }

    OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
    osw.write(str);
    osw.close();
  }

  protected static Map<String, String> stringToMap(String str) throws Exception {

    Map<String, String> map = new HashMap<>();

    if(str.equals("")) {
      return map;
    }

    String [] key_values = str.split("&");

    for(String key_value: key_values) {
      if(key_value.split("=").length != 2 && key_value.charAt(key_value.length() - 1) != '=') {
        throw new Exception("Receive_Value_Length_Exception");
      }
      String key = key_value.split("=")[0];
      String value = key_value.split("=").length < 2 ? "null" : key_value.split("=")[1];
      value = URLDecoder.decode(value, "utf-8");

      map.put(key, value);
    }

    return map;
  }
}
