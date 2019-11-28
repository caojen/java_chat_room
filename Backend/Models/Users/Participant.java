package Backend.Models.Users;

import Backend.Models.Room;
import Backend.Models.User;

public final class Participant extends User{
  public String name;
  public String age;
  public String email;
  public String phone;

  // What can Participant do?

  /**
   * To create a new room
   * @return a new room with 
   */
  public Room create_room() {
    // TODO: create a room
    return null;
  }

  /**
   * To delete a room(The Paricipant should be its owner)
   * @param room_id
   * @return true if success
   */
  public boolean delete_room(String room_id) {
    // TODO: delete a room with id
    return true;
  }

  /**
   * To delete a member from room(The Participant should be its owner)
   * @param room_id The room's id
   * @param participant the participant' username that will be deleted from room
   * @return true if success
   */
  public boolean delete_member(String room_id, String participant) {
    // TODO: delete a member with id
    return true;
  }

  /**
   * To exit the room(The participant shouldn't be its owner, and should be in this room)
   * @param room_id The room's id
   * @return true if success
   */
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
    // TODO: ...
    return true;
  }


  /**
   * Give away this room to a new user(must not be admin)
   * @param room_id the room's id
   * @param to_user the new user's username
   * @return true if success
   */
  public boolean give_room(String room_id, String to_user) {
    // TODO: ......
    return true;
  }


  // deal with the database:

  /**
   * To save to the database
   */
  public void save() {
    // TODO: ...
  }

  /**
   * To get this participant based on username
   * @param username the querying username
   * @return Participant
   */
  public static Participant get(String username) {
    // TODO: ...
    return null;
  }

  /**
   * Delete this user
   */
  public void delete() {
    // TODO: ...
  }

  /**
   * To create a Participant
   */
  public static void create() {

  }
}