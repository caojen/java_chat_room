package FGUI;

import FGUI.Configuation.Configuation;
import FGUI.Console.Form;

public class Main {
  public static void main(String args[]) {
    Form.start();
    Configuation.load_from_file();
  }
}