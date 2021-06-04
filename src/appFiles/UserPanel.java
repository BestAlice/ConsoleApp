package appFiles;

import javax.swing.*;

public abstract class UserPanel extends JPanel{
    protected SpringLayout layout;
    protected int DISTANCE = 20;

    public UserPanel(SpringLayout layout){
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

}
