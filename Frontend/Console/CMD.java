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
      if(args[0].equals("#quit")) {
        // quit this room
        Helper.quitRoom();
        return false;

      } else if(args[0].equals("#remove")) {

        // To delete a user in the room(should be admin or owner)
        String username = args[1];
        Helper.removeParticipant(username);

      } else if(args[0].equals("#deleteroom")) {

        // To delete this room(should be admin or owner)
        Helper.deleteRoom();

      } else if(args[0].equals("#ownerchange")) {
        
        // To change owner(should be admin or owner)
        String target = args[1];
        Helper.changeOwner(target);

      } else if(args[0].equals("#showmember")) {

        // show all the participant
        Map<String, String> result = Helper.getMembers();

        System.out.println("[show members] [at] [username]");
        for(String key: result.keySet()) {
          System.out.println("["+key+"]" +"["+result.get(key)+"]");
        }
        System.out.println("[Done]");

      } else {
        throw new Exception("");
      }
    } catch (Exception e) {
      throw new Exception("CMD usage error!");
    }
    return true;
  }
}