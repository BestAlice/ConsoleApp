package client;

import collection_control.BadValueException;
import collection_control.CheckInput;
import collection_control.RequestObject;
import labwork_class.Difficulty;
import labwork_class.LabWork;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class BuildRequest {
    private String[] commandLine;
    private CheckInput check = new CheckInput();
    private LocalDateTime timeInit =  LocalDateTime.now();
    private Scanner scan;
    private RequestObject request = null;

    public BuildRequest (String[] commandLine, Scanner scan){
        this.commandLine = commandLine;
        this.scan = scan;
        request = new RequestObject();
    }

    public void help() {
        request.setCommand("help");
        request.setReady();
    }


    public void info(){
        request.setCommand("info");
        request.setReady();
    }

    public void show(){
        request.setCommand("show");
        request.setReady();
    }

    public void showShort(){
        request.setCommand("show_short");
        request.setReady();
    }

    public void showEl() {
        try {
            Long id = check.checkId(commandLine[1]);
            request.setCommand("show_short");
            request.setId(id);
            request.setReady();
        } catch (BadValueException e) {
            e.message("input");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void add(){
        LabWork newLab = new LabWork().newLab().create(scan);
        request.setCommand("add");
        request.setLaba(newLab);
        request.setReady();
    }

    public void update() {
        try {
            Long id = check.checkId(commandLine[1]);
            LabWork updateLab = new LabWork().create(scan);
            request.setCommand("update");
            request.setId(id);
            request.setLaba(updateLab);
            request.setReady();
        } catch (BadValueException e) {
            e.message("input");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void remove_by_id (){
        try {
            Long id = check.checkId(commandLine[1]);
            request.setCommand("remove_by_id");
            request.setId(id);
            request.setReady();
        } catch (BadValueException e) {
            e.message("input");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено id");
        }
    }

    public void clear(){
        request.setCommand("clear");
        request.setReady();
    }

    public void remove_first(){
        request.setCommand("remove_first");
        request.setReady();
    }

    public void add_if_max(){
        request.setCommand("add_if_max");
        LabWork laba = new LabWork().newLab().create(scan);
        request.setLaba(laba);
        request.setReady();
    }

    public void add_if_min() {
        request.setCommand("add_if_min");
        LabWork laba = new LabWork().newLab().create(scan);
        request.setLaba(laba);
        request.setReady();
    }

    public void remove_any_by_personal_qualities_maximum (){
        try {
            Long var = check.checkLong(commandLine[1], 0L, Long.MAX_VALUE, true);
            request.setCommand("remove_any_by_personal_qualities_maximum");
            request.setPersonalQualitiesMaximum(var);
            request.setReady();
        } catch (BadValueException e) {
            e.message("input", "personalQualitiesMaximum");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено PersonalQualitiesMaximum для сравнения");
        }
    }

    public void min_by_creation_date(){
        request.setCommand("min_by_creation_date");
        request.setReady();
    }

    public void count_by_difficulty(){
        try{
            Difficulty dif = check.checkEnum(commandLine[1], Difficulty.class, true );
            request.setCommand("min_by_creation_date");
            request.setDif(dif);
            request.setReady();
        } catch (BadValueException e) {
            e.message("input", "Difficulty");
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Не введено Difficulty для сравнения");
        } catch (NoSuchElementException e){
            System.out.println("Коллекция пуста");
        }
    }

    public void exit() {
        request.setCommand("exit");
        request.setReady();
    }

    public RequestObject getRequest() {
        return request;
    }
}
