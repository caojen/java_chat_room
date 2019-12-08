package Frontend.Console;

import java.util.Map;

import Frontend.Frontend.Frontend;
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
        Frontend.running = false;
        Helper.deleteRoom();
        System.out.println("[delete room accept] [redirect to quit]");
        return false;

      } else if(args[0].equals("#ownerchange")) {
        
        // To change owner(should be admin or owner)
        String target = args[1];
        Helper.changeOwner(target);

      } else if(args[0].equals("#showmember")) {

        // show all the participant
        Map<String, String> result = Helper.getMembers();

        System.out.println("[show members]\n\n[at]\t[username]");
        for(String key: result.keySet()) {
          System.out.println("["+key+"]\t" +"["+result.get(key)+"]");
        }
        System.out.println("[Done]\n");

      } else if(args[0].equals("#createroom")) {
        boolean result = Helper.createRoom(args[1]);

        if(result == false) {
          System.out.println("[Room_id Duplicated]");
        } else {
          System.out.println("[createRoom success]");
        }
      } 
      
      else {
        throw new Exception("");
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw new Exception("CMD usage error!\n");
    }
    return true;
  }
}