package labwork_class;

import collection_control.BadValueException;
import collection_control.CheckInput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Coordinates implements Serializable {
	private Long id;
	private Long x; //Максимальное значение поля: 691, Поле не может быть null
    private Long y; //Поле не может быть null
	transient private Scanner scan;
	transient private CheckInput check = new CheckInput();

    
    //сделать конструктор
    public Coordinates(String type, Scanner scan){
    	if (type.equals("input")) {
			this.scan = scan;
    		System.out.println("Введите координаты");
    		int i = 0;
    		while (i < 2) {
				try{ switch (i) {
					case 0:
						System.out.print("X: ");
						x =  check.checkLong(scan.nextLine(), Long.MIN_VALUE, 691L, false);
						i++;break;
					case 1:
						System.out.print("Y: ");
						y = check.checkLong(scan.nextLine(), Long.MIN_VALUE, Long.MAX_VALUE, false);
						i++;break;
				}} catch (BadValueException e) {e.message("input");}
    		}
    	}
    }
	public void setX (String value, String type) throws BadValueException{
		try{
			x = check.checkLong(value, Long.MIN_VALUE, 691L, false);
		} catch (BadValueException e) {
			throw new BadValueException(type, "x");
		}
    }

    public void setY (String value, String type) throws BadValueException{
		try{
    	y = check.checkLong(value, Long.MIN_VALUE, Long.MAX_VALUE, false);
		} catch (BadValueException e) {
			throw new BadValueException(type, "y");
		}
    }

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId(){
    	return id;
	}

	public Long getX() {
    	return x;
    }

    public Long getY() {
    	return y;
    }

    public void show(){
		System.out.println("coordinates");
    	System.out.println("\tx : " + x);
    	System.out.println("\ty : " + y);
	}

	public ArrayList<String> getFullInfo(){
		ArrayList<String> info = new ArrayList<>();
		info.add("coordinates");
		info.add("\tx : " + x);
		info.add("\ty : " + y);
		return info;
	}
}
