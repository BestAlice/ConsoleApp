package collection_control;

public class BadValueException extends Exception {
	private String message;
	
	public BadValueException(String message){
	     
		this.message = message;
    }
	
	public void message(String type) {
		if (type.equals("read")) {
			System.out.println("���������� �������������� ��� ������ id. " + message);
		} else if (type.equals("input")) {
			System.out.println("������������ ����. " + message + " ���������� �����");
		} else {
			System.out.println(message);
		}
	}

	public void message(String type, String var) {
		if (type.equals("read")) {
			System.out.printf("���������� �������������� ��� ������ �������. ������ ������ ���������� %s. %s \n", var, message);
		} else if (type.equals("input")) {
			System.out.println("������������ ����. " + message + " ���������� �����");
		} else {
			System.out.println(message);
		}
	}
	public void message(String type, Long id, String var) {
		if (type.equals("read")) {
			System.out.printf("���������� �������������� ��� ������ ������� � id %d. ������ ������ ���������� %s. %s \n", id, var, message);
		} else if (type.equals("input")) {
			System.out.println("������������ ����. " + message + " ���������� ����� \n");
		} else {
			System.out.println(message);
		}
	}

}
