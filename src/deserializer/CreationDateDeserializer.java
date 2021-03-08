package deserializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class CreationDateDeserializer implements JsonDeserializer<LocalDateTime> {

    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return LocalDateTime.now();
    }


}
