package collection_control;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import deserializer.*;
import labwork_class.Coordinates;
import labwork_class.Discipline;
import labwork_class.LabWork;
import serializer.*;

import java.io.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ParseJson {
    public static LinkedList<LabWork> parseFromJson(String varName) throws FileNotFoundException, UnsupportedEncodingException {
        Gson gson = new Gson();
        LinkedList<LabWork> LabList = new LinkedList<>();
        FileInputStream in;
        BufferedReader buffer;
        Reader text;
        String finalText = null;

        File fileWay = new File(System.getenv(varName));
        in = new FileInputStream(fileWay);
        text = new InputStreamReader(in, "UTF-8");
        buffer = new BufferedReader(text);
        finalText = buffer.lines().collect(Collectors.joining()).replaceAll("\t", "").replaceAll(" ", "").replaceAll("\n", "").trim();;
        gson = new GsonBuilder()
                .registerTypeAdapter(LinkedList.class, new LinkedListDeserializer())
                .registerTypeAdapter(LabWork.class, new LabWorkDeserializer())
                .create();
        LabList = gson.fromJson(finalText, LinkedList.class);
        LabList.sort(LabWork::compareTo);

        return LabList;
    }

    public static String parseToJson(LinkedList<LabWork> labList){
        Gson gson = new Gson();
        gson = new GsonBuilder()
                .registerTypeAdapter(LinkedList.class, new LinkedListSerializer())
                .registerTypeAdapter(LabWork.class, new LabWorkSerializer())
                .registerTypeAdapter(Coordinates.class, new CoordinatesSerializer())
                .registerTypeAdapter(Discipline.class, new DisciplineSerializer())
                .registerTypeAdapter(LocalDateTime.class, new CreationDateSerializer())
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(labList);
        return json;
    }
}
