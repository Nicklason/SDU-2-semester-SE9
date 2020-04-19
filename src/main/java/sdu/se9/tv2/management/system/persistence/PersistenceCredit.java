package sdu.se9.tv2.management.system.persistence;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import sdu.se9.tv2.management.system.domain.Credit;

import java.util.ArrayList;
import java.util.Iterator;

public class PersistenceCredit implements IPersistenceCredit {

    private Persistence persistence = new Persistence("credit.json");

    private int lastID = -1;

    private ArrayList<Credit> credits = new ArrayList<Credit>();

    public PersistenceCredit() {
        this.read();
    }

    @Override
    public Credit createCredit(int programID, int personID, String roleName) {
        lastID++;
        Credit newCredit = new Credit(lastID, programID, personID, roleName);
        credits.add(newCredit);

        // Save changes to file

        this.write();

        return newCredit;
    }

    @Override
    public ArrayList<Credit> getCredits(int programID) {
        ArrayList<Credit> result = new ArrayList<Credit>();

        for (int i = 0; i < this.credits.size(); i++) {
            Credit element = this.credits.get(i);

            if (element.getProgramID() == programID) {
                result.add(element);
            }
        }

        return result;
    }

    /**
     * Get credits a person has for a program
     * @param programID Program id of the program
     * @param personID Person id of the person
     * @return
     */
    public ArrayList<Credit> getCredits(int programID, int personID) {
        ArrayList<Credit> result = new ArrayList<Credit>();

        for (int i = 0; i < this.credits.size(); i++) {
            Credit element = this.credits.get(i);

            if (element.getProgramID() == programID && element.getPersonID() == personID) {
                result.add(element);
            }
        }

        return result;
    }

    public Credit getCredit (int programID, int personID, String roleName) {
        for (int i = 0; i < this.credits.size(); i++) {
            Credit element = this.credits.get(i);

            if (element.getProgramID() == programID && element.getPersonID() == personID && element.getRole().equals(roleName)) {
                return element;
            }
        }

        return null;
    }

    private void read() {
        JSONObject obj = null;

        // Try and read the file
        try {
            obj = this.persistence.read();
        } catch (ParseException err) {
            err.printStackTrace();
        }

        if (obj != null) {
            // The file exists and has correct formatting, get lastID and list of producers

            // obj.get("lastID") returns Long
            this.lastID = Math.toIntExact((Long)obj.get("lastID"));

            JSONArray objList = (JSONArray)obj.get("list");

            ArrayList<Credit> parsedList = new ArrayList<Credit>();

            // Parse producer list
            Iterator<JSONObject> iterator = objList.iterator();
            while (iterator.hasNext()) {
                // Get element of the list array
                JSONObject element = iterator.next();
                // Parse the JSONObject using Producer.parseJSON
                parsedList.add(Credit.parseJSON(element));
            }

            // Set producer list
            credits = parsedList;
        }
    }

    private void write() {
        // Create JSONObject to save
        JSONObject obj = new JSONObject();

        // JSONArray that contains the producers
        JSONArray list = new JSONArray();

        // Go through producer list and parse as JSON objects
        for (int i = 0; i < this.credits.size(); i++) {
            list.add(Credit.parseJSON(this.credits.get(i)));
        }

        // Add lastID to JSON object
        obj.put("lastID", lastID);
        // Add list to JSON object
        obj.put("list", list);

        // Overwrite the file with new JSON object
        this.persistence.write(obj);
    }
}

