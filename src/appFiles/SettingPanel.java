package appFiles;

import client.ClientMessageGeneration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

public class SettingPanel extends UserPanel{
    public JLabel user_label;
    public JButton language_box;
    public JButton ru_button;
    public JButton et_button;
    public JButton bg_button;
    public JButton en_button;
    public JButton enter_button;
    public JComboBox row;
    public JTextField field;
    public JButton ok;
    
    public SettingPanel(SpringLayout layout){
        super(layout);
        //setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        viewSettings();
    }

    @Override
    public void createPanel() {
        user_label = new JLabel(locale.getString("setting.user") + " " + "User");
        user_label.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));


        language_box = new JButton("Изменить язык");
        language_box.addActionListener(new ChooseLanguage());
        ru_button = new JButton("Ru");
        et_button = new JButton("Et");
        bg_button = new JButton("Bg");
        en_button = new JButton("En");
        enter_button = new JButton("Отмена");
        enter_button.addActionListener(new Esc());
        String[] items = {
               "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "no"
        };
        row = new JComboBox(items);
        field = new JTextField();
        ok = new JButton(locale.getString("setting.filter"));
        ok.addActionListener(new Ok());
        update();
    }

    public void viewSettings() {
        removeAll();
        add(user_label, CENTER_ALIGNMENT);
        add(row);
        add(field);
        add(ok);
        //add(language_box);
        update();
    }

    public void viewLanguages() {
        removeAll();
        add(ru_button);
        add(et_button);
        add(bg_button);
        add(en_button);
        add(enter_button);
        update();
    }

    public void setPosition(){
        //row.setBounds(0,0,40,20);
        //field.setBounds(60,0,100,20);


        layout.putConstraint(SpringLayout.EAST, user_label, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, row, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, row, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, field, 3*DISTANCE,
                SpringLayout.WEST, row);
        layout.putConstraint(SpringLayout.NORTH, field, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.SOUTH, field, 0,
                SpringLayout.SOUTH, row);
        field.setPreferredSize(new Dimension(150, 20));
        layout.putConstraint(SpringLayout.WEST, ok, DISTANCE,
                SpringLayout.EAST, field);

        /*layout.putConstraint(SpringLayout.EAST, ok, -DISTANCE,
                SpringLayout.EAST, user_label);
        layout.putConstraint(SpringLayout.WEST, ok, -4*DISTANCE,
                SpringLayout.EAST, user_label);

        layout.putConstraint(SpringLayout.EAST, field, DISTANCE,
                SpringLayout.EAST, ok);
        layout.putConstraint(SpringLayout.NORTH, ok, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, ok, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.SOUTH, ok, 0,
                SpringLayout.SOUTH, this);
                */
        /*
        layout.putConstraint(SpringLayout.WEST, user_label, -200,
                SpringLayout.EAST, this);

         */
        /*
        layout.putConstraint(SpringLayout.NORTH, user_label, 0,
                SpringLayout.NORTH, language_box);
        layout.putConstraint(SpringLayout.SOUTH, user_label, 0,
                SpringLayout.SOUTH, language_box);

        layout.putConstraint(SpringLayout.WEST, language_box, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, language_box, -DISTANCE,
                SpringLayout.WEST, user_label);

        layout.putConstraint(SpringLayout.WEST, ru_button, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, et_button, DISTANCE,
                SpringLayout.EAST, ru_button);
        layout.putConstraint(SpringLayout.WEST, bg_button, DISTANCE,
                SpringLayout.EAST, et_button);
        layout.putConstraint(SpringLayout.WEST, en_button, DISTANCE,
                SpringLayout.EAST, bg_button);
        layout.putConstraint(SpringLayout.WEST, enter_button, DISTANCE,
                SpringLayout.EAST, en_button);

         */
    }

    @Override
    public void updateLocale() {
        locale = Application.rb;
        ok.setText(locale.getString("setting.filter"));
        user_label.setText(locale.getString("setting.user") + " " + ClientMessageGeneration.login);
        update();
    }

    public void setUser(String newUser){
        user_label.setText(locale.getString("setting.user") + " " + newUser);
        update();
    }

    public class ChooseLanguage implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            viewLanguages();
        }
    }

    public class Esc implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            viewSettings();
        }
    }

    public class Ok implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = field.getText();
            int rows = row.getSelectedIndex();
            Application.table_panel.setFilterRow(10);
            Application.table_panel.setValue(text);
            Application.table_panel.setFilterRow(rows);

        }
    }
}
