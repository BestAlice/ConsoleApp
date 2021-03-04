package serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class CreationDateSerializer implements JsonSerializer<LocalDateTime>
{

    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject result = new JsonObject();
        result.addProperty("year", String.valueOf(src.getYear()));
        result.addProperty("month", String.valueOf(src.getMonthValue()));
        result.addProperty("day", String.valueOf(src.getDayOfMonth()));
        result.addProperty("hour", String.valueOf(src.getHour()));
        result.addProperty("minutes", String.valueOf(src.getMinute()));
        result.addProperty("second", String.valueOf(src.getSecond()));
        return result;
    }
}
