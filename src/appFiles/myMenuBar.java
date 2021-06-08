package appFiles;

import client.ClientMessageGeneration;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Locale;

public class myMenuBar extends JMenuBar {
    JMenu language;
    Application app;
    public myMenuBar() {
        super();
        language  = new JMenu("Language");

        JRadioButtonMenuItem ru = new JRadioButtonMenuItem("Русский");
        ru.addActionListener(new Ru());
        JRadioButtonMenuItem bg = new JRadioButtonMenuItem("Български");
        bg.addActionListener(new Bg());
        JRadioButtonMenuItem et = new JRadioButtonMenuItem("Eesti");
        et.addActionListener(new Et());
        JRadioButtonMenuItem en = new JRadioButtonMenuItem("English");
        en.addActionListener(new En());
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(ru);
        buttonGroup.add(bg);
        buttonGroup.add(et);
        buttonGroup.add(en);
        language.add(ru);
        language.add(bg);
        language.add(et);
        language.add(en);
        add(language);

    }

    public void updateLocale() {
        language.setText(Application.rb.getString("bar.language"));
        this.repaint();
    }

    public class Ru implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Locale locale = new Locale("ru", "RU");
            app.updateLocale(locale);
        }
    }

    public class Bg implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Locale locale = new Locale("bg", "BG");
            app.updateLocale(locale);
        }
    }

    public class Et implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Locale locale = new Locale("et", "EE");
            app.updateLocale(locale);
        }
    }

    public class En implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Locale locale = new Locale("en", "GB");
            app.updateLocale(locale);
        }
    }

    public void setApp(Application app) {
        this.app = app;
    }

}
