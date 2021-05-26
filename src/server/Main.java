package server;

import collection_control.BadValueException;
import collection_control.CheckInput;

public class Main {

    private static int PORT;

    public static void main(String[] args) {


        if (args.length < 1) {
            System.out.println("Ошибка синтаксиса. Требуется ввести порт подключения.");
            System.exit(0);
        }

        try {
            PORT = CheckInput.checkInt(args[0], 1, 9999);
        } catch (BadValueException e) {
            System.out.println("Ошибка порта. " + e.getMessage());
            System.exit(0);
        }

        ServerProcess process = new ServerProcess(PORT);
        process.run();
    }
}