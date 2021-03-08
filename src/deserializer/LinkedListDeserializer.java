package deserializer;

import collection_control.BadValueException;
import labwork_class.LabWork;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.LinkedList;

public class LinkedListDeserializer implements JsonDeserializer<LinkedList> {

	public LinkedList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
		LinkedList<LabWork> result = new LinkedList<>();
		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonList = jsonObject.get("list").getAsJsonArray();
		for (int i = 0; i < jsonList.size(); i++) {
			try {
				JsonObject newObject = jsonList.get(i).getAsJsonObject();
				LabWork nextLaba = context.deserialize(newObject, LabWork.class);
				result.add(nextLaba);
			} catch (JsonParseException e) {
				System.out.println("Ошибка поймана");
			} catch (NullPointerException e) {
				System.out.println(e.getMessage());
			}
		}
		return result;
		}
}
