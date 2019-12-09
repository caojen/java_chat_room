package Frontend.Console;

import java.util.Map;

import Frontend.Frontend.Frontend;
import Frontend.Helper.Helper;
import Frontend.Configuation.Configuation;

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
    // command also not allow &, + , ', =, ...
    if(CMD.valid(cmd) == false) {
      throw new Exception("[input error] Do not input = or & or ' or \" or ? or +");
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
        return Helper.quitRoom();

      } else if(args[0].equals("#remove")) {

        // To delete a user in the room(should be admin or owner)
        String username = args[1];
        Helper.removeParticipant(username);

      } else if(args[0].equals("#deleteroom")) {

        // To delete this room(should be admin or owner)
        boolean deleteRoomResult = !Helper.deleteRoom();
        Frontend.running = deleteRoomResult;
        if(deleteRoomResult == false) {
          System.out.println("[delete room accept] [redirect to quit]");
        }
        return deleteRoomResult;

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
      } else if(args[0].equals("#logout")) {
        Map<String, String> result = Helper.logout();

        if(result.get("status").equals("200")) {
          System.out.println("[logout success] " + result.get("message"));
          Frontend.running = false;
          Configuation.logout();
          // System.out.println("[logout success] Enter any words to finish logout process...\t");
          System.out.println("[wait] Trying to restart frontend...");
          try {
            Thread.sleep(5000);
          } catch(Exception e) {

          }
          Frontend.start();
        } else {
          System.out.println("[logout failed] " + result.get("message"));
        }
      }
      
      else {
        throw new Exception("");
      }
    } catch (Exception e) {
      throw new Exception("CMD usage error!\n");
    }
    return true;
  }

    /**
     * To judge cmd valid. 
     * @param message same as cmd
     * @return
     */
    private static boolean valid(String message) {
      if(message.contains("=") || message.contains("&") || message.contains("'") || message.contains("\"") || message.contains("?") || message.contains("+")) {
          return false;
      }
      return true;
  }

}