package server;

import collection_control.*;
import labwork_class.Difficulty;
import labwork_class.LabWork;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommandInterpreter {
    private  MessageObject message = null;
    private  MessageObject answer = null;
    private static List<LabWork> LabList;
    private static DataBase BD;
    private LocalDateTime timeInit =  LocalDateTime.now();
    private static ArrayList<Long> usingId = LabWork.getUsingId();
    private static ArrayList<User> UserList;
    private Socket socket = null;
    private User user;

    public CommandInterpreter(){
        this.LabList = ServerProcess.getLabList();
    }

    public static ArrayList<User> getUserList() {
        return UserList;
    }

    public static void setUserList(ArrayList<User> userList) {
        UserList = userList;
    }

    public void setUser(User user){this.user = user;}

    public static void setBD(DataBase base) {BD = base;}



    public void setMessage(MessageObject message){
        this.message = message;
        this.answer = new MessageObject();
        answer.setCommand("message");
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
            case "sing_in": sing_in(); break;
            case "sing_up": sing_up(); break;
            default: {
                if (user.haveAccess(message.getLogin(), message.getPassword())) {
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
                        case "getUsingId": getUsingId(); break;
                        case "exit": exit(); break;
                        case "remove_first": remove_first(); break;
                        case "add_if_max": add_if_max(); break;
                        case "add_if_min": add_if_min(); break;
                        case "remove_any_by_personal_qualities_maximum": remove_any_by_personal_qualities_maximum(); break;
                        case "min_by_creation_date": min_by_creation_date(); break;
                        case "count_by_difficulty": count_by_difficulty(); break;
                        default: wrong_command();
                    }
                } else {

                    answer.addMessage("��� �� ����� �����? ������ � ����� �� ��������� � ������� �������");
                    answer.getReady();
                }
            }
        }
    }

    private void sing_up() {
        user.setLogin(message.getLogin());
        user.setPassword(message.getPassword());
        answer.setCommand("autorization");
        if (BD.insertUser(user)) {
            answer.addMessage("permission");
            answer.addMessage("�������� �����������");
            answer.setLogin(user.getLogin());
            answer.setPassword(user.getPassword());
            answer.setId(user.getId());
            answer.getReady();
            user.setNeedUpdate(true);
        } else {
            user.setId(null);
            user.setLogin(null);
            user.setPassword(null);
            answer.addMessage("rejection");
            answer.addMessage("������ �����������: ������������ ��� ����������");
            answer.getReady();
        }
    }

    private void sing_in() {
        User BD_user = BD.selectUser(message.getLogin(), message.getPassword());
        answer.setCommand("autorization");
        if (BD_user == null) {
            answer.addMessage("rejection");
            answer.addMessage("������ �����: �������� ����� ��� ������");
            answer.getReady();
        } else {
            answer.addMessage("permission");
            answer.addMessage("�������� ����");
            answer.setId(BD_user.getId());
            answer.setLogin(BD_user.getLogin());
            answer.setPassword(BD_user.getPassword());
            user.setId(BD_user.getId());
            user.setLogin(BD_user.getLogin());
            user.setPassword(BD_user.getPassword());
            user.setNeedUpdate(true);
            answer.setReady();

        }
    }

    private void wrong_command() {
        System.out.printf("�������� ��������� ������� %s\n", message.getCommand());
        answer.addMessage(String.format("������� %s �� ����������\n", message.getCommand()));
    }


    private void exit() {
        try{
            user.setRunning(false);
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        answer = null;
    }

    private void getUsingId() {
        answer.setCommand("usingId");
        LabWork.getUsingId().stream().forEach(x -> answer.addMessage(String.valueOf(x)));
    }



    public void help() {
        try {
            File readme = new File("README.txt"); //readme = new File("ConsoleApp.jar/README.txt"); getClass().getResource("/README.txt").getPath()
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(readme), "UTF-8"));
            for (String line; (line = br.readLine()) != null; ) {
                answer.addMessage(line);
            }
            br.close();
        } catch (FileNotFoundException e) {
            answer.addMessage("���� README �� ������");
            System.out.println(e.getMessage());
        } catch (IOException e) {
            answer.addMessage(e.getMessage());
            System.out.println(e.getMessage());
        }
    }


    public void info(){
        answer.addMessage("��� ���������:\tLinkedList");
        answer.addMessage("�������� ������ ������:\tLabWork");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss dd.MM.YYYY");
        answer.addMessage("����� ������������: " + timeInit.format(formatter));
        answer.addMessage(String.format("����������� ���������: %d", LabList.size()));
    }

    public void show(){
        if (usingId.isEmpty()) {
            answer.addMessage("��������� �����");
        }
        LabList.stream()
                .map(x -> x.getFullInfo())
                .flatMap(ArrayList::stream)
                .forEach(x -> answer.addMessage(x));
    }

    public void showShort(){

        if (usingId.isEmpty()) {
            answer.addMessage("��������� �����");
        } else {
            LabList.stream()
                    .map(x -> String.format("%d: %s", x.getId(), x.getName()))
                    .forEach(x -> answer.addMessage(x));
        }
    }

    public void showEl() {
        try{
            Long id = message.getId();
            LabWork lab = this.findById(id);
            lab.getFullInfo().stream()
                    .forEach(x -> answer.addMessage(x));
        } catch (NullPointerException e) {
            answer.addMessage("������� �� ���������� id");
        }
    }

    public void add(){
        LabWork newLab = message.getLaba().newLab();
        newLab.findWeight();

        newLab.setUserId(user.getId());
        BD.insertLabWork(newLab);

        LabList.add(newLab);
        LabWork.addId(newLab.getId());
        sort();
        answer.addMessage("������������ ������� ���������");
        System.out.println("��� ����� ������������: " + newLab.getWeight());
        UpdatedAll();
    }

    public void update() {
        try {
            Long id = message.getLaba().getId();
            LabWork updateLab = this.findById(id);
                BD.updateLabWork(id, message.getLaba().newLab());
                updateLab.update(message.getLaba());
                updateLab.findWeight();
                sort();
                answer.addMessage("������ ������� ���������");
                UpdatedAll();

        } catch (ArrayIndexOutOfBoundsException e) {
            answer.addMessage("������ ������");
        } catch (NullPointerException e) {
            answer.addMessage("������� �� ���������� id");
            e.printStackTrace();
        }
    }

    public void remove_by_id (){
        try {
            Long id = message.getId();
            LabWork lab = LabList.stream()
                    .filter(x -> x.getId().equals(id))
                    .limit(1)
                    .findFirst().get();
                BD.deteteLabWork(lab.getId());
                remove(lab);
                sort();
                answer.addMessage("�������� ���������");
                UpdatedAll();


        }catch (ArrayIndexOutOfBoundsException e) {
            answer.addMessage("�� ������� id");
        }
    }

    public void clear(){
        boolean changes = true;
        while (changes) {
            changes=false;
            for (LabWork lab: LabList) {
                if (user.getId().equals(lab.getUserId())) {
                    changes = true;
                    BD.deteteLabWork(lab.getId());
                    remove(lab);
                    break;
                }
            }
        }
        answer.addMessage("�������� ������������� ������������ ����� ���������");
        UpdatedAll();
    }

    public void remove_first(){
        try{
            Long id = LabList.get(0).getId();
            if (user.haveAccessToLabWork(LabList.get(0))) {
                BD.deteteLabWork(id);
                LabWork.removeId(id);
                LabList.remove(0);
                answer.addMessage("�������� ���������");
                UpdatedAll();
            } else  {
                answer.addMessage("��� ������ ����������� ������� ������������");
            }
        } catch (NoSuchElementException e) {
            answer.addMessage("��������� �����");
        }
    }

    public void add_if_max(){

        LabWork maxElement = LabList.stream()
                .max(Comparator.comparing(x -> x.getWeight()))
                .get();

        LabWork newLab = message.getLaba();
        newLab.findWeight();
        if (newLab.compareTo(maxElement) > 0) {
            newLab.newLab();

            newLab.setUserId(user.getId());
            BD.insertLabWork(newLab);

            LabList.add(newLab);
            answer.addMessage("����� ������� ��������");
            UpdatedAll();
        } else {
            answer.addMessage("����� ������ �� ������������� ������� ����������");
        }
    }

    public void add_if_min() {
        LabWork minElement = LabList.stream()
                .min(Comparator.comparing(x -> x.getWeight()))
                .get();

        LabWork newLab = message.getLaba();
        newLab.findWeight();
        if (newLab.compareTo(minElement) < 0) {
            newLab.newLab();

            newLab.setUserId(user.getId());
            BD.insertLabWork(newLab);

            LabList.add(newLab);
            answer.addMessage("����� ������� ��������");
            UpdatedAll();
        } else {
            answer.addMessage("����� ������ �� ������������� ������� ����������");
        }
    }

    public void remove_any_by_personal_qualities_maximum (){
        try {
            Long var = message.getPersonalQualitiesMaximum();

            LabWork lab = LabList.stream().filter(x -> x.getPersonalQualitiesMaximum().equals(var)).limit(1).findFirst().get();
            if (lab != null){

                if (user.haveAccessToLabWork(lab)) {
                    BD.deteteLabWork(lab.getId());
                    answer.addMessage(String.format("������� %d ��� �����", lab.getId()));
                    remove(lab);
                    UpdatedAll();
                } else  {
                    answer.addMessage("��� ������ ����������� ������� ������������");
                }
            } else {
                answer.addMessage("���������� ��������� �� ���� �������");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            answer.addMessage("�� ������� PersonalQualitiesMaximum ��� ���������");
        }
    }

    public void min_by_creation_date(){
        try {
            LabWork minTime = LabList.get(0);
            for (LabWork lab : LabList) {
                if (comparatorByDate.compare(minTime.getCreationDate(), lab.getCreationDate()) > 0) {
                    minTime = lab;
                }
            }
            answer.addMessage("����� ������ ������������");
            for (String line: minTime.getFullInfo()) {
                answer.addMessage(line);
            };
        } catch (NoSuchElementException e) {
            answer.addMessage("��������� �����");
        } catch (NullPointerException e) {
            answer.addMessage("��������� �����");
        }
    }

    public void count_by_difficulty(){
        try{

            Difficulty dif = message.getDif();
            long counter = 0;
            try{
                counter = LabList.stream()
                        .filter(x -> x.getDifficulty()==dif)
                        .count();
            } catch (NullPointerException e) {
                counter = 0;
            }

            answer.addMessage("������������ ������ ��������� " + dif + " : " + counter); //
        }catch (ArrayIndexOutOfBoundsException e) {
            answer.addMessage("�� ������� Difficulty ��� ���������");
        } catch (NoSuchElementException e){
            answer.addMessage("��������� �����");
        }
    }

    public LabWork findById(Long id) {
        for (LabWork element: LabList) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        System.out.printf("Id %d �� ����������", id);
        return null;
    }

    public void sort(){
        LabList.sort(LabWork::compareTo);
    }

    public void remove (LabWork lab){
        try {
            LabWork.removeId(lab.getId());
            LabList.remove(lab);
            System.out.println("������ ������� �����");
        } catch (NoSuchElementException e) {
            System.out.println("��������� �����");
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

    public void UpdatedAll() {
        for (User user: UserList) {
            user.setNeedUpdate(true);
        }
    }

    public MessageObject updateTable() {
        MessageObject table = new MessageObject();
        table.setCommand("updateTable");
        for (LabWork lab: LabList) {
            table.addMap(lab.getMap());
        }
        return table;
    }

}
