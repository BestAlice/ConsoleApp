package client;

import collection_control.ComandReader;
import collection_control.ConsoleCommands;
import collection_control.MessageObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandReader {
    private Socket socket;
    private SocketChannel channel;
    private ClientMessager messager;
    private ClientMessageGeneration messageGeneration;
    private  MessageObject message;
    private Scanner scan;
    private MessageObject answer = null;
    private boolean readScript = false;
    private boolean reading = true;

    public CommandReader(Socket socket, Scanner scan) {
        this.socket = socket;
        this.scan = scan;
        this.messageGeneration = new ClientMessageGeneration(scan);
        this.messager = new ClientMessager(socket);
    }

    public boolean isReading(){
        return reading;
    }

    public boolean readCommand(String command) throws IOException {

        command = command.trim();

        if (command==null) {return false;}
        String[] arrLine = command.split(" ");
        String mainCommand = arrLine[0].trim();
        ArrayList<Long> usingId = getUsingId();

        messageGeneration.newMessage();
        messageGeneration.setArgumets(arrLine, usingId);
        switch (mainCommand){
            case "help": messageGeneration.help(); break;
            case "info": messageGeneration.info(); break;
            case "show": messageGeneration.show(); break;
            case "show_el": messageGeneration.showEl(); break;
            case "show_short": messageGeneration.showShort(); break;
            case "add": messageGeneration.add(); break;
            case "update": messageGeneration.update(); break;
            case "remove_by_id": messageGeneration.remove_by_id(); break;
            case "clear": messageGeneration.clear(); break;
            case "execute_script":
                try {
                    executeScript(arrLine[1].trim());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Не введено имя читаемого файла");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }break;
            case "exit":    messageGeneration.exit();
                            reading = false; break;
            case "remove_first": messageGeneration.remove_first(); break;
            case "add_if_max": messageGeneration.add_if_max(); break;
            case "add_if_min": messageGeneration.add_if_min(); break;
            case "remove_any_by_personal_qualities_maximum": messageGeneration.remove_any_by_personal_qualities_maximum(); break;
            case "min_by_creation_date": messageGeneration.min_by_creation_date(); break;
            case "count_by_difficulty": messageGeneration.count_by_difficulty(); break;
            default: System.out.printf("Команды %s не существует\n", mainCommand);
        }
        message = messageGeneration.getMessage();
        if (message.getReady()) {
            messager.sendMessage(messageGeneration.getMessage());
            answer = messager.getMessage();
            for (String message : answer.getMessages()) {
                System.out.println(message);
            }
            return true;
        } else {
            System.out.println("Сообщение не готово к отправке");
            return false;
        }
    }

    private ArrayList<Long> getUsingId() throws  IOException{
        messageGeneration.newMessage();
        messageGeneration.getUsingId();
        messager.sendMessage(messageGeneration.getMessage());
        answer = messager.getMessage();
        ArrayList<Long> usungId = new ArrayList<>();
        for (String message : answer.getMessages()) {
            usungId.add(Long.getLong(message));
        }
        return usungId;
    }

    public boolean executeScript(String fileName) throws Exception{
        File file = new File(fileName);
        if (!file.canRead()) {throw new Exception("Файл не может быть прочитан");}
        FileInputStream stream = new FileInputStream(file);
        readScript = true;
        CommandReader commandReader = new CommandReader(socket, new Scanner(stream));
        while (commandReader.isReading()){
            try {
                commandReader.readCommand(scan.nextLine());
            } catch (NoSuchElementException e) {
                System.out.println("Чтение скрипта завершено");
                break;
            }
        }
        readScript = false;
        return true;
    }
}
