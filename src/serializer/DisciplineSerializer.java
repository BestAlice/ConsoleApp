package serializer;

import labwork_class.Discipline;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import static serializer.LabWorkSerializer.nullSerializer;

public class DisciplineSerializer implements JsonSerializer<Discipline>
{   public JsonElement serialize(Discipline src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();
        result.addProperty("name", nullSerializer(src.getName()));
        result.addProperty("practiceHours", nullSerializer(String.valueOf(src.getPracticeHours())));
        return result;
    }
}