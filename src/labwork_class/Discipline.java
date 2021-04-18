package labwork_class;

import collection_control.BadValueException;
import collection_control.CheckInput;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Discipline implements  Serializable {
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
    
    public void setName (String value, String type) throws  BadValueException{
    	try{ name = check.checkString(value); }
    	catch (BadValueException e) {throw new BadValueException("discipline name", e.getMessage());}
    }
    
    public void setPracticeHours (String value, String type) throws  BadValueException{
		try{ practiceHours = check.checkInt(value, Integer.MIN_VALUE, Integer.MAX_VALUE);}
    	catch (BadValueException e) {throw new BadValueException("practiceHours", e.getMessage());}
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

	public ArrayList<String> getFullInfo(){
		ArrayList<String> info = new ArrayList<>();
		info.add("discipline :");
		info.add("\tdiscipline name : " + name);
		info.add("\tpracticeHours : " + practiceHours);
		return info;
	}
}
