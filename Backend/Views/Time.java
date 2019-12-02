package Backend.Views;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
  private static SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

  public static String getTime() {
    return df.format(new Date());  
  }
}