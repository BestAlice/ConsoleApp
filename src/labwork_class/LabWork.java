package labwork_class;

import collection_control.BadValueException;
import collection_control.CheckInput;
import collection_control.Serializing;

import java.io.IOException;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

public class LabWork implements Comparable<LabWork>, Serializable {
	
	private transient  static LinkedList<Long> index = new LinkedList<>();
	private transient  static ArrayList<Long> usingId = new ArrayList<>();
	static  {
		for ( Long i = 0L; i < 1000L; i++ ) {
			index.add(i + 1);
		}
		Collections.shuffle(index);
	}


	private transient  Scanner scan = new Scanner(System.in);
	transient CheckInput check = new CheckInput();


	private Long id; //���� �� ����� ���� null, �������� ���� ������ ���� ������ 0, �������� ����� ���� ������ ���� ����������, �������� ����� ���� ������ �������������� �������������
    private String name = ""; //���� �� ����� ���� null, ������ �� ����� ���� ������
    private Coordinates coordinates; //���� �� ����� ���� null
    private LocalDateTime creationDate; //���� �� ����� ���� null, �������� ����� ���� ������ �������������� �������������
    private int minimalPoint; //�������� ���� ������ ���� ������ 0
    private Long personalQualitiesMaximum; //���� ����� ���� null, �������� ���� ������ ���� ������ 0
    private Difficulty difficulty = null; //���� ����� ���� null
    private Discipline discipline = null; //���� ����� ���� null
	private String userName = null;
	private Long userId = null;


	private int weight = 0;

	public LabWork newLab() {
		creationDate =LocalDateTime.now();
		return this;
	}



	public LabWork	create(Scanner scan){

    	System.out.println("�������� ������ ������� ������ LabWork");
    	inputFields(scan);

    	return this;
    }

    public void update(LabWork newLab) {
		System.out.printf("���������� ������ ��� %s\n", this.name);
		name = newLab.getName();
		coordinates = newLab.getCoordinates();
		minimalPoint = newLab.getMinimalPoint();
		personalQualitiesMaximum = newLab.getPersonalQualitiesMaximum();
		difficulty = newLab.getDifficulty();
		discipline = newLab.getDiscipline();
	}

    public void inputFields(Scanner scan){
		this.scan = scan;
		int fieldIndex = 0;
		while (fieldIndex < 7) {
			try {
				switch (fieldIndex) {
					case 0:
						System.out.print("������� �������� ������������: ");
						name = check.checkString(scan.nextLine()).replaceAll(" ", "_");
						fieldIndex++;break;
					case 1:
						coordinates = new Coordinates("input", scan);
						fieldIndex++;break;
					case 2:
						System.out.print("������� ����������� �����: ");
						minimalPoint = check.checkInt(scan.nextLine(), 0, Integer.MAX_VALUE);
						fieldIndex++;break;
					case 3:
						System.out.print("������� �������� ������ �������: ");
						personalQualitiesMaximum = check.checkLong(scan.nextLine(), 0L, Long.MAX_VALUE, true);
						fieldIndex++;break;
					case 4:
						System.out.print("������� ��������� ������ (EASY, IMPOSSIBLE, INSANE, TERRIBLE): ");
						difficulty = check.checkEnum(scan.nextLine(), Difficulty.class, true);
						fieldIndex++;break;
					case 5:
						System.out.print("������� �� ��������� ��������� � ����������? (yes/no) ");
						String answer =  check.checkString(scan.nextLine());
						if (answer.equals("yes")) {fieldIndex++;}
						if (answer.equals("no")) {fieldIndex += 2;}
						break;
					case 6:
						discipline = new Discipline("input", scan);
						fieldIndex++;break;
				}
			} catch (BadValueException e) {
				e.message("input");
			}
		}
		System.out.println("���������� ���������");
	}
    
    public static void addId(Long id) {
    	usingId.add(id);
    }

    public void removeIdFromUssing(Long id) throws BadValueException{
    	try {
			check.checkId(id.toString(), usingId);
			usingId.remove(id);
		} catch (BadValueException e) {
    		throw new BadValueException("id", e.getMessage());
    	}
	}
    
    public void setName (String value, String type) throws BadValueException{
    	try {name = check.checkString(value);} 
    	catch (BadValueException e) 
    	{throw new BadValueException("name", e.getMessage());}
    } 
    
    public void setCoordinates (Coordinates coord, String type){
    	try {coordinates = coord;}
		 catch (Exception e)
    	{System.out.println(e.getMessage());}
    }
    

    public void setCreationDate (LocalDateTime date, String type) {
    	creationDate = date;
    }
    
    public void setMinimalPoint (String value, String type)  throws BadValueException {
    	try {minimalPoint = check.checkInt(value, 0, Integer.MAX_VALUE);} 
    	catch (BadValueException e) 
    	{throw new BadValueException(type, id, name, "minimalPoint", e.getMessage());}
    }
    
    public void setPersonalQualitiesMaximum (String value, String type) throws BadValueException {
    	try {personalQualitiesMaximum =  check.checkLong(value, 0L, Long.MAX_VALUE, true);} 
    	catch (BadValueException e) 
    	{throw new BadValueException("personalQualitiesMaximum", e.getMessage());}
    }
    
    public void setDifficulty (String value, String type) throws BadValueException {
    	try {difficulty = check.checkEnum(value, Difficulty.class, true);} 
    	catch (BadValueException e) 
    	{throw new BadValueException("difficulty", e.getMessage());}
    }
    
    public void setDiscipline (Discipline dis, String type) throws BadValueException {
    	try {discipline = dis;}
    	catch (Exception e) 
    	{e.getMessage();}
    }

    public void setId(Long id) {this.id = id; }

	public Long getId() {
		return id;
	}

	public static void removeId (Long id) {
		usingId.remove(id);
	}

	public int getMinimalPoint() {
		return minimalPoint;
	}

	public Long getPersonalQualitiesMaximum() {
		return personalQualitiesMaximum;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}


	public Coordinates getCoordinates() {
		return coordinates;
	}

	public static ArrayList<Long> getUsingId() {
		return usingId;
	}

	public int getWeight(){
		return weight;
	}

	public int compareTo(LabWork o) {
		return this.getWeight() - o.getWeight();
	}

	public void findWeight()  {
		try{
			byte[] objData = Serializing.serializeObject(this);
			weight = objData.length;
		} catch (IOException e) {
			System.out.println("��������� ������� �������� ��� �������");
		}

	}

	public void show() {
    	System.out.println("id : " + id);
		System.out.println("name : " + name);
		coordinates.show();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY hh:mm:ss");
		System.out.println("creationDate : " + creationDate.format(formatter));
		System.out.println("minimalPoint : " + minimalPoint);
		System.out.println("personalQualitiesMaximum : " + personalQualitiesMaximum);
		System.out.println("difficulty : " + difficulty);
		if (discipline == null) {
			System.out.println("discipline : null");
		} else {
			discipline.show();
		}
		System.out.println("userId : " + userId);
	}

	public ArrayList<String> getFullInfo() {
		ArrayList<String> info = new ArrayList<>();
		info.add("id : " + id);
		info.add("name : " + name);
		for (String line: coordinates.getFullInfo()) {
			info.add(line);
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY hh:mm:ss");
		info.add("creationDate : " + creationDate.format(formatter));
		info.add("minimalPoint : " + minimalPoint);
		info.add("personalQualitiesMaximum : " + personalQualitiesMaximum);
		info.add("difficulty : " + difficulty);
		if (discipline == null) {
			info.add("discipline : null");
		} else {
			for (String line: discipline.getFullInfo()) {
				info.add(line);
			}
		}
		info.add("userId : " + userId);
		info.add("");
		return info;
	}

	public Discipline getDiscipline() {
    	return discipline;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public HashMap<String, Object> getMap() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("name", name);
		map.put("x", coordinates.getX());
		map.put("y", coordinates.getY());
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.YYYY hh:mm:ss");
		map.put("creationDate", creationDate);
		map.put("minimalPoint", minimalPoint);
		map.put("personalQualitiesMaximum", personalQualitiesMaximum);
		map.put("difficulty", difficulty);

		if (discipline == null) {
			map.put("disciplineName", null);
			map.put("practiceHours", null);
		} else {
			map.put("disciplineId", discipline.getId());
			map.put("disciplineName", discipline.getName());
			map.put("practiceHours", discipline.getPracticeHours());
		}
		map.put("userId", userId);
		map.put("userName", userName);
		map.put("coordinatesId", coordinates.getId());

		findWeight();
		map.put("weight", weight);
		return map;
	}
}

