package appFiles;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends UserPanel{
     Component label;
     Component login_text;
     Component login_field;
     Component password_text;
     Component password_field;
     Component enter;
     Component register;
    
    public LoginPanel(SpringLayout layout) {
        super(layout);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        setPreferredSize(new Dimension(300, 150));
    }

    @Override
    public void createPanel() {
         label = new JLabel("����/�����������");
         login_text = new JLabel("�����");
         login_field = new JTextField(10);
         password_text = new JLabel("������");
         password_field = new JPasswordField(10);
         enter = new JButton("�����");
         register = new JButton("�����������������");

        //login_text.setPreferredSize(new Dimension(35, 20));

        add(label);
        add(login_text);
        add(login_field);
        add(password_text);
        add(password_field);
        add(enter);
        add(register);
        update();

        
    }

    @Override
    public void setPosition() {
        //��������� label
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , label, 0,
                SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.NORTH , label, 0,
                SpringLayout.NORTH , this);

        //��������� ������ ������
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , login_field, 20,
                SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.NORTH , login_field, DISTANCE ,
                SpringLayout.SOUTH , label);
        layout.putConstraint(SpringLayout.NORTH , login_text, 0 ,
                SpringLayout.NORTH , login_field);
        layout.putConstraint(SpringLayout.EAST , login_text, -DISTANCE ,
                SpringLayout.WEST , login_field);

        //��������� ������ ������
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , password_field, 20,
                SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.NORTH , password_field, DISTANCE ,
                SpringLayout.SOUTH , login_field);
        layout.putConstraint(SpringLayout.NORTH , password_text, 0 ,
                SpringLayout.NORTH , password_field);
        layout.putConstraint(SpringLayout.EAST , password_text, -DISTANCE ,
                SpringLayout.WEST , password_field);

        // ������
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER , register, 60,
                SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.NORTH , register, DISTANCE ,
                SpringLayout.SOUTH , password_field);
        layout.putConstraint(SpringLayout.NORTH , enter, 0 ,
                SpringLayout.NORTH , register);
        layout.putConstraint(SpringLayout.EAST , enter, -DISTANCE ,
                SpringLayout.WEST , register);
    }
}
