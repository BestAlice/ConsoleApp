package labwork_class;

import collection_control.BadValueException;
import collection_control.CheckInput;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDateTime;

public class LabWork implements Comparable<LabWork> {
	
	private static LinkedList<Long> index = new LinkedList<>();
	private static ArrayList<Long> usingId = new ArrayList<>();
	static {for ( Long i = 0L; i < 1000L; i++ ) {
		index.add(i + 1);
	}
	Collections.shuffle(index);}
	
	
	
	private Scanner scan = new Scanner(System.in);
	CheckInput check = new CheckInput();
	
	private Long id = index.pollFirst();; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int minimalPoint; //Значение поля должно быть больше 0
    private Long personalQualitiesMaximum; //Поле может быть null, Значение поля должно быть больше 0
    private Difficulty difficulty; //Поле может быть null
    private Discipline discipline = null; //Поле может быть null

	public LabWork newLab() {
		addId(id);
		return this;
	}

	public LabWork	create(Scanner scan){
    	//автоматически
    	creationDate =LocalDateTime.now();

    	System.out.println("Создание нового объекта класса LabWork");
    	inputFields(scan);

    	return this;
    }

    public void update(Scanner scan) {
		System.out.printf("Обновление данных для %s\n", this.name);
		inputFields(scan);
	}

    public void inputFields(Scanner scan){
		this.scan = scan;
		int fieldIndex = 0;
		while (fieldIndex < 7) {
			try {
				switch (fieldIndex) {
					case 0:
						System.out.print("Введите название Лабораторной: ");
						name = check.checkString(scan.nextLine()).replaceAll(" ", "_");
						fieldIndex++;break;
					case 1:
						coordinates = new Coordinates("input", scan);
						fieldIndex++;break;
					case 2:
						System.out.print("Введите минимальную точку: ");
						minimalPoint = check.checkInt(scan.nextLine(), 0, Integer.MAX_VALUE);
						fieldIndex++;break;
					case 3:
						System.out.print("Введите максимум личных качеств: ");
						personalQualitiesMaximum = check.checkLong(scan.nextLine(), 0L, Long.MAX_VALUE, true);
						fieldIndex++;break;
					case 4:
						System.out.print("Введите сложность работы (EASY, IMPOSSIBLE, INSANE, TERRIBLE): ");
						difficulty = check.checkEnum(scan.nextLine(), Difficulty.class, true);
						fieldIndex++;break;
					case 5:
						System.out.print("Желаете ли заполнить инфомацию о дисциплине? (yes/no) ");
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
			System.out.println();
		}
		System.out.println("Заполнение закончено");
	}
    
    public void addId(Long id) {
    	usingId.add(id);
    }

    public void removeIdFromUssing(Long id) {
    	try {
			check.checkId(id.toString(), new LabWork().getUsingId());
			usingId.remove(id);
		} catch (BadValueException e) {
    		e.message("input", "id");
    	}
	}
    
    public void setName (String value, String type) {
    	try {name = check.checkString(value);} 
    	catch (BadValueException e) 
    	{e.message(type, id,"name");}
    } 
    
    public void setCoordinates (Coordinates coord, String type) {
    	try {coordinates = coord;}
    	catch (Exception e) 
    	{e.getMessage();}
    }
    

    public void setCreationDate (LocalDateTime date, String type) {
    	creationDate = date;
    }
    
    public void setMinimalPoint (String value, String type) {
    	try {minimalPoint = check.checkInt(value, 0, Integer.MAX_VALUE);} 
    	catch (BadValueException e) 
    	{e.message(type, id,"minimalPoint");}
    }
    
    public void setPersonalQualitiesMaximum (String value, String type) {
    	try {personalQualitiesMaximum =  check.checkLong(value, 0L, Long.MAX_VALUE, true);} 
    	catch (BadValueException e) 
    	{e.message(type, id,"personalQualitiesMaximum");}
    }
    
    public void setDifficulty (String value, String type) {
    	try {difficulty = check.checkEnum(value, Difficulty.class, true);} 
    	catch (BadValueException e) 
    	{e.message(type, id,"difficulty");}
    }
    
    public void setDiscipline (Discipline dis, String type) {
    	try {discipline = dis;}
    	catch (Exception e) 
    	{e.getMessage();}
    }

	public Long getId() {
		return id;
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

	public ArrayList<Long> getUsingId() {
		return usingId;
	}

	public int compareTo(LabWork o) {
		return this.getMinimalPoint() - o.getMinimalPoint();
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
	}

	public Discipline getDiscipline() {
    	return discipline;
	}
}

