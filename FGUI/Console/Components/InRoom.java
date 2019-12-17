package FGUI.Console.Components;

import java.awt.Container;
import java.awt.event.*;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;

import FGUI.Configuation.Configuation;
import FGUI.Console.Form;
import FGUI.Helper.Helper;

public class InRoom implements Component {
  public static void start() {
    Form.form.setTitle("[Room] " + Configuation.get_room_id());
    Form.form.getContentPane().removeAll();
    Form.form.setVisible(false);
    Container c = Form.form.getContentPane();

    JMenuBar menuBar = new JMenuBar();

    JMenu globalMenu = new JMenu("Global");
    menuBar.add(globalMenu);

    JMenuItem quitMenuItem = new JMenuItem("Quit room");
    JMenuItem logoutMenuItem = new JMenuItem("Logout");
    JMenuItem exitMenuItem = new JMenuItem("Exit");

    globalMenu.add(quitMenuItem);
    globalMenu.add(logoutMenuItem);
    globalMenu.add(exitMenuItem);

    quitMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("quit room");
      }
    });

    logoutMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("logout");
      }
    });

    exitMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Exit");
      }
    });

    Form.form.setJMenuBar(menuBar);
    
    JPanel jpanel = new JPanel();
    jpanel.setLayout(null);

    JTextArea jTextArea = new JTextArea("[Message]\n");
    jTextArea.setLineWrap(true);
    jTextArea.setEditable(false);

    JScrollPane scr = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scr.setBounds(10, 10, 660, 380);
    jpanel.add(scr);

    JTextArea inputArea = new JTextArea();
    inputArea.setLineWrap(true);
    JScrollPane inputscr = new JScrollPane(inputArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    inputscr.setBounds(10, 400, 580, 100);
    jpanel.add(inputscr);

    JButton sendBtn = new JButton("Send");
    sendBtn.setBounds(590, 400, 80, 100);
    jpanel.add(sendBtn);

    JList<String> jList = new JList<>();
    JScrollPane partsrc = new JScrollPane(jList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    partsrc.setBounds(680, 10, 150, 380);
    jpanel.add(partsrc);

    JButton changeOwnerBtn = new JButton("Change Owner");
    changeOwnerBtn.setBounds(680, 400, 150, 47);
    jpanel.add(changeOwnerBtn);

    JButton deleteParBtn = new JButton("Delete User");
    deleteParBtn.setBounds(680, 452, 150, 47);
    jpanel.add(deleteParBtn);

    c.add(jpanel);


    Form.form.setSize(850, 560);

    Form.form.setVisible(true);
  }
}