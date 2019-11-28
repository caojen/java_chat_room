package Backend.Models.Users;

import Backend.Models.User;

public final class Admin extends User{
  Participant tmp_participant = new Participant(); // Do not store to database, just use its method.

  // Admin has no data
  // What can Admin do?

  /**
   * To delete a room
   * @param room_id
   * @return true if success
   */
  public boolean delete_room(String room_id) {
    // TODO: delete a room with id
    return true;
  }
  
  /**
   * To delete a member from room
   * @param room_id The room's id
   * @param participant the participant' username that will be deleted from room
   * @return true if success
   */
  public boolean delete_member(String room_id, String participant) {
    // TODO: delete a member with id
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

  // to deal with database
}