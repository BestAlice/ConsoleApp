package appFiles;

import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;



public class GrafPainter extends JPanel {

    ArrayList<HashMap> LabList = new ArrayList<>();
    ArrayList<MyCircle> circles = new ArrayList<>();
    MyCircle circle;
    Graphics2D g2;
    static int HEIGHT = 280;
    static int WIDTH  = 420;
    double k;

    public GrafPainter () {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (MyCircle circle: circles) {
                    if (circle.contains(e.getPoint())) {
                        Long id = (Long) circle.getLabwork().get("id");
                        HashMap<String, Object> laba = Application.table_panel.getLabWorkById(id);
                        Application.info_panel.setLabwork(laba);
                    }
                }
            }
        });
    }

    public void setLabList(ArrayList<HashMap> labList) {
        LabList = labList;
        circles = new ArrayList<>();
        int maxW = Integer.MIN_VALUE;
        int minW = 692;
        int maxH = Integer.MIN_VALUE;
        int minH = Integer.MAX_VALUE;
        for (HashMap<String, Object> labwork: LabList) {
            MyCircle circle = new MyCircle(labwork);
            circles.add(circle);
            if ((int) (long)labwork.get("x") > maxW) { maxW = (int) (long)labwork.get("x"); }
            if ((int) (long)labwork.get("x") < minW) { minW = (int) (long)labwork.get("x"); }
            if ((int) (long)labwork.get("y") > maxH) { maxH = (int) (long)labwork.get("y"); }
            if ((int) (long)labwork.get("y") < minH) { minH = (int) (long)labwork.get("y"); }
        }
        /*
        double[] coeffs = {Math.abs(minH/(0.5*HEIGHT)), Math.abs(maxH/(0.5*HEIGHT)),
                Math.abs(minW/(0.5*WIDTH)), Math.abs(maxW/(0.5*WIDTH))};
        */
        double[] coeffs = {Math.abs(0.5*(HEIGHT -40)/minH), Math.abs(0.5*(HEIGHT -40)/maxH),
                Math.abs(0.5*(WIDTH -40)/minW), Math.abs(0.5*(WIDTH -40)/maxW)};

        k = 10000;
        for (double num : coeffs) {
            if (num < k) {
                k = num;
            }
        }
        MyCircle.setK(k);
        if (circles.size() > 0) {
            for (MyCircle circle: circles) {
                circle.recalculateCoords();
                //g2.setColor(circle.getColor());
                circle.drawAnimation(g2);
            }
            long start = System.currentTimeMillis();
            long finish = System.currentTimeMillis();
            long elapsed = finish - start;
            while (elapsed <= 2000) {
                Application.visual_panel.update();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish = System.currentTimeMillis();
                elapsed = finish - start;
        }
    }}

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2= (Graphics2D) g;

        // очищаем фон
        Rectangle r=getBounds();
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, r.width, r.height);

        //circle = new MyCircle(100, 100, 22);
        //circles.add(circle);

        //g2.setColor(circle.getColor());
        //g2.fill(circle);


        // выводим надпись и выводим квадрат красного цвет
        g2.setColor(Color.red);
        //g.drawString("Hello, world", 20, 20);
        //g.fillRect(60,60, 120, 120);

        g2.drawLine(0, HEIGHT/2, WIDTH, HEIGHT/2);
        g2.drawLine(WIDTH/2, 0, WIDTH/2, HEIGHT);
        if (circles.size() > 0){
            for (MyCircle circle: circles) {
                circle.recalculateCoords();
                g2.setColor(circle.getColor());
                g2.fill(circle);
                //circle.drawAnimation(g2);
            }

        }
    }
}





