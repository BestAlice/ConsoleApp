package appFiles;

import javax.swing.*;
import java.awt.*;

public class CommadPanel extends UserPanel{
    private JTextField command_field;
    private JButton command_execute;
    private JTextArea command_answer;

    public CommadPanel(SpringLayout layout){
        super(layout);
        setLayout(layout);
    }
    
    public void createPanel(){
        command_field = new JTextField();
        command_execute = new JButton("Исполнить");
        command_answer = new JTextArea("Hello");
        command_answer.setEditable(false);

        command_answer.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        add(command_field);
        add(command_execute);
        add(command_answer);
        update();
    }

    @Override
    public void setPosition() {
        layout.putConstraint(SpringLayout.WEST, command_field, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, command_field, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, command_field, -DISTANCE,
                SpringLayout.WEST, command_execute);
        layout.putConstraint(SpringLayout.SOUTH, command_field, 0,
                SpringLayout.SOUTH, command_execute);
        layout.putConstraint(SpringLayout.NORTH, command_execute, 0,
                SpringLayout.NORTH, command_field);
        layout.putConstraint(SpringLayout.EAST, command_execute, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, command_answer, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, command_answer, DISTANCE,
                SpringLayout.SOUTH, command_field);
        layout.putConstraint(SpringLayout.EAST, command_answer, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, command_answer, 0,
                SpringLayout.SOUTH, this);
    }


}
