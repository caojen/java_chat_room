package Backend.Models.Users;

import Backend.Control.Control;
import Backend.Models.User;
import Backend.Models.ModelType.ModelTypes;
import Backend.Models.ModelType;

public final class Admin extends User{

  @Override
  public ModelTypes getType() {
    return ModelTypes.Admin;
  }

  @Override
  public String getTypeStr() {
    return ModelType.toString(getType());
  }

  /**
   * To delete a room
   * @param room_id
   * @return true if success
   */
  public boolean delete_room(String room_id) {
    return Control.delete_room(room_id);
  }
  
  /**
   * To delete a member from room
   * @param room_id The room's id
   * @param participant the participant' username that will be deleted from room
   * @return true if success
   */
  public boolean delete_member(String room_id, String participant) {
    return Control.delete_participant(room_id, participant);
  }
  /**
   * Enter a room...
   * @param room_id
   * @return true if success
   */
  public boolean access_room(String room_id) {
    return Control.add_participant(room_id, this.getUsername());
  }

  // to deal with database
  
  public static boolean isAdmin(User user) {
    if(Control.getUserType(user.getUsername()).equals("Admin")) {
      return true;
    } else {
      return false;
    }
  }
}