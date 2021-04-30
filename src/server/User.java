package server;

import collection_control.MessageObject;

import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class User {
    private static LinkedList<Long> index = new LinkedList<>();
    private static ArrayList<Long> allUsersId = new ArrayList<>();
    static  {
        for ( Long i = 0L; i < 1000L; i++ ) {
            index.add(i + 1);
        }
        Collections.shuffle(index);
    }


    private Long id;
    private String name = null;
    private String password = null;
    private MessageObject message;
    private MessageObject answer;
    private Socket socket;
    private SocketChannel channel;

    public User(SocketChannel channel){
        id = index.getFirst();
        this.channel = channel;
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

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }
}
