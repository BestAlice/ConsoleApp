package collection_control;

import labwork_class.Difficulty;
import labwork_class.LabWork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MessageObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String login;
    private String password;

    private String command;
    private Long id;
    private LabWork laba;
    private Long personalQualitiesMaximum;
    private Difficulty dif;
    private transient boolean ready = false;
    private ArrayList<String> messages = new ArrayList<>();
    private ArrayList<Long> usingId;
    private ArrayList<HashMap> tableMap = new ArrayList<>();


    public String getCommand(){
        return command;
    }

    public Long getId(){
        return id;
    }
    public Long getPersonalQualitiesMaximum(){
        return personalQualitiesMaximum;
    }

    public LabWork getLaba(){
        return laba;
    }

    public Difficulty getDif(){
        return dif;
    }

    public Boolean getReady(){
        return ready;
    }

    public void setReady(){
        ready = true;
    }
    public void setCommand(String command){
        this.command = command;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setPersonalQualitiesMaximum(Long personalQualitiesMaximum){
        this.personalQualitiesMaximum = personalQualitiesMaximum;
    }

    public void setLaba(LabWork laba){
        this.laba = laba;
    }

    public void setDif(Difficulty dif){
        this.dif = dif;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        this.messages.add(message);
    }

    public ArrayList<Long> getUsingId() {
        return usingId;
    }

    public void setUsingId(ArrayList<Long> usingId) {
        this.usingId = usingId;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void addMap(HashMap<String, Object> newMap) {
        tableMap.add(newMap);
    }

    public ArrayList<HashMap> getTableMap(){
        return tableMap;
    }
}
