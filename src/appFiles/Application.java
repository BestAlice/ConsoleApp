package appFiles;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;


public class Application {
    JFrame window;
    Container content_panel;
    SpringLayout layout;
    int HEIGHT = 900;
    int WIDTH  = 1300;
    int DISTANCE  = 20;

    public TablePanel table_panel;
    public VisualPanel visual_panel;
    public CommadPanel command_panel;
    public InfoPlane info_panel;
    public SettingPanel setting_panel;

    JPanel login_panel;

    public Application(){
        window = new JFrame("MyApp");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        content_panel = window.getContentPane();

        layout = new SpringLayout();

        content_panel.setLayout(layout);
        createUserLabel();

        //Таблица
        table_panel = new TablePanel(layout);

        //Визуализация
        visual_panel = new VisualPanel(layout);
        visual_panel.setPreferredSize(new Dimension(420, 300));

        //окно командной строки
        command_panel = new CommadPanel(layout);

        //область информации
        info_panel = new InfoPlane(layout);

        //имя пользователя и выбор языка
        setting_panel = new SettingPanel(layout);

        login_panel = new LoginPanel(layout);

        window.setSize(WIDTH, HEIGHT);
        window.setVisible(true);
        update();
    }

    public void viewLoginingPanel() {
        content_panel.removeAll();

        content_panel.add(login_panel);

        update();
    }

    public void viewMainPanel() throws InterruptedException {

        content_panel.removeAll();

        content_panel.add(table_panel);
        content_panel.add(visual_panel);
        content_panel.add(command_panel);
        content_panel.add(info_panel);
        content_panel.add(setting_panel);

        update();
    }

    public void update() {
        content_panel.validate();
        setPosition();
        content_panel.repaint();
    }

    public void createUserLabel() {

    }


    public void setPosition() {
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, login_panel, 0,
                SpringLayout.HORIZONTAL_CENTER, content_panel);
        layout.putConstraint(SpringLayout.VERTICAL_CENTER, login_panel, 0,
                SpringLayout.VERTICAL_CENTER, content_panel);

        layout.putConstraint(SpringLayout.NORTH, table_panel, DISTANCE,
                SpringLayout.NORTH, content_panel);
        layout.putConstraint(SpringLayout.WEST, table_panel, DISTANCE,
                SpringLayout.WEST, content_panel);
        layout.putConstraint(SpringLayout.EAST, table_panel, -(WIDTH-800-DISTANCE), //-(int)(window.getWidth()/2 - 0.3*window.getWidth())
                SpringLayout.EAST, content_panel);
        layout.putConstraint(SpringLayout.SOUTH, table_panel, -(HEIGHT-480-DISTANCE), //-(int)(window.getHeight()/2 - 0.3*window.getHeight())
                SpringLayout.SOUTH, content_panel);

        layout.putConstraint(SpringLayout.NORTH, command_panel, 480+DISTANCE, //
                SpringLayout.NORTH, content_panel);
        layout.putConstraint(SpringLayout.WEST, command_panel, DISTANCE,
                SpringLayout.WEST, content_panel);
        layout.putConstraint(SpringLayout.SOUTH, command_panel, -DISTANCE,
                SpringLayout.SOUTH, content_panel);
        layout.putConstraint(SpringLayout.EAST, command_panel, -(WIDTH-800-DISTANCE),
                SpringLayout.EAST, content_panel);

        layout.putConstraint(SpringLayout.NORTH, visual_panel, 4*DISTANCE,
                SpringLayout.NORTH, content_panel);
        layout.putConstraint(SpringLayout.WEST, visual_panel, WIDTH-480,
                SpringLayout.WEST, content_panel);
        layout.putConstraint(SpringLayout.EAST, visual_panel, -DISTANCE,
                SpringLayout.EAST, content_panel);
        layout.putConstraint(SpringLayout.SOUTH, visual_panel, -(HEIGHT - (4*DISTANCE+300)),
                SpringLayout.SOUTH, content_panel);

        layout.putConstraint(SpringLayout.WEST, info_panel, DISTANCE,
                SpringLayout.EAST, command_panel);
        layout.putConstraint(SpringLayout.NORTH, info_panel, DISTANCE,
                SpringLayout.SOUTH, visual_panel);
        layout.putConstraint(SpringLayout.EAST, info_panel, -DISTANCE,
                SpringLayout.EAST, content_panel);
        layout.putConstraint(SpringLayout.SOUTH, info_panel, -DISTANCE,
                SpringLayout.SOUTH, content_panel);

        layout.putConstraint(SpringLayout.NORTH, setting_panel, DISTANCE,
                SpringLayout.NORTH, content_panel);
        layout.putConstraint(SpringLayout.WEST, setting_panel, WIDTH-480,
                SpringLayout.WEST, content_panel);
        layout.putConstraint(SpringLayout.SOUTH, setting_panel, -DISTANCE,
                SpringLayout.NORTH, visual_panel);
        layout.putConstraint(SpringLayout.EAST, setting_panel, -DISTANCE,
                SpringLayout.EAST, content_panel);
    }
}
