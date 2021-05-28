package appFiles;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GrafPainter extends JPanel {

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D) g;

        // очищаем фон
        Rectangle r=getBounds();
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, r.width, r.height);

        // выводим надпись и выводим квадрат красного цвет
        g.setColor(Color.red);
        g.drawString("Hello, world", 20, 20);
        //g.fillRect(60,60, 120, 120);

        g.drawLine(3, 3, 3, r.height-3);
        int[][] points = {{40, 3}, {12, 20}, {20, -15}};
        for (int[] coords: points) {
            int rad = 20;
            g.fillOval(coords[0], coords[1], rad, rad);
        }
    }


}


