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
    private  static CommandReader commandReader =null;
    private static Thread mainThread;

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

        mainThread = new Thread(new connectThread());
        mainThread.run();
    }

    public static class connectThread implements Runnable {
        @Override
        public void run() {
            Thread autorizate = null;
            Thread running = null;
            while (needConnect){
                try {
                    try {
                        if (connection.getSocket() == null){

                            connection.connect("localhost", PORT);
                            socket = connection.getSocket();
                        }

                        commandReader = new CommandReader(socket, scan);
                        //BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
                        autorizate = new Thread(new Authorization());
                        running = new Thread(new Run());
                        autorizate.run();
                        System.out.println("Введите команду");
                        running.run();

                        while (commandReader.isReading()){
                            if (!socket.isConnected()) {
                                throw new IOException();
                            }
                        }
                        break;
                    } finally {
                        socket.close();
                        autorizate.interrupt();
                        running.interrupt();
                    }

                } catch (IOException e) {
                    if (commandReader.isReading()) {
                        System.out.println("Соединение разорвано...");
                        reconnection();
                    } else {
                        System.out.println("Успешно отстоединился от сервера...");
                    }
                } catch (NullPointerException e) {
                    System.out.println("Соединение не найдено...");
                    reconnection();
                }
            }
            System.out.println("Завершение работы приложения");
        }
    }

    public static class Authorization implements Runnable{
        @Override
        public void run() {
            while (!commandReader.isAuthorized()) {
                try {
                    System.out.println("Необходимо авторизоваться(sing_in) или зарегестрироваться(sing_up)");
                    String command = "";
                    System.out.print("> ");
                    command = scan.nextLine();
                    commandReader.Authorization(command);
                } catch (IOException e) {
                    break;
                }

            }

        }
    }

    public static class Run implements Runnable{
        @Override
        public void run() {
            while (commandReader.isReading()){
                try {
                    if (commandReader.isAuthorized()){
                        String command = "";
                        System.out.print("> ");
                        command = scan.nextLine();
                        commandReader.readCommand(command);
                    }
                } catch (IOException e) {
                    break;
                }

            }
        }
    }



    public static void reconnection(){
        boolean needReconnect = true;
        connection.setSocket(null);
        while (needReconnect){
            if (connection.reconnect()) {
                socket = connection.getSocket();
                //channel = connection.getChannel();
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
