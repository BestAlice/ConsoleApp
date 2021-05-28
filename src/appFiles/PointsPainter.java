package appFiles;

import javax.swing.*;
import java.awt.*;

public class PointsPainter extends JFrame {

    public PointsPainter() {

        initUI();
    }

    private void initUI() {

        var drawPanel = new GrafPainter();
        add(drawPanel);

        setSize(350, 250);
        setTitle("Points");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            var ex = new PointsPainter();
            ex.setVisible(true);
        });
    }
}
