package FGUI.Console.Components;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import FGUI.Configuation.Configuation;
import FGUI.Console.Form;
import FGUI.Helper.Helper;

public class SelectRoom implements Component {
  public static void start() {
    Form.form.getContentPane().removeAll();
    Form.form.setVisible(false);
    Form.form.setTitle("Click to Select a Room");
    Container c = Form.form.getContentPane();

    JScrollPane jScrollPane = new JScrollPane();

    Map<String, String> rooms = Helper.getRoomList();
    ArrayList<String> string_rooms = roomsFormat(rooms);

    ListModel<String> jListModel = new DefaultComboBoxModel<>((String[]) string_rooms.toArray(new String[0]));
    JList<String> jList = new JList<>();
    jList.setModel(jListModel);
    jScrollPane.setViewportView(jList);

    jList.addListSelectionListener(new ListSelectionListener() {

      @Override
      public void valueChanged(ListSelectionEvent e) {
        String selectedValue = jList.getSelectedValue();
        if(selectedValue == null) {
          return;
        }
        if(selectedValue.equals("Create a new room...")) {
          String r = JOptionPane.showInputDialog(null, "Please enter new room id");
          try {
            if(r == null || r.equals("")) {
              return;
            }
            boolean create_result = Helper.createRoom(r);
            if(create_result == true) {
              JOptionPane.showMessageDialog(null, "OK", "Create Room",JOptionPane.PLAIN_MESSAGE);
              Map<String, String> t_rooms = Helper.getRoomList();
              ArrayList<String> t_string_rooms = roomsFormat(t_rooms);
              ListModel<String> t_jListModel = new DefaultComboBoxModel<>((String []) t_string_rooms.toArray(new String[0]));
              jList.setModel(t_jListModel);
            } else {
              JOptionPane.showMessageDialog(null, "Room id already exists.", "ERROR!",JOptionPane.ERROR_MESSAGE);
            }
          } catch (Exception exc) {
            exc.printStackTrace();
            JOptionPane.showMessageDialog(null, "Unexpected error occurred!", "ERROR!",JOptionPane.ERROR_MESSAGE);
          }
          jList.setSelectedValue(null, false);
        } else if(selectedValue.equals("Logout...")) {
          System.out.println("logout");
          try {
            Helper.logout();
          } catch (Exception exc) {
            exc.printStackTrace();
          }
          LoginorRegister.start();
        } else {
          Object[] options = {"Yes", "No", "Cancel"};
          int response = JOptionPane.showOptionDialog(null, "Yes--Enter the Room;\nNo--Delete the Room", "title", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

          if(response == 0) {
            String roomid = getRoomid(selectedValue);
            Map<String, String> res = Helper.enterRoom(roomid);
            if(!res.get("status").equals("200")) {
              String message = res.get("message");
              JOptionPane.showMessageDialog(null, message, "Enter Error!",JOptionPane.ERROR_MESSAGE);
            } else {
              Configuation.set_room_id(roomid);
              InRoom.start();
            }
          } else if(response == 1) {
            String roomid = getRoomid(selectedValue);
            try {
              Helper.deleteRoom(roomid);
            } catch (Exception exc) {
              JOptionPane.showMessageDialog(null, exc.getMessage(), "ERROR",JOptionPane.ERROR_MESSAGE); 
            }
          }
        }
      }
      
    });

    c.add(jScrollPane);
    Form.form.setSize(400, 500);
    Form.form.setVisible(true);
  }

  public static ArrayList<String> roomsFormat(Map<String, String> rooms) {
    ArrayList<String> ret = new ArrayList<>();

    ret.add("Create a new room...");
    ret.add("Logout...");
    if(rooms != null) {

      for(String key: rooms.keySet()) {
        ret.add("[roomid]='" + key + "' [owner]='" + rooms.get(key) + "'");
      }

    }
    return ret;
  }

  public static String getRoomid(String str) {
    String[] v = str.split(" ");
    String[] k = v[0].split("=");
    String s = k[1].substring(1).substring(0, k[1].substring(1).length()-1);
    return s;
  }
}