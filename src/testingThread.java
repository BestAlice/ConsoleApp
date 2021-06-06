import appFiles.Application;

import javax.swing.plaf.TableHeaderUI;


public class testingThread {



    public static void main(String[] args) throws InterruptedException {

        Application app = new Application();

        app.viewMainPanel();

        for (int i = 0; i < 20; i++){
            //Thread.sleep(100);
            app.command_panel.command_answer.setText(app.command_panel.command_answer.getText() + "\n" + "1234567890");
        }
        String[] list = {"111111", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        app.table_panel.addRow(list);
        app.table_panel.model.addRow(new Object[]{"111111", "2", "3", "4", "5", "6", "7", "8", "9", "10"});
        //Application.command_panel.update();
        app.table_panel.update();
        app.update();
        Thread.sleep(0);
        //app.viewLoginingPanel();

        //app.info_panel.viewValueElements();

        //app.info_panel.viewValueElements();


        //app.table_panel.update();
        //app.visual_panel.update();
        //app.command_panel.update();
        //app.setting_panel.viewLanguages();
        //app.repaint();



        /*


        Scanner scan = new Scanner(System.in);

        JFrame frame = new JFrame("SpringLayoutTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();

        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        Component label = new JLabel("ћетка");
        Component field = new JTextField(10);
        Component button = new JButton("Hello");
        button.setPreferredSize(new Dimension(100, 20));


        contentPane.add(label);
        contentPane.add(field);
        contentPane.add(button);
        layout.putConstraint(SpringLayout.WEST, label, 10,
                SpringLayout.WEST, contentPane);
        layout.putConstraint(SpringLayout.NORTH, label, 25,
                SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.NORTH, field, 25,
                SpringLayout.NORTH, contentPane);
        layout.putConstraint(SpringLayout.WEST, field, 20,
                SpringLayout.EAST, label);
        layout.putConstraint(SpringLayout.NORTH, button, 0, SpringLayout.NORTH, field);
        layout.putConstraint(SpringLayout.WEST, button, 10,
                SpringLayout.EAST, field);


        frame.setSize(500, 110);
        frame.setVisible(true);
        ;
        Thread.sleep(3000);
        frame.remove(button);
        frame.repaint();
        Thread.sleep(3000);
        frame.repaint();
        while (true) {
            System.out.println("¬ведите €зык");
            String lang = scan.nextLine();
            Locale locale;
            switch (lang) {
                case "ru":
                    locale = new Locale("ru", "RU");
                    break;
                case "en":
                    locale = new Locale("en", "UK");
                    break;
                default:
                    locale = new Locale("en", "UK");
                    break;
            }
            ResourceBundle rb;
            rb = ResourceBundle.getBundle("locale.text", locale);

            String hello = rb.getString("test.1");
            System.out.println(hello);
            String world = rb.getString("test.2");
            System.out.println(world);
        }*/

    }
}
