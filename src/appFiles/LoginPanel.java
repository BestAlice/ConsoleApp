package appFiles;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends UserPanel{

    public LoginPanel(SpringLayout layout) {
        super(layout);
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.setPreferredSize(new Dimension(300, 150));


    }

    @Override
    public void createPanel() {
        Component label = new JLabel("Вход/Регистрация");
        Component login_text = new JLabel("Логин");
        Component login_field = new JTextField(10);
        Component password_text = new JLabel("Пароль");
        Component password_field = new JPasswordField(10);
        Component enter = new JButton("Войти");
        Component register = new JButton("Зарегетрироваться");

        //login_text.setPreferredSize(new Dimension(35, 20));

        panel.add(label);
        panel.add(login_text);
        panel.add(login_field);
        panel.add(password_text);
        panel.add(password_field);
        panel.add(enter);
        panel.add(register);


        //Положение label
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , label, 0,
                SpringLayout.HORIZONTAL_CENTER , panel);
        layout.putConstraint(SpringLayout.NORTH , label, 0,
                SpringLayout.NORTH , panel);

        //Положение строки Логина
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , login_field, 20,
                SpringLayout.HORIZONTAL_CENTER , panel);
        layout.putConstraint(SpringLayout.NORTH , login_field, DISTANCE ,
                SpringLayout.SOUTH , label);
        layout.putConstraint(SpringLayout.NORTH , login_text, 0 ,
                SpringLayout.NORTH , login_field);
        layout.putConstraint(SpringLayout.EAST , login_text, -DISTANCE ,
                SpringLayout.WEST , login_field);

        //Положение строки Пароля
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , password_field, 20,
                SpringLayout.HORIZONTAL_CENTER , panel);
        layout.putConstraint(SpringLayout.NORTH , password_field, DISTANCE ,
                SpringLayout.SOUTH , login_field);
        layout.putConstraint(SpringLayout.NORTH , password_text, 0 ,
                SpringLayout.NORTH , password_field);
        layout.putConstraint(SpringLayout.EAST , password_text, -DISTANCE ,
                SpringLayout.WEST , password_field);

        // Кнопки
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , register, 60,
                SpringLayout.HORIZONTAL_CENTER , panel);
        layout.putConstraint(SpringLayout.NORTH , register, DISTANCE ,
                SpringLayout.SOUTH , password_field);
        layout.putConstraint(SpringLayout.NORTH , enter, 0 ,
                SpringLayout.NORTH , register);
        layout.putConstraint(SpringLayout.EAST , enter, -DISTANCE ,
                SpringLayout.WEST , register);
    }
}
