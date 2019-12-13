package FGUI.Console.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import FGUI.Console.Form;

public class SelectRoom {
  public static void start() {
    Form.form.getContentPane().removeAll();
    Form.form.setVisible(false);
    Form.form.setTitle("Select Room");
    // Container c = Form.form.getContentPane();

    // JPanel panel = new JPanel();
    // panel.setLayout(new FlowLayout());

    // JLabel title_label = new JLabel("Please select a room:");
    // // title_label.setBounds(20, 20, 400, 20);
    // panel.add(title_label, "North");

    // JScrollPane jScrollPane = new JScrollPane();
    // jScrollPane.setPreferredSize(new Dimension(10, 10));
    // ListModel jListModel = new DefaultComboBoxModel<>(new String[] {"1", "2"});
    // JList jList = new JList<>();
    // jList.setModel(jListModel);
    // jScrollPane.setViewportView(jList);

    // // panel.add(jScrollPane, "Center");
    // c.add(panel);
    // c.add(jScrollPane);
    // Form.form.setSize(400, 500);
    Form.form.setVisible(true);
  }
}