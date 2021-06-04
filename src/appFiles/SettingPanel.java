package appFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingPanel extends UserPanel{

    public JTextArea user_label;
    public JButton language_box;
    public JButton ru_button;
    public JButton et_button;
    public JButton bg_button;
    public JButton en_button;
    public JButton enter_button;
    
    public SettingPanel(SpringLayout layout){
        super(layout);
        //setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        viewSettings();
    }

    @Override
    public void createPanel() {
        user_label = new JTextArea("User: " + "User");
        user_label.setEditable(false);
        language_box = new JButton("Изменить язык");
        language_box.addActionListener(new ChooseLanguage());
        ru_button = new JButton("Ru");
        et_button = new JButton("Et");
        bg_button = new JButton("Bg");
        en_button = new JButton("En");
        enter_button = new JButton("Отмена");
        enter_button.addActionListener(new Esc());
        update();

    }

    public void viewSettings() {
        removeAll();
        add(user_label);
        add(language_box);
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
        layout.putConstraint(SpringLayout.EAST, user_label, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, user_label, -200,
                SpringLayout.EAST, this);
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
}
