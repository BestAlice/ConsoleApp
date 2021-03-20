package collection_control;

import labwork_class.LabWork;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ComandReader {
    private LinkedList<LabWork> LabList;
    private static LinkedList<String> executed_files = new LinkedList<>();
    private String json;
    private RequestObject request;

    public ComandReader(LinkedList labList,boolean execute, String fileName, String json){
        LabList = labList;
        this.json = json;
        if (!execute) {
            executed_files.clear();
            reader(System.in);
        } else {
            try {
                if (executed_files.contains(fileName)) {
                    System.out.println("Рекурсия при попытке исполнить execute_script");
                    executed_files.clear();
                } else {
                    File file = new File(fileName);
                    if (!file.canRead()) {throw new Exception("Файл не может быть прочитан");}
                    FileInputStream stream = new FileInputStream(file);
                    executed_files.addLast(fileName);
                    reader(stream);
                    executed_files.pollLast();
                }
            } catch (FileNotFoundException e) {
                System.out.printf("Файла %s не существует\n", fileName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


    private void reader(InputStream input){
        Scanner scan = new Scanner(input);
        String command;
        boolean go = true;
        while (go) {
            try{
                command = scan.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Чтение скрипта завершено");
                go = false;
                break;
            }
            if (command==null) {continue;}
            String[] arrLine = command.split(" ");
            String mainCommand = arrLine[0].trim();
            ConsoleCommands console = new ConsoleCommands(LabList, arrLine, scan);
            switch (mainCommand){
                case "help": console.help(); break;
                case "info": console.info(); break;
                case "show": console.show(); break;
                case "show_el": console.showEl(); break;
                case "show_short": console.showShort(); break;
                case "add": console.add(); break;
                case "update": console.update(); break;
                case "remove_by_id": console.remove_by_id(); break;
                case "clear": console.clear(); break;
                case "save": console.save(json); break;
                case "execute_script":
                    try {
                        new ComandReader(LabList, true, arrLine[1]+".txt", json);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Не введено имя читаемого файла");
                    } break;
                case "exit": go = false; break;
                case "remove_first": console.remove_first(); break;
                case "add_if_max": console.add_if_max(); break;
                case "add_if_min": console.add_if_min(); break;
                case "remove_any_by_personal_qualities_maximum": console.remove_any_by_personal_qualities_maximum(); break;
                case "min_by_creation_date": console.min_by_creation_date(); break;
                case "count_by_difficulty": console.count_by_difficulty(); break;
                default: System.out.printf("Команды %s не существует\n", mainCommand);
            }
        }
        scan.close();
    }
}
