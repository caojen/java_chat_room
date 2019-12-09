package Frontend.Configuation;

import Frontend.Configuation.UserType.UserTypeEnum;

public class Configuation {
  // test, just use static valuable.
  public static String ApiPrifix = "http://172.18.42.159:8888/";
  // for console:
  public static String sendMessage = "send/";
  public static String getMessage = "get/";
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

  public static String LogPath = "Frontend/Log/frontend.log";

  public static void logout() {
    Configuation.username = null;
    Configuation.room_id = null;
    Configuation.usertype = null;
    Configuation.token = null;
  }

  public static boolean isLogin() {
    return Configuation.username != null && Configuation.token != null && Configuation.usertype != null;
  }
}
