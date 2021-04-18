package server;

import collection_control.BadValueException;
import collection_control.CheckInput;
import collection_control.MessageObject;
import collection_control.ParseJson;
import labwork_class.Difficulty;
import labwork_class.LabWork;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CommandInterpreter {
    private  MessageObject message = null;
    private  MessageObject answer = null;
    private LinkedList<LabWork> LabList;
    private String fileName;
    private LocalDateTime timeInit =  LocalDateTime.now();
    private   static ArrayList<Long> usingId = LabWork.getUsingId();
    private Socket socket = null;

    public CommandInterpreter(LinkedList<LabWork> LabList, String fileName){
        this.LabList = LabList;
        this.fileName = fileName;
    }

    public void setMessage(MessageObject message){
        this.message = message;
        this.answer = new MessageObject();
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

    public MessageObject getAnswer(){
        return answer;
    }

    public void run() {
        String command = message.getCommand();
        switch (command) {
            case "help": help(); break;
            case "info": info(); break;
            case "show": show(); break;
            case "show_el": showEl(); break;
            case "show_short": showShort(); break;
            case "add": add(); break;
            case "update": update(); break;
            case "remove_by_id": remove_by_id(); break;
            case "clear": clear(); break;
            case "save": save(fileName); break;
            case "getUsingId": getUsingId(); break;
            case "exit": exit(); break;
            case "remove_first": remove_first(); break;
            case "add_if_max": add_if_max(); break;
            case "add_if_min": add_if_min(); break;
            case "remove_any_by_personal_qualities_maximum": remove_any_by_personal_qualities_maximum(); break;
            case "min_by_creation_date": min_by_creation_date(); break;
            case "count_by_difficulty": count_by_difficulty(); break;
            default: wrong_command(); ;
        }
    }

    private void wrong_command() {
        System.out.printf("Получена ошибочная команда %s\n", message.getCommand());
        answer.addMessage(String.format("Команды %s не существует\n", message.getCommand()));
    }

    private void exit() {
        try{
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getUsingId() {
        for (Long id: LabWork.getUsingId()){
            answer.addMessage(String.valueOf(id));
        }
    }



    public void help() {
        try {
            File readme = new File("./README.txt"); //readme = new File("ConsoleApp.jar/README.txt"); getClass().getResource("/README.txt").getPath()
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readme), "UTF-8"));
            for (String line; (line = br.readLine()) != null; ) {
                answer.addMessage(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            answer.addMessage("Файл README не найден");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            answer.addMessage(e.getMessage());
            System.out.println(e.getMessage());
        }
    }


    public void info(){
        answer.addMessage("Тип коллекции:\tLinkedList");
        answer.addMessage("Содержит объеты класса:\tLabWork");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss dd MM YYYY");
        answer.addMessage("Время иницилизации: " + timeInit.format(formatter));
        answer.addMessage(String.format("Колличество элементов: %d\n", LabList.size()));
    }

    public void show(){
        for (LabWork lab: LabList) {
            for (String line: lab.getFullInfo()) {
                answer.addMessage(line);
            }
            answer.addMessage("");
        }
    }

    public void showShort(){
        for (LabWork lab: LabList) {
            answer.addMessage(String.format("%d: %s\n", lab.getId(), lab.getName()));
        }
    }

    public void showEl() {
        Long id = message.getId();
        LabWork lab = this.findById(id);
        for (String line: lab.getFullInfo()) {
            answer.addMessage(line);
        }
    }

    public void add(){
        LabWork newLab = message.getLaba();
        LabList.add(newLab);
        sort();
        answer.addMessage("Лабораторная учпешно добавлена");
    }

    public void update() {
        try {
            Long id = message.getId();
            LabWork updateLab = this.findById(id);
            updateLab.update(message.getLaba());
            sort();
        } catch (ArrayIndexOutOfBoundsException e) {
            answer.addMessage("Ошибка поиска");
        }
    }

    public void remove_by_id (){
        try {
            Long id = message.getId();
            for (LabWork element: LabList) {
                if (element.getId().equals(id)) {
                    remove(element);
                    break;
                }
            }
            sort();
            answer.addMessage("Удаление завершено");
        }catch (ArrayIndexOutOfBoundsException e) {
            answer.addMessage("Не введено id");
        }
    }

    public void clear(){
        LabList.clear();
        answer.addMessage("Отчистка завершена");
    }

    public void save(String nameJson){
        String json = ParseJson.parseToJson(LabList);
        File file = new File(System.getenv(nameJson));
        try {
            PrintWriter writer = new PrintWriter(file);

            writer.print(json);
            writer.close();
            answer.addMessage("Сохранение прошло успешно");
        } catch (FileNotFoundException e) {
            answer.addMessage(e.getMessage());
        }
    }

    public void remove_first(){
        try{
            LabList.removeFirst();
            answer.addMessage("Удаление завершено");
        } catch (NoSuchElementException e) {
            answer.addMessage("Коллекция пуста");
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
        LabWork newLab = message.getLaba();
        if (newLab.compareTo(maxElement) > 0) {
            newLab.newLab();
            LabList.add(newLab);
            answer.addMessage("Новый элемент добавлен");
        } else {
            answer.addMessage("Новый элемен не удовлетворяет условию добавления");
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
        LabWork newLab = message.getLaba();
        if (newLab.compareTo(minElement) < 0) {
            newLab.newLab();
            LabList.add(newLab);
            answer.addMessage("Новый элемент добавлен");
        } else {
            answer.addMessage("Новый элемен не удовлетворяет условию добавления");
        }
    }

    public void remove_any_by_personal_qualities_maximum (){
        try {
            Long var = message.getPersonalQualitiesMaximum();
            boolean delited = false;
            for (LabWork lab : LabList) {
                if (lab.getPersonalQualitiesMaximum().equals(var)) {
                    answer.addMessage(String.format("Элемент %d подходит\n", lab.getId()));
                    delited = true;
                    remove(lab);
                    break;
                }
            }
            if (!delited) {
                answer.addMessage("Не нашлось подходящих элементов");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            answer.addMessage("Не введено PersonalQualitiesMaximum для сравнения");
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
            for (String line: minTime.getFullInfo()) {
                answer.addMessage(line);
            };
        } catch (NoSuchElementException e) {
            answer.addMessage("Коллекция пуста");
        }
    }

    public void count_by_difficulty(){
        try{
            Difficulty dif = message.getDif();
            int counter = 0;
            for (LabWork lab : LabList) {
                if(lab.getDifficulty() ==dif) {counter++;}
            }
            answer.addMessage("Лабораторных уровня сложности " + dif + " : " + counter);
        }catch (ArrayIndexOutOfBoundsException e) {
            answer.addMessage("Не введено Difficulty для сравнения");
        } catch (NoSuchElementException e){
            answer.addMessage("Коллекция пуста");
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
                    10^4 * o1.getHour() + 10^6 *o1.getDayOfYear() + 10^10 * o1.getYear();
            int time2 = o2.getSecond() + 10^2 * o2.getMinute() +
                    10^4 * o2.getHour() + 10^6 *o2.getDayOfYear() + 10^10 * o2.getYear();
            return time1 - time2;
        }
    };

}
