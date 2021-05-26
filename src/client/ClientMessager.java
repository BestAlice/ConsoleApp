package client;

import collection_control.MessageObject;


import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientMessager {
    private static boolean executed = false;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientMessager(Socket socket) {
        this.socket = socket;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public MessageObject getAnswer() throws IOException{
        MessageObject answerObject = null;
        try{
            answerObject = (MessageObject) in.readObject();
            return answerObject;

        } catch (IOException e) {
            socket.getChannel().close();
            socket.close();
            System.out.println("Ошибка передачи данных");
        } catch (ClassNotFoundException e) {
            System.out.println("Передан ошибочный класс данных");
        }
        return answerObject;
    }

    public boolean sendMessage(MessageObject message){
        try{
            out.writeObject(message);
            out.flush();
            return true;
        } catch (IOException e) {
            System.out.println("Ошибка передачи данных");
            return false;
        }
    }
}
