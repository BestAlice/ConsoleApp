package client;

import collection_control.BadValueException;
import collection_control.CheckInput;
import collection_control.MessageObject;
import labwork_class.Difficulty;
import labwork_class.LabWork;

import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientMessageGeneration {
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
        argumets = null;
    }

    public void setArgumets(String[] argumets, ArrayList<Long> usingId) {
        this.argumets = argumets;
        this.usingId = usingId;
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
            message.setCommand("show_short");
            message.setId(id);
            message.setReady();
        } catch (BadValueException e) {
            e.message("input");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void add(){
        LabWork newLab = new LabWork().newLab().create(scan);
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
        LabWork laba = new LabWork().newLab().create(scan);
        message.setLaba(laba);
        message.setReady();
    }

    public void add_if_min() {
        message.setCommand("add_if_min");
        LabWork laba = new LabWork().newLab().create(scan);
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
            Difficulty dif = checker.checkEnum(argumets[1], Difficulty.class, true );
            message.setCommand("min_by_creation_date");
            message.setDif(dif);
            message.setReady();
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
}
