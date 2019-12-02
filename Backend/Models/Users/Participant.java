package Backend.Models.Users;

import Backend.Control.Control;
import Backend.Models.Room;
import Backend.Models.User;

public final class Participant extends User{
  public String email;
  public String phone;

  // What can Participant do?

  /**
   * To create a new room
   * @return a new room with 
   */
  public Room create_room(String roomid) {
    if(Control.create_room(roomid, this.getUsername())) {

      Room r = new Room();
      r.setRoomId(roomid);
      r.setOwner((User) this);
      return r;

    } else {
      return null;
    }
  }

  /**
   * To delete a room(The Paricipant should be its owner)
   * @param room_id
   * @return true if success
   */
  public boolean delete_room(String room_id) {
    if(Control.delete_room(room_id)) {
      return true;
    } else{
      return false;
    }
  }

  /**
   * To delete a member from room(The Participant should be its owner)
   * @param room_id The room's id
   * @param participant the participant' username that will be deleted from room
   * @return true if success
   */
  public boolean delete_member(String room_id, String participant) {
    if(Control.delete_participant(room_id, participant)) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * To exit the room(The participant shouldn't be its owner, and should be in this room)
   * @param room_id The room's id
   * @return true if success
   */
  @Deprecated
  public boolean exit_room(String room_id) {
    // TODO: exit room from storage
    return true;
  }

  /**
   * Enter a room...
   * @param room_id
   * @return true if success
   */
  public boolean access_room(String room_id) {
    if(Control.add_participant(room_id, this.getUsername())) {
      return true;
    } else {
      return false;
    }
  }


  /**
   * Give away this room to a new user(must not be admin)
   * @param room_id the room's id
   * @param to_user the new user's username
   * @return true if success
   */
  public boolean give_room(String room_id, String to_user) {
    return Control.changeOwner(room_id, this.getUsername(), to_user);
  }


  // deal with the database:

  /**
   * To save to the database
   */
  public void save() {
    Control.save_participant(this);
  }

  /**
   * To get this participant based on username
   * @param username the querying username
   * @return Participant
   */
  public static Participant get(String username) {
    return Control.get_participant(username);
  }

  /**
   * Delete this user
   */
  @Deprecated
  public void delete() {
    // TODO: ...
  }

  /**
   * To create a Participant
   */
  public static Participant create(String username, String password, String email, String phone) {
    if(Control.create_participant(username, password, email, phone) == false) {
      return null;
    }

    Participant p = new Participant();

    p.setUsername(username);
    p.setPassword(password);
    p.email = email;
    p.phone = phone;

    return p;
  }
}