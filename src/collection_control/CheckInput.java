package collection_control;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CheckInput  {
	
	public static Long checkId (String value, ArrayList<Long> usingId) throws BadValueException {
		Long id = checkLong(value, 0L, Long.MAX_VALUE, false);
		if (usingId.contains(id)) {
			return id;
		}
		throw new BadValueException("Такого id не существует.");
	}
	
	public static <T extends Enum<T>> T checkEnum (String value, Class<T> clazz, boolean can_null) throws BadValueException {
		String newLine = value;
		T thisEnum;
		if (!can_null && nullCheck(newLine)){
			throw new BadValueException("Данное поле не может быть null.");
		} else if (can_null && nullCheck(newLine)) {return null;}
		newLine = newLine.trim();
		try {
			thisEnum = T.valueOf(clazz, newLine);
		} catch (Exception e) {
			throw new BadValueException("Не является именем костанты enum.");
		}
		
		return thisEnum;
	}
	
	public static int checkInt (String value, int min, int max) throws BadValueException {
		String newLine = value;
		int num;
		if (nullCheck(newLine)){
			throw new BadValueException("Данное поле не может быть null.");
		}
		newLine = newLine.trim();
		try {
			num = Integer.parseInt(newLine);
		} catch (Exception e) {
			throw new BadValueException("Неверный тип переменной.");
		}
		
		if (num <= min) {
			throw new BadValueException(String.format("Значение должно быть больше %d.", min));
		} else if (num > max) {
			throw new BadValueException(String.format("Значение превышает максимальное %d.", max));
		}
		return num;
	}
	
	public static String checkString (String value) throws BadValueException, UnsupportedOperationException {
		String newLine = value;
		if (nullCheck(newLine)){
			throw new BadValueException("Данное поле не может быть null.");
		}
		if (newLine.trim().length() == 0) {
			throw new BadValueException("Пустая строка.");
		}
		String final_line = "";
		try {
			final_line = new String(newLine.getBytes(), "Cp866"); //"Cp866""UTF-16"
		} catch (UnsupportedEncodingException e) {
			System.out.println("Кодировка - хрень");
		}

 		return newLine.trim();
	}
	
	public static Long checkLong (String value, Long min, Long max, boolean can_null) throws BadValueException  {
		Long num;
		String newLine = value;
		
		if (!can_null && nullCheck(newLine)){
			throw new BadValueException("Данное поле не может быть null.");
		} else if (can_null && nullCheck(newLine)) {return null;}
		newLine = newLine.trim();
		try {
			num = Long.valueOf(newLine);
		} catch (Exception e) {
			throw new BadValueException("Неверный тип переменной.");
		}
		
		if (num <= min) {
			throw new BadValueException(String.format("Значение должно быть больше %d.", min));
		} else if (num > max) {
			throw new BadValueException(String.format("Значение превышает максимальное %d.", max));
		}
		return num;
	}
	
	public static boolean nullCheck(String newLine) {
		if (newLine == null) {return true;}
		if (newLine.equals("") || newLine.equals("null")) {
			return true;
		} else {
			return  false;
		}
	}
}
