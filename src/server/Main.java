package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    public static void main(String[] args){
        try {
            try  {
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("—ервер запущен!"); // хорошо бы серверу
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // и отправл€ть
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    while (true){
                        String word = in.readLine(); // ждЄм пока клиент что-нибудь нам напишет
                        if (word.equals("exit")) {break;}
                        System.out.println(word);
                        // не долго дума€ отвечает клиенту
                        out.write("ѕривет, это —ервер! ѕодтверждаю, вы написали : " + word + "\n");
                        out.flush(); // выталкиваем все из буфера
                    }

                } finally { // в любом случае сокет будет закрыт
                    clientSocket.close();
                    // потоки тоже хорошо бы закрыть
                    in.close();
                    out.close();
                }
        } finally {
            System.out.println("—ервер закрыт!");
            server.close();
        }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

