package appFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ResourceBundle;

public class LoginPanel extends UserPanel{
     JLabel label;
     JLabel login_text;
     JTextField login_field;
     JLabel password_text;
     JPasswordField password_field;
     JButton enter;
     JButton register;
     static JTextArea error_line;
    
    public LoginPanel(SpringLayout layout) {
        super(layout);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        //setPreferredSize(new Dimension(300, 150));
    }

    @Override
    public void createPanel() {
        locale = Application.rb;
         label = new JLabel(locale.getString("login.enter_reg"));
         login_text = new JLabel(locale.getString("login.login"));
         login_field = new JTextField(10);
         password_text = new JLabel(locale.getString("login.password"));
         password_field = new JPasswordField(10);
         enter = new JButton(locale.getString("login.enter"));
         enter.addActionListener(new Sing_in());
         register = new JButton(locale.getString("login.registr"));
         register.addActionListener(new Sing_up());
         error_line = new JTextArea("");
         error_line.setForeground(Color.red);

        //error_line.setPreferredSize(new Dimension(100, 20));

        //login_text.setPreferredSize(new Dimension(35, 20));

        add(label);
        add(login_text);
        add(login_field);
        add(password_text);
        add(password_field);
        add(enter);
        add(register);
        add(error_line);
        update();
    }

    @Override
    public void updateLocale() {
        locale = Application.rb;
        label.setText(locale.getString("login.enter_reg"));
        login_text.setText(locale.getString("login.login"));
        password_text.setText(locale.getString("login.password"));
        enter.setText(locale.getString("login.enter"));
        register.setText(locale.getString("login.registr"));
        update();
    }

    @Override
    public void setPosition() {

        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , error_line, 0,
                SpringLayout.HORIZONTAL_CENTER , this);

        layout.putConstraint(SpringLayout.NORTH , error_line, 10,
                SpringLayout.NORTH , this);


        //Положение label
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , label, 0,
                SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.NORTH , label, DISTANCE+10,
                SpringLayout.NORTH , this);

        //Положение строки Логина
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , login_field, 20,
                SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.NORTH , login_field, DISTANCE ,
                SpringLayout.SOUTH , label);
        layout.putConstraint(SpringLayout.NORTH , login_text, 0 ,
                SpringLayout.NORTH , login_field);
        layout.putConstraint(SpringLayout.EAST , login_text, -DISTANCE ,
                SpringLayout.WEST , login_field);

        //Положение строки Пароля
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , password_field, 20,
                SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.NORTH , password_field, DISTANCE ,
                SpringLayout.SOUTH , login_field);
        layout.putConstraint(SpringLayout.NORTH , password_text, 0 ,
                SpringLayout.NORTH , password_field);
        layout.putConstraint(SpringLayout.EAST , password_text, -DISTANCE ,
                SpringLayout.WEST , password_field);

        // Кнопки
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , register, 60,
                SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.NORTH , register, DISTANCE ,
                SpringLayout.SOUTH , password_field);
        layout.putConstraint(SpringLayout.NORTH , enter, 0 ,
                SpringLayout.NORTH , register);
        layout.putConstraint(SpringLayout.EAST , enter, -DISTANCE ,
                SpringLayout.WEST , register);
    }

    public class Sing_in implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String login = login_field.getText();
            String password = String.valueOf(password_field.getPassword());
            if (login.equals("") || password.equals("")){
                setError(locale.getString("login.not_empty"));
                update();
            } else {
                try {
                    Application.commandReader.Authorization(String.format("sing_in %s %s", login, password));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public class Sing_up implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String login = login_field.getText();
            String password = login_field.getText();
            if (login.equals("") || password.equals("")){
                setError(locale.getString("login.not_empty"));
                update();
            } else {
                try {
                    Application.commandReader.Authorization(String.format("sing_up %s %s", login, password));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    public static void setError(String line){
        error_line.setText("Error: " + line);
    }
}
