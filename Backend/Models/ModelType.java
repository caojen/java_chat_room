package Backend.Models;

public class ModelType {
  public static enum ModelTypes {
    Room,
    Admin,
    Participant,
    User,
  };

  public static String toString(ModelTypes t) {
    if(t == ModelTypes.Room) {
      return "Room";
    } else if(t == ModelTypes.Admin) {
      return "Admin";
    } else if(t == ModelTypes.Participant) {
      return "Participant";
    } else if(t == ModelTypes.User) {
      return "User";
    } else {
      return "[ErrorType]";
    }
  } 

  public static ModelTypes toEnum(String s) {
    if(s.equals("Admin")) {
      return ModelTypes.Admin;
    } else if(s.equals("Room")) {
      return ModelTypes.Room;
    } else if(s.equals("Participant")) {
      return ModelTypes.Participant;
    } else if(s.equals("User")) {
      return ModelTypes.User;
    } else {
      return null;
    }
  } 
}