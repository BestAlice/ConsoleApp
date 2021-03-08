package collection_control;

import labwork_class.Difficulty;
import labwork_class.LabWork;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleCommands {
    private LinkedList<LabWork> LabList;
    private String[] commandLine;
    CheckInput check = new CheckInput();
    LocalDateTime timeInit =  LocalDateTime.now();
    Scanner scan;

    public ConsoleCommands (LinkedList<LabWork> LabList, String[] commandLine, Scanner scan){
        this.LabList = LabList;
        this.commandLine = commandLine;
        this.scan = scan;
    }

    public void help() {
        try {
            File readme = new File("./README.txt"); //readme = new File("ConsoleApp.jar/README.txt"); getClass().getResource("/README.txt").getPath()
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readme), "UTF-8"));
            for (String line; (line = br.readLine()) != null; ) {
                System.out.println(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл README не найден");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void info(){
        System.out.println("Тип коллекции:\tLinkedList");
        System.out.println("Содержит объеты класса:\tLabWork");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ss:mm:hh dd MM YYYY");
        System.out.println("Время иницилизации: " + timeInit.format(formatter));
        System.out.printf("Колличество элементов: %d\n", LabList.size());
    }

    public void show(){
        for (LabWork lab: LabList) {
            System.out.println();
            lab.show();
        }
    }

    public void showShort(){
        for (LabWork lab: LabList) {
            System.out.printf("%d: %s\n", lab.getId(), lab.getName());
        }
    }

    public void showEl() {
        try {
            System.out.println(commandLine[1]);
            System.out.println(new LabWork().getUsingId());
            Long id = check.checkId(commandLine[1], new LabWork().getUsingId());
            LabWork lab = this.findById(id);
            lab.show();
        } catch (BadValueException e) {
            e.message("input");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void add(){
        LabWork newLab = new LabWork().newLab().create(scan);
        LabList.add(newLab);
        sort();
    }

    public void update() {
        try {
            Long id = check.checkId(commandLine[1], new LabWork().getUsingId());
            LabWork updateLab = this.findById(id);
            updateLab.update(scan);
            sort();
        } catch (BadValueException e) {
            e.message("input");
        } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Не введено id");
        }
    }

    public void remove_by_id (){
        try {
            Long id = check.checkId(commandLine[1], new LabWork().getUsingId());
            for (LabWork element: LabList) {
                if (element.getId().equals(id)) {
                    remove(element);
                    break;
                }
            }
            sort();
            System.out.println("Удаление завершено");
        } catch (BadValueException e) {
            e.message("input");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void clear(){
        LabList.clear();
        System.out.println("Отчистка завершена");
    }

    public void save(String nameJson){
        String json = ParseJson.parseToJson(LabList);
        File file = new File(System.getenv(nameJson));
        try {
            PrintWriter writer = new PrintWriter(file);

            writer.print(json);
            writer.close();
            System.out.println("Сохранение прошло успешно");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove_first(){
        try{
            LabList.removeFirst();
            System.out.println("Удаление завершено");
        } catch (NoSuchElementException e) {
            System.out.println("Коллекция пуста");
        }
    }

    public void add_if_max(){
        LabWork maxElement = new LabWork();
        try{
            maxElement.setMinimalPoint("1", "read");
        } catch (BadValueException e) {
            e.message();
        }
        for (LabWork lab: LabList) {
            if (lab.compareTo(maxElement) > 0)
                {maxElement = lab;}
        }
        LabWork newLab = new LabWork().newLab().create(scan);
        if (newLab.compareTo(maxElement) > 0) {
            newLab.newLab();
            LabList.add(newLab);
            System.out.println("Новый элемент добавлен");
        } else {
            System.out.println("Новый элемен не удовлетворяет условию добавления");
        }
    }

    public void add_if_min() {
        LabWork minElement = new LabWork();
        try{
            minElement.setMinimalPoint(String.valueOf(Long.MAX_VALUE), "read");
        } catch (BadValueException e) {
            e.message();
        }
        for (LabWork lab: LabList) {
            if (lab.compareTo(minElement) < 0)
            {minElement = lab;}
        }
        LabWork newLab = new LabWork().create(scan);
        if (newLab.compareTo(minElement) < 0) {
            newLab.newLab();
            LabList.add(newLab);
            System.out.println("Новый элемент добавлен");
        } else {
            System.out.println("Новый элемен не удовлетворяет условию добавления");
        }
    }

    public void remove_any_by_personal_qualities_maximum (){
        try {
            Long var = check.checkLong(commandLine[1], 0L, Long.MAX_VALUE, true);
            boolean delited = false;
            for (LabWork lab : LabList) {
                if (lab.getPersonalQualitiesMaximum().equals(var)) {
                    System.out.printf("Элемент %d удалён\n", lab.getId());
                    delited = true;
                    remove(lab);

                    break;
                }
            }
            if (!delited) {
                System.out.println("Не нашлось подходящих элементов");
            }
        } catch (BadValueException e) {
            e.message("input", "personalQualitiesMaximum");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено PersonalQualitiesMaximum для сравнения");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void min_by_creation_date(){
        try {
            LabWork minTime = LabList.getFirst();
            for (LabWork lab : LabList) {
                if (comparatorByDate.compare(minTime.getCreationDate(), lab.getCreationDate()) > 0) {
                    minTime = lab;
                }
            }
            minTime.show();
        } catch (NoSuchElementException e) {
            System.out.println("Коллекция пуста");
        }
    }

    public void count_by_difficulty(){
        try{
            Difficulty dif = check.checkEnum(commandLine[1], Difficulty.class, true );
            int counter = 0;
            for (LabWork lab : LabList) {
                if(lab.getDifficulty() ==dif) {counter++;}
            }
            System.out.println("Лабораторных уровня сложности " + dif + " : " + counter);
        } catch (BadValueException e) {
            e.message("input", "Difficulty");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено Difficulty для сравнения");
        } catch (NoSuchElementException e){
            System.out.println("Коллекция пуста");
        }
    }

    public LabWork findById(Long id) {
        for (LabWork element: LabList) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }

    public void sort(){
        LabList.sort(LabWork::compareTo);
    }

    public void remove (LabWork lab){
        try {
            LabList.remove(lab);
            new LabWork().removeIdFromUssing(lab.getId());
            System.out.println("Объект успешно удалён");
        } catch (BadValueException e) {
            e.message("input");
        } catch (NoSuchElementException e) {
            System.out.println("Коллекция пуста");
        }
    }

    public Comparator<LocalDateTime> comparatorByDate = new Comparator<LocalDateTime>() {
        @Override
        public int compare(LocalDateTime o1, LocalDateTime o2) {
            int time1 = o1.getSecond() + 10^2 * o1.getMinute() +
                    10^4 * o1.getHour() + 10^6 *o1.getDayOfYear() + 10^9 * o1.getYear();
            int time2 = o2.getSecond() + 10^2 * o2.getMinute() +
                    10^4 * o2.getHour() + 10^6 *o2.getDayOfYear() + 10^9 * o2.getYear();
            return time1 - time2;
        }
    };

}
