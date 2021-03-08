package deserializer;

import collection_control.BadValueException;
import labwork_class.Coordinates;
import labwork_class.Discipline;
import labwork_class.LabWork;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Scanner;

public class LabWorkDeserializer implements JsonDeserializer<LabWork> {

    public LabWork deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException, NullPointerException {
        LabWork laba = null;


        try {
            laba = new LabWork().newLab();
            JsonObject jsonObject = json.getAsJsonObject();
            laba.setName(jsonObject.get("name").getAsString(), "read");
            laba.setCoordinates(parseCoord(jsonObject.get("coordinates").getAsJsonObject()), "read");
            laba.setCreationDate(parseCreationDate(jsonObject.get("creationDate").getAsJsonObject()), "read");
            laba.setMinimalPoint(jsonObject.get("minimalPoint").getAsString(), "read");
            laba.setPersonalQualitiesMaximum(jsonObject.get("personalQualitiesMaximum").getAsString(), "read");
            laba.setDifficulty(jsonObject.get("difficulty").getAsString(), "read");
            if (jsonObject.get("discipline").toString().equals("\"null\"")) {
                laba.setDiscipline(null, "read");
            } else {
                laba.setDiscipline(parseDiscipline(jsonObject.get("discipline").getAsJsonObject()),"read");
            }
        } catch (JsonParseException e) {
            System.out.println(e.getMessage());
        } catch (BadValueException e) {
            e.message ("read", laba.getId(), laba.getName());
            System.out.println("Мы нашли ошибку");
            throw new JsonParseException("");
        } catch (NullPointerException e) {
            String message =String.format("Для элемента с name \'%s\' не обнаружен один из параметров\n", laba.getName());
            throw new NullPointerException(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return laba;
    }

    public Discipline parseDiscipline(JsonObject json) throws BadValueException, NullPointerException{
        JsonObject jsonLesson = json;
        Discipline lesson = new Discipline("read", new Scanner(System.in));
        lesson.setName(jsonLesson.get("name").getAsString(), "read");
        lesson.setPracticeHours(jsonLesson.get("practiceHours").getAsString(), "read");
        return lesson;
    }

    public Coordinates parseCoord(JsonObject json) throws BadValueException, NullPointerException{
        JsonObject jsonCoord = json;
        Coordinates coords = new Coordinates("read", new Scanner(System.in));
        coords.setX(jsonCoord.get("x").getAsString(), "read");
        coords.setY(jsonCoord.get("y").getAsString(), "read");
        return coords;
    }

    public LocalDateTime parseCreationDate(JsonObject json)throws NullPointerException{
        JsonObject jsonDate = json;
        LocalDateTime time = null;

        String year = jsonDate.get("year").getAsString();
        String month = LPad(jsonDate.get("month").getAsString());
        String date = LPad(jsonDate.get("day").getAsString());
        String hour = LPad(jsonDate.get("hour").getAsString());
        String minutes = LPad(jsonDate.get("minutes").getAsString());
        String second = LPad(jsonDate.get("second").getAsString());
        String result = String.format("%s-%s-%sT%s:%s:%s", year, month, date, hour, minutes, second);
        time = LocalDateTime.parse(result);

        return time;
    }

    public static String nullDeserializer(String var) {
        if (var.equals("null")) {return null;}
        else {return var;}
    }

    public static String LPad(String str) {
        return String.format("%2s", str).replaceAll(" ", "0");
    }
}
