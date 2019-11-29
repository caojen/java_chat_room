package Frontend.Console;

import java.util.Map;

import Frontend.Helper.Helper;

public class CMD {
  /**
   * Execute a string command begin with '#'
   * @param cmd the command
   * @throws Exception if not begin with '#' or cmd not exist.
   * @return true if not quit
   */
  public static boolean execute(String cmd) throws Exception {
    if(cmd.charAt(0) != '#') {
      throw new Exception("cmd is not begining with '#'");
    }
    return run(cmd.split(" "));
  }

  /**
   * Run the command
   * @param args
   * @return true if not quit
   * @throws Exception if error or execute error(permission defined, etc...)
   */
  private static boolean run(String[] args) throws Exception{
    if(args.length <= 0) {
      throw new Exception("Not a command");
    }
    try {
      switch (args[0]) {
        case "#quit": {
          // quit this room
          Helper.quitRoom();
          return false;
        }
        case "#remove": {
          // To delete a user in the room(should be admin or owner)
          String username = args[1];
          Helper.removeParticipant(username);
          break;
        }
        case "#deleteroom": {
          // To delete this room(should be admin or owner)
          Helper.deleteRoom();
          break;
        }
        case "#ownerchange": {
          // To change owner(should be admin or owner)
          String target = args[1];
          Helper.changeOwner(target);
          break;
        }
        case "#showmember": {
          // show all the participant
          Map<String, String> result = Helper.getMembers();

          System.out.println("[show members] [username] [name]");
          for(String key: result.keySet()) {
            System.out.println("["+key+"]" +"["+result.get(key)+"]");
          }
          System.out.println("[Done]");
          break;
        }
        default:
          throw new Exception("");
      }
    } catch (Exception e) {
      throw new Exception("CMD usage error!");
    }
    return true;
  }
}