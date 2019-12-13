package FGUI.Console.Components;

import FGUI.Console.Form;

public class InRoom implements Component {
  public static void start() {
    Form.form.getContentPane().removeAll();
    Form.form.setVisible(false);
    Form.form.setSize(700, 500);











    Form.form.setVisible(true);
  }
}