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
            coord.setX("26", "read");
            coord.setY("0", "read");
            discipline.setName("HELLO there", "read");
            discipline.setPracticeHours("666", "read");
            lab.setName("Vivaldi", "read");
            lab.setCoordinates(coord, "read");
            lab.setMinimalPoint("345", "read");
            lab.setPersonalQualitiesMaximum("324", "read");
            lab.setDifficulty("EASY", "read");
            //lab.setDiscipline(discipline, "read");
        } catch (BadValueException e ) {
            System.out.println("BAD VALUE");
            System.out.println(e.getMessage());
        }


        DataBase BD = new DataBase();
        BD.insertLabWork(lab);
        try{
            lab.setName("Horizon", "read");
            lab.setDiscipline(discipline, "read");
            lab.setDifficulty(null, "read");
        }catch (BadValueException e) {}

        //BD.createIterators();
        BD.updateLabWork(lab.getId(), lab);

    }
}