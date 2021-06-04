package appFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class InfoPlane extends UserPanel{
    protected int FIELD_DISTANSE = 10;

    private JButton createButton;
    private JButton changeButton;
    private JButton deleteButton;

    private JTextArea name_text;
    private JTextArea coordinates_text;
    private JTextArea x_text;
    private JTextArea y_text;
    private JTextArea creationDate_text;
    private JTextArea minimalPoint_text;
    private JTextArea personalQualitiesMaximum_text;
    private JTextArea difficulty_text;
    private JTextArea discipline_text;
    private JTextArea disciplineName_text;
    private JTextArea practiceHours_text;
    private JTextArea user_text;
    
    private JTextArea name_value;
    private JTextArea coordinates_value;
    private JTextArea x_value;
    private JTextArea y_value;
    private JTextArea creationDate_value;
    private JTextArea minimalPoint_value;
    private JTextArea personalQualitiesMaximum_value;
    private JTextArea difficulty_value;
    private JTextArea discipline_value;
    private JTextArea disciplineName_value;
    private JTextArea practiceHours_value;
    private JTextArea user_value;

    private JTextField name_field;
    private JTextField coordinates_field;
    private JTextField x_field;
    private JTextField y_field;
    private JTextField creationDate_field;
    private JTextField minimalPoint_field;
    private JTextField personalQualitiesMaximum_field;
    private JTextField difficulty_field;
    private JTextField discipline_field;
    private JTextField disciplineName_field;
    private JTextField practiceHours_field;
    private JTextField user_field;

    ArrayList<JTextArea> texts;
    ArrayList<JTextField> fields;
    ArrayList<JTextArea> values;

    boolean changeing = false;

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
        createButton = new JButton("Создать");
        changeButton = new JButton("Изменить");
        changeButton.addActionListener(new Change());
        deleteButton = new JButton("Удалить");


        name_text = new JTextArea("Имя" + ":");
        coordinates_text = new JTextArea("Координаты" + ":");
        x_text = new JTextArea("X" + ":");
        y_text = new JTextArea("Y" + ":");
        creationDate_text = new JTextArea("Время создания" + ":");
        minimalPoint_text = new JTextArea("Минимальная точка" + ":");
        personalQualitiesMaximum_text = new JTextArea("Максимум личностных качеств" + ":");
        difficulty_text = new JTextArea("Сложность" + ":");
        discipline_text = new JTextArea("Дисциплина" + ":");
        disciplineName_text = new JTextArea("Название дисциплины" + ":");
        practiceHours_text = new JTextArea("Часы практики" + ":");
        user_text = new JTextArea("Владелец" + ":");
        texts = new ArrayList<>(Arrays.asList(name_text, coordinates_text, x_text, y_text, creationDate_text,
                minimalPoint_text, personalQualitiesMaximum_text, difficulty_text, discipline_text,
                disciplineName_text, practiceHours_text, user_text));
        for (JTextArea area: texts){
            area.setEditable(false);
        }

        name_value = new JTextArea("Заглушка");
        coordinates_value = new JTextArea("Заглушка");
        x_value = new JTextArea("Заглушка");
        y_value = new JTextArea("Заглушка");
        creationDate_value = new JTextArea("Заглушка");
        minimalPoint_value = new JTextArea("Заглушка");
        personalQualitiesMaximum_value = new JTextArea("Заглушка");
        difficulty_value = new JTextArea("Заглушка");
        discipline_value = new JTextArea("Заглушка");
        disciplineName_value = new JTextArea("Заглушка");
        practiceHours_value = new JTextArea("Заглушка");
        user_value = new JTextArea("Заглушка");
        values = new ArrayList<>(Arrays.asList(name_value, coordinates_value, x_value, y_value, creationDate_value,
                minimalPoint_value, personalQualitiesMaximum_value, difficulty_value, discipline_value,
                disciplineName_value, practiceHours_value, user_value));
        for (JTextArea area: values){
            area.setEditable(false);
        }

        name_field = new JTextField("Заглушка");
        coordinates_field = new JTextField("Заглушка");
        x_field = new JTextField("Заглушка");
        y_field = new JTextField("Заглушка");
        creationDate_field = new JTextField("Заглушка");
        minimalPoint_field = new JTextField("Заглушка");
        personalQualitiesMaximum_field = new JTextField("Заглушка");
        difficulty_field = new JTextField("Заглушка");
        discipline_field = new JTextField("Заглушка");
        disciplineName_field = new JTextField("Заглушка");
        practiceHours_field = new JTextField("Заглушка");
        user_field = new JTextField("Заглушка");
        fields = new ArrayList<>(Arrays.asList(name_field, coordinates_field, x_field, y_field, creationDate_field,
                minimalPoint_field, personalQualitiesMaximum_field, difficulty_field, discipline_field,
                disciplineName_field, practiceHours_field, user_field));
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
        layout.putConstraint(SpringLayout.NORTH, user_field, 0,
                SpringLayout.NORTH, user_text);

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
        layout.putConstraint(SpringLayout.WEST,user_field, FIELD_DISTANSE,
                SpringLayout.EAST, user_text);
        
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
        update();
    }
    
    public void viewFieldElements(){
        for (JTextArea value: values){
            remove(value);
        }
        for (JTextField field: fields){
            add(field);
        }
        update();
    }

    public void viewValueElements(){
        for (JTextField field: fields){
            remove(field);
        }
        for (JTextArea value: values){
            add(value);
        }
        update();
    }

    public class Change implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (changeing) {
                viewValueElements();
                changeing =false;
            } else {
                viewFieldElements();
                changeing = true;
            }
        }
    }

}
