package client;

import collection_control.BadValueException;
import collection_control.CheckInput;

import java.io.*;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;


public class Main {
    private static Socket socket;
    private static SocketChannel channel;
    private static Scanner scan = new Scanner(System.in);
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Connection connection= new Connection();
    private static boolean needConnect = true;
    private static int PORT;

    public static void main(String[] args){
        //Проверяем введённый аргумент на то, что оно - порт
        if (args.length < 1) {
            System.out.println("Ошибка синтаксиса. Требуется ввести порт подключения.");
        }
        try {
            PORT = CheckInput.checkInt(args[0], 1, 9999);
        } catch (BadValueException e) {
            System.out.println("Ошибка порта. " + e.getMessage());
            System.exit(0);
        }

        while (needConnect){
            try {
                try {
                    if (connection.getSocket() == null){
                        connection.connect("localhost", PORT);
                        socket = connection.getSocket();
                        channel = connection.getChannel();
                    }

                    CommandReader commandReader = new CommandReader(socket, scan);
                    while (commandReader.isReading()){
                        System.out.print("> ");
                        commandReader.readCommand(scan.nextLine());


                    }
                    break;
                } finally {
                    socket.close();
                    channel.close();
                    in.close();
                    out.close();
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Соединение разорвано...");
                reconnection();
            } catch (NullPointerException e) {
                System.out.println("Соединение не найдено...");
                reconnection();
            }

        }
        System.out.println("Завершение работы приложения");
    }

    public static void reconnection(){
        boolean needReconnect = true;
        connection.setSocket(null);
        while (needReconnect){
            boolean rec = connection.reconnect();
            if (rec) {
                socket = connection.getSocket();
                break;
            } else {
                System.out.println("Желаете ли попробовать подключиться снова? (yes, no)");
                while (true) {
                    String command = scan.nextLine();
                    if (command.equals("yes")){
                        break;
                    } else if (command.equals("no")){
                        needConnect = false;
                        needReconnect = false;
                        break;
                    } else {
                        System.out.println("Некорректный ввод...");
                    }

                }
            }
        }
    }
}
