package labwork_class;

import collection_control.BadValueException;
import collection_control.CheckInput;

import java.util.Scanner;

public class Discipline{
	private String name; //Поле не может быть null, Строка не может быть пустой
    private int practiceHours;
    private Scanner scan;
	CheckInput check = new CheckInput();
    
    public Discipline(String type, Scanner scan) {
    	this.scan = scan;
    	if (type.equals("input")) {
    		int i = 0;
    		while (i < 2) {
				try {
    			switch (i) {
    			case 0:
    				System.out.print("Введите название дисциплины: ");
					name = check.checkString(scan.nextLine());
					i++;break;
    			case 1:
    				System.out.print("Введите количество часов практики: ");
					practiceHours = check.checkInt(scan.nextLine(), Integer.MIN_VALUE, Integer.MAX_VALUE);
					i++;break;
    			}} catch (BadValueException e) {e.message("input");}
				System.out.println();
    		}}
    	}
    
    public void setName (String value, String type){
    	try{ name = check.checkString(value); }
    	catch (BadValueException e) {e.message(type, "Discipline name");}
    }
    
    public void setPracticeHours (String value, String type) {
		try{ practiceHours = check.checkInt(value, Integer.MIN_VALUE, Integer.MAX_VALUE);}
    	catch (BadValueException e) {e.message(type, "practiceHours");}
    }
    
    public String getName() {
    	return name;
    }
    
    public int getPracticeHours() {
    	return practiceHours;
    }

	public void show(){

    	System.out.println("discipline :");
    	System.out.println("\tdiscipline name : " + name);
    	System.out.println("\tpracticeHours : " + practiceHours);

    }
}
