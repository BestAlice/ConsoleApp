package serializer;

import labwork_class.LabWork;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import com.google.gson.*;


public class LabWorkSerializer implements JsonSerializer<LabWork>
{

    public JsonElement serialize(LabWork src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();

        result.addProperty("name", src.getName());
        result.add("coordinates", context.serialize(src.getCoordinates()));
        result.add("creationDate", context.serialize(src.getCreationDate()));
        result.addProperty("minimalPoint", String.valueOf(src.getMinimalPoint()));
        result.addProperty("personalQualitiesMaximum", String.valueOf(src.getPersonalQualitiesMaximum()));
        result.addProperty("difficulty", nullSerializer(src.getDifficulty()));

        if (src.getDiscipline() == null) {
            result.addProperty("discipline", "null");
        } else {
            result.add("discipline", context.serialize(src.getDiscipline()));
        }

        return result;
    }

    public static String nullSerializer(Object var) {
        if (var == null) {return "null";}
        else {return var.toString();}
    }
}