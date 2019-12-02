package Frontend.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import Frontend.Configuation.Configuation;

/**
 * deal with frontend log...
 */
public class Log {
  /**
   * write a log to ./frontend.log
   * note: only http will call this function
   * @param log a string for log
   */
  public static void write(String log) {
    try {
      log = "["+Time.getTime()+"]\n" + log;
      File logFile = new File(Configuation.LogPath);
      FileOutputStream fos = null;
      if(logFile.exists() == false) {
        logFile.createNewFile();
        fos = new FileOutputStream(logFile);
      } else {
        fos = new FileOutputStream(logFile, true);
      }
      OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
      osw.write(log + "\n");
      osw.close();
    } catch (Exception e) {
      // err log
      e.printStackTrace(); // hide it if not in dev enrivonment.
    }
  }

  public static void writeMap(Map<String, String> map) {
    String m = mapToString(map);

    Log.write(m);
  }

  /**
   * Convert a map<String, String> to log-string
   * @param map the data that wiil convert to string
   * @return the result String
   */
  private static String mapToString(Map<String, String> map) {
    StringBuffer str = new StringBuffer();
    if(map == null) {
      str.append("[empty map]\n");
    } else {
      str.append("[map]\n");
      for(String key: map.keySet()) {
        str.append("[" + key + "]="+map.get(key)+"\n");
      }
    }
    return str.toString();
  }
}