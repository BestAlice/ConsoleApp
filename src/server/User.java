package server;

import collection_control.MessageObject;
import labwork_class.LabWork;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class User {
    private static ArrayList<Long> allUsersId = new ArrayList<>();
    private CommandInterpreter interpreter;


    private Long id;
    private String login;
    private String password;
    private boolean autorizated = false;
    private boolean running = true;
    private MessageObject message;
    private MessageObject answer;
    private MessageObject table;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean needUpdate = false;

    public User () {
        interpreter = new CommandInterpreter();
        interpreter.setUser(this);
    }

    public User(Socket socket) throws IOException{
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        interpreter = new CommandInterpreter();
        interpreter.setUser(this);
    }

    public void setId(Long id) {this.id = id;}

    public Long getId() {
        return id;
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
        synchronized (out) {
            out.writeObject(answer);
        }
    }

    public void writeTable() throws  IOException{
        synchronized (out) {
            out.writeObject(table);
        }
    }

    public void interpret() {
        System.out.println("Исполняю комагду " + message.getCommand());
        synchronized (ServerProcess.getLabList()) {
            interpreter.setMessage(message);
            interpreter.run();
            answer = interpreter.getAnswer();

            System.out.println("Исполнение завершено");
        }
    }


    public void setAccess(String name, String password){
        this.login = name;
        this.password = password;
    }

    public boolean haveAccess(String login, String password){
        if (this.login == null || this.password == null) {
            return false;
        }
        if (this.login.equals(login) && this.password.equals(password)) {
            return  true;
        } else {
            return  false;
        }
    }

    public boolean haveAccessToLabWork (LabWork laba) {
        if (this.id.equals(laba.getUserId())) {
            return true;
        } else {
            return false;
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
        return  String.format("%d - %s", id, login);
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {return login;}
    public String getPassword() {return  password;}

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public boolean isAutorizated() {
        return autorizated;
    }

    public void setAutorizated(boolean autorizated) {
        this.autorizated = autorizated;
    }


    public void update() throws IOException {
        table = interpreter.updateTable();
        synchronized (out) {
            out.writeObject(table);
        }

    }

    public MessageObject getTable() {
        return table;
    }
}
