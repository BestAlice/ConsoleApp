package appFiles;

import client.CommandReader;

import javax.swing.*;
import java.awt.*;


public class Application {
    public static JFrame window;
    public static Container content_panel;
    public static SpringLayout layout;
    public static CommandReader commandReader =null;
    static int HEIGHT = 850;
    static int WIDTH  = 1300;
    static int DISTANCE  = 20;

    public static TablePanel table_panel;
    public static VisualPanel visual_panel;
    public static CommandPanel command_panel;
    public static InfoPlane info_panel;
    public static SettingPanel setting_panel;

    private static String nowView = "Nothing";

    public static JPanel login_panel;

    public Application(){
        window = new JFrame("MyApp");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        content_panel = window.getContentPane();

        layout = new SpringLayout();

        content_panel.setLayout(null);
        //createUserLabel();

        //Таблица
        table_panel = new TablePanel(layout);

        //Визуализация
        visual_panel = new VisualPanel(layout);
        visual_panel.setPreferredSize(new Dimension(420, 300));

        //окно командной строки
        command_panel = new CommandPanel(layout);

        //область информации
        info_panel = new InfoPlane(layout);

        //имя пользователя и выбор языка
        setting_panel = new SettingPanel(layout);

        login_panel = new LoginPanel(layout);

        window.setSize(WIDTH, HEIGHT);
        window.setVisible(true);
        update();
    }

    public static void viewLoginingPanel() {
        nowView = "login";
        content_panel.removeAll();

        content_panel.add(login_panel);

        update();
    }

    public static void viewMainPanel()  {
        nowView = "main";
        content_panel.removeAll();

        content_panel.add(table_panel);
        content_panel.add(visual_panel);
        content_panel.add(command_panel);
        content_panel.add(info_panel);
        content_panel.add(setting_panel);

        update();
    }

    public static void update() {
        content_panel.revalidate();
        setPosition();
        content_panel.repaint();
    }


    public static void setPosition() {
        login_panel.setBounds((WIDTH-300)/2, (HEIGHT-260)/2, 300, 200);
        table_panel.setBounds(DISTANCE, DISTANCE, 800, 460);
        command_panel.setBounds(DISTANCE, 500, 800, 290);
        setting_panel.setBounds(840, DISTANCE, 420, 30);
        visual_panel.setBounds(840, 70, 420, 280);
        info_panel.setBounds(840, 370, 420, 420);
        /*
        login_panel.setMaximumSize(new Dimension(300, 150));
        login_panel.setPreferredSize(new Dimension(300, 150));
        layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, login_panel, 0,
                SpringLayout.HORIZONTAL_CENTER, content_panel);
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

        layout.putConstraint(SpringLayout.NORTH, command_panel, 480, SpringLayout.NORTH, content_panel);
        layout.putConstraint(SpringLayout.WEST, command_panel, DISTANCE, SpringLayout.WEST, content_panel);
        layout.putConstraint(SpringLayout.SOUTH, command_panel, -DISTANCE, SpringLayout.SOUTH, content_panel);
        layout.putConstraint(SpringLayout.EAST, command_panel, -(WIDTH-800-DISTANCE), SpringLayout.EAST, content_panel);

        layout.putConstraint(SpringLayout.NORTH, visual_panel, (int)(3.5*DISTANCE),
                SpringLayout.NORTH, content_panel);
        layout.putConstraint(SpringLayout.WEST, visual_panel, WIDTH-480,
                SpringLayout.WEST, content_panel);
        layout.putConstraint(SpringLayout.EAST, visual_panel, -DISTANCE,
                SpringLayout.EAST, content_panel);
        layout.putConstraint(SpringLayout.SOUTH, visual_panel, -(HEIGHT - (4*DISTANCE+300)),
                SpringLayout.SOUTH, content_panel);

        layout.putConstraint(SpringLayout.WEST, info_panel, WIDTH-480,
                SpringLayout.WEST, content_panel);
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

 */}


    public static void setCommandReader(CommandReader reader){
        Application.commandReader = reader;
    }
}
