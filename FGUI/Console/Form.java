package FGUI.Console;

import javax.swing.*;
import java.awt.Component;

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
    

  }

  /**
   * To clean the form
   */
  public void fresh() {
    this.removeAll();
  }

  /**
   * to fresh and add new component
   * @param newCmp the component that will add to the Jframe
   */
  public void fresh(Component newCmp) {
    this.removeAll();
    this.setVisible(false);
    this.add(newCmp);
    this.setVisible(true);
  }
}