package appFiles;

import javax.swing.*;
import java.util.ResourceBundle;

public abstract class UserPanel extends JPanel{
    protected ResourceBundle locale;
    protected SpringLayout layout;
    protected int DISTANCE = 20;

    public UserPanel(SpringLayout layout){
        locale = Application.rb;
        this.layout = layout;
        setLayout(layout);
        createPanel();
    }

    abstract public void createPanel();

    abstract public void setPosition();

    public JPanel getPanel() {return this;}

    public void update(){
        validate();
        setPosition();
        repaint();
    }

    public void updateLocale() {};

}
