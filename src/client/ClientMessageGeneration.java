package client;

import collection_control.BadValueException;
import collection_control.CheckInput;
import collection_control.MessageObject;
import labwork_class.Difficulty;
import labwork_class.LabWork;

import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientMessageGeneration {
    private static String login;
    private static String password;
    private String[] argumets;
    private CheckInput checker = new CheckInput();
    private LocalDateTime timeInit =  LocalDateTime.now();
    private Scanner scan;
    private MessageObject message = null;
    ArrayList<Long> usingId;


    public ClientMessageGeneration(Scanner scan){
        this.scan = scan;
    }

    public void newMessage(){
        message = new MessageObject();
        message.setLogin(login);
        message.setPassword(password);
        argumets = null;
    }

    public void setArgumets(String[] argumets, ArrayList<Long> usingId) {
        this.argumets = argumets;
        this.usingId = usingId;
        if (usingId != null){
            for (Long id: usingId) {
                if (!LabWork.getUsingId().contains(id)){
                    LabWork.addId(id);
                }
            }
        }

    }

    public void getUsingId () {
        message.setCommand("getUsingId");
        message.setReady();
    }

    public void help() {
        message.setCommand("help");
        message.setReady();
    }


    public void info(){
        message.setCommand("info");
        message.setReady();
    }

    public void show(){
        message.setCommand("show");
        message.setReady();
    }

    public void showShort(){
        message.setCommand("show_short");
        message.setReady();
    }

    public void showEl() {
        try {
            Long id = checker.checkId(argumets[1], usingId);
            message.setCommand("show_el");
            message.setId(id);
            message.setReady();
        } catch (BadValueException e) {
            e.message("input");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void add(){
        LabWork newLab = new LabWork().create(scan);
        message.setCommand("add");
        message.setLaba(newLab);
        message.setReady();
    }

    public void update() {
        try {
            Long id = checker.checkId(argumets[1], usingId);
            LabWork updateLab = new LabWork().create(scan);
            message.setCommand("update");
            message.setId(id);
            message.setLaba(updateLab);
            message.setReady();
        } catch (BadValueException e) {
            e.message("input");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void remove_by_id (){
        try {
            Long id = checker.checkId(argumets[1], usingId);
            message.setCommand("remove_by_id");
            message.setId(id);
            message.setReady();
        } catch (BadValueException e) {
            e.message("input");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void clear(){
        message.setCommand("clear");
        message.setReady();
    }

    public void remove_first(){
        message.setCommand("remove_first");
        message.setReady();

    }

    public void add_if_max(){
        message.setCommand("add_if_max");
        LabWork laba = new LabWork().create(scan);
        message.setLaba(laba);
        message.setReady();
    }

    public void add_if_min() {
        message.setCommand("add_if_min");
        LabWork laba = new LabWork().create(scan);
        message.setLaba(laba);
        message.setReady();

    }

    public void remove_any_by_personal_qualities_maximum (){
        try {
            Long var = checker.checkLong(argumets[1], 0L, Long.MAX_VALUE, true);
            message.setCommand("remove_any_by_personal_qualities_maximum");
            message.setPersonalQualitiesMaximum(var);
            message.setReady();
        } catch (BadValueException e) {
            e.message("input", "personalQualitiesMaximum");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено PersonalQualitiesMaximum для сравнения");
        }
    }

    public void min_by_creation_date(){
        message.setCommand("min_by_creation_date");
        message.setReady();
    }

    public void count_by_difficulty(){
        try{
            Difficulty dif = null;
            if (argumets.length > 1) {
                dif = checker.checkEnum(argumets[1], Difficulty.class, true );
                message.setCommand("count_by_difficulty");
                message.setDif(dif);
                message.setReady();
            } else {
                System.out.println("Ошибка: отсутствует сложность");
            }

        } catch (BadValueException e) {
            e.message("input", "Difficulty");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено Difficulty для сравнения");
        } catch (NoSuchElementException e){
            System.out.println("Коллекция пуста");
        }
    }

    public void exit() {
        message.setCommand("exit");
        message.setReady();
    }

    public MessageObject getMessage() {
        return message;
    }

    public void sing_in() {
        System.out.println("Введите логин:");
        System.out.print("> ");
        String login = scan.nextLine().replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", "").replaceAll(" ", "_");
        System.out.println("Введите пароль:");
        System.out.print("> ");
        String password = scan.nextLine().replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", "").replaceAll(" ", "_");
        password = md5Custom(password);
        message.setCommand("sing_in");
        message.setLogin(login);
        message.setPassword(password);
        message.setReady();
    }

    public void sing_up() {
        System.out.println("Введите новый логин:");
        System.out.print("> ");
        String login = scan.nextLine().replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", "").trim().replaceAll(" ", "_");
        System.out.println("Введите новый пароль:");
        System.out.print("> ");
        String password = scan.nextLine().replaceAll("[^\\da-zA-Zа-яёА-ЯЁ ]", "").replaceAll(" ", "_");
        password = md5Custom(password);
        message.setCommand("sing_up");
        message.setLogin(login);
        message.setPassword(password);
        message.setReady();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String md5Custom(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            st = "soul"+st;
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);
        return md5Hex;
    }
}