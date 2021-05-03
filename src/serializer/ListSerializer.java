package serializer;

import labwork_class.LabWork;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class ListSerializer implements JsonSerializer<List<LabWork>> {

    public JsonElement serialize(List<LabWork> list, Type typeOfSrc, JsonSerializationContext context)
    {
    JsonObject result = new JsonObject();
    JsonArray labList = new JsonArray();
    for (LabWork lab: list) {
        labList.add(context.serialize(lab));
    };
    result.add("list", labList) ;
    return result;}
}
