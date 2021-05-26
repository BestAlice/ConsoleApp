package collection_control;

public class BadValueException extends Exception {
	private String message;
	private String type;
	private Long id;
	private String var;
	private String name;


	public BadValueException(String message){
		this.message = message;
    }

	public BadValueException(String message, String var){
		this.message = message;
		this.var = var;
	}

	public BadValueException(String type, Long id, String name, String var, String message){
		this.type = type;
		this.id = id;
		this.var = var;
		this.message = message;
		this.name = name;
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
			System.out.println("���������� �������������� ��� ������ " + var + ". " + message);
		} else if (type.equals("input")) {
			System.out.println("������������ ����. " + message + " ���������� �����");
		} else {
			System.out.println(message);
		}
	}
	public void message() {
		if (type.equals("read")) {
			System.out.printf("���������� �������������� ��� ������ ������� � id %d (���: \"%s\"). ������ ������ ���������� %s. %s \n", id, name, var, message);
		} else if (type.equals("input")) {
			System.out.println("������������ ����. " + message + " ���������� ����� \n");
		} else {
			System.out.println(message);
		}
	}

	public void message(String type, Long id, String name) {
		if (type.equals("read")) {
			System.out.printf("���������� �������������� ��� ������ ������� � id %d (���: \"%s\"). ������ ������ ���������� %s. %s \n", id, name, var, message);
		} else if (type.equals("input")) {
			System.out.println("������������ ����. " + message + " ���������� ����� \n");
		} else {
			System.out.println(message);
		}
	}

	public String getVar() {
		return var;
	}

	public String getType() {return type;}

	public String getMessage () {
		return message;
	}
}
