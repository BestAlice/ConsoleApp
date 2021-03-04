package labwork_class;

import collection_control.BadValueException;
import collection_control.CheckInput;

import java.util.Scanner;

public class Coordinates {
	private Long x; //Максимальное значение поля: 691, Поле не может быть null
    private Long y; //Поле не может быть null
    private Scanner scan;
	CheckInput check = new CheckInput();

    
    //сделать конструктор
    public Coordinates(String type, Scanner scan){

    	if (type.equals("input")) {
			this.scan = scan;
    		System.out.println("Введите координаты");
    		int i = 0;
    		while (i < 2) {
				try{
    			switch (i) {
					case 0:
						System.out.print("X: ");
						x =  check.checkLong(scan.nextLine(), Long.MIN_VALUE, 691L, false);
						i++;break;
					case 1:
						System.out.print("Y: ");
						y = check.checkLong(scan.nextLine(), Long.MIN_VALUE, Long.MAX_VALUE, false);
						i++;break;
				}} catch (BadValueException e) {e.message("input");}
				System.out.println();
    		}
    	}
    }
	public void setX (String value, String type) {
		try{
			x = check.checkLong(value, Long.MIN_VALUE, 691L, false);
		} catch (BadValueException e) {e.message(type,"x");
		}}

    public void setY (String value, String type) {
		try{
    	y = check.checkLong(value, Long.MIN_VALUE, Long.MAX_VALUE, false);
		} catch (BadValueException e) {e.message(type,"y");
    }}

    public Long getX() {
    	return x;
    }

    public Long getY() {
    	return y;
    }

    public void show(){
    	System.out.println("x : " + x);
    	System.out.println("y : " + y);
	}
}
