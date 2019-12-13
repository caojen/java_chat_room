package FGUI.Console.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URLDecoder;
import java.util.Map;

import FGUI.Console.Form;
import FGUI.Helper.Helper;

public class LoginorRegister implements Component{
  public static void start() {
    Form.form.getContentPane().removeAll();
    Form.form.setVisible(false);
    Form.form.setTitle("Login / Register");

    Container c = Form.form.getContentPane();

    JPanel Panel = new JPanel();
    Panel.setLayout(null);
    JLabel login_label = new JLabel("Login");
    login_label.setBounds(180, 20, 100, 20);
    Panel.add(login_label);

    JLabel username_label = new JLabel("Username:");
    username_label.setBounds(50, 50, 100, 20);
    Panel.add(username_label);
    JTextField username_text = new JTextField();
    username_text.setBounds(150, 50, 200, 20);
    Panel.add(username_text);

    JLabel password_label = new JLabel("Password:");
    password_label.setBounds(50, 80, 100, 20);
    Panel.add(password_label);
    JPasswordField password_text = new JPasswordField();
    password_text.setBounds(150, 80, 200, 20);
    Panel.add(password_text);

    JButton login_button = new JButton("Login");
    login_button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        String username = username_text.getText();
        String password = String.valueOf(password_text.getPassword());

        if(!username.equals("") && !password.equals("")) {
          Map<String, String> login_result = Helper.login(username, password);

          if(login_result.get("status").equals("200") == false) {
            JOptionPane.showMessageDialog(null, "Password Error or Username Not Exists!");
          } else {
            InRoom.start();
          }
        }
      }
    });
    login_button.setBounds(250, 110, 100, 30);
    Panel.add(login_button);
    

    JLabel register_label = new JLabel("Register");
    register_label.setBounds(180, 150, 100, 20);
    Panel.add(register_label);

    JLabel new_username_label = new JLabel("New Username:");
    new_username_label.setBounds(50, 180, 100, 20);
    Panel.add(new_username_label);
    JTextField new_username_text = new JTextField();
    new_username_text.setBounds(150, 180, 200, 20);
    Panel.add(new_username_text);

    JLabel new_password_label = new JLabel("New Password:");
    new_password_label.setBounds(50, 210, 100, 20);
    Panel.add(new_password_label);
    JPasswordField new_password_text = new JPasswordField();
    new_password_text.setBounds(150, 210, 200, 20);
    Panel.add(new_password_text);

    JLabel confirm_password_label = new JLabel("Confirm:");
    confirm_password_label.setBounds(50, 240, 100, 20);
    Panel.add(confirm_password_label);
    JPasswordField confirm_password_text = new JPasswordField();
    confirm_password_text.setBounds(150, 240, 200, 20);
    Panel.add(confirm_password_text);

    JLabel new_email_label = new JLabel("Your Email:");
    new_email_label.setBounds(50, 270, 100, 20);
    Panel.add(new_email_label);
    JTextField new_email_text = new JTextField();
    new_email_text.setBounds(150, 270, 200, 20);
    Panel.add(new_email_text);

    JLabel new_phone_label = new JLabel("Your Phone:");
    new_phone_label.setBounds(50, 300, 100, 20);
    Panel.add(new_phone_label);
    JTextField new_phone_text = new JTextField();
    new_phone_text.setBounds(150, 300, 200, 20);
    Panel.add(new_phone_text);

    JButton register_button = new JButton("Register");
    register_button.setBounds(250, 330, 100, 30);
    register_button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        String username = new_username_text.getText();
        String password = String.valueOf(new_password_text.getPassword());
        String confirm  = String.valueOf(confirm_password_text.getPassword());
        String email = new_email_text.getText();
        String phone = new_phone_text.getText();

        if(
          username.equals("") || 
          password.equals("") ||
          confirm.equals("")  ||
          email.equals("")    ||
          phone.equals("")
        ) {
          return;
        }

        if(password.equals(confirm) == false) {
          JOptionPane.showMessageDialog(null, "Password and Confirm Differ!");
          return;
        }

        Map<String, String> register_result = Helper.register(username, password, email, phone);
        if(register_result.get("status").equals("200") == false) {
          try {
            JOptionPane.showMessageDialog(null, "[register failed] " + URLDecoder.decode(register_result.get("message"), "utf-8"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          new_username_text.setText("");
          new_password_text.setText("");
          confirm_password_text.setText("");
          new_email_text.setText("");
          new_phone_text.setText("");
          JOptionPane.showMessageDialog(null, "Register Success! Please Login!");
        }
      }
    });
    Panel.add(register_button);
    c.add(Panel);

    Form.form.setSize(400, 400);
    Form.form.setVisible(true);
  }
}