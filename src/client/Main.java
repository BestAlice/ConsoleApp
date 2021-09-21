package client;

import appFiles.Application;
import collection_control.BadValueException;
import collection_control.CheckInput;
import collection_control.MessageObject;

import javax.swing.plaf.TableHeaderUI;
import java.io.*;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main {
    private static Socket socket;
    private static SocketChannel channel;
    private static Scanner scan = new Scanner(System.in);
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Connection connection= new Connection();
    private static boolean needConnect = true;
    private static int PORT;
    private static CommandReader commandReader =null;
    private static Thread mainThread;
    private static ExecutorService executorPool = Executors.newCachedThreadPool();
    private static Application app;

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
        app = new Application();

        mainThread = new Thread(new connectThread());
        mainThread.run();
    }

    public static class connectThread implements Runnable {
        @Override
        public void run() {
            Thread read_thread = null;
            Thread write_thread = null;
            app.viewLoginingPanel();
            while (needConnect){
                try {
                    try {
                        if (connection.getSocket() == null){
                            connection.connect("localhost", PORT);
                            socket = connection.getSocket();
                        }
                        commandReader = new CommandReader(socket, scan);
                        commandReader.setApp(app);
                        Application.setCommandReader(commandReader);
                        write_thread = new Thread(new WriteThread());
                        read_thread = new Thread(new ReadThread());
                        read_thread.setDaemon(true);

                        executorPool.submit(write_thread);
                        executorPool.submit(read_thread);

                        MessageObject test = new MessageObject();
                        test.setCommand("test");
                        while (commandReader.isReading()){
                            //commandReader.messager.sendMessage(test);
                            //Thread.sleep(5000);
                        }

                    } finally {
                        socket.close();
                        executorPool.shutdown();
                        break;
                    }

                } catch (IOException e) {
                    if (commandReader.isReading()) {
                        System.out.println("Соединение разорвано...");
                        reconnection();
                    } else {
                        System.out.println("Успешно отстоединился от сервера...");
                        break;
                    }

                } catch (NullPointerException e) {
                    System.out.println("Соединение не найдено...");
                    reconnection();
                }
        }System.out.println("Завершение работы приложения");
    }}

    public static class WriteThread implements Runnable{

        @Override
        public void run() {
            while (commandReader.isReading()){
                try {
                    String command = "";
                    if (commandReader.isAuthorized()){
                        command = scan.nextLine();
                        commandReader.writeCommand(command);
                    } else {
                        System.out.println("Необходимо авторизоваться(sing_in) или зарегестрироваться(sing_up)");
                        command = scan.nextLine();
                        commandReader.Authorization(command);
                    }
                    Thread.sleep(10);
                } catch (IOException | InterruptedException e) {
                    //reconnection();
                    break;
                }

            }
        }
    }

    public static class ReadThread implements Runnable{

        @Override
        public void run() {
            while (commandReader.isReading()){
                try {
                    commandReader.readAnswer();
                } catch (IOException | InterruptedException e) {
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
                break;
            }
        }
    }

    Executor myExecutor = (runnable) -> {
        Thread thread = new Thread(runnable);
        executorPool.submit(thread);
    };

    public static void setPanel(String panel) {
        if (panel.equals("login")) {
            app.viewLoginingPanel();
        } else {
            app.viewMainPanel();
        }
    }


}
