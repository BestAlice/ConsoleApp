package server;

import collection_control.*;
import labwork_class.LabWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.exit;

public class ServerProcess {
    private static ServerSocket serverSocket;
    private static Selector selector;
    private static int PORT;
    private static int BUFFER_SIZE = 4096;
    private static LinkedList<LabWork> LabList;
    private static ByteBuffer sharedBuffer;
    private static Socket clientSocket = null;
    private static SocketChannel clientSocketChannel = null;
    private static ServerSocketChannel serverSocketChannel;
    private static MessageObject message = null;
    private static MessageObject answer = null;
    private static CommandInterpreter interpreter;
    private static Scanner scan = new Scanner(System.in);

    public ServerProcess(int port, String fileName){

        PORT = port;

        try {
            System.out.println("Читаю Json...");
            LabList = ParseJson.parseFromJson(fileName);
            System.out.println("Чтение прошло успешно");
        } catch (NullPointerException e){
            System.out.println("Файл не явялетя Json-ом");
            exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            exit(0);
        }

        sharedBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        interpreter = new CommandInterpreter(LabList, fileName);


        selector = null;
        serverSocket = null;

    }

    public boolean run(){
        System.out.println("Запускаю сервер...");
        try {
            serverSocketChannel =
                    ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocket = serverSocketChannel.socket();
            InetSocketAddress inetSocketAddress =
                    new InetSocketAddress(PORT);
            System.out.println(inetSocketAddress.getHostName());
            serverSocket.bind(inetSocketAddress);
            selector = Selector.open();
            serverSocketChannel.register(
                    selector, SelectionKey.OP_ACCEPT);
            System.out.println("Сервер запущен...");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("Невозможно настроить среду для запуска сервера");
            exit(-1);
        }

        BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {

                if (consoleIn.ready()) {
                    if (scan.hasNext()) {
                        String command = scan.nextLine();
                        if (command.equals("exit")) {
                            if (interpreter.save()) {
                                selector.close();
                                serverSocket.close();
                                return true;
                            }
                        }
                    }
                }

                if (selector.selectNow() != 0) {
                    /*
                    int count = selector.select();
                    // нечего обрабатывать
                    if (count == 0) {
                        continue;
                    }

                     */


                    Set keySet = selector.selectedKeys();
                    Iterator itor = keySet.iterator();
                    SelectionKey selectionKey;

                    while (itor.hasNext()) {
                        selectionKey =
                                (SelectionKey) itor.next();
                        itor.remove();

                        try {
                            if (selectionKey.isAcceptable()) {
                                accept(selectionKey);
                            }

                            if (selectionKey.isReadable()) {
                                read(selectionKey);
                            }


                        } catch (IOException e) {
                            selectionKey.cancel();
                            System.out.println(e.getMessage());
                        } catch (BufferUnderflowException e) {
                            System.out.println("Непредвниденный разрыв соединения");
                            selectionKey.cancel();
                        }
                    }
                }


            }
        } catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
                serverSocketChannel.close();
            } catch (IOException e) {
                System.out.println("Сервер уже прекратил работу");
            }
        }
        return true;
    }



    public static void accept(SelectionKey selectionKey) throws IOException{
        clientSocket = null;
        clientSocketChannel = null;
        System.out.println("Найдено новое подкючение");
        try {
            clientSocket = serverSocket.accept();
            System.out.println
                    ("Connection from: " + clientSocket);
            clientSocketChannel = clientSocket.getChannel();
        } catch (IOException e) {
            System.err.println("Невозможно усановить соединение");
            e.printStackTrace();
            selectionKey.cancel();
        }
        if (clientSocketChannel != null) {
            try {
                System.out.println
                        ("Читаем нового пользоваетелся");
                clientSocketChannel.configureBlocking(false);
                clientSocketChannel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                System.err.println("Невозможно усановить соединение");
                e.printStackTrace();
                selectionKey.cancel();
            }
        }
    }



    public static void read(SelectionKey selectionKey) throws IOException{
        clientSocket = null;
        clientSocketChannel = null;
        message = null;
        answer = null;
        //sharedBuffer.clear();

        System.out.println("Reading channel...");
        clientSocketChannel =
                (SocketChannel) selectionKey.channel();
        if (!clientSocketChannel.isConnected()) {
            clientSocketChannel.close();
        }
        clientSocket = clientSocketChannel.socket();
        //------------------------------------------------------------------------
        int bytes = -1;
        ByteBuffer new_buffer = ByteBuffer.allocate(BUFFER_SIZE);
        bytes = clientSocketChannel.read(sharedBuffer);
        sharedBuffer.flip();
        new_buffer.put(sharedBuffer.duplicate());
        sharedBuffer.clear();
        new_buffer.flip();
        int summaryLength = new_buffer.getInt();
        if (bytes >= summaryLength+4){
            try {
                byte[] clientObjectData = new byte[summaryLength];
                new_buffer.position(4);
                new_buffer.get(clientObjectData, 0, summaryLength);
                message = (MessageObject) Serializing.deserializeObject(clientObjectData);
            } catch (ClassNotFoundException e){
                System.out.println("Ошибка распаковки пакета...");
                System.out.println(e.getMessage());
            }
            if (message != null) {
                System.out.printf("Пользователь запросил выполнение команды %s... \n", message.getCommand());
                interpreter.setMessage(message);
                interpreter.setSocket(clientSocket);
                interpreter.setSelectionKey(selectionKey);
                interpreter.run();
                interpreter.setSocket(null);
                interpreter.setSelectionKey(null);
                System.out.println("Команда исполнена...");
                answer = interpreter.getAnswer();
            }
            if (answer != null) {
                System.out.println("Отправляю ответ...");
                //ByteBuffer answer_buffer = ByteBuffer.allocate(BUFFER_SIZE);

                byte[] ans = Serializing.serializeObject(answer);

                ByteBuffer answerDataBuffer = ByteBuffer.allocate(ans.length+4);
                answerDataBuffer.putInt(ans.length);
                answerDataBuffer.put(ans);
                answerDataBuffer.flip();
                clientSocketChannel.write(answerDataBuffer);
                System.out.println("Ответ отправлен");
            }
        }
        System.out.println("Next...");
    }


}
