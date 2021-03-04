package collection_control;

import java.util.LinkedList;
import labwork_class.LabWork;

public class Main {

	public static void main(String[] args) throws BadValueException  {
		LinkedList<LabWork> LabList = ParseJson.parseFromJson("Json_input");
		new ComandReader(LabList, false, "stream");
	}
}

