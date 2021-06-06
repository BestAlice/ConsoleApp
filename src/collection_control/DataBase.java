package collection_control;

import appFiles.CommandPanel;
import labwork_class.Coordinates;
import labwork_class.Discipline;
import labwork_class.LabWork;
import server.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DataBase {
    private Connection connection;
    private Statement  stmt;
    private String sql;

    public DataBase() {
        try {
            System.out.println("Подключение к Базе Данных");
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:8085/labworks","postgres", "1369245780qwe");
            connection.setAutoCommit(false);
            System.out.println("Подключение прошло успешно");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
    }

    public List<LabWork> selectLabList() {
        System.out.println("Запрашиваю список LabWork");
        List<LabWork> LabList = new LinkedList<>();

        List<Long> id_List = this.selectAllLabWorkId();
        for (Long id : id_List) {
            LabList.add(this.selectLabWork(id));
        }
        System.out.println("Создание cписка LabWork прошло успешно");
        return LabList;
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
                dif = String.format("\'%s\'", laba.getDifficulty());
            } else {
                dif = null;
            }

            Long index = selectIndex("lab_id_iterator");
            laba.setId(index);

            sql = "INSERT INTO labworks VALUES (%d, '%s', %d, '%s', %d, %d, %s, %d, %d)";

            sql = String.format(sql,
                    laba.getId(),
                    laba.getName(),
                    laba.getCoordinates().getId(),
                    java.sql.Timestamp.valueOf(laba.getCreationDate()),
                    laba.getMinimalPoint(),
                    laba.getPersonalQualitiesMaximum(),
                    dif,
                    dis,
                    laba.getUserId()
                    );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на вставке Лабораторной");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }

    public LabWork selectLabWork (Long id) {
        LabWork laba = null;
        String name = null; //Поле не может быть null, Строка не может быть пустой
        Long coordinates_id = null; //Поле не может быть null
        LocalDateTime creationDate = null; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
        int minimalPoint; //Значение поля должно быть больше 0
        Long personalQualitiesMaximum = null; //Поле может быть null, Значение поля должно быть больше 0
        String difficulty = null; //Поле может быть null
        Long discipline_id = null; //Поле может быть null
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
                Coordinates coordinates = this.selectCoordinates(coordinates_id);
                laba.setCoordinates(coordinates, "read");
                laba.setCreationDate(creationDate, "read");
                laba.setMinimalPoint(String.valueOf(minimalPoint), "read");
                laba.setPersonalQualitiesMaximum(String.valueOf(personalQualitiesMaximum), "read");
                laba.setDifficulty(difficulty, "read");
                Discipline discipline = this.selectDiscipline(discipline_id);
                laba.setDiscipline(discipline, "read");
                laba.setUserId(rs.getLong("user_id"));

                User user = selectUser(laba.getUserId());
                laba.setUserName(user.getLogin());
            }
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на получении Лабораторной");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        } catch (BadValueException e) {
            System.out.println("Плохие значения в БД");
            System.out.println(e.getMessage());
        }
        return laba;
    }

    public void deteteLabWork (Long id) {
        try {
            LabWork laba = selectLabWork(id);
            deleteCoordinates(laba.getCoordinates().getId());
            if (laba.getDiscipline() != null){
                deleteDiscipline(laba.getDiscipline().getId());
            }
            sql = "DELETE FROM labworks WHERE id = %d;";
            sql = String.format(sql,
                    id
            );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на удалении лабораторной");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }

    public void updateLabWork (Long id, LabWork new_laba) {
        try {
            LabWork old_laba = selectLabWork(id);
            String dif;
            updateCoordinates(old_laba.getCoordinates().getId(), new_laba.getCoordinates());
            boolean oldHaveDiscipline = (old_laba.getDiscipline()!=null);
            boolean newHaveDiscipline = (new_laba.getDiscipline()!=null);
            Long discipline_id = null;
            if (oldHaveDiscipline & newHaveDiscipline) {
                updateDiscipline(old_laba.getDiscipline().getId(), new_laba.getDiscipline());
                discipline_id = old_laba.getDiscipline().getId();
            } else if (oldHaveDiscipline & !newHaveDiscipline) {
                deleteDiscipline(old_laba.getDiscipline().getId());
                discipline_id = null;
            } else if (!oldHaveDiscipline & newHaveDiscipline) {
                insertDiscipline(new_laba.getDiscipline());
                discipline_id = new_laba.getDiscipline().getId();
            } else if (!oldHaveDiscipline & !newHaveDiscipline) {
                discipline_id = null;
            }
            if (new_laba.getDifficulty() != null) {
                dif = String.format("\'%s\'", new_laba.getDifficulty());
            } else {
                dif = null;
            }

            sql = "update labworks set " +
                    "name = '%s', " +
                    "coordinates_id = %d,  " +
                    "creationdate = '%s', " +
                    "minimalpoint = %d, " +
                    "personalqualitiesmaximum = %d, " +
                    "difficulty = %s, " +
                    "discipline_id = %d, " +
                    "user_id = %d " +
                    "where id = %d;";
            sql = String.format(sql,
                    new_laba.getName(),
                    old_laba.getCoordinates().getId(),
                    java.sql.Timestamp.valueOf(new_laba.getCreationDate()),
                    new_laba.getMinimalPoint(),
                    new_laba.getPersonalQualitiesMaximum(),
                    dif,
                    discipline_id,
                    old_laba.getUserId(),
                    id
            );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на обновлении Лабораторной");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
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
            System.out.println("БД сломалась на вставке координат");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }

    public Coordinates selectCoordinates(Long id) {
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
                coord.setId(id);
                coord.setX(String.valueOf(x), "read");
                coord.setY(String.valueOf(y), "read");
            }
        } catch (SQLException e) {
            System.out.println("БД сломалась на получении координат");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        } catch (BadValueException e) {
            System.out.println("КАК, БЛЯТЬ, ТЫ СМОГ ЗАПИХНУТЬ В КОЛЛЕКЦИЮ ЭТУ ПЕРЕМЕННУЮ?");
        }

        return coord;
    }

    public void deleteCoordinates(Long id) {
        try {
            sql = "DELETE FROM coordinates WHERE id = %d;";
            sql = String.format(sql,
                    id
            );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на удалении координат");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }

    }

    public void updateCoordinates(Long id, Coordinates new_coordinates) {
        try {
            sql = "update coordinates set x = %d, y = %d where id = %d;";
            sql = String.format(sql,
                    new_coordinates.getX(),
                    new_coordinates.getY(),
                    id
            );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на обновлении координат");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
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
            System.out.println("БД сломалась на вставке дисциплины");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
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
                discipline.setId(id);
                discipline.setName(name, "read");
                discipline.setPracticeHours(String.valueOf(practiceHours), "read");
            }
        } catch (SQLException e) {
            System.out.println("БД сломалась на получении дисциплины");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        } catch (BadValueException e) {
            System.out.println("КАК, БЛЯТЬ, ТЫ СМОГ ЗАПИХНУТЬ В КОЛЛЕКЦИЮ ЭТУ ПЕРЕМЕННУЮ?");
        }
        return discipline;
    }

    public void deleteDiscipline(Long id) {
        try {
            sql = "DELETE FROM discipline WHERE id = %d;";
            sql = String.format(sql,
                    id
            );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на удалении дисциплины");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }

    public void updateDiscipline(Long id, Discipline new_discipline) {
        try {
            sql = "update discipline set name = '%s', practiceHours = %d where id = %d;";
            sql = String.format(sql,
                    new_discipline.getName(),
                    new_discipline.getPracticeHours(),
                    id
            );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на обновлении дисциплины");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }

    public boolean insertUser(User user){
        try{
            User check_user = selectUser(user.getLogin(), user.getPassword());
            if (check_user != null) {
                return false;
            }
            Long index = selectIndex("user_id_iterator");
            user.setId(index);

            sql = "INSERT INTO users(id, login, password) VALUES (%d, '%s', '%s')";
            sql = String.format(sql,
                    user.getId(),
                    user.getLogin(),
                    user.getPassword()
            );
            stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
        } catch (SQLException e) {
            System.out.println("БД сломалась на добавлении пользователя");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return true;
    }

    public User selectUser(String login, String password) {
        User user = null;
        try{
            sql = "select * FROM users WHERE login = '%s' and password = '%s' ;";
            sql = String.format(sql,
                    login,
                    password);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                stmt.close();
                connection.commit();
                return user;
            } else {
                stmt.close();
                connection.commit();
                return null;
            }

        } catch (SQLException e) {
            System.out.println("БД сломалась на получении пользователя");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return user;
    }

    public User selectUser(Long id) {
        User user = null;
        try{
            sql = "select * FROM users WHERE id = %d ;";
            sql = String.format(sql,
                    id);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new User();
                user.setId(rs.getLong("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                stmt.close();
                connection.commit();
                return user;
            } else {
                stmt.close();
                connection.commit();
                return null;
            }

        } catch (SQLException e) {
            System.out.println("БД сломалась на получении пользователя");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return user;
    }

    public List<Long> selectAllLabWorkId() {
        List<Long> id_List = new LinkedList<>();
        try{
            sql = "select id from labworks;";
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                id_List.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            System.out.println("БД сломалась на координатах");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return id_List;
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
            System.out.println("БД сломалась на получении индекса");
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
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
            System.out.println("Итератор lab_id_iterator создан");
        } catch (SQLException e) {
            System.out.println("Итератор lab_id_iterator уже существует");
        }

        try{
            Statement stmt = connection.createStatement();
            sql = "create sequence user_id_iterator start with 1 increment by 1;";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            System.out.println("Итератор user_id_iterator создан");
        } catch (SQLException e) {
            System.out.println("Итератор user_id_iterator уже существует");
        }
        try{
            Statement stmt = connection.createStatement();
            sql = "create sequence coordinates_id_iterator start with 1 increment by 1;";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            System.out.println("Итератор coordinates_id_iterator создан");
        } catch (SQLException e) {
            System.out.println("Итератор coordinates_id_iterator уже существует");
        }
        try{
            Statement stmt = connection.createStatement();
            sql = "create sequence discipline_id_iterator start with 1 increment by 1;";
            stmt.executeUpdate(sql);
            stmt.close();
            connection.commit();
            System.out.println("Итератор discipline_id_iterator создан");
        } catch (SQLException e) {
            System.out.println("Итератор discipline_id_iterator уже существует");
        }
    }
}
