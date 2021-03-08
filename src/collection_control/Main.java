package collection_control;

import java.io.File;
import java.util.LinkedList;
import labwork_class.LabWork;

public class Main {

	public static void main(String[] args) throws BadValueException  {
		LinkedList<LabWork> LabList;
		String fileName;
		if (args.length > 0) {
			try {
				System.out.println(args[0]);
				File newInputFile = new File(System.getenv(args[0]));
				LabList = ParseJson.parseFromJson(args[0]);
				fileName = args[0];
			} catch (NullPointerException e) {
				System.out.printf("ѕроблема чтени€ переменной %s окружени€. ѕопытка использовать стандартную переменную Json_input\n", args[0]);
				LabList = ParseJson.parseFromJson("Json_input");
				fileName = "Json_input";
			}
		} else {
			LabList = ParseJson.parseFromJson("Json_input");
			fileName = "Json_input";
		}
		new ComandReader(LabList, false, "stream", fileName);
	}
}

