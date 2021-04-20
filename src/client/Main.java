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
        //��������� �������� �������� �� ��, ��� ��� - ����
        if (args.length < 1) {
            System.out.println("������ ����������. ��������� ������ ���� �����������.");
        }
        try {
            PORT = CheckInput.checkInt(args[0], 1, 9999);
        } catch (BadValueException e) {
            System.out.println("������ �����. " + e.getMessage());
            System.exit(0);
        }

        while (needConnect){
            CommandReader commandReader =null;
            try {
                try {
                    if (connection.getSocket() == null){

                        connection.connect("localhost", PORT);
                        //channel = connection.getChannel();
                        socket = connection.getSocket();
                    }

                    commandReader = new CommandReader(socket, scan);
                    BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

                    String command = "";
                    while (commandReader.isReading()){
                        System.out.print("> ");

                        while (true) {
                            if (consoleIn.ready()) {
                                command = scan.nextLine();
                                break;
                            }
                            if (!socket.isConnected()) {
                                throw new IOException();
                            }
                        }
                        commandReader.readCommand(command);

                    }
                    break;
                } finally {
                    socket.close();
                }

            } catch (IOException e) {
                if (commandReader.isReading()) {
                    System.out.println("���������� ���������...");
                    reconnection();
                } else {
                    System.out.println("������� ������������� �� �������...");
                }
            } catch (NullPointerException e) {
                System.out.println("���������� �� �������...");
                reconnection();
            }
        }
        System.out.println("���������� ������ ����������");
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
                System.out.println("������� �� ����������� ������������ �����? (yes, no)");
                while (true) {
                    String command = scan.nextLine();
                    if (command.equals("yes")){
                        break;
                    } else if (command.equals("no")){
                        needConnect = false;
                        needReconnect = false;
                        break;
                    } else {
                        System.out.println("������������ ����...");
                    }

                }
            }
        }
    }
}
