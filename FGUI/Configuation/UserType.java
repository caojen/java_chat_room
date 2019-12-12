package FGUI.Configuation;

public class UserType {
  public static enum UserTypeEnum{
    Admin,
    Participant
  };

  public static String toString(UserTypeEnum usertype) {
    if(usertype == UserTypeEnum.Admin) {
      return "Admin";
    } else {
      return "Participant";
    }
  }

  public static UserTypeEnum toEnum(String usertype) {
    if(usertype.equals("Admin")) {
      return UserTypeEnum.Admin;
    } else {
      return UserTypeEnum.Participant;
    }
  }
}