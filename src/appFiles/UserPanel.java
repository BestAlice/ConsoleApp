package appFiles;

import javax.swing.*;

public abstract class UserPanel {
    protected SpringLayout layout;
    protected JPanel panel;
    protected int DISTANCE = 20;

    public UserPanel(SpringLayout layout){
        this.layout = layout;
        panel = new JPanel();
        panel.setLayout(layout);
        createPanel();
    }

    abstract public void createPanel();

    public JPanel getPanel() {return panel;}

}
