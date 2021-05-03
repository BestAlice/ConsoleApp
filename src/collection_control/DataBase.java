package collection_control;

import labwork_class.Coordinates;
import labwork_class.Difficulty;
import labwork_class.Discipline;
import labwork_class.LabWork;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.sql.Date;
import java.util.Scanner;

public class DataBase {
    private Connection connection;
    private Statement  stmt;
    private String sql;

    public DataBase() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/LabWorks","postgres", "1369245780qwe");
            connection.setAutoCommit(false);
            System.out.println("-- Opened database successfully");
            /*
            stmt = connection.createStatement();
            sql = "create sequence id_iterator start with 1 increment by 1;";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
             */
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public void insertLabWork(LabWork laba){
        try{
             //(nextval('lab_id_iterator'))
            Long dis;
            String dif;
            this.insertCoordinates(laba.getCoordinates());
            if (laba.getDiscipline() != null) {
                this.insertDiscipline(laba.getDiscipline());
                dis = laba.getDiscipline().getId();
            } else {
                dis = null;
            }

            if (laba.getDifficulty() != null) {
                dif = String.format("'%s'", laba.getDifficulty());
            } else {
                dif = null;
            }

            Long index = selectIndex("lab_id_iterator");
            laba.setId(index);
            if (dif != null) {
                sql = "INSERT INTO labworks VALUES (%d, '%s', %d, '%s', %d, %d, '%s', %d)";
            } else {
                sql = "INSERT INTO labworks VALUES (%d, '%s', %d, '%s', %d, %d, %s, %d)";
            }

            sql = String.format(sql,
                    laba.getId(),
                    laba.getName(),
                    laba.getCoordinates().getId(),
                    java.sql.Timestamp.valueOf(laba.getCreationDate()),
                    laba.getMinimalPoint(),
                    laba.getPersonalQualitiesMaximum(),
                    laba.getDifficulty(),
                    dis
                    );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("�� ��������� �� ����");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public LabWork selectLabWork (Long id) {
        LabWork laba = null;
        String name = null; //���� �� ����� ���� null, ������ �� ����� ���� ������
        Long coordinates_id = null; //���� �� ����� ���� null
        LocalDateTime creationDate = null; //���� �� ����� ���� null, �������� ����� ���� ������ �������������� �������������
        int minimalPoint; //�������� ���� ������ ���� ������ 0
        Long personalQualitiesMaximum = null; //���� ����� ���� null, �������� ���� ������ ���� ������ 0
        String difficulty = null; //���� ����� ���� null
        Long discipline_id = null; //���� ����� ���� null
        try{
            sql = "select * FROM labworks WHERE id = %d;";
            sql = String.format(sql,
                    id);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                name = rs.getString("name");
                coordinates_id = rs.getLong("coordinates_id");
                creationDate = rs.getTimestamp("creationdate").toLocalDateTime();
                minimalPoint = rs.getInt("minimalPoint");
                personalQualitiesMaximum = rs.getLong("personalQualitiesMaximum");
                if (rs.getString("difficulty") != null) {
                    difficulty = rs.getString("difficulty");
                }
                discipline_id = rs.getLong("discipline_id");

                laba = new LabWork();
                laba.setId(id);
                laba.setName(name, "read");
                Coordinates coordinates = this.selectCoodinates(coordinates_id);
                laba.setCoordinates(coordinates, "read");
                laba.setCreationDate(creationDate, "read");
                laba.setMinimalPoint(String.valueOf(minimalPoint), "read");
                laba.setPersonalQualitiesMaximum(String.valueOf(personalQualitiesMaximum), "read");
                laba.setDifficulty(difficulty, "read");
                Discipline discipline = this.selectDiscipline(discipline_id);
                laba.setDiscipline(discipline, "read");


            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("�� ��������� �� ����");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } catch (BadValueException e) {
            System.out.println("������ �������� � ��");
            System.out.println(e.getMessage());
        }
        return laba;
    }

    public void insertCoordinates(Coordinates coordinates) {
        try{
            Long index = selectIndex("coordinates_id_iterator");
            coordinates.setId(index);

            sql = "INSERT INTO coordinates(id, x, y) VALUES (%d, %d, %d)";
            sql = String.format(sql,
                    coordinates.getId(),
                    coordinates.getX(),
                    coordinates.getY()
                    );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("�� ��������� �� �����������");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public Coordinates selectCoodinates(Long id) {
        Coordinates coord = null;
        Long x = null;
        Long y = null;
        try{
            sql = "select * FROM coordinates WHERE id = %d;";
            sql = String.format(sql,
                    id);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                x = rs.getLong("x");
                y = rs.getLong("y");
            }
            stmt.close();
            connection.commit();
            if (y != null){
                coord = new Coordinates("read", new Scanner(System.in));
                coord.setX(String.valueOf(x), "read");
                coord.setY(String.valueOf(y), "read");
            }
        } catch (SQLException e) {
            System.out.println("�� ��������� �� �����������");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } catch (BadValueException e) {
            System.out.println("���, �����, �� ���� ��������� � ��������� ��� ����������?");
        }

        return coord;
    }

    public void insertDiscipline(Discipline discipline) {
        try{
            Long index = selectIndex("discipline_id_iterator");
            discipline.setId(index);

            sql = "INSERT INTO discipline(id, name, practiceHours) VALUES (%d, '%s', %d)";
            sql = String.format(sql,
                    discipline.getId(),
                    discipline.getName(),
                    discipline.getPracticeHours()
                    );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("�� ��������� �� ����������");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public  Discipline selectDiscipline(Long id) {
        Discipline discipline = null;
        String name = null;
        Long practiceHours = null;
        try{
            sql = "select * FROM discipline WHERE id = %d;";
            sql = String.format(sql,
                    id);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                name = rs.getString("name");
                practiceHours = rs.getLong("practiceHours");
            }
            stmt.close();
            connection.commit();
            if (practiceHours != null){
                discipline = new Discipline("read", new Scanner(System.in));
                discipline.setName(name, "read");
                discipline.setPracticeHours(String.valueOf(practiceHours), "read");
            }
        } catch (SQLException e) {
            System.out.println("�� ��������� �� ����������");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } catch (BadValueException e) {
            System.out.println("���, �����, �� ���� ��������� � ��������� ��� ����������?");
        }
        return discipline;
    }

    public void TestDatabase() {
        try {

            sql = "INSERT INTO coordinates(id, x, y) VALUES (1, 1, 1)";
            stmt = connection.prepareStatement(sql);
            //sql = "INSERT INTO labworks (id,name,coordinates_id,creationdate,minimalpoint,personalqualitiesmaximum,difficulty,discipline_id) " +
            //        "VALUES (1, 'Paul', 1, TO_TIMESTAMP('2014-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF'), 4, 'EASY', 3);";
            stmt.executeUpdate(sql);

            stmt.close();
            connection.commit();
            System.out.println("-- Records created successfully");
            /*
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM COMPANY;" );
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                String  address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println(String.format("ID=%s NAME=%s AGE=%s ADDRESS=%s SALARY=%s",id,name,age,address,salary));
            }
            rs.close();
            stmt.close();
            connection.commit();
            System.out.println("-- Operation SELECT done successfully");

             */


        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("-- All Operations done successfully");
    }



    public Long selectIndex(String iterator){
        Long index = null;
        try{
            sql = "select nextval('%s');";
            sql = String.format(sql,
                    iterator);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                index = rs.getLong(1);
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("�� ��������� �� ��������� �������");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return index;
    }

    public void createIterators() {
        try{
            Statement stmt = connection.createStatement();
            sql = "create sequence lab_id_iterator start with 1 increment by 1;";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            System.out.println("�������� lab_id_iterator ������");
        } catch (SQLException e) {
            System.out.println("�������� lab_id_iterator ��� ����������");
        }

        try{
            Statement stmt = connection.createStatement();
            sql = "create sequence user_id_iterator start with 1 increment by 1;";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            System.out.println("�������� user_id_iterator ������");
        } catch (SQLException e) {
            System.out.println("�������� user_id_iterator ��� ����������");
        }
        try{
            Statement stmt = connection.createStatement();
            sql = "create sequence coordinates_id_iterator start with 1 increment by 1;";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            System.out.println("�������� coordinates_id_iterator ������");
        } catch (SQLException e) {
            System.out.println("�������� coordinates_id_iterator ��� ����������");
        }
        try{
            Statement stmt = connection.createStatement();
            sql = "create sequence discipline_id_iterator start with 1 increment by 1;";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            System.out.println("�������� discipline_id_iterator ������");
        } catch (SQLException e) {
            System.out.println("�������� discipline_id_iterator ��� ����������");
        }
    }
}
