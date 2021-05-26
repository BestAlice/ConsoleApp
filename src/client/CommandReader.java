package client;

import collection_control.BadValueException;
import collection_control.CheckInput;
import collection_control.MessageObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandReader {
    private Socket socket;
    private ClientMessager messager;
    private ClientMessageGeneration messageGeneration;
    private  MessageObject message;
    private Scanner scan;
    private MessageObject answer = null;
    private static boolean readScript = false;
    private boolean reading = true;
    private static ArrayList<Long> usingId =null;
    private static boolean authorized = false;

    public CommandReader(Socket socket, Scanner scan) {
        this.socket = socket;
        this.scan = scan;
        this.messageGeneration = new ClientMessageGeneration(scan);
        this.messager = new ClientMessager(socket);
    }

    public static boolean isAuthorized() {
        return authorized;
    }

    public static void setAuthorized(boolean authorized) {
        CommandReader.authorized = authorized;
    }

    public boolean isReading(){
        return reading;
    }

    public boolean readCommand(String command) throws IOException {

        command = command.trim();

        if (command==null) {return false;}
        String[] arrLine = command.split(" ");
        String mainCommand = arrLine[0].trim();

        getUsingId();
        messageGeneration.newMessage();
        messageGeneration.setArgumets(arrLine.clone(), usingId);

        switch (mainCommand){
            case "help": messageGeneration.help(); break;
            case "info": messageGeneration.info(); break;
            case "show": messageGeneration.show(); break;
            case "show_el":
                messageGeneration.showEl(); break;
            case "show_short": messageGeneration.showShort(); break;
            case "add": messageGeneration.add(); break;
            case "update":
                messageGeneration.update(); break;
            case "remove_by_id":
                messageGeneration.remove_by_id(); break;
            case "clear": messageGeneration.clear(); break;
            case "execute_script":
                try {
                    executeScript(arrLine[1].trim());
                    return true;
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Не введено имя читаемого файла");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }break;
            case "exit":
                try {
                    messageGeneration.exit();
                    messager.sendMessage(messageGeneration.getMessage());
                }catch (NullPointerException e) {}
                reading = false;
                return true;
            case "remove_first": messageGeneration.remove_first(); break;
            case "add_if_max": messageGeneration.add_if_max(); break;
            case "add_if_min": messageGeneration.add_if_min(); break;
            case "remove_any_by_personal_qualities_maximum":
                messageGeneration.remove_any_by_personal_qualities_maximum(); break;
            case "min_by_creation_date": messageGeneration.min_by_creation_date(); break;
            case "count_by_difficulty": messageGeneration.count_by_difficulty(); break;
            default: System.out.printf("Команды %s не существует\n", mainCommand); return true;
        }
        message = messageGeneration.getMessage();
        if (message.getReady()) {
            if (messager.sendMessage(messageGeneration.getMessage())) {
                answer = messager.getAnswer();
                for (String message : answer.getMessages()) {
                    System.out.println(message.trim() );
                }
                return true;
            }
        } else {
            System.out.println("Сообщение сформированно некорректно");
            return false;
        }
        return true;
    }

    public boolean Authorization(String command) throws IOException {
        try{
            command = CheckInput.checkString(command.trim());
        } catch (BadValueException e) {
            System.out.println("Проблемка ввода");
            return false;
        }


        if (command==null) {return false;}
        String[] arrLine = command.split(" ");
        String mainCommand = arrLine[0].trim();

        messageGeneration.newMessage();
        messageGeneration.setArgumets(arrLine.clone(), usingId);
        switch (mainCommand){
            case "sing_in" : messageGeneration.sing_in();break;
            case "sing_up" : messageGeneration.sing_up(); break;
            default: System.out.println("Введена не верная команда. Попробуйте sing_in или sing_up");
        }
        message = messageGeneration.getMessage();
        if (message.getReady()) {
            if (messager.sendMessage(messageGeneration.getMessage())) {
                answer = messager.getAnswer();
                String line = answer.getMessages().get(0);
                switch (line) {
                    case "permission": {setAuthorized(true);
                        messageGeneration.setLogin(answer.getLogin());
                        messageGeneration.setPassword(answer.getPassword());
                        System.out.println(answer.getMessages().get(1));break;}
                    case "rejection" : System.out.println(answer.getMessages().get(1)); break;
                    default: System.out.println("Ошибка в полученном сообщении");
                }
                return true;
            }
        } else {
            System.out.println("Сообщение сформированно некорректно");
            return false;
        }
        return true;
    }

    public void getUsingId() throws  IOException{
        messageGeneration.newMessage();
        messageGeneration.getUsingId();
        messager.sendMessage(messageGeneration.getMessage());
        answer = messager.getAnswer();
        if (answer != null) {
            usingId = new ArrayList<>();
            for (String message : answer.getMessages()) {
                usingId.add(Long.parseLong(message));
            }
        }
    }

    public boolean executeScript(String fileName) throws  IOException, InterruptedException{
        File file = new File(fileName);
        if (!file.canRead()) {throw new IOException("Файл не может быть прочитан");}
        FileInputStream stream = new FileInputStream(file);
        readScript = true;
        Scanner scriptScan = new Scanner(stream);
        CommandReader commandReader = new CommandReader(socket, scriptScan);
        while (commandReader.isReading()){
            try {
                Thread.sleep(1000);
                String new_command = scriptScan.nextLine();
                System.out.println(new_command);
                commandReader.readCommand(new_command);
            } catch (NoSuchElementException e) {
                System.out.println("Чтение скрипта завершено");
                break;
            }
        }
        readScript = false;
        return true;
    }
}
