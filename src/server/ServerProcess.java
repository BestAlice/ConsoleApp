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
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;
import static java.lang.System.in;

public class ServerProcess {
    private static ServerSocket serverSocket;
    //private static Selector selector;
    private static int PORT;
    //private static int BUFFER_SIZE = 4096;
    private static List<LabWork> LabList;
    //private static ByteBuffer sharedBuffer;
    private static Socket clientSocket = null;
    //private static SocketChannel clientSocketChannel = null;
    //private static ServerSocketChannel serverSocketChannel;
    private static MessageObject message = null;
    private static MessageObject answer = null;
    private static Scanner scan = new Scanner(System.in);
    private static ArrayList<User> UserList = new ArrayList<>();
    private static Socket socket = null;
    private static ExecutorService readerPool = Executors.newCachedThreadPool();
    private static ExecutorService commandExecutorPool = Executors.newCachedThreadPool();
    private static ExecutorService writerPool = Executors.newCachedThreadPool();
    private static ExecutorService accepterPool = Executors.newCachedThreadPool();
    //private static Thread accepterThreat;


    public ServerProcess(int port, String fileName){

        PORT = port;

        try {
            DataBase BD = new DataBase();
            LabList = Collections.synchronizedList(BD.selectLabList());
            CommandInterpreter.setBD(BD);
            for (LabWork lab: LabList) {
                LabWork.addId(lab.getId());
            }
            /*
            System.out.println("Читаю Json...");
            LabList = Collections.synchronizedList(ParseJson.parseFromJson(fileName));
            System.out.println("Чтение прошло успешно");
            CommandInterpreter.setFileName(fileName);

             */
        } catch (NullPointerException e){
            System.out.println("Файл не явялетя Json-ом");
            exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            exit(0);
        }

        serverSocket = null;

    }

    public boolean run(){
        System.out.println("Запускаю сервер...");
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен...");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("Невозможно настроить среду для запуска сервера");
            exit(-1);
        }

        //BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            try {
                deamonExecutor.execute(new consoleReader());
                acceptExecutor.execute(new Accepter());

            } catch (NullPointerException e){
                System.out.println("Кто-то схлопотал NullPointer");
                e.printStackTrace();
            }

        return true;
    }



    public class consoleReader implements Runnable{

        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                String command = scan.nextLine();
                if (command.equals("exit")) {
                    accepterPool.shutdown();
                    readerPool.shutdown();
                    commandExecutorPool.shutdown();
                    writerPool.shutdown();
                    for (User user: getUserList()) {
                        try{
                            user.getSocket().close();
                        }catch (IOException e){
                            System.out.println("Сокет пользователя "+ user.toString() + " уже закрыт");
                        }
                    }
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        System.out.println("Сокет сервера уже закрыт");
                    }

                }
            }
        }
    }


    public class Accepter implements Runnable{

        public void run() {
            try {

                    while (!Thread.currentThread().isInterrupted()) {
                        // Блокируется до возникновения нового соединения:
                        try {
                            Socket socket = serverSocket.accept();
                            System.out.println("Подключаю нового пользователя");
                            User new_user = new User(socket);
                            UserList.add(new_user); // добавить новое соединенние в список
                            System.out.println("Пользователь успешно подключён");
                            readExecutor.execute(new Reader(new_user));
                        } catch (IOException e) {
                            // Если завершится неудачей, закрывается сокет,
                            // в противном случае, нить закроет его при завершении работы:
                            System.out.println("Закрываю сокет");
                            socket.close();
                        }
                    }

            } catch (IOException e) {
                System.out.println("Ошибка в Accepter");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Закрыт Accepter");
            }
        }
        /*
        SelectionKey selectionKey;
        public Accepter(SelectionKey selectionKey){
            this.selectionKey = selectionKey;
        }
        @Override
        public void run() {
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
                    User new_user = new User(clientSocketChannel);
                } catch (IOException e) {
                    System.err.println("Невозможно усановить соединение");
                    e.printStackTrace();
                    selectionKey.cancel();
                }
            }
        }

         */
    }
/*
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

 */

    public class Reader implements Runnable  {
        User user;
        public Reader(User user){
            this.user = user;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted() & user.isRunning()) {
                try {
                    System.out.println("Читаю новые сообщения от пользователя");
                    user.readMessage();
                    System.out.println("Сообщение успешно прочитано");
                    commandExecuteExecutor.execute(new commandExecutor(user));
                } catch (IOException e) {
                    System.out.printf("Связь с пользоваетелем прервана\n");
                    e.printStackTrace();

                }
            }
            System.out.println("Этот Reader завершил свою работу");
            /*
            try {
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
                }
            } catch (IOException e) {
                selectionKey.cancel();
                System.out.println(e.getMessage());
            } catch (BufferUnderflowException e) {
                System.out.println("Непредвниденный разрыв соединения");
                selectionKey.cancel();
            } */
        }
    }

    public class commandExecutor implements Runnable {
        User user;
        public commandExecutor(User user){
            this.user = user;
        }
        @Override
        public void run() {
            user.interpret();
            writeExecutor.execute(new Writer(user));
        }
    }

    public class Writer implements Runnable {
        User user;
        public Writer(User user){
            this.user = user;
        }
        @Override
        public void run() {
            try {
                System.out.println("Отправляю ответ");
                user.writeAnswer();
                System.out.println("Ответ отправлен");
            } catch (IOException e) {
                System.out.println("Прервано соединение с пользователем");
            }
        }
    }


    /*
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

            }
        }
        System.out.println("Next...");
    }
    */


    //ExecutorService service = Executors.newCachedThreadPool();

    Executor deamonExecutor = (runnable) -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    };

    Executor acceptExecutor = (runnable) -> {
        Thread thread = new Thread(runnable);
        accepterPool.submit(thread);
    };

    Executor readExecutor = (runnable) -> {
        Thread thread = new Thread(runnable);
        readerPool.submit(thread);
    };

    Executor writeExecutor = (runnable) -> {
        Thread thread = new Thread(runnable);
        writerPool.submit(thread);
    };

    Executor commandExecuteExecutor = (runnable) -> {
        Thread thread = new Thread(runnable);
        commandExecutorPool.submit(thread);
    };

    public static ArrayList<User> getUserList() {
        return UserList;
    }

    public static List<LabWork> getLabList() {
        return LabList;
    }


}
