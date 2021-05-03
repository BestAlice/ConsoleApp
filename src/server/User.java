package server;

import collection_control.MessageObject;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class User {
    private static LinkedList<Long> index = new LinkedList<>();
    private static ArrayList<Long> allUsersId = new ArrayList<>();
    private CommandInterpreter interpreter;

    static  {
        for ( Long i = 0L; i < 1000L; i++ ) {
            index.add(i + 1);
        }
        Collections.shuffle(index);
    }


    private Long id;
    private String name;
    private String password;
    private boolean running = true;
    private MessageObject message;
    private MessageObject answer;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public User(Socket socket) throws IOException{
        id = index.getFirst();
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        interpreter = new CommandInterpreter();
        interpreter.setUser(this);
    }

    public void readMessage() throws IOException{
        try {
            MessageObject new_message;
            synchronized (in) {
                new_message = (MessageObject) in.readObject();
            }
            setMessage(new_message);
        } catch (ClassNotFoundException e) {
            System.out.println("Прислан ошибочный класс");
        } catch (EOFException | SocketException e) {
            System.out.println("Пользователь разорвал соединение");
            running = false;
        }
    }

    public void writeAnswer() throws IOException{
        out.writeObject(answer);
    }

    public void interpret() {
        System.out.println("Исполняю");
        synchronized (ServerProcess.getLabList()) {
            interpreter.setMessage(message);
            interpreter.run();
            answer = interpreter.getAnswer();

            System.out.println("Исполнение завершено");
        }
    }


    public void setAccess(String name, String password){
        this.name = name;
        this.password = password;
    }

    public boolean haveAccess(String name, String password){
        if (this.name == null || this.password == null) {
            return false;
        }
        if (this.name == name && this.password == password) {
            return  true;
        } else {
            return  false;
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setMessage(MessageObject message){
        this.message = message;
    }

    public MessageObject getMessage() {
        return message;
    }

    public MessageObject getAnswer() {
        return answer;
    }

    public void setAnswer(MessageObject answer) {
        this.answer = answer;
    }

    public boolean isRunning() {return running;}

    public void setRunning(boolean running) {this.running = running;}

    public String toString(){
        return  String.format("%d - %s", id, name);
    }
}
