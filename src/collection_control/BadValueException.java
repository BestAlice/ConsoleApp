package collection_control;

public class BadValueException extends Exception {
	private String message;
	
	public BadValueException(String message){
	     
		this.message = message;
    }
	
	public void message(String type) {
		if (type.equals("read")) {
			System.out.println("Обнаружено несоответствие при чтении id. " + message);
		} else if (type.equals("input")) {
			System.out.println("Некорректный ввод. " + message + " Попробуйте снова");
		} else {
			System.out.println(message);
		}
	}

	public void message(String type, String var) {
		if (type.equals("read")) {
			System.out.printf("Обнаружено несоответствие при чтении объекта. Ошибка чтения переменной %s. %s \n", var, message);
		} else if (type.equals("input")) {
			System.out.println("Некорректный ввод. " + message + " Попробуйте снова");
		} else {
			System.out.println(message);
		}
	}
	public void message(String type, Long id, String var) {
		if (type.equals("read")) {
			System.out.printf("Обнаружено несоответствие при чтении объекта с id %d. Ошибка чтения переменной %s. %s \n", id, var, message);
		} else if (type.equals("input")) {
			System.out.println("Некорректный ввод. " + message + " Попробуйте снова \n");
		} else {
			System.out.println(message);
		}
	}

}
