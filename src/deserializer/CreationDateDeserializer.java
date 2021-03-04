package deserializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class CreationDateDeserializer implements JsonDeserializer<LocalDateTime> {

    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String year = jsonObject.get("year").getAsString();
        String month = LPad(jsonObject.get("month").getAsString());
        String date = LPad(jsonObject.get("day").getAsString());
        String hour = LPad(jsonObject.get("hour").getAsString());
        String minutes = LPad(jsonObject.get("minutes").getAsString());
        String second = LPad(jsonObject.get("second").getAsString());
        String result = String.format("%s-%s-%sT%s:%s:%s", year, month, date, hour, minutes, second);
        LocalDateTime time = LocalDateTime.parse(result);
        return time;
    }

    public static String LPad(String str) {
        return String.format("%2s", str).replaceAll(" ", "0");
    }
}
