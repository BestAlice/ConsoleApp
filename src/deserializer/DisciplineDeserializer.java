package deserializer;

import labwork_class.Discipline;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Scanner;

public class DisciplineDeserializer implements JsonDeserializer<Discipline> {

    public Discipline deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Discipline lesson = new Discipline("read", new Scanner(System.in));
        JsonObject jsonObject = json.getAsJsonObject();
        lesson.setName(jsonObject.get("name").getAsString(), "read");
        lesson.setPracticeHours(jsonObject.get("practiceHours").getAsString(), "read");
        return lesson;
    }


}
