package Backend.Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Backend.Models.Room;
import Backend.Models.User;
import Backend.Models.Users.Participant;

public class Control {
  
  public static boolean create_participant(String name, String password, String email, String phone) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "insert into participant (username, email, phone, maxroomnum, valid)" + 
                  "values ('" + name + "', '" + mail +"', '" + phone + "'," + " 2, 1);"; 

      state.executeUpdate(sql);

      String sql "insert into user (username, password, token)" + 
                "values ('" + username +"', '" + password + "', '');";

      state.executeUpdate(sql);
          
      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean create_room(String roomid, String owner) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "insert into room (roomid, owner, participants, valid)" +
                  "values ('" + roomid +"', '" + owner + "', '', 1);";

      state.executeUpdate(sql);
      
      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
  
  public static boolean delete_room(String roomid) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "delete from room where roomid = '" + roomid + "';";

      state.executeUpdate(sql);
      
      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean add_participant(String roomid, String participant) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from participant where roomid = '" + roomid + "';";

      ResultSet rs = state.executeQuery(sql);
      
      participant = rs.getString("participants") + participant + ",";

      sql = "update participant set participants = '" + participant + "' where roomid = '"  + roomid +"';";

      state.executeUpdate(sql);

      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean delete_participant(String roomid, String participant) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from participant where roomid = '" + roomid + "';";

      ResultSet rs = state.executeQuery(sql);

      String p = rs.getString("participants");

      String[] ps = p.split(",");

      StringBuffer pb = new StringBuffer();

      for(String a: ps) {
        if(!a.equals(participant)) {
          pb.append(a + ",");
        }
      }

      p = pb.toString();

      sql = "update participant set participants = '" + p + "' where roomid = '" + roomid + "';"; 

      state.executeUpdate(sql);

      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean add_message(String roomid, String username, String newMessage, String time) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "insert into message (roomid, time, username, message)" +
                  "values ('" + roomid +"', '" + time + "', '" + username + "', '" + newMessage +"');";

      state.executeUpdate(sql);
      
      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 
   * @param roomid
   * @param time
   * @return time-message-map(null if not exists)
   */
  public static Map<String, String> get_message(String roomid, String time) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from message where roomid = '" + roomid + "';";

      ResultSet rs = state.executeQuery(sql);

      Map<String, String> result = null;

      while(rs.next()) {
        if(rs.getString("time").compareTo(time) > 0) {
          if(result == null) {
            result = new HashMap<String, String>();
          }
          result.put(rs.getString("time"), rs.getString("message"));
        }
      }

      state.close();
      con.commit();
      con.close();

      return result;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 
   * @param username
   * @param password
   * @return String - the token(null if error)
   */
  public static String login(String username, String password) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from user where username = '" + username + "' and password = '" + password +"';";

      ResultSet rs = state.executeQuery(sql);

      if(rs.next()) {
        String token = token_create();

        sql = "update user set token = '" + token + "' where username = '" + username + "';"; 

        state.executeUpdate(sql);

        state.close();
        con.commit();
        con.close();
        return token;
      } else {
        state.close();
        con.commit();
        con.close();
        return null;
      }

    } catch (Exception e) {
      return null;
    }
  }

  public static boolean login_required(String username, String token) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from user where username = '" + username + "' and token = '" + token + "';";

      ResultSet rs = state.executeQuery(sql);

      state.close();
      con.commit();
      con.close();

      if(!rs.next()) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean exists_room(String roomid) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from room where roomid = '" + roomid + "';";

      ResultSet rs = state.executeQuery(sql);

      state.close();
      con.commit();
      con.close();

      if(!rs.next()) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }
   
  public static boolean exists_user(String username) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from user where username = '" + username + "';";

      ResultSet rs = state.executeQuery(sql);

      state.close();
      con.commit();
      con.close();

      if(!rs.next()) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 
   * @param username
   * @return Admin or Participant("error when exception occurred...")
   */
  public static String getUserType(String username) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from admin where username = '" + username + "';";

      ResultSet rs = state.executeQuery(sql);

      state.close();
      con.commit();
      con.close();

      if(rs.next()) {
        return "Admin";
      }

      return "Participant";

    } catch (Exception e) {
      return "Error";
    }
  }

  /**
   * create a token with ramdom char with 64 size;
   * @return String - token(64 digits)
   */
  private static String token_create() {
    int length = 64;

    StringBuffer sb = new StringBuffer();

    for(int i = 0;i < length;i++) {
      sb.append(String.valueOf((char)(Math.round(Math.random()*25+65))));
    }

    return sb.toString();
  }

  public static boolean save_user(String username, String password) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "update user set password = '" + password + "' where username = '" + username + "';"; 

      state.executeUpdate(sql);

      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean changeOwner(String roomid, String oldOwner, String newOwner) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "update room set owner = '" + newOwner + "' where roomid = '" + roomid + "' and owner = '" + oldOwner + "';";

      state.executeUpdate(sql);

      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * remember to call the user.save if password change
   * @param participant
   * @return
   */
  public static boolean save_participant(Participant participant) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "update participant set email = '" + participant.email + "', phone = '" + participant.phone + "' where username = '" + participant.getUsername() + "';";

      state.executeUpdate(sql);

      state.close();
      con.commit();
      con.close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * 
   * @param username
   * @return participant (null if not exists)
   */
  public static Participant get_participant(String username) {
    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from participant where username = '" + username + "';";

      ResultSet rs = state.executeQuery(sql);

      if(rs.next()) {
        Participant p = new Participant();

        p.email = rs.getString("email");
        p.phone = rs.getString("phone");

        p.setUsername(username);
        p.setPassword("");
        state.close();
        con.commit();
        con.close();
        return p;
      } else {
        state.close();
        con.commit();
        con.close();
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 
   * @param roomid
   * @return Room (null if not exist)
   */
  public static Room get_room(String roomid) {
    try {
      Class.forName("org.sqlite.JDBC");
   
      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "select * from room where roomid = '" + roomid + "';";

      ResultSet rs = state.executeQuery(sql);

      if(rs.next()) {
        Room r = new Room();
        
        User owner = new User();
        owner.setUsername(rs.getString("username"));
        owner.setPassword("");
        r.setOwner(owner);

        r.setRoomId(roomid);

        List<User> lu = new ArrayList<User>();

        String participants = rs.getString("participants");
        String[] ps = participants.split(",");
        for(String s: ps) {
          if(!s.equals("")) {
            User u = new User();
            u.setUsername(s);
            u.setPassword("");
            lu.add(u);
          }
        }
        r.setParticipants(lu);

        state.close();
        con.commit();
        con.close();

        return r;
      } else {
        state.close();
        con.commit();
        con.close();
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public static void save_room(Room room) {
    String roomid = room.getRoomId();
    String owner = room.getOwner().getUsername();
    List<User> participants = room.getParticipants();

    String p = "";

    for(User u: participants) {
      p += u.getUsername() + ",";
    }

    try {
      Class.forName("org.sqlite.JDBC");

      String db = "Backend/Control/database.db";
      Connection con = DriverManager.getConnection("jdbc:sqlite:" + db);

      Statement state = con.createStatement();

      String sql = "update room set owner = '" + owner + "', participants = '" + p + "' where roomid = '" + roomid + "';";

      state.executeUpdate(sql);

      state.close();
      con.commit();
      con.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}