package Backend.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Backend.Control.Control;
import Backend.Models.ModelType.ModelTypes;

public class Room implements Models{
  
  @Override
  public String getTypeStr() {
    return ModelType.toString(this.getType());
  }

  @Override
  public ModelTypes getType() {
    return ModelTypes.Room;
  }

  private String room_id = null;

  /**
   * get the room id
   * @return String stands for the room (key)
   */
  public String getRoomId() {
    return this.room_id;
  }

  /**
   * Deprecated.
   * To set this room id.
   * Note that the id will not be restore in database.
   * @param id the new id
   */
  public void setRoomId(String id) {
    this.room_id = id;
  }

  private User owner = null;

  /**
   * To get the room's owner
   * @return User of owner
   */
  public User getOwner() {
    return this.owner;
  }

  /**
   * To reset the room's owner
   * @param owner The new Owner
   */
  public void setOwner(User owner) {
    this.owner = owner;
  }

  private List<User> participants = new ArrayList<User>();

  /**
   * To add a new Participants to this room. If the paricipant already exists, do nothing.
   * @param participant the new participant
   */
  public void appendParticipants(User participant) {
    if(this.isParticipantIn(participant) == false) {
      this.participants.add(participant);
    }
  }

  public void setParticipants(List<User> participants) {
    for(User us: participants) {
      User n = new User();
      n.setUsername(us.getUsername());
      n.setPassword("");
      this.participants.add(us);
    }
  }

  public List<User> getParticipants() {
    return this.participants;
  }

  /**
   * To judge if a participant is already in the list
   * @param participant
   * @return true if is in
   */
  public boolean isParticipantIn(User participant) {
    boolean isIn = false;

    for(User u : this.participants) {
      if(u.getUsername() == participant.getUsername()) {
        isIn = true;
        break;
      }
    }

    return isIn;
  }

  /**
   * To delete a participant from the list. If the participant not exists, do nothing.
   * @param participant the participant that will be deleted.
   */
  public void deleteParicipants(User participant) {
    if(this.isParticipantIn(participant)) {
      this.participants.remove(participant);
    }
  }

  private Map<String, String> historyMessage = null;

  /**
   * To append message to local storage
   * @param message new message
   */
  public void appendMessage(User user, String message) {
    if(this.historyMessage == null) {
      this.historyMessage = new HashMap<String, String>();
    }
    this.historyMessage.put(user.getUsername(), message);
  }

  /**
   * To delete the local Message Storage
   */
  public void deleteMessage() {
    this.historyMessage = null;
  }

  /*note that these method below will access to the database*/

  /**
   * To save this Room to Storage
   */
  public void save() {
    Control.save_room(this);
  }

  /**
   * To delete this Room to Storage
   */
  public void delete() {
    Control.delete_room(this.room_id);
  }

  /**
   * To load a Room from database
   * @param id
   * @return the Room required
   */
  public static Room LoadRoom(String id) {
    // TODO: load a room with 'control' here
    return Control.get_room(id);
  }

  /**
   * just another name of LoadRoom
   * @param id
   * @return Room
   */
  public static Room get(String id) {
    return Room.LoadRoom(id);
  }

  /**
   * To create a Room from memory
   * @param Owner The User Owner
   * @return The New Room
   */
  public static Room create(String roomid, User Owner) {
    Control.create_room(roomid, Owner.getUsername());
    return null;
  }
}