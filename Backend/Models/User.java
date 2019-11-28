package Backend.Models;

public class User {
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
   * @return String
   */
  public String getPassword() {
    return this.password;
  }


  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * To anthenticate a user's password
   * @param password the asking password
   * @return true if success
   */
  public boolean anthenticate(String password) {
    return this.password == password;
  }


  // these methods below deal with the database:

  /**
   * To save data to database
   */
  public void save() {
    // TODO: save data with 'control' here
  }

  /**
   * To create a user
   * @return the new User
   */
  public static User create_user() {
    // TODO: create a new user with 'control' here
    return null;
  }

  /**
   * Delete this user. The Admin or Participant will be deleted in database.
   */
  public void delete_user() {
    // TODO: delete the user with 'control' here
  }

  /**
   * To get a specific User
   * @param username The querying username
   * @return User
   */
  public static User get_user(String username) {
    // TODO: get the user from database
    return null;
  }
}