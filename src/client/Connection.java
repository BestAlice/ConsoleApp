package client;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.channels.AlreadyConnectedException;
import java.nio.channels.SocketChannel;

public class Connection {
    Socket socket = null;
    SocketChannel channel = null;
    String host;
    int port;

    public void connect (String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
        System.out.println("Соединение с сервером установлено...");
    }

    public boolean reconnect(){
        System.out.println("Попытка переподключения");
        long startTime = System.currentTimeMillis();
        long nowTime = System.currentTimeMillis();
        while(nowTime - startTime < 10000){
            try{
                try{
                    socket = new Socket(host, port);
                } catch (AlreadyConnectedException e) {
                    System.out.println("Данный канал уже окрыт");
                    System.out.println(e.getMessage());
                }

                System.out.println("Соединение восстановлено");

                return true;
            } catch (IOException e) {
                nowTime = System.currentTimeMillis();
            }
        }
        System.out.println("Сервер не отвечает.");
        return false;
    }

    public Socket getSocket(){
        return socket;
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

}
