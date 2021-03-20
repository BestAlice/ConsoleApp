package client;

import collection_control.ComandReader;
import collection_control.RequestObject;


import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class RequestControll {
    private static boolean executed = false;
    private RequestObject request;
    private Socket socket;
    private static ObjectInputStream in;
    private static ObjectOutputStream out;

    public RequestControll(Socket socket, ObjectInputStream in, ObjectOutputStream out){
        this.socket = socket;
        this.in = in;
        this.out = out;
        executed = false;
    }

    public void start() throws IOException{
        commandReader(System.in);
    }

    public void readExecute(String fileName){
        try {
            if (executed) {
                System.out.println("Запрещено исполнение execute_script при чтении скрипта");
            } else {
                File file = new File(fileName);
                if (!file.canRead()) {throw new Exception("Файл не может быть прочитан");}
                FileInputStream stream = new FileInputStream(file);
                executed = true;
                commandReader(stream);
                executed = false;
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Файла %s не существует\n", fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private void commandReader(InputStream input) throws IOException{
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
            command=new String(command.getBytes(),"Cp866");
            String[] arrLine = command.split(" ");
            String mainCommand = arrLine[0].trim();
            BuildRequest builder = new BuildRequest(arrLine, scan);
            switch (mainCommand){
                case "help": builder.help(); break;
                case "info": builder.info(); break;
                case "show": builder.show(); break;
                case "show_el": builder.showEl(); break;
                case "show_short": builder.showShort(); break;
                case "add": builder.add(); break;
                case "update": builder.update(); break;
                case "remove_by_id": builder.remove_by_id(); break;
                case "clear": builder.clear(); break;
                case "execute_script":
                    try {
                        readExecute(arrLine[1]+".txt");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Не введено имя читаемого файла");
                    } break;
                case "exit": go = false; builder.exit(); break;
                case "remove_first": builder.remove_first(); break;
                case "add_if_max": builder.add_if_max(); break;
                case "add_if_min": builder.add_if_min(); break;
                case "remove_any_by_personal_qualities_maximum": builder.remove_any_by_personal_qualities_maximum(); break;
                case "min_by_creation_date": builder.min_by_creation_date(); break;
                case "count_by_difficulty": builder.count_by_difficulty(); break;
                default: System.out.printf("Команды %s не существует\n", mainCommand);
            }
            RequestObject request = builder.getRequest();
            if (request.getReady()){
                sendRequest(request);
                RequestObject answer = getAnswer();
                for (String massenge: answer.getMessages()) {
                    System.out.println(massenge);
                }
            } else {
                System.out.println("Ошибка при создании запроса");
            }
        }
        scan.close();
    }

    public static RequestObject getAnswer() {
        try{
            RequestObject answer = (RequestObject) in.readObject();
            return answer;
        } catch (IOException e) {
            System.out.println("Ошибка передачи данных");
        } catch (ClassNotFoundException e) {
            System.out.println("Передан ошибочный класс данных");
        }
        return new RequestObject();
    }

    public static void sendRequest(RequestObject request){
        try{
            out.writeObject(request);
            out.flush();
        } catch (IOException e) {
            System.out.println("Ошибка передачи данных");
        }
    }
}
