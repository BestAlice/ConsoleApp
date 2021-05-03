import collection_control.BadValueException;
import collection_control.DataBase;
import labwork_class.Coordinates;
import labwork_class.Discipline;
import labwork_class.LabWork;

import java.util.Scanner;

public class testingThread
{
    public static void main(String[] args) {
        Coordinates coord = new Coordinates("read", new Scanner(System.in));
        Discipline discipline = new Discipline("read", new Scanner(System.in));
        LabWork lab = new LabWork().newLab();
        try {
            coord.setX("2", "read");
            coord.setY("6", "read");
            discipline.setName("Name", "read");
            discipline.setPracticeHours("333", "read");
            lab.setName("makarena", "read");
            lab.setCoordinates(coord, "read");
            lab.setMinimalPoint("345", "read");
            lab.setPersonalQualitiesMaximum("324", "read");
            lab.setDifficulty("EASY", "read");
            lab.setDiscipline(discipline, "read");
        } catch (BadValueException e ) {
            System.out.println("BAD VALUE");
            System.out.println(e.getMessage());
        }


        DataBase BD = new DataBase();
        //BD.createIterators();
        BD.insertLabWork(lab);

        LabWork readed_laba = BD.selectLabWork(lab.getId());
        readed_laba.show();

    }
}