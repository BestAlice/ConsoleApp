package appFiles;

import javax.swing.*;
import java.awt.*;

public class InfoPlane extends UserPanel{
    protected int FIELD_DISTANSE = 10;

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

    public InfoPlane(SpringLayout layout){
        super(layout);
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
    }
    
    @Override
    public void createPanel() {
        name_text = new JTextArea("Èìÿ");
        coordinates_text = new JTextArea("Èìÿ");
        x_text = new JTextArea("Èìÿ");
        y_text = new JTextArea("Èìÿ");
        creationDate_text = new JTextArea("Èìÿ");
        minimalPoint_text = new JTextArea("Èìÿ");
        personalQualitiesMaximum_text = new JTextArea("Èìÿ");
        difficulty_text = new JTextArea("Èìÿ");
        discipline_text = new JTextArea("Èìÿ");
        disciplineName_text = new JTextArea("Èìÿ");
        practiceHours_text = new JTextArea("Èìÿ");
        user_text = new JTextArea("Èìÿ");

        panel.add(name_text);
        panel.add(coordinates_text);
        panel.add(x_text);
        panel.add(y_text);
        panel.add(creationDate_text);
        panel.add(minimalPoint_text);
        panel.add(personalQualitiesMaximum_text);
        panel.add(difficulty_text);
        panel.add(discipline_text);
        panel.add(disciplineName_text);
        panel.add(practiceHours_text);
        panel.add(user_text);

        layout.putConstraint(SpringLayout.WEST, name_text, DISTANCE,
                SpringLayout.WEST, panel);
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

        FIELD_DISTANSE = 13;
        layout.putConstraint(SpringLayout.NORTH, name_text, DISTANCE,
                SpringLayout.NORTH, panel);
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
    }
}
