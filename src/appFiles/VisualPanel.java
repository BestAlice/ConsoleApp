package appFiles;

import javax.swing.*;
import java.awt.*;

public class VisualPanel extends UserPanel{
    GrafPainter img;
    public VisualPanel(SpringLayout layout){
        super(layout);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    }

    @Override
    public void createPanel() {
        img = new GrafPainter();
        add(img);
        update();
    }

    @Override
    public void setPosition() {
        layout.putConstraint(SpringLayout.NORTH, img, 0,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, img, 0,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, img, 0,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, img, 0,
                SpringLayout.SOUTH, this);
    }


}
