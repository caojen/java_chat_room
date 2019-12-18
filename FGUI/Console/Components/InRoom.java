package FGUI.Console.Components;

import java.awt.Container;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
        MessageReceiver.running = false;
        DetectParticipators.running = false;
        
        try {
          // Form.form.remove(menuBar);
          menuBar.setVisible(false);
          Helper.quitRoom();
        } catch (Exception exc) {
          exc.printStackTrace();
        }

        SelectRoom.start();
      }
    });

    logoutMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        MessageReceiver.running = false;
        DetectParticipators.running = false;

        try {
          menuBar.setVisible(false);
          Helper.logout();
        } catch (Exception exc) {
          
        }

        LoginorRegister.start();
      }
    });

    exitMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        MessageReceiver.running = false;
        DetectParticipators.running = false;

        try {
          Helper.logout();
        } catch (Exception exc) {
          exc.printStackTrace();
        }

        System.exit(0);
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
    sendBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          sendMessage(inputArea.getText());
          inputArea.setText("");
        } catch (Exception exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE); 
        }
      }
    });
    jpanel.add(sendBtn);

    JList<String> jList = new JList<>();
    JScrollPane partsrc = new JScrollPane(jList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    partsrc.setBounds(680, 10, 150, 380);
    jpanel.add(partsrc);

    JButton changeOwnerBtn = new JButton("Change Owner");
    changeOwnerBtn.setBounds(680, 400, 150, 47);
    changeOwnerBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(jList.getSelectedValue() == null) {
          return;
        }

        try {
          Helper.changeOwner(jList.getSelectedValue());
          JOptionPane.showMessageDialog(null, "OK", "Change Owner Result", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), "Change Owner Result: ERROR",JOptionPane.ERROR_MESSAGE); 
        }

      }
    });
    jpanel.add(changeOwnerBtn);

    JButton deleteParBtn = new JButton("Delete User");
    deleteParBtn.setBounds(680, 452, 150, 47);
    deleteParBtn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(jList.getSelectedValue() == null) {
          return;
        }
        
        try {
          Helper.removeParticipant(jList.getSelectedValue());
          JOptionPane.showMessageDialog(null, "OK", "Delete User Result", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception exc) {
          JOptionPane.showMessageDialog(null, exc.getMessage(), "Delete User Result: Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    jpanel.add(deleteParBtn);

    c.add(jpanel);

    MessageReceiver.start(jTextArea);
    DetectParticipators.start(jList);
    Form.form.setSize(850, 560);

    Form.form.setVisible(true);
  }


  private static void sendMessage(String text) throws Exception {
    if(text == null || text.equals("")) {
      return;
    }

    if(new TextValidChecker(text).check() == false) {
      throw new Exception("Cannot send =, &, ', \", ? or +");
    }

    Map<String, String> message = new HashMap<String, String>();
    message.put("message", text);
    Map<String, String> result = Helper.sendMessage(message);
    if(!result.get("status").equals("200")) {
      throw new Exception("Send Message Error");
    }
  }

}

class TextValidChecker {
  String text = null;
  final String[] invalidChar = new String[] {"=", "&", "'", "\"", "?", "+"};

  public TextValidChecker(String text) {
    this.text = text;
  }

  public boolean check() {
    if(text == null) {
      return false;
    }

    for(String t: invalidChar) {
      if(text.contains(t)) {
        return false;
      }
    }
    return true;
  } 
}

class MessageReceiver implements Runnable {

  public static Map<String, String> showText = new LinkedHashMap<>();
  public static boolean running = false;
  public static JTextArea messageArea = null;
  public void append(String key, String data) {
    try {
      if(showText.containsKey(key)) {
        throw new Exception("Concole_Key_Duplicated");
      } else {
        showText.put(key, data);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    while(MessageReceiver.running == true) {
      try {
        String last_key = "";
        if(MessageReceiver.showText.isEmpty() == true) {
          last_key = null;
        } else {
          for(String key: MessageReceiver.showText.keySet()) {
            last_key = key;
          }
        }

        Map<String, String> result = Helper.getMessage(last_key);

        result = sort(result);

        if(result != null) {
          for(String key: result.keySet()) {
            MessageReceiver.showText.put(key, result.get(key));
            MessageReceiver.messageArea.setText(MessageReceiver.messageArea.getText() + key + " " + result.get(key) + "\n");
          }
        }
        Thread.sleep(500);
      } catch (Exception e) {
        MessageReceiver.running = false;
        MessageReceiver.messageArea.setText(MessageReceiver.messageArea.getText() + "[Disconnected...]" + "\n");
      }
    }
  }
  
  public static void start(JTextArea jTextArea) {
    MessageReceiver.running = true;
    MessageReceiver.messageArea = jTextArea;

    MessageReceiver messageReceiver = new MessageReceiver();
    Thread t = new Thread(messageReceiver);
    t.start();
  }

  private Map<String, String> sort(Map<String, String> map) {
    if(map == null) {
      return null;
    } else {
      return new TreeMap<String, String>(map);
    }
  }
}

class DetectParticipators implements Runnable {
  public static JList<String> participatorList = null;
  public static boolean running = false;

  @Override
  public void run() {
    while (DetectParticipators.running == true) {
      try {
        Map<String, String> map_members = Helper.getMembers();

        ArrayList<String> strings_members = new ArrayList<>();

        for(String s: map_members.keySet()) {
          strings_members.add(map_members.get(s));
        }

        ListModel<String> jListModel = new DefaultComboBoxModel<>((String []) strings_members.toArray(new String[0]));
        DetectParticipators.participatorList.setModel(jListModel);
        Thread.sleep(5000);
      } catch (Exception e) {
        e.printStackTrace();
        DetectParticipators.running = false;
      }
    }
  }

  public static void start(JList<String> plist) {
    DetectParticipators.participatorList = plist;
    DetectParticipators.running = true;

    DetectParticipators detectParticipators = new DetectParticipators();
    Thread t = new Thread(detectParticipators);
    t.start();
  }
}