package FGUI;

import FGUI.Configuation.Configuation;
import FGUI.Console.Form;

public class Main {
  public static void main(String args[]) {
    Configuation.load_from_file();
    
    Form.start();
  }
}