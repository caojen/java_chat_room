package FGUI.Console;

import javax.swing.*;

import FGUI.Console.Components.*;

public class Form extends JFrame {
  private static final long serialVersionUID = 8280050013528051744L;

  public static Form form = new Form();

  public static void start() {
    // IpSwitch.start();
    SelectRoom.start();
  }

  public Form() {
    this.setTitle("Form");
    this.setSize(400,500);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}