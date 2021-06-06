package collection_control;

import appFiles.CommandPanel;

public class BadValueException extends Exception {
	private String message;
	private String type = "input";
	private Long id;
	private String var;
	private String name;

	
	
	public BadValueException(String message){
		this.message = message;
    }

	public BadValueException(String var, String message){
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

	public void message() {
		if (type.equals("read")) {
			CommandPanel.addEntry(String.format("Обнаружено несоответствие при чтении объекта с id %d (Имя: \"%s\"). Ошибка чтения переменной %s. %s \n", id, name, var, message));
		} else if (type.equals("input")) {
			CommandPanel.addEntry("Некорректный ввод переменной "+ var +". " + message + " Попробуйте снова \n");
		} else {
			CommandPanel.addEntry(message);
		}
	}

	public void message(String type) {
		
		if (type.equals("read")) {
			CommandPanel.addEntry("Обнаружено несоответствие при чтении id. " + message);
		} else if (type.equals("input")) {
			CommandPanel.addEntry("Некорректный ввод. " + message + " Попробуйте снова");
		} else {
			CommandPanel.addEntry(message);
		}
	}

	public void message(String type, String var) {
		if (type.equals("read")) {
			CommandPanel.addEntry("Обнаружено несоответствие при чтении " + var + ". " + message);
		} else if (type.equals("input")) {
			CommandPanel.addEntry("Некорректный ввод. " + message + " Попробуйте снова");
		} else {
			CommandPanel.addEntry(message);
		}
	}


	public void message(String type, Long id, String name) {
		if (type.equals("read")) {
			CommandPanel.addEntry(String.format("Обнаружено несоответствие при чтении объекта с id %d (Имя: \"%s\"). Ошибка чтения переменной %s. %s \n", id, name, var, message));
		} else if (type.equals("input")) {
			CommandPanel.addEntry("Некорректный ввод. " + message + " Попробуйте снова \n");
		} else {
			CommandPanel.addEntry(message);
		}
	}

	public  void setType(String type) {this.type = type;}

	public  void setVar(String var) {this.var = var;}


	public String getVar() {
		return var;
	}

	public String getType() {return type;}

	public String getMessage () {
		return message;
	}
}
