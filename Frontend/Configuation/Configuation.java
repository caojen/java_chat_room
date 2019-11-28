package Frontend.Configuation;

import Frontend.Configuation.UserType.UserTypeEnum;

public class Configuation {
  // test, just use static valuable.
  public static String ApiPrifix = "http://localhost:8080/";
  public static String sendMessage = "send/";
  public static String getMessage = "get/";
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

  public static void set_usertype(UserTypeEnum newUserType) {
    usertype = newUserType;
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
}
