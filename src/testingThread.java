import appFiles.Application;

public class testingThread {
    public static void main(String[] args) throws InterruptedException {

        Application app = new Application();
        app.viewMainPanel();
        /*


        Scanner scan = new Scanner(System.in);

        JFrame frame = new JFrame("SpringLayoutTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();

        SpringLayout layout = new SpringLayout();
        contentPane.setLayout(layout);

        Component label = new JLabel("�����");
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
            System.out.println("������� ����");
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