package FGUI.Configuation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import FGUI.Configuation.UserType.UserTypeEnum;

public class Configuation {
  // test, just use static valuable.
  public static String ApiPrifix = null;
  // for console:
  public static String sendMessage = "send/";
  public static String getMessage = "get/";
  public static String Verify = "verify/";
  // login:
  public static String login = "login/";
  // logout:
  public static String logout = "logout/";
  // register:
  public static String register = "register/";
  // all room:
  public static String allRoom = "room/all/";
  // enter room:
  public static String enterRoom = "room/enter/";
  // quit room:
  public static String quitRoom = "room/quit/";
  // remove participant:
  public static String removeParticipant = "room/remove/";
  // delete room:
  public static String deleteRoom = "room/delete/";
  // change owner:
  public static String changeOwner = "room/change/";
  // get all members:
  public static String getMembers = "room/members/";
  // create new room:
  public static String createRoom = "room/create/";
  // ... more configuations...

  // user's information:
  // 
  private static String username = null;
  private static String token = null;
  private static UserTypeEnum usertype = null;

  public static void set_username(String _username) {
    username = _username;
  }

  public static String get_username() {
    return username;
  }

  public static void set_token(String _token) {
    token = _token;
  }

  public static String get_token() {
    return token;
  }

  public static void set_usertype(UserTypeEnum userTypeEnum) {
    usertype = userTypeEnum;
  }

  public static UserTypeEnum get_usertype() {
    return usertype;
  }

  private static String room_id = null;

  public static void set_room_id(String _room_id) {
    room_id = _room_id;
  }

  public static String get_room_id() {
    return room_id;
  }

  public static String LogPath = "FGUI/Log/frontend.log";

  public static void logout() {
    Configuation.username = null;
    Configuation.room_id = null;
    Configuation.usertype = null;
    Configuation.token = null;
  }

  public static boolean isLogin() {
    return Configuation.username != null && Configuation.token != null && Configuation.usertype != null;
  }

  private static String configuation_file = "Frontend/Configuation/Configuation.cfg";

  /**
   * Load configuation from file
   * @return true if success
   */
  public static void load_from_file() {
    try {
      File file = new File(Configuation.configuation_file);
      BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
      String strLine = null;
      StringBuffer sb = new StringBuffer();
      while(null != (strLine = bufferedReader.readLine())){
          sb.append(strLine + "\n");
      }
      bufferedReader.close();

      Map<String, String> key_values = Configuation.stringBuffertoMap(sb);

      if(null == key_values || !key_values.containsKey("ip") || !key_values.containsKey("port")) {
        throw new Exception("error");
      }

      Configuation.ApiPrifix = "http://" + key_values.get("ip") + ":" + key_values.get("port") + "/";
    }catch(Exception e){
      e.printStackTrace();
      System.out.println("[Load Error] Cannot load from configuation.cfg, reset it");
      Configuation.ApiPrifix = "http://localhost:8888/";
    }
  }

  /**
   * To format a stringbuffer to map(=, \n);
   * @param sb the buffer
   * @return key-value map(null if empty)
   */
  private static Map<String, String> stringBuffertoMap(StringBuffer sb) throws Exception {
    String str = sb.toString();
    if(str.equals("")) {
      return null;
    }
    Map<String, String> res = null;

    String[] key_values = str.split("\n");
    for(String key_value: key_values) {
      if(key_value.equals("")) {
        continue;
      }
      String key = key_value.split("=")[0];
      String value = key_value.split("=")[1];

      if(null == res) {
        res = new HashMap<String, String>();
      }
      res.put(key, value);
    }
    return res;
  }
}
