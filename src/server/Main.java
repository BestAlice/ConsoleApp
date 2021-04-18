package server;

import collection_control.BadValueException;
import collection_control.CheckInput;
import collection_control.ParseJson;
import collection_control.Serializing;
import com.google.gson.JsonSyntaxException;
import labwork_class.LabWork;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Main {

    private static int PORT;
    private static int BUFFER_SIZE = 10000;
    private static String fileName;

    public static void main(String[] args) {


        if (args.length < 1) {
            System.out.println("������ ����������. ��������� ������ ���� �����������.");
            System.out.println("����������: ����, ���������� ���������");
            System.exit(0);
        }
        if (args.length < 2) {
            System.out.println("������ ����������. ��������� ������ ���������� ���������.");
            System.out.println("����������: ����, ���������� ���������");
            System.exit(0);
        }

        try {
            PORT = CheckInput.checkInt(args[0], 1, 9999);
        } catch (BadValueException e) {
            System.out.println("������ �����. " + e.getMessage());
            System.exit(0);
        }


        try {
            fileName = args[1];
            File InputFile = new File(System.getenv(fileName));
            if (!InputFile.exists()) {throw new FileNotFoundException("���� �� ����������");}
            if (!InputFile.canRead()) {throw new Exception("���� �� ����� ���� ��������");}
            if (!InputFile.canWrite()) {throw new Exception("� ���� ������ ����������");}
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        ServerProcess process = new ServerProcess(PORT, fileName);
        process.run();
    }
}




                /*
                server = new ServerSocket(4004); // ����������� ������������ ���� 4004
                System.out.println("������ �������!"); // ������ �� �������
                LinkedList<LabWork> LabList;
                String fileName;
                Scanner scan = new Scanner(System.in);
                while (true) {
                    try {
                        System.out.print("������� ��� ���������� ��������: ");
                        fileName = scan.nextLine();
                        if (fileName.equals("exit")) {System.exit(-1);}
                        File InputFile = new File(System.getenv(fileName));
                        if (!InputFile.exists()) {throw new FileNotFoundException();}
                        if (!InputFile.canRead()) {throw new Exception("���� �� ����� ���� ��������");}
                        if (!InputFile.canWrite()) {throw new Exception("� ���� ������ ����������");}
                        LabList = ParseJson.parseFromJson(fileName);
                        break;

                    } catch (NullPointerException e){
                        System.out.printf("�������� ������ ���������� ���������.\n");
                    } catch (FileNotFoundException e) {
                        System.out.println("���� �� ������");
                    } catch (JsonSyntaxException e) {
                        System.out.println("������ � ��������� json");
                    } catch (IOException e){
                        System.out.println("������ ������ ������");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                System.out.println("������ ������� ���������\n");
                
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

                 */

