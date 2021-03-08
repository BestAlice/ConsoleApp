package deserializer;

import collection_control.BadValueException;
import labwork_class.Coordinates;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Scanner;

public class CoordinatesDeserializer implements JsonDeserializer<Coordinates> {

    public Coordinates deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Coordinates coords = new Coordinates("read", new Scanner(System.in));
        JsonObject jsonObject = json.getAsJsonObject();
        try {
            coords.setX(jsonObject.get("x").getAsString(), "read");
            coords.setY(jsonObject.get("y").getAsString(), "read");
        } catch (BadValueException e) {

        }

        return coords;}
}
