package collection_control;

import java.util.ArrayList;
import java.util.LinkedList;

import client.RequestControll;
import client.RequestControll.*;

public class CheckInput  {
	
	public Long checkId (String value) throws BadValueException {
		Long id = checkLong(value, 0L, Long.MAX_VALUE, false);
		RequestObject req = new RequestObject();
		req.setCommand("get_usindId");
		RequestControll.sendRequest(req);
		RequestObject ans = RequestControll.getAnswer();
		ArrayList<Long> usingId = ans.getUsingId();
		if (usingId.contains(id)) {
			return id;
		}
		throw new BadValueException("������ id �� ����������.");
	}
	
	public <T extends Enum<T>> T checkEnum (String value, Class<T> clazz, boolean can_null) throws BadValueException {
		String newLine = value;
		T thisEnum;
		if (!can_null && nullCheck(newLine)){
			throw new BadValueException("������ ���� �� ����� ���� null.");
		} else if (can_null && nullCheck(newLine)) {return null;}
		newLine = newLine.trim();
		try {
			thisEnum = T.valueOf(clazz, newLine);
		} catch (Exception e) {
			throw new BadValueException("�� �������� ������ �������� enum.");
		}
		
		return thisEnum;
	}
	
	public int checkInt (String value, int min, int max) throws BadValueException {
		String newLine = value;
		int num;
		if (nullCheck(newLine)){
			throw new BadValueException("������ ���� �� ����� ���� null.");
		}
		newLine = newLine.trim();
		try {
			num = Integer.parseInt(newLine);
		} catch (Exception e) {
			throw new BadValueException("�������� ��� ����������.");
		}
		
		if (num <= min) {
			throw new BadValueException(String.format("�������� ������ ���� ������ %d.", min));
		} else if (num > max) {
			throw new BadValueException(String.format("�������� ��������� ������������ %d.", max));
		}
		return num;
	}
	
	public String checkString (String value) throws BadValueException {
		String newLine = value;
		if (nullCheck(newLine)){
			throw new BadValueException("������ ���� �� ����� ���� null.");
		}
		if (newLine.trim().length() == 0) {
			throw new BadValueException("������ ������.");
		}
		return newLine.trim();
	}
	
	public Long checkLong (String value, Long min, Long max, boolean can_null) throws BadValueException  {
		Long num;
		String newLine = value;
		
		if (!can_null && nullCheck(newLine)){
			throw new BadValueException("������ ���� �� ����� ���� null.");
		} else if (can_null && nullCheck(newLine)) {return null;}
		newLine = newLine.trim();
		try {
			num = Long.valueOf(newLine);
		} catch (Exception e) {
			throw new BadValueException("�������� ��� ����������.");
		}
		
		if (num <= min) {
			throw new BadValueException(String.format("�������� ������ ���� ������ %d.", min));
		} else if (num > max) {
			throw new BadValueException(String.format("�������� ��������� ������������ %d.", max));
		}
		return num;
	}
	
	public static boolean nullCheck(String newLine) {
		if (newLine.equals("") || newLine.equals("null")) {
			return true;
		} else {
			return  false;
		}
	}
}
