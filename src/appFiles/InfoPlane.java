package appFiles;

import client.ClientMessageGeneration;
import collection_control.BadValueException;
import labwork_class.Coordinates;
import labwork_class.Difficulty;
import labwork_class.Discipline;
import labwork_class.LabWork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class InfoPlane extends UserPanel{
    protected int FIELD_DISTANSE = 10;

    private JButton createButton;
    private JButton changeButton;
    private JButton deleteButton;
    private JButton enterButton;
    private JButton cancelButton;

    private JLabel name_text;
    private JLabel coordinates_text;
    private JLabel x_text;
    private JLabel y_text;
    private JLabel creationDate_text;
    private JLabel minimalPoint_text;
    private JLabel personalQualitiesMaximum_text;
    private JLabel difficulty_text;
    private JLabel discipline_text;
    private JLabel disciplineName_text;
    private JLabel practiceHours_text;
    private JLabel user_text;
    
    private JLabel name_value;
    private JLabel coordinates_value;
    private JLabel x_value;
    private JLabel y_value;
    private JLabel creationDate_value;
    private JLabel minimalPoint_value;
    private JLabel personalQualitiesMaximum_value;
    private JLabel difficulty_value;
    private JLabel discipline_value;
    private JLabel disciplineName_value;
    private JLabel practiceHours_value;
    private JLabel user_value;

    private JTextField name_field;
    private JTextField coordinates_field;
    private JTextField x_field;
    private JTextField y_field;
    private JTextField creationDate_field;
    private JTextField minimalPoint_field;
    private JTextField personalQualitiesMaximum_field;
    private JComboBox difficulty_field;
    private JTextField discipline_field;
    private JTextField disciplineName_field;
    private JTextField practiceHours_field;

    ArrayList<JLabel> texts;
    ArrayList<JTextField> fields;
    ArrayList<JLabel> values;

    boolean changeing = false;
    boolean created = false;

    private HashMap<String, Object> labwork = null;

    public InfoPlane(SpringLayout layout){
        super(layout);
        setBackground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    }

    @Override
    public void createPanel() {

        createElements();

        viewConstantElements();
        viewValueElements();
        update();


        //setVisible(true);

    }



    public void createElements() {
        createButton = new JButton(locale.getString("info.create"));
        createButton.addActionListener(new CreateNew());
        changeButton = new JButton(locale.getString("info.change"));
        changeButton.addActionListener(new Change());
        deleteButton = new JButton(locale.getString("info.delete"));
        deleteButton.addActionListener(new Delete());
        enterButton = new JButton(locale.getString("info.enter"));
        enterButton.addActionListener(new Enter());
        cancelButton = new JButton(locale.getString("info.cancel"));
        cancelButton.addActionListener(new Cancel());


        name_text = new JLabel(locale.getString("info.name")); //locale.getString("")
        coordinates_text = new JLabel(locale.getString("info.coordinates"));
        x_text = new JLabel(locale.getString("info.x"));
        y_text = new JLabel(locale.getString("info.y"));
        creationDate_text = new JLabel(locale.getString("info.creationDate"));
        minimalPoint_text = new JLabel(locale.getString("info.minimalPoint"));
        personalQualitiesMaximum_text = new JLabel(locale.getString("info.personalQualitiesMaximum"));
        difficulty_text = new JLabel(locale.getString("info.difficulty"));
        discipline_text = new JLabel(locale.getString("info.discipline"));
        disciplineName_text = new JLabel(locale.getString("info.disciplineName"));
        practiceHours_text = new JLabel(locale.getString("info.practiceHours"));
        user_text = new JLabel(locale.getString("info.user"));
        texts = new ArrayList<>(Arrays.asList(name_text, coordinates_text, x_text, y_text, creationDate_text,
                minimalPoint_text, personalQualitiesMaximum_text, difficulty_text, discipline_text,
                disciplineName_text, practiceHours_text, user_text));


        name_value = new JLabel("");
        coordinates_value = new JLabel("");
        x_value = new JLabel("");
        y_value = new JLabel("");
        creationDate_value = new JLabel("");
        minimalPoint_value = new JLabel("");
        personalQualitiesMaximum_value = new JLabel("");
        difficulty_value = new JLabel("");
        discipline_value = new JLabel("");
        disciplineName_value = new JLabel("");
        practiceHours_value = new JLabel("");
        user_value = new JLabel("");
        values = new ArrayList<>(Arrays.asList(name_value, x_value, y_value,
                minimalPoint_value, personalQualitiesMaximum_value, difficulty_value,
                disciplineName_value, practiceHours_value));

        name_field = new JTextField("");
        coordinates_field = new JTextField("");
        x_field = new JTextField("");
        y_field = new JTextField("");
        creationDate_field = new JTextField("");
        minimalPoint_field = new JTextField("");
        personalQualitiesMaximum_field = new JTextField("");

        String[] elements = new String[] {"", "EASY", "IMPOSSIBLE",
                "INSANE", "TERRIBLE"};
        DefaultComboBoxModel difModel = new DefaultComboBoxModel<String>(elements);
        difficulty_field = new JComboBox<String>(difModel);
        //difficulty_field = new JTextField("");


        discipline_field = new JTextField("");
        disciplineName_field = new JTextField("");
        practiceHours_field = new JTextField("");
        fields = new ArrayList<>(Arrays.asList(name_field, x_field, y_field,
                minimalPoint_field, personalQualitiesMaximum_field,
                disciplineName_field, practiceHours_field));
    }

    public void setPosition() {

        //Кнопки сверху
        layout.putConstraint(SpringLayout.NORTH, createButton, DISTANCE,
                SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, changeButton, 0,
                SpringLayout.NORTH, createButton);
        layout.putConstraint(SpringLayout.NORTH, deleteButton, 0,
                SpringLayout.NORTH, createButton);
        layout.putConstraint(SpringLayout.WEST, createButton, DISTANCE,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, deleteButton, -DISTANCE,
                SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.WEST, changeButton, DISTANCE,
                SpringLayout.EAST, createButton);
        layout.putConstraint(SpringLayout.EAST, changeButton, -DISTANCE,
                SpringLayout.WEST, deleteButton);

        // Поля - заголовки
        layout.putConstraint(SpringLayout.WEST, name_text, DISTANCE,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.WEST, coordinates_text, 0,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, x_text, DISTANCE,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, y_text, DISTANCE,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, creationDate_text, 0,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, minimalPoint_text, 0,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, personalQualitiesMaximum_text, 0,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, difficulty_text, 0,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, discipline_text, 0,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, disciplineName_text, DISTANCE,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, practiceHours_text, DISTANCE,
                SpringLayout.WEST, name_text);
        layout.putConstraint(SpringLayout.WEST, user_text, 0,
                SpringLayout.WEST, name_text);

        FIELD_DISTANSE = 10;
        layout.putConstraint(SpringLayout.NORTH, name_text, DISTANCE,
                SpringLayout.SOUTH, createButton);
        layout.putConstraint(SpringLayout.NORTH, coordinates_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, name_text);
        layout.putConstraint(SpringLayout.NORTH, x_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, coordinates_text);
        layout.putConstraint(SpringLayout.NORTH, y_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, x_text);
        layout.putConstraint(SpringLayout.NORTH, creationDate_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, y_text);
        layout.putConstraint(SpringLayout.NORTH, minimalPoint_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, creationDate_text);
        layout.putConstraint(SpringLayout.NORTH, personalQualitiesMaximum_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, minimalPoint_text);
        layout.putConstraint(SpringLayout.NORTH, difficulty_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, personalQualitiesMaximum_text);
        layout.putConstraint(SpringLayout.NORTH, discipline_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, difficulty_text);
        layout.putConstraint(SpringLayout.NORTH, disciplineName_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, discipline_text);
        layout.putConstraint(SpringLayout.NORTH, practiceHours_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, disciplineName_text);
        layout.putConstraint(SpringLayout.NORTH, user_text, FIELD_DISTANSE,
                SpringLayout.SOUTH, practiceHours_text);

        //нижние кнопки
        layout.putConstraint(SpringLayout.WEST, enterButton, 100,
                SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, enterButton, FIELD_DISTANSE,
                SpringLayout.SOUTH, user_text);

        layout.putConstraint(SpringLayout.NORTH, cancelButton, FIELD_DISTANSE,
                SpringLayout.SOUTH, user_text);
        layout.putConstraint(SpringLayout.WEST, cancelButton, DISTANCE,
                SpringLayout.EAST, enterButton);


        //поля ввода
        layout.putConstraint(SpringLayout.NORTH, name_field, 0,
                SpringLayout.NORTH, name_text);
        layout.putConstraint(SpringLayout.NORTH, coordinates_field, 0,
                SpringLayout.NORTH, coordinates_text);
        layout.putConstraint(SpringLayout.NORTH, x_field, 0,
                SpringLayout.NORTH, x_text);
        layout.putConstraint(SpringLayout.NORTH, y_field, 0,
                SpringLayout.NORTH, y_text);
        layout.putConstraint(SpringLayout.NORTH, creationDate_field, 0,
                SpringLayout.NORTH, creationDate_text);
        layout.putConstraint(SpringLayout.NORTH, minimalPoint_field, 0,
                SpringLayout.NORTH, minimalPoint_text);
        layout.putConstraint(SpringLayout.NORTH, personalQualitiesMaximum_field, 0,
                SpringLayout.NORTH, personalQualitiesMaximum_text);
        layout.putConstraint(SpringLayout.NORTH, difficulty_field, 0,
                SpringLayout.NORTH, difficulty_text);
        layout.putConstraint(SpringLayout.NORTH, discipline_field, 0,
                SpringLayout.NORTH, discipline_text);
        layout.putConstraint(SpringLayout.NORTH, disciplineName_field, 0,
                SpringLayout.NORTH, disciplineName_text);
        layout.putConstraint(SpringLayout.NORTH, practiceHours_field, 0,
                SpringLayout.NORTH, practiceHours_text);

        layout.putConstraint(SpringLayout.WEST, name_field, FIELD_DISTANSE,
                SpringLayout.EAST, name_text);
        layout.putConstraint(SpringLayout.WEST, coordinates_field, FIELD_DISTANSE,
                SpringLayout.EAST, coordinates_text);
        layout.putConstraint(SpringLayout.WEST, x_field, FIELD_DISTANSE,
                SpringLayout.EAST, x_text);
        layout.putConstraint(SpringLayout.WEST, y_field, FIELD_DISTANSE,
                SpringLayout.EAST, y_text);
        layout.putConstraint(SpringLayout.WEST, creationDate_field, FIELD_DISTANSE,
                SpringLayout.EAST, creationDate_text);
        layout.putConstraint(SpringLayout.WEST, minimalPoint_field, FIELD_DISTANSE,
                SpringLayout.EAST, minimalPoint_text);
        layout.putConstraint(SpringLayout.WEST, personalQualitiesMaximum_field, FIELD_DISTANSE,
                SpringLayout.EAST, personalQualitiesMaximum_text);
        layout.putConstraint(SpringLayout.WEST, difficulty_field, FIELD_DISTANSE,
                SpringLayout.EAST, difficulty_text);
        layout.putConstraint(SpringLayout.WEST, discipline_field, FIELD_DISTANSE,
                SpringLayout.EAST, discipline_text);
        layout.putConstraint(SpringLayout.WEST, disciplineName_field, FIELD_DISTANSE,
                SpringLayout.EAST, disciplineName_text);
        layout.putConstraint(SpringLayout.WEST, practiceHours_field, FIELD_DISTANSE,
                SpringLayout.EAST, practiceHours_text);

        //тупо поля текста
        layout.putConstraint(SpringLayout.NORTH, name_value, 0,
                SpringLayout.NORTH, name_text);
        layout.putConstraint(SpringLayout.NORTH, coordinates_value, 0,
                SpringLayout.NORTH, coordinates_text);
        layout.putConstraint(SpringLayout.NORTH, x_value, 0,
                SpringLayout.NORTH, x_text);
        layout.putConstraint(SpringLayout.NORTH, y_value, 0,
                SpringLayout.NORTH, y_text);
        layout.putConstraint(SpringLayout.NORTH, creationDate_value, 0,
                SpringLayout.NORTH, creationDate_text);
        layout.putConstraint(SpringLayout.NORTH, minimalPoint_value, 0,
                SpringLayout.NORTH, minimalPoint_text);
        layout.putConstraint(SpringLayout.NORTH, personalQualitiesMaximum_value, 0,
                SpringLayout.NORTH, personalQualitiesMaximum_text);
        layout.putConstraint(SpringLayout.NORTH, difficulty_value, 0,
                SpringLayout.NORTH, difficulty_text);
        layout.putConstraint(SpringLayout.NORTH, discipline_value, 0,
                SpringLayout.NORTH, discipline_text);
        layout.putConstraint(SpringLayout.NORTH, disciplineName_value, 0,
                SpringLayout.NORTH, disciplineName_text);
        layout.putConstraint(SpringLayout.NORTH, practiceHours_value, 0,
                SpringLayout.NORTH, practiceHours_text);
        layout.putConstraint(SpringLayout.NORTH, user_value, 0,
                SpringLayout.NORTH, user_text);

        layout.putConstraint(SpringLayout.WEST, name_value, FIELD_DISTANSE,
                SpringLayout.EAST, name_text);
        layout.putConstraint(SpringLayout.WEST, coordinates_value, FIELD_DISTANSE,
                SpringLayout.EAST, coordinates_text);
        layout.putConstraint(SpringLayout.WEST, x_value, FIELD_DISTANSE,
                SpringLayout.EAST, x_text);
        layout.putConstraint(SpringLayout.WEST, y_value, FIELD_DISTANSE,
                SpringLayout.EAST, y_text);
        layout.putConstraint(SpringLayout.WEST, creationDate_value, FIELD_DISTANSE,
                SpringLayout.EAST, creationDate_text);
        layout.putConstraint(SpringLayout.WEST, minimalPoint_value, FIELD_DISTANSE,
                SpringLayout.EAST, minimalPoint_text);
        layout.putConstraint(SpringLayout.WEST, personalQualitiesMaximum_value, FIELD_DISTANSE,
                SpringLayout.EAST, personalQualitiesMaximum_text);
        layout.putConstraint(SpringLayout.WEST, difficulty_value, FIELD_DISTANSE,
                SpringLayout.EAST, difficulty_text);
        layout.putConstraint(SpringLayout.WEST, discipline_value, FIELD_DISTANSE,
                SpringLayout.EAST, discipline_text);
        layout.putConstraint(SpringLayout.WEST, disciplineName_value, FIELD_DISTANSE,
                SpringLayout.EAST, disciplineName_text);
        layout.putConstraint(SpringLayout.WEST, practiceHours_value, FIELD_DISTANSE,
                SpringLayout.EAST, practiceHours_text);
        layout.putConstraint(SpringLayout.WEST,user_value, FIELD_DISTANSE,
                SpringLayout.EAST, user_text);
    }

    @Override
    public void updateLocale() {
        locale = Application.rb;
        createButton.setText(locale.getString("info.create"));
        createButton.addActionListener(new CreateNew());
        changeButton.setText(locale.getString("info.change"));
        changeButton.addActionListener(new Change());
        deleteButton.setText(locale.getString("info.delete"));
        deleteButton.addActionListener(new Delete());
        enterButton.setText(locale.getString("info.enter"));
        enterButton.addActionListener(new Enter());
        cancelButton.setText(locale.getString("info.cancel"));
        cancelButton.addActionListener(new Cancel());


        name_text.setText(locale.getString("info.name")); //locale.getString("")
        coordinates_text.setText(locale.getString("info.coordinates"));
        x_text.setText(locale.getString("info.x"));
        y_text.setText(locale.getString("info.y"));
        creationDate_text.setText(locale.getString("info.creationDate"));
        minimalPoint_text.setText(locale.getString("info.minimalPoint"));
        personalQualitiesMaximum_text.setText(locale.getString("info.personalQualitiesMaximum"));
        difficulty_text.setText(locale.getString("info.difficulty"));
        discipline_text.setText(locale.getString("info.discipline"));
        disciplineName_text.setText(locale.getString("info.disciplineName"));
        practiceHours_text.setText(locale.getString("info.practiceHours"));
        user_text.setText(locale.getString("info.user"));
        update();
    }

    public void viewConstantElements() {
        add(createButton);
        add(changeButton);
        add(deleteButton);
        add(name_text);
        add(coordinates_text);
        add(x_text);
        add(y_text);
        add(creationDate_text);
        add(minimalPoint_text);
        add(personalQualitiesMaximum_text);
        add(difficulty_text);
        add(discipline_text);
        add(disciplineName_text);
        add(practiceHours_text);
        add(user_text);
        add(user_value);
        add(creationDate_value);
        update();
    }

    public void viewFieldElements(){
        for (JLabel value: values){
            remove(value);
        }
        for (JTextField field: fields){
            add(field);
        }
        add(difficulty_field);
        difficulty_field.setPreferredSize(new Dimension(100, 20));
        add(enterButton);
        add(cancelButton);
        for (JTextField field: fields){
            field.setPreferredSize(new Dimension(100, 20));
        }
        update();
    }

    public void viewValueElements(){
        for (JTextField field: fields){
            remove(field);
        }
        remove(difficulty_field);
        remove(enterButton);
        remove(cancelButton);
        for (JLabel value: values){
            add(value);
        }
        if (labwork != null) {
            viewSelectedLabWork();
        }
        update();
    }

    public HashMap<String, Object> getLabwork() {
        return labwork;
    }

    public void setLabwork(HashMap<String, Object> labwork) {
        this.labwork = labwork;
        if (labwork == null) {
            viewClearLabWork();
        } else {
            viewSelectedLabWork();
        }
    }

    public void viewSelectedLabWork(){
        name_value.setText((String) labwork.get("name"));
        x_value.setText(((Long) labwork.get("x")).toString());
        y_value.setText(((Long) labwork.get("y")).toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(locale.getString("info.formatter"));
        if (!changeing & !created){
            creationDate_value.setText( ((LocalDateTime) labwork.get("creationDate")).format(formatter) );
        }
        minimalPoint_value.setText(String.valueOf((int) labwork.get("minimalPoint")));
        personalQualitiesMaximum_value.setText(((Long) labwork.get("personalQualitiesMaximum")).toString());
        if (labwork.get("difficulty") != null) {
            difficulty_value.setText((labwork.get("difficulty")).toString());
        } else {
            difficulty_value.setText("");
        }
        if (labwork.get("disciplineName") != null) {
            disciplineName_value.setText((String) labwork.get("disciplineName"));
            practiceHours_value.setText(labwork.get("practiceHours").toString());
        } else {
            disciplineName_value.setText("");
            practiceHours_value.setText("");
        }
        if (!changeing & !created) {
            user_value.setText((String) labwork.get("userName"));
        }

        name_field.setText((String) labwork.get("name"));
        x_field.setText(((Long) labwork.get("x")).toString());
        y_field.setText(((Long) labwork.get("y")).toString());

        minimalPoint_field.setText(String.valueOf((int) labwork.get("minimalPoint")));
        personalQualitiesMaximum_field.setText(((Long) labwork.get("personalQualitiesMaximum")).toString());
        if (labwork.get("difficulty") != null) {
            difficulty_field.setSelectedItem((labwork.get("difficulty")).toString());
        } else {
            difficulty_field.setSelectedItem("");
        }
        if (labwork.get("disciplineName") != null) {
            disciplineName_field.setText((String) labwork.get("disciplineName"));
            practiceHours_field.setText(labwork.get("practiceHours").toString());
        } else {
            disciplineName_field.setText("");
            practiceHours_field.setText("");
        }
        update();
    }

    public void viewClearLabWork(){
        for (JLabel value: values) {
            value.setText("");
        }
        for (JTextField field: fields) {
            field.setText("");
        }
        difficulty_field.setSelectedItem("");
        for (JTextField field: fields){
            field.setPreferredSize(new Dimension(100, 20));
        }
        update();
    }

    public class Change implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (labwork==null){
                CommandPanel.addEntry(locale.getString("info.notSelected"));
            } else if (!((String)labwork.get("userName")).equals(ClientMessageGeneration.login)){
                CommandPanel.addEntry(locale.getString("info.notYou"));
            } else{
                if (created) {
                    viewValueElements();
                    created = false;
                }
                if (changeing) {
                    changeing =false;
                    viewValueElements();
                } else {
                    changeing = true;
                    viewSelectedLabWork();
                    user_value.setText((String) labwork.get("userName"));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(locale.getString("info.formatter"));

                    creationDate_value.setText(((LocalDateTime)labwork.get("creationDate")).format(formatter));
                    viewFieldElements();
                }

                update();
            }
        }
    }

    public class CreateNew implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                if (changeing) {
                    viewValueElements();
                    changeing = false;
                }
                if (created) {
                    created =false;
                    viewValueElements();
                } else {
                    created = true;
                    viewClearLabWork();
                    user_value.setText(ClientMessageGeneration.login);
                    creationDate_value.setText("");
                    viewFieldElements();
                }
                for (JTextField field: fields){
                    field.setText("");
                    field.setPreferredSize(new Dimension(100, 20));
                }
                update();
        }
    }

    public class Cancel implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            created = false;
            changeing = false;
            viewValueElements();
        }
    }

    public class Enter implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            LabWork newLaba = new LabWork().newLab();
            Coordinates coord = new Coordinates();
            Discipline dis = new Discipline();
            if (changeing) {
                newLaba.setId((Long)labwork.get("id"));
                newLaba.setUserName((String) labwork.get("userName"));
                newLaba.setUserId((Long) labwork.get("userId"));
                newLaba.setCreationDate((LocalDateTime) labwork.get("creationDate"), "read");
                coord.setId((Long)labwork.get("coordinatesId"));
                dis.setId((Long)labwork.get("disciplineId"));
            } else {
                newLaba.setUserName(ClientMessageGeneration.login);
                newLaba.setUserId(ClientMessageGeneration.userId);
            }
            try{
                newLaba.setName(name_field.getText(), "input");
                coord.setX(x_field.getText(), "input");
                coord.setY(y_field.getText(), "input");
                newLaba.setCoordinates(coord, "input");
                newLaba.setMinimalPoint(minimalPoint_field.getText(), "input");
                newLaba.setPersonalQualitiesMaximum(personalQualitiesMaximum_field.getText(), "field");
                if (!((String)difficulty_field.getSelectedItem()).equals("")) {
                    newLaba.setDifficulty((String) difficulty_field.getSelectedItem(), "input");
                }
                if (!disciplineName_field.getText().equals("") & !practiceHours_field.getText().equals("")){
                    dis.setName(disciplineName_field.getText(), "input");
                    dis.setPracticeHours(practiceHours_field.getText(), "input");
                    newLaba.setDiscipline(dis,"input");
                }
                newLaba.findWeight();
                if (Application.commandReader != null) {
                    Application.commandReader.setLabwork(newLaba);
                    if (changeing) {
                        Application.commandReader.writeCommand("update");
                        changeing = false;
                        setLabwork(newLaba.getMap());
                        Thread.sleep(100);
                        Application.commandReader.setLabwork(null);
                        //viewValueElements();
                    } else if (created) {
                        Application.commandReader.writeCommand("add");
                        created = false;
                        Thread.sleep(100);
                        Application.commandReader.setLabwork(null);
                        //viewValueElements();
                    } else {
                        CommandPanel.addEntry("Шо происходит?"); //не переводить
                    }
                } else {
                    CommandPanel.addEntry(locale.getString("info.creationComlited"));
                }
                viewValueElements();
            } catch (BadValueException badValueException) {
                badValueException.setType("input");
                badValueException.message();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    }

    public class Delete implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            try {
                if (labwork == null) {
                    CommandPanel.addEntry(locale.getString("info.notSelected"));
                } else if (!((String)labwork.get("userName")).equals(ClientMessageGeneration.login)){
                    CommandPanel.addEntry(locale.getString("info.notYou"));
                } else {
                    Application.commandReader.writeCommand("remove_by_id " + (Long) labwork.get("id"));
                }

            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

        }
    }
}
