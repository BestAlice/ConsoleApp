package appFiles;

import client.Main;
import com.sun.webkit.Timer;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyCircle extends Ellipse2D.Double {
    private static ExecutorService executorPool = Executors.newCachedThreadPool();
    static int height = GrafPainter.HEIGHT/2;
    static int width = GrafPainter.WIDTH/2;
    static double k;
    int x = 0;
    int y = 0;
    int abs_x = 0;
    int abs_y = 0;
    int rad = 0;
    Color color = Color.black;
    HashMap<String, Object> labwork;


    public MyCircle (HashMap<String, Object> labwork) {
        this.abs_x = (int) (long)labwork.get("x");
        this.abs_y = (int)(long)labwork.get("y");
        this.rad = (int)(((int)labwork.get("weight")-500)*0.05);
        this.labwork = labwork;
        Long userId = (Long)(((Long)labwork.get("userId")*2650+1564)%(255*255*255));

        String rgb = String.format("%6s", Integer.toHexString((int)(long)userId)).replace(' ', '0');
        color = Color.decode("#" + rgb);
    }

    public MyCircle (int x, int y, int rad) {
        this.x = x;
        this.y = y;
        this.rad = rad;
    }

    public void recalculateCoords() {
        this.x = (int)(width+k*abs_x - 0.5*rad);
        this.y = (int)(height-k*abs_y - 0.5*rad);
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }


    public void setLabwork(HashMap<String, Object> labwork) {
        this.labwork = labwork;
    }

    public void drawAnimation(Graphics2D g2) {
        Thread thread = new Thread(new Animation(g2, this));
        executorPool.submit(thread);
    }

    public static class Animation implements Runnable{
        Graphics2D g2;
        MyCircle circle;
        public Animation (Graphics2D g2, MyCircle circle) {
            this.g2 = g2;
            this.circle = circle;
        }
        @Override
        public void run() {
            long start = System.currentTimeMillis();
            long finish = System.currentTimeMillis();
            long elapsed = finish - start;
            int x = circle.x+0;
            int y = circle.y+0;
            while (elapsed <= 1000) {
                g2.setColor(circle.getColor());
                circle.setX((int)(x*elapsed/1000));
                circle.setY((int)(y*elapsed/1000));
                finish = System.currentTimeMillis();
                elapsed = finish - start;

            }
            circle.setX((int)(x));
            circle.setY((int)(y));
        }
    }

    public HashMap<String, Object> getLabwork() {
        return labwork;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double getWidth() {
        return rad;
    }

    @Override
    public double getHeight() {
        return rad;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setFrame(double x, double y, double w, double h) {

    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    public static void setK(double k) {
        MyCircle.k = k;
    }

    public void setRad(int rad) {this.rad = rad;}

}
