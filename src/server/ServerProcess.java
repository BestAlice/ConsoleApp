package server;

import collection_control.*;
import labwork_class.LabWork;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static java.lang.System.exit;

public class ServerProcess {
    private static ServerSocket serverSocket;
    private static int PORT;
    private static List<LabWork> LabList;
    private static Scanner scan = new Scanner(System.in);
    private static ArrayList<User> UserList = new ArrayList<>();
    private static Socket socket = null;
    private static ExecutorService readerPool = Executors.newCachedThreadPool();
    private static ExecutorService commandExecutorPool = Executors.newCachedThreadPool();
    private static ExecutorService writerPool = Executors.newCachedThreadPool();
    private static ExecutorService accepterPool = Executors.newCachedThreadPool();


    public ServerProcess(int port){

        PORT = port;

        try {
            DataBase BD = new DataBase();
            LabList = Collections.synchronizedList(BD.selectLabList());
            CommandInterpreter.setBD(BD);
            CommandInterpreter.setUserList(UserList);
            for (LabWork lab: LabList) {
                LabWork.addId(lab.getId());
            }
        } catch (NullPointerException e){
            System.out.println("NullPointer");
            System.out.println(e.getMessage());
            exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            exit(0);
        }

        serverSocket = null;

    }

    public boolean run(){
        System.out.println("�������� ������...");
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("������ �������...");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.err.println("���������� ��������� ����� ��� ������� �������");
            exit(-1);
        }

            try {
                deamonExecutor.execute(new consoleReader());
                acceptExecutor.execute(new Accepter());

            } catch (NullPointerException e){
                System.out.println("���-�� ��������� NullPointer");
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
                            System.out.println("����� ������������ "+ user.toString() + " ��� ������");
                        }
                    }
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        System.out.println("����� ������� ��� ������");
                    }

                }
            }
        }
    }


    public class Accepter implements Runnable{

        public void run() {
            try {

                    while (!Thread.currentThread().isInterrupted()) {
                        // ����������� �� ������������� ������ ����������:
                        try {
                            Socket socket = serverSocket.accept();
                            System.out.println("��������� ������ ������������");
                            User new_user = new User(socket);
                            UserList.add(new_user); // �������� ����� ����������� � ������
                            System.out.println("������������ ������� ���������");
                            readExecutor.execute(new Reader(new_user));
                            deamonExecutor.execute(new Updater(new_user));
                        } catch (IOException e) {
                            // ���� ���������� ��������, ����������� �����,
                            // � ��������� ������, ���� ������� ��� ��� ���������� ������:
                            System.out.println("�������� �����");
                            socket.close();
                        }
                    }

            } catch (IOException e) {
                System.out.println("������ � Accepter");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("������ Accepter");
            }
        }
    }

    public class Updater implements Runnable  {
        User user;
        public Updater(User user){
            this.user = user;
        }

        public void run() {
            while (true) {
                if (user.isNeedUpdate() & user.isAutorizated()) {
                    try {
                        System.out.println("�������� ������� ��� "+ user.getLogin());

                        user.update();
                        user.setNeedUpdate(false);

                    } catch (IOException e) {
                        user.setNeedUpdate(false);
                        System.out.println("������ ��������");
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Reader implements Runnable  {
        User user;
        public Reader(User user){
            this.user = user;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted() & user.isRunning()) {
                try {
                    user.readMessage();
                    if (!user.getMessage().getCommand().equals("test")){
                        System.out.println("����� ����� ��������� �� ������������");
                        commandExecuteExecutor.execute(new commandExecutor(user));
                    } else {
                        continue;
                    }
                } catch (IOException e) {
                    System.out.printf("����� � �������������� ��������\n");
                    e.printStackTrace();
                }
            }
            System.out.println("���� Reader �������� ���� ������");
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
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("��������� ����� ������������ "+ user.getLogin());
                user.writeAnswer();
                System.out.println("����� ���������");
                if (!user.isAutorizated()){
                    user.setAutorizated(true);
                }
            } catch (IOException e) {
                System.out.println("�������� ���������� � �������������");
            }
        }
    }

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
