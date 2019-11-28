package Backend.Models;

public class ModelType {
  public static enum ModelTypes {
    Room,
    Admin,
    Participant,
  };

  public static String toString(ModelTypes t) {
    if(t == ModelTypes.Room) {
      return "Room";
    } else if(t == ModelTypes.Admin) {
      return "Admin";
    } else if(t == ModelTypes.Participant) {
      return "Participant";
    } else {
      return "[ErrorType]";
    }
  } 

  public static ModelTypes toEnum(String s) {
    if(s == "Admin") {
      return ModelTypes.Admin;
    } else if(s == "Room") {
      return ModelTypes.Room;
    } else if(s == "Participant") {
      return ModelTypes.Participant;
    } else {
      return null;
    }
  } 
}