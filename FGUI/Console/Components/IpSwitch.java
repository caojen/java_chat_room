package FGUI.Console.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import FGUI.Console.Form;
import FGUI.Helper.Helper;
import FGUI.Configuation.Configuation;

public class IpSwitch implements Component{

  public static JTextField ip_text = null;

  public static void start() {
    Form.form.getContentPane().removeAll();
    Form.form.setVisible(false);
    Form.form.setTitle("Ip Switch");
    
    Container c = Form.form.getContentPane();

    JPanel titlePanel = new JPanel();
    titlePanel.setLayout(new FlowLayout());
    titlePanel.add(new JLabel("Switch IP to Server"));
    c.add(titlePanel, "North");


    JLabel ip_label = new JLabel("Connect to:");
    ip_text = new JTextField();
    JPanel bodyPanel = new JPanel();
    bodyPanel.setLayout(null);
    ip_label.setBounds(50, 20, 100, 20);
    bodyPanel.add(ip_label);
    ip_text.setText(Configuation.ApiPrifix);
    ip_text.setBounds(180, 20, 200, 20);
    bodyPanel.add(ip_text);
    c.add(bodyPanel);


    JPanel buttomPanel = new JPanel();
    buttomPanel.setLayout(new FlowLayout());
    JButton jButton = new JButton();
    jButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        Configuation.ApiPrifix = IpSwitch.ip_text.getText();
        boolean res = Helper.ipVerify(Configuation.ApiPrifix);
        if(res == false) {
          JOptionPane.showMessageDialog(null, "No Response from " + Configuation.ApiPrifix);
        } else {
          LoginorRegister.start();
        }
      }
    });
    jButton.setText("OK");
    buttomPanel.add(jButton);
    c.add(buttomPanel, "South");

    Form.form.setSize(400, 150);

    Form.form.setVisible(true);
  }
}