package Backend.Views;

import com.sun.net.httpserver.HttpHandler;

import Frontend.Log.Time;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public abstract class Views implements HttpHandler {
  @Override
  public abstract void handle(HttpExchange exchange) throws IOException;

  private static String logPath = "Backend/Log/backend.log";

  protected void Log(Map<String, String> map) throws IOException {
    if(map == null) {
      map = new HashMap<>();
    }

    map.put("server.time", Time.getTime());

    String str = mapToString(map);

    writeToFile(str);
  }

  private static String mapToString(Map<String, String> map) {
    StringBuffer sb = new StringBuffer();

    for(String s: map.keySet()) {
      sb.append("[" + s +"] " + map.get(s));
    }

    return sb.toString();
  }

  private static void writeToFile(String str) throws IOException {
    str = "[" + Time.getTime() + "]\n" + str + "\n";

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
}
