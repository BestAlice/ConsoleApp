package appFiles;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class Application {
    JFrame window;
    Container contentPane;
    SpringLayout layout;
    int HEIGHT = 800;
    int WIDTH  = 1300;
    int DISTANCE  = 20;

    public Application(){
        window = new JFrame("MyApp");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = window.getContentPane();
        layout = new SpringLayout();

        //contentPane.setLayout(new BorderLayout(1,1));
        contentPane.setLayout(layout);

        window.setSize(WIDTH, HEIGHT);
        window.setVisible(true);
    }

    public void viewLoginingPanel() {
        contentPane.removeAll();

        LoginPanel login = new LoginPanel(layout);
        JPanel login_panel = login.getPanel();

        contentPane.add(login_panel);
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, login_panel, 0,
                SpringLayout.HORIZONTAL_CENTER, contentPane);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, login_panel, 0,
                SpringLayout.VERTICAL_CENTER, contentPane);


        window.setVisible(true);
        window.repaint();
    }

    public void viewMainPanel() {

        contentPane.removeAll();
        //Таблица
        TablePanel table = new TablePanel(layout);
        JPanel tableWithScroll = table.getPanel();
        //JScrollPane tableWithScroll = createTable();
        tableWithScroll.setPreferredSize(new Dimension(800, 480));

        //Визуализация
        VisualPanel visual = new VisualPanel(layout);
        JPanel img = visual.getPanel();
        img.setPreferredSize(new Dimension(420, 300));

        //окно командной строки
        CommadPanel commands = new CommadPanel(layout);
        JPanel command_pale = commands.getPanel();

        //область информации
        InfoPlane info = new InfoPlane(layout);
        JPanel info_pale = info.getPanel();

        contentPane.add(tableWithScroll);
        contentPane.add(img);
        contentPane.add(command_pale);
        contentPane.add(info_pale);
        {
            layout.putConstraint(SpringLayout.NORTH, tableWithScroll, DISTANCE,
                    SpringLayout.NORTH, contentPane);
            layout.putConstraint(SpringLayout.WEST, tableWithScroll, DISTANCE,
                    SpringLayout.WEST, contentPane);

            layout.putConstraint(SpringLayout.NORTH, img, DISTANCE,
                    SpringLayout.NORTH, contentPane);
            layout.putConstraint(SpringLayout.WEST, img, DISTANCE,
                    SpringLayout.EAST, tableWithScroll);
            layout.putConstraint(SpringLayout.EAST, img, -DISTANCE,
                    SpringLayout.EAST, contentPane);

            layout.putConstraint(SpringLayout.NORTH, command_pale, DISTANCE,
                    SpringLayout.SOUTH, tableWithScroll);
            layout.putConstraint(SpringLayout.WEST, command_pale, DISTANCE,
                    SpringLayout.WEST, contentPane);
            layout.putConstraint(SpringLayout.SOUTH, command_pale, -DISTANCE,
                    SpringLayout.SOUTH, contentPane);
            layout.putConstraint(SpringLayout.EAST, command_pale, 0,
                    SpringLayout.EAST, tableWithScroll);

            layout.putConstraint(SpringLayout.WEST, info_pale, DISTANCE,
                    SpringLayout.EAST, tableWithScroll);
            layout.putConstraint(SpringLayout.NORTH, info_pale, DISTANCE,
                    SpringLayout.SOUTH, img);
            layout.putConstraint(SpringLayout.EAST, info_pale, -DISTANCE,
                    SpringLayout.EAST, contentPane);
            layout.putConstraint(SpringLayout.SOUTH, info_pale, -DISTANCE,
                    SpringLayout.SOUTH, contentPane);
        }

        window.setVisible(true);
        contentPane.repaint();
    }
}
