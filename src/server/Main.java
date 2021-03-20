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
                server = new ServerSocket(4004); // ����������� ������������ ���� 4004
                System.out.println("������ �������!"); // ������ �� �������
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // � ����������
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    while (true){
                        String word = in.readLine(); // ��� ���� ������ ���-������ ��� �������
                        if (word.equals("exit")) {break;}
                        System.out.println(word);
                        // �� ����� ����� �������� �������
                        out.write("������, ��� ������! �����������, �� �������� : " + word + "\n");
                        out.flush(); // ����������� ��� �� ������
                    }

                } finally { // � ����� ������ ����� ����� ������
                    clientSocket.close();
                    // ������ ���� ������ �� �������
                    in.close();
                    out.close();
                }
        } finally {
            System.out.println("������ ������!");
            server.close();
        }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

