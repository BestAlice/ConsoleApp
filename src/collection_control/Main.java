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
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		}

		System.out.println("������ ������� ���������\n");
		System.out.println("����� ���������� � �� ���������� ����������.");
		System.out.println("����� ������� ��������� �������, ������� help.");
		System.out.println("����� ����������� ���������, ������� show.");
		System.out.println();
		new ComandReader(LabList, false, "stream", fileName);
	}
}

