package collection_control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import com.google.gson.JsonSyntaxException;
import labwork_class.LabWork;

public class Main {

	public static void main(String[] args) throws BadValueException  {
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
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

		System.out.println("Чтение успешно завершено\n");
		System.out.println("Добро пожаловать в моё консольное приложение.");
		System.out.println("Чтобы увидеть доступные команды, введите help.");
		System.out.println("Чтобы просмотреть коллекцию, введите show.");
		System.out.println();
		new ComandReader(LabList, false, "stream", fileName);
	}
}

