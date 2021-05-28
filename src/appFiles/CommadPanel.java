package appFiles;

import javax.swing.*;
import java.awt.*;

public class CommadPanel extends UserPanel{
    private JTextField command_field;
    private JButton command_execute;
    private JTextArea command_answer;

    public CommadPanel(SpringLayout layout){
        super(layout);
    }
    
    public void createPanel(){
        command_field = new JTextField();
        command_execute = new JButton("Исполнить");
        command_answer = new JTextArea("Hello");
        command_answer.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        panel.add(command_field);
        panel.add(command_execute);
        panel.add(command_answer);

        layout.putConstraint(SpringLayout.WEST, command_field, 0,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, command_field, 0,
                SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.EAST, command_field, -DISTANCE,
                SpringLayout.WEST, command_execute);
        layout.putConstraint(SpringLayout.SOUTH, command_field, 0,
                SpringLayout.SOUTH, command_execute);
        layout.putConstraint(SpringLayout.NORTH, command_execute, 0,
                SpringLayout.NORTH, command_field);
        layout.putConstraint(SpringLayout.EAST, command_execute, 0,
                SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.WEST, command_answer, 0,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, command_answer, DISTANCE,
                SpringLayout.SOUTH, command_field);
        layout.putConstraint(SpringLayout.EAST, command_answer, 0,
                SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, command_answer, 0,
                SpringLayout.SOUTH, panel);
    }


}
