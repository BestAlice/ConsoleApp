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
            System.out.println("Ошибка синтаксиса. Требуется ввести порт подключения.");
            System.out.println("Необходимы: порт, перменнаая окружения");
            System.exit(0);
        }
        if (args.length < 2) {
            System.out.println("Ошибка синтаксиса. Требуется ввести переменную окружения.");
            System.out.println("Необходимы: порт, перменнаая окружения");
            System.exit(0);
        }

        try {
            PORT = CheckInput.checkInt(args[0], 1, 9999);
        } catch (BadValueException e) {
            System.out.println("Ошибка порта. " + e.getMessage());
            System.exit(0);
        }


        try {
            fileName = args[1];
            File InputFile = new File(System.getenv(fileName));
            if (!InputFile.exists()) {throw new FileNotFoundException("Файл не существует");}
            if (!InputFile.canRead()) {throw new Exception("Файл не может быть прочитан");}
            if (!InputFile.canWrite()) {throw new Exception("В файл нельзя записывать");}
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

        ServerProcess process = new ServerProcess(PORT, fileName);
        process.run();
    }
}




                /*
                server = new ServerSocket(4004); // серверсокет прослушивает порт 4004
                System.out.println("Сервер запущен!"); // хорошо бы серверу
                LinkedList<LabWork> LabList;
                String fileName;
                Scanner scan = new Scanner(System.in);
                while (true) {
                    try {
                        System.out.print("Введите имя переменной окруения: ");
                        fileName = scan.nextLine();
                        if (fileName.equals("exit")) {System.exit(-1);}
                        File InputFile = new File(System.getenv(fileName));
                        if (!InputFile.exists()) {throw new FileNotFoundException();}
                        if (!InputFile.canRead()) {throw new Exception("Файл не может быть прочитан");}
                        if (!InputFile.canWrite()) {throw new Exception("В файл нельзя записывать");}
                        LabList = ParseJson.parseFromJson(fileName);
                        break;

                    } catch (NullPointerException e){
                        System.out.printf("Проблема чтения переменной окружения.\n");
                    } catch (FileNotFoundException e) {
                        System.out.println("Файл не найден");
                    } catch (JsonSyntaxException e) {
                        System.out.println("Ошибка в структуре json");
                    } catch (IOException e){
                        System.out.println("Ошибка чтения потока");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }

                System.out.println("Чтение успешно завершено\n");
                
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    // и отправлять
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    while (true){
                        String word = in.readLine(); // ждём пока клиент что-нибудь нам напишет
                        if (word.equals("exit")) {break;}
                        System.out.println(word);
                        // не долго думая отвечает клиенту
                        out.write("Привет, это Сервер! Подтверждаю, вы написали : " + word + "\n");
                        out.flush(); // выталкиваем все из буфера
                    }

                } finally { // в любом случае сокет будет закрыт
                    clientSocket.close();
                    // потоки тоже хорошо бы закрыть
                    in.close();
                    out.close();
                }
        } finally {
            System.out.println("Сервер закрыт!");
            server.close();
        }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}

                 */

