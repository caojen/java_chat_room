package Frontend.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
  private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static String getTime() {
    return df.format(new Date());  
  }
}