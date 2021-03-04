package serializer;

import labwork_class.Coordinates;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;


public class CoordinatesSerializer implements JsonSerializer<Coordinates>
{

    public JsonElement serialize(Coordinates src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();
        result.addProperty("x", String.valueOf(src.getX()));
        result.addProperty("y", String.valueOf(src.getY()));
        return result;
    }
}