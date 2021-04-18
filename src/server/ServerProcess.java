package server;

import collection_control.*;
import labwork_class.LabWork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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

public class ServerProcess {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static ServerSocket serverSocket;
    private static Selector selector;
    private static int PORT;
    private static int BUFFER_SIZE = 4096;
    private static LinkedList<LabWork> LabList;
    private static ByteBuffer sharedBuffer;
    private static Socket socket = null;
    private static SocketChannel channel = null;
    private static ServerSocketChannel serverSocketChannel;
    private static MessageObject message = null;
    private static MessageObject answer = null;
    private static CommandInterpreter interpreter;

    public ServerProcess(int port, String fileName){

        PORT = port;

        try {
            LabList = ParseJson.parseFromJson(fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        sharedBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        interpreter = new CommandInterpreter(LabList, fileName);


        selector = null;
        serverSocket = null;

    }

    public void run(){
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("Unable to setup environment");
            System.exit(-1);
        }
        try {
            while (true) {
                int count = selector.select();
                // нечего обрабатывать
                if (count == 0) {
                    continue;
                }

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
                        System.out.println("Непредниденный разрыв соединения");
                        selectionKey.cancel();
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
    }



    public static void accept(SelectionKey selectionKey) {
        Socket socket = null;
        SocketChannel channel = null;
        System.out.println("Got acceptable key");
        try {
            socket = serverSocket.accept();
            System.out.println
                    ("Connection from: " + socket);
            channel = socket.getChannel();
        } catch (IOException e) {
            System.err.println("Unable to accept channel");
            e.printStackTrace();
            selectionKey.cancel();
        }
        if (channel != null) {
            try {
                System.out.println
                        ("Watch for something to read");
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_READ);
            } catch (IOException e) {
                System.err.println("Unable to use channel");
                e.printStackTrace();
                selectionKey.cancel();
            }
        }
    }



    public static void read(SelectionKey selectionKey) throws IOException{
        socket = null;
        channel = null;
        message = null;
        answer = null;
        sharedBuffer.clear();

        System.out.println("Reading channel...");
        SocketChannel socketChannel =
                (SocketChannel) selectionKey.channel();
        //------------------------------------------------------------------------
        int bytes = -1;
        ByteBuffer new_buffer = ByteBuffer.allocate(BUFFER_SIZE);
        bytes = socketChannel.read(sharedBuffer);
        sharedBuffer.flip();
        new_buffer.put(sharedBuffer.duplicate());
        sharedBuffer.clear();
        new_buffer.flip();
        int summaryLength = new_buffer.getInt();
        if (bytes >= 4+summaryLength){
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
                interpreter.run();
                System.out.println("Команда исполнена. Отправляю ответ...");
                answer = interpreter.getAnswer();
            }
            if (answer != null) {

                //ByteBuffer answer_buffer = ByteBuffer.allocate(BUFFER_SIZE);

                byte[] ans = Serializing.serializeObject(answer);

                ByteBuffer answerDataBuffer = ByteBuffer.allocate(ans.length+4);
                answerDataBuffer.putInt(ans.length);
                answerDataBuffer.put(ans);
                answerDataBuffer.flip();
                socketChannel.write(answerDataBuffer);
                System.out.println("Ответ отправлен");
            }
        }
        System.out.println("Next...");
    }


}
