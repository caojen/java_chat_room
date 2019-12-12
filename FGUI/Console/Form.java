package FGUI.Console;

import javax.swing.*;

public class Form extends JFrame {
  private static final long serialVersionUID = 8280050013528051744L;

  public static Form form = new Form();

  public static void start() {
    Form.form.setVisible(true);
  }

  public Form() {
    this.setTitle("Form");
    this.setSize(400,500);
    this.setLocationRelativeTo(null);
    this.add(new JButton("This a Button"));
  }
}