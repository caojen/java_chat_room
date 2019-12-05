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
}
