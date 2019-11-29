package Frontend.Helper;

import java.util.Map;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Frontend.Configuation.Configuation;
import Frontend.Configuation.UserType;
import Frontend.Http.HttpRequestSender;
import Frontend.Http.HttpRequest;

public class Helper {
  static HttpRequest http = new HttpRequestSender();

  /**
   * send sender console message to server
   * @param message the message
   * @return http-response-map
   */
  public static Map<String, String> sendMessage(Map<String, String> message) throws Exception {
    try {
      message.put("username", Configuation.get_username());
      message.put("token", Configuation.get_token());
      message.put("room", Configuation.get_room_id());
      return Helper.http.post(Configuation.ApiPrifix + Configuation.sendMessage, message);
    } catch (Exception e) {
      throw e;
    }
  }


  /**
   * get the message from server based on the newest key.
   * @param last_key the last key of show_text. if is null, server will return the last three message.
   * @return a message map in order. (can be added directly to show_text)
   */
  public static Map<String, String> getMessage(String last_key) throws Exception {
    Map<String, String> message = new HashMap<String, String>();
    message.put("username", Configuation.get_username());
    message.put("token", Configuation.get_token());
    message.put("room", Configuation.get_room_id());

    if(last_key == null) {
      message.put("last_key", "null");
    } else {
      message.put("last_key", last_key);
    }

    Map<String, String> result = Helper.http.post(Configuation.ApiPrifix + Configuation.getMessage, message);
    
    return toMap(result.get("data"));
  }

  public static Map<String, String> login(String username, String password) {
    Map<String, String> body = new HashMap<String, String>();
    try {
      body.put("username", URLEncoder.encode(username, "utf-8"));
      body.put("password", URLEncoder.encode(password, "utf-8"));
      Map<String, String> result = http.post(Configuation.ApiPrifix + Configuation.login, body);
      
      Configuation.set_token(URLDecoder.decode(result.get("token"), "utf-8"));
      Configuation.set_username(URLDecoder.decode(result.get("username"), "utf-8"));
      Configuation.set_usertype(UserType.toEnum(URLDecoder.decode(result.get("usertype"), "utf-8")));

      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Register a new user.
   * @param username
   * @param password
   * @param email
   * @param phone
   * @return http-response-map(status == 200 if success)
   */
  public static Map<String, String> register(String username, String password, String email, String phone) {
    Map<String, String> body = new HashMap<String, String>();
    try {
      body.put("username", URLEncoder.encode(username, "utf-8"));
      body.put("password", URLEncoder.encode(password, "utf-8"));
      body.put("email", URLEncoder.encode(email, "utf-8"));
      body.put("phone", URLEncoder.encode(phone, "utf-8"));
    
      return http.post(Configuation.ApiPrifix + Configuation.register, body);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * get all the room_id
   * @return a map contain all room(roomid - owner)
   */
  public static Map<String, String> getRoomList() {
    try {
      Map<String, String> roomList = http.get(Configuation.ApiPrifix + Configuation.allRoom, null);
      if(!roomList.get("status").equals("200")) {
        throw new Exception("ACCESS_ROOM_LIST_ERROR");
      }
      return toMap(roomList.get("data"));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Try to enter the room
   * @param roomid a string stand for the room id
   * @return http-response-map
   */
  public static Map<String, String> enterRoom(String roomid) {
    Map<String, String> body = new HashMap<String, String>();
    try {
      body.put("roomid", URLEncoder.encode(roomid, "utf-8"));
      body.put("username", URLEncoder.encode(Configuation.get_username(), "utf-8"));
      body.put("token", URLEncoder.encode(Configuation.get_token(), "utf-8"));
      return http.post(Configuation.ApiPrifix + Configuation.enterRoom, body);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * format a key-value-string to a map
   * @param array a String with messages, e.g. key1=en(opopojo)&key2="asdf". the value should be encoded.
   * @return return a order map
   */
  private static Map<String, String> toMap(String array_str) throws Exception {
    String[] key_value = array_str.split("&");
    Map<String, String> ret = new LinkedHashMap<String, String>();

    for(String ele : key_value) {
      if(ele.split("=").length != 2) {
        throw new Exception("Return_Value_Length_Exception");
      }
      String key = ele.split("=")[0];
      String value = ele.split("=")[1];
      ret.put(key, value);
    }
    return ret;
  }

  /**
   * quit the room
   * @throws Exception if the user is the owner
   */
  public static void quitRoom() throws Exception {
    Map<String, String> body = new HashMap<String, String>();

    body.put("username", URLEncoder.encode(Configuation.get_username(), "utf-8"));
    body.put("token", URLEncoder.encode(Configuation.get_token(), "utf-8"));
    body.put("roomid", URLEncoder.encode(Configuation.get_room_id(), "utf-8"));

    Map<String, String> result = http.get(Configuation.ApiPrifix + Configuation.quitRoom, body);

    if(!result.get("status").equals("200")) {
      throw new Exception("Cannot quit because of " + result.get("message"));
    }
  }

  /**
   * Remove a participant from the room
   * @param username the target
   * @throws Exception  if permission defined
   */
  public static void removeParticipant(String username) throws Exception {
    Map<String, String> body = new HashMap<String, String>();

    body.put("username", URLEncoder.encode(Configuation.get_username(), "utf-8"));
    body.put("token", URLEncoder.encode(Configuation.get_token(), "utf-8"));
    body.put("target", URLEncoder.encode(username, "utf-8"));
    body.put("roomid", URLEncoder.encode(Configuation.get_room_id(), "utf-8"));

    Map<String, String> result = http.get(Configuation.ApiPrifix + Configuation.removeParticipant, body);

    if(!result.get("status").equals("200")) {
      throw new Exception("Cannot remove because of " + result.get("message"));
    }
  }
  /**
   * Delete the Room
   * @throws Exception if permission defined
   */
  public static void deleteRoom() throws Exception {
    Map<String, String> body = new HashMap<String, String>();

    body.put("username", URLEncoder.encode(Configuation.get_username(), "utf-8"));
    body.put("token", URLEncoder.encode(Configuation.get_token(), "utf-8"));
    body.put("roomid", URLEncoder.encode(Configuation.get_room_id(), "utf-8"));

    Map<String, String> result = http.get(Configuation.ApiPrifix + Configuation.deleteRoom, body);

    if(!result.get("status").equals("200")) {
      throw new Exception("Cannot delete because of " + result.get("message"));
    }
  }

  /**
   * Change owner of room
   * @param username the target's username
   * @throws Exception  if permission defined
   */
  public static void changeOwner(String username) throws Exception {
    Map<String, String> body = new HashMap<String, String>();

    body.put("username", URLEncoder.encode(Configuation.get_username(), "utf-8"));
    body.put("token", URLEncoder.encode(Configuation.get_token(), "utf-8"));
    body.put("roomid", URLEncoder.encode(Configuation.get_room_id(), "utf-8"));
    body.put("target", URLEncoder.encode(username, "utf-8"));

    Map<String, String> result = http.get(Configuation.ApiPrifix + Configuation.changeOwner, body);

    if(!result.get("status").equals("200")) {
      throw new Exception("Cannot delete because of " + result.get("message"));
    }
  }

  public static Map<String, String> getMembers() throws Exception {
    Map<String, String> body = new HashMap<>();

    body.put("roomid", URLEncoder.encode(Configuation.get_room_id(), "utf-8"));

    Map<String, String> result = http.get(Configuation.ApiPrifix + Configuation.getMembers, body);

    if(!result.get("status").equals("200")) {
      throw new Exception(result.get("message"));
    }

    String members = result.get("data");

    return Helper.toMap(members);
  }
}

