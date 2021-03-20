package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Main {
    private static Socket clientSocket;
    private static Scanner scan = new Scanner(System.in);
    private static ObjectInputStream in;
    private static ObjectOutputStream out;
    private static Connection connection= new Connection();
    private static boolean needConnect = true;

    public static void main(String[] args){

        while (needConnect){
            try {
                try {
                    if (connection.getSocket() == null){
                        connection.connect("localhost", 4004);
                    }
                    clientSocket = connection.getSocket();
                    in = new ObjectInputStream(clientSocket.getInputStream());
                    out = new ObjectOutputStream(clientSocket.getOutputStream());

                    RequestControll controll = new RequestControll(clientSocket, in, out);
                    controll.start();

                    break;
                } finally {
                    clientSocket.close();
                    in.close();
                    out.close();
                }

            } catch (IOException e) {
                System.out.println("Соединение разорвано");
                reconnection();
            } catch (NullPointerException e) {
                System.out.println("Соединение не найдено");
                reconnection();
            } catch (Exception e) {
                System.err.println(e);
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
                clientSocket = connection.getSocket();
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
                        System.out.println("Некорректный ввод");
                    }

                }
            }
        }
    }
}
