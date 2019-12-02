package Backend.Models;

import Backend.Control.Control;
import Backend.Models.ModelType.ModelTypes;

public class User implements Models {

  @Override
  public String getTypeStr() {
    return ModelType.toString(getType());
  }

  @Override
  public ModelTypes getType() {
    return ModelTypes.User;
  }

  private String username = null;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  private String password = null;

  @Deprecated
  /**
   * Deprecated because of security. Use anthenticate to check id instand.
   * 
   * @return String
   */
  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // these methods below deal with the database:

  /**
   * To anthenticate a user's password
   * 
   * @return token if success (null if error)
   */
  public String anthenticate() {
    return Control.login(this.username, this.password);
  }

  public boolean anthenticate(String token) {
    return Control.login_required(this.username, token);
  }

  /**
   * To save data to database
   */
  public void save() {
    Control.save_user(this.username, this.password);
  }

  /**
   * To create a user
   * 
   * @return the new User
   */
  @Deprecated
  public static User create_user() {
    return null;
  }

  /**
   * Delete this user. The Admin or Participant will be deleted in database.
   */
  @Deprecated
  public void delete_user() {
    // TODO: delete the user with 'control' here
  }

  /**
   * To get a specific User
   * 
   * @param username The querying username
   * @return User(null if not exist)
   */
  public static User get_user(String username) {

    if(!Control.exists_user(username)) {
      return null;
    }

    User ret = new User();
    ret.username = username;
    return ret;
  }

  public String getUserType() {
    return Control.getUserType(this.username);
  }
}