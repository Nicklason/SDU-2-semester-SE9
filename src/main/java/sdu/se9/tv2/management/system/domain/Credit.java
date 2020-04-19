package sdu.se9.tv2.management.system.domain;

import org.json.simple.JSONObject;

public class Credit {
    private int id;
    private String role;
    private int personID;
    private int programID;

    public Credit(int id, int programID, int personID, String role) {
        this.id = id;
        this.role = role;
        this.personID = personID;
        this.programID = programID;
    }

    public int getID() {
        return id;
    }

    public int getProgramID() {
        return programID;
    }

    public String getRole() {
        return role;
    }

    public int getPersonID() {
        return personID;
    }

    public static Credit parseJSON(JSONObject credit) {
        int id = Math.toIntExact((Long)credit.get("id"));
        int programID = Math.toIntExact((Long)credit.get("programID"));
        int personID = Math.toIntExact((Long)credit.get("personID"));
        String role = (String)credit.get("role");
        return new Credit(id, programID, personID, role);
    }

    public static JSONObject parseJSON(Credit credit) {
        // Create new json object
        JSONObject obj = new JSONObject();

        // Add properties to json object
        obj.put("id", credit.getID());
        obj.put("programID", credit.getProgramID());
        obj.put("personID", credit.getPersonID());
        obj.put("role", credit.getRole());

        // Return object
        return obj;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id='" + id + '\'' +
                ", programID='" + programID + '\'' +
                ", role='" + role + '\'' +
                ", personID=" + personID +
                '}';
    }
}
