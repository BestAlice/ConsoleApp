package appFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CommandPanel extends UserPanel{
    private JTextField command_field;
    private JButton command_execute;
    public static JTextArea command_answer;
    public JScrollPane scroll_pane;

    public CommandPanel(SpringLayout layout){
        super(layout);
        setLayout(layout);
        setPreferredSize(new Dimension(800, 300));
    }
    
    public void createPanel(){
        command_field = new JTextField();
        command_execute = new JButton("Исполнить");
        command_execute.addActionListener(new SendCommand());
        command_answer = new JTextArea("Hello");
        command_answer.setEditable(false);
        scroll_pane = new JScrollPane(command_answer);
        scroll_pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_pane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));

        add(command_field);
        add(command_execute);
        add(scroll_pane);
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
        layout.putConstraint(SpringLayout.WEST, scroll_pane, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, scroll_pane, DISTANCE,
                SpringLayout.SOUTH, command_field);
        layout.putConstraint(SpringLayout.EAST, scroll_pane, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, scroll_pane, 0,
                SpringLayout.SOUTH, this);
    }

    public static void addEntry(String line){
        command_answer.setText(command_answer.getText() + "\n\n" + line);
    }

    public class SendCommand implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = command_field.getText();
            try {
                Application.commandReader.writeCommand(command);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

}
