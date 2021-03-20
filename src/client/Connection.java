package client;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    Socket socket = null;
    String host;
    int port;

    public void connect (String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        socket = new Socket(host, port);
    }

    public boolean reconnect(){
        System.out.println("������� ���������������");
        long startTime = System.currentTimeMillis();
        long nowTime = System.currentTimeMillis();
        while(nowTime - startTime < 5000){
            try{
                socket = new Socket(host, port);
                System.out.println("���������� �������������");
                return true;
            } catch (IOException e) {
                nowTime = System.currentTimeMillis();
            }
        }
        System.out.println("������ �� ��������.");
        return false;
    }

    public Socket getSocket(){
        return socket;
    }

    public void setSocket(Socket socket){
        this.socket = socket;
    }

}
