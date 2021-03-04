package deserializer;

import collection_control.BadValueException;
import labwork_class.Coordinates;
import labwork_class.Discipline;
import labwork_class.LabWork;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class LabWorkDeserializer implements JsonDeserializer<LabWork> {

    public LabWork deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        LabWork laba = null;

        laba = new LabWork().newLab();
        JsonObject jsonObject = json.getAsJsonObject();
        laba.setName(jsonObject.get("name").getAsString(), "read");
        laba.setCoordinates((Coordinates) context.deserialize(jsonObject.get("coordinates"), Coordinates.class), "read");
        laba.setCreationDate((LocalDateTime) context.deserialize(jsonObject.get("creationDate"), LocalDateTime.class), "read");
        laba.setMinimalPoint(jsonObject.get("minimalPoint").getAsString(), "read");
        laba.setPersonalQualitiesMaximum(jsonObject.get("personalQualitiesMaximum").getAsString(), "read");
        laba.setDifficulty(jsonObject.get("difficulty").getAsString(), "read");
        if (jsonObject.get("discipline").toString().equals("\"null\"")) {laba.setDiscipline(null, "read");}
        else{laba.setDiscipline((Discipline) context.deserialize(jsonObject.get("discipline"), Discipline.class), "read");}
        return laba;
    }

    public static String nullDeserializer(String var) {
        if (var.equals("null")) {return null;}
        else {return var;}
    }
}
