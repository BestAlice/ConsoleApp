package appFiles;

import javax.swing.*;
import java.awt.*;

public class VisualPanel extends UserPanel{

    public VisualPanel(SpringLayout layout){
        super(layout);
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    }

    @Override
    public void createPanel() {
        GrafPainter img = new GrafPainter();
        panel.add(img);

        layout.putConstraint(SpringLayout.NORTH, img, 0,
                SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, img, 0,
                SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.EAST, img, 0,
                SpringLayout.EAST, panel);
        layout.putConstraint(SpringLayout.SOUTH, img, 0,
                SpringLayout.SOUTH, panel);
    }
}
